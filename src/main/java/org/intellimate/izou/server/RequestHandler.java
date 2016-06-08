package org.intellimate.izou.server;

import com.google.common.io.ByteStreams;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.intellimate.izou.addon.AddOnModel;
import org.intellimate.izou.config.AddOn;
import org.intellimate.izou.identification.AddOnInformation;
import org.intellimate.izou.identification.AddOnInformationManager;
import org.intellimate.izou.main.Main;
import org.intellimate.izou.main.UpdateManager;
import org.intellimate.izou.util.AddonThreadPoolUser;
import org.intellimate.izou.util.IzouModule;
import org.intellimate.server.proto.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author LeanderK
 * @version 1.0
 */
//TODO new status for update errored?
class RequestHandler extends IzouModule implements AddonThreadPoolUser {
    private static JsonFormat.Printer PRINTER = JsonFormat.printer().includingDefaultValueFields();
    private static JsonFormat.Parser PARSER = JsonFormat.parser();
    private CompletableFuture<Void> updateFuture;


    public RequestHandler(Main main) {
        super(main);
    }

    Response handleRequests(Request request) {
        if (request.getUrl().startsWith("/apps")) {
            if (getMain().getState().equals(IzouInstanceStatus.Status.DISABLED)) {
                return sendErrorDisabled();
            }
            return handleApps(request);
        } else if (request.getUrl().equals("/status")) {
            return handleStatus(request);
        }
        return sendNotFound("illegal request, no suitable route found");
    }

    private Response handleApps(Request request) {
        String url = request.getUrl();
        Pattern serverIDPattern = Pattern.compile("/apps/(?<id>\\d+)(/.*)?");
        Matcher serverIDMatcher = serverIDPattern.matcher(url);
        if (url.equals("/apps")) {
            return handleListApps(request);
        } else if (serverIDMatcher.matches()) {
            return handleAddonHTTPRequest(request, serverIDMatcher, id -> getMain().getAddOnInformationManager().getAddOn(Integer.parseInt(id)));
        } else if (url.matches("/apps/dev/\\w+(/.*)?")) {
            Pattern pattern = Pattern.compile("/apps/dev/(?<id>\\w+)(/.*)?");
            Matcher devIDMatcher = pattern.matcher(url);
            if (request.getMethod().equals("POST") && url.matches("/apps/dev/\\w+/\\d+/\\d+/\\d+")) {
                return saveLocalApp(request, url);
            } else if (request.getMethod().equals("GET") && devIDMatcher.matches()) {
                return handleAddonHTTPRequest(request, devIDMatcher, id -> getMain().getAddOnInformationManager().getAddOn(id));
            }
        }
        return sendNotFound("no suitable route found");
    }

    private Response saveLocalApp(Request request, String url) {
        Pattern pattern = Pattern.compile("/apps/dev/(?<id>\\w+)/(?<major>\\d+)/(?<minor>\\d+)/(?<patch>\\d+)");
        Matcher matcher = pattern.matcher(url);
        String name = matcher.group("id");
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));
        int patch = Integer.parseInt(matcher.group("patch"));
        AddOn addOn = new AddOn(name, major+"."+minor+"."+patch, -1);
        if (getMain().getAddOnInformationManager().getSelectedAddOns().stream()
                .noneMatch(selected -> selected.name.equals(addOn.name))) {
            try {
                getMain().getAddOnInformationManager().addAddonToSelectedList(addOn);
            } catch (IOException e) {
                error("unable to write to config file", e);
                return sendStringMessage("izou was unable to update the config file", 500);
            }
        }

        try {
            Files.list(getMain().getFileSystemManager().getNewLibLocation().toPath())
                    .map(Path::toFile)
                    .filter(file -> file.getName().matches("\\w+-[\\.\\d]+\\.zip"))
                    .filter(file -> file.getName().startsWith(addOn.name))
                    .forEach(file -> {
                        boolean deleted = file.delete();
                        if (!deleted) {
                            error("unable to delete downloaded version: " + file.toString());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(getMain().getFileSystemManager().getNewLibLocation(), name+"-"+major+"."+minor+"."+patch+".zip");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                error("unable to create File "+ file);
                return sendStringMessage("an internal error occured", 500);
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            long copied = ByteStreams.copy(request.getData(), fos);
            if (copied != request.getContentLength()) {
                file.delete();
                return sendStringMessage("Body size does not equal advertised size", 400);
            }
        } catch (IOException e) {
            error("unable to write to file", e);
            return sendStringMessage("an internal error occured", 500);
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                error("unable to cloe FileOutput", e);
            }
        }
        return sendStringMessage("OK", 201);
    }

    private Response handleListApps(Request request) {
        if (request.getMethod().equals("GET")) {
            return returnAddonsList();
        } else if (request.getMethod().equals("PATCH")) {
            Optional<String> json = null;
            try {
                json = request.getDataAsUTF8String();
            } catch (IOException e) {
                return sendBadRequest("unable to read request body");
            }
            if (json.isPresent()) {
                IzouAppList.Builder builder = IzouAppList.newBuilder();
                try {
                    PARSER.merge(json.get(), builder);
                } catch (InvalidProtocolBufferException e) {
                    error("unable to parse message", e);
                    return sendInternalServerError(e);
                }
                IzouAppList list = builder.build();
                List<AddOn> selected = list.getSelectedList().stream()
                        .map(this::toAddon)
                        .collect(Collectors.toList());
                try {
                    getMain().getAddOnInformationManager()
                            .setSelectedList(selected);
                } catch (IOException e) {
                    error("unable to to save config", e);
                    return sendInternalServerError(e);
                }
                return returnAddonsList();
            } else {
                return sendBadRequest("unable to read request body");
            }
        }
        return sendNotFound("illegal request, no suitable route found");
    }

    private Response returnAddonsList() {
        List<AddOnInformation> installed = null;
        List<AddOn> scheduledToDelete = null;
        List<AddOn> scheduledToInstall = null;
        try {
            installed = getMain().getAddOnManager().getInstalledWithDependencies();
            scheduledToDelete = getMain().getAddOnManager().getScheduledToDelete();
            scheduledToInstall = getMain().getAddOnManager().getScheduledToInstall();
        } catch (IOException e) {
            debug("unable to access App File-Directories", e);
            return sendStringMessage("unable to access App File-Directories", 500);
        }
        IzouAppList appList = IzouAppList.newBuilder()
                .addAllSelected(
                        getMain().getAddOnInformationManager().getSelectedAddOns().stream()
                                .map(this::toApp)
                                .collect(Collectors.toList())
                )
                .addAllInstalled(
                        installed.stream()
                                .map(this::toApp)
                                .collect(Collectors.toList())
                )
                .addAllToDelete(
                        scheduledToDelete.stream()
                                .map(this::toApp)
                                .collect(Collectors.toList())
                )
                .addAllToInstall(
                        scheduledToInstall.stream()
                                .map(this::toApp)
                                .collect(Collectors.toList())
                )
                .build();
        return sendMessage(appList, 200);
    }

    private App toApp(AddOn addOn) {
        App.Builder builder = App.newBuilder()
                .setName(addOn.name)
                .addVersions(App.AppVersion.newBuilder().setVersion(addOn.version));
        addOn.getId().ifPresent(builder::setId);
        return builder.build();
    }

    private App toApp(AddOnInformation addOn) {
        App.Builder builder = App.newBuilder()
                .setName(addOn.getArtifactID())
                .addVersions(App.AppVersion.newBuilder().setVersion(addOn.getVersion().toString()));
        addOn.getServerID().ifPresent(builder::setId);
        return builder.build();
    }

    private AddOn toAddon(App app) {
        int id = -1;
        if (app.hasField(app.getDescriptorForType().findFieldByNumber(App.ID_FIELD_NUMBER))) {
            id = app.getId();
        }
        return new AddOn(app.getName(), null, id);
    }

    private Response handleAddonHTTPRequest(Request httpRequest, Matcher matcher, Function<String, Optional<AddOnModel>> getAddon) {
        String id = matcher.group("id");
        Boolean sameApp = httpRequest.getParams().entrySet().stream()
                .filter(param -> param.getKey().equals("app"))
                .map(Map.Entry::getValue)
                .findAny()
                .map(list -> list.stream().filter(authorizedApp -> authorizedApp.equals(id)).findAny().isPresent())
                .orElse(true);

        Request request = httpRequest;
        if (!sameApp) {
            HashMap<String, List<String>> httpParams = new HashMap<>(httpRequest.getParams());
            httpParams.remove("token");
            request = ((RequestImpl) httpRequest).changeParams(httpParams);
        }
        Request finalRequest = request;

        Optional<AddOnModel> addOnModel = getAddon.apply(id);
        if (!addOnModel.isPresent()) {
            return sendNotFound("no local app found with id: "+id);
        }

        return addOnModel.map(addOnModelInstance -> submit(() -> Optional.ofNullable(addOnModelInstance.handleRequest(finalRequest))))
                .flatMap(future -> {
                    try {
                        return future.join();
                    } catch (Exception e) {
                        return Optional.of(handleException(e, "an internal server error occured", 500));
                    }
                })
                .orElseGet(() -> sendNotFound("Addon does not server route: "+finalRequest.getUrl()));
    }

    private Response handleStatus(Request request) {
        if (request.getMethod().equals("GET")) {
            IzouInstanceStatus.Status status = IzouInstanceStatus.Status.RUNNING;
            if (getMain().getUpdateManager().isPresent() && getMain().getUpdateManager().get().isUpdating()) {
                status = IzouInstanceStatus.Status.UPDATING;
            }
            if (getMain().getState().equals(IzouInstanceStatus.Status.DISABLED)) {
                status = IzouInstanceStatus.Status.DISABLED;
            }
            IzouInstanceStatus statusMessage = IzouInstanceStatus.newBuilder().setStatus(status).build();
            return sendMessage(statusMessage, 200);
        } else if (request.getMethod().equals("PATCH")) {
            String json = null;
            try {
                Optional<String> stringOpt = request.getDataAsUTF8String();
                if (stringOpt.isPresent()) {
                    json = stringOpt.get();
                } else {
                   return sendStringMessage("body-size does not match advertised size", 400);
                }
            } catch (IOException e) {
                return sendStringMessage("unable to read body", 500);
            }
            IzouInstanceStatus.Builder builder = IzouInstanceStatus.newBuilder();
            try {
                PARSER.merge(json, builder);
            } catch (InvalidProtocolBufferException e) {
                error("unable to parse message", e);
                return sendStringMessage("unable to parse message", 400);
            }
            if (getMain().getState().equals(IzouInstanceStatus.Status.DISABLED)) {
                if (builder.getStatus() == IzouInstanceStatus.Status.DISABLED) {
                    return sendMessage(builder.build(), 200);
                }
            }
            //TODO implement (beware of concurrency!)
            switch (builder.getStatus()) {
                case UNRECOGNIZED: return sendStringMessage("unable to parse message", 400);
                case UPDATING: return handleUpdateRequest();
                case RESTARTING: return sendStringMessage("not implemented yet", 501);
                case RUNNING: return handleRunningRequests();
                case DISABLED: return handleDisabledRequests();
            }
        }
        return sendNotFound("illegal request, no suitable route found");
    }

    private Response handleDisabledRequests() {
        if (!getMain().getState().equals(IzouInstanceStatus.Status.DISABLED)) {
            try {
                getMain().getAddOnInformationManager().setNewStateToConfig(IzouInstanceStatus.Status.DISABLED);
            } catch (IOException e) {
                error("unable to update config-file", e);
                return sendInternalServerError(e);
            }
            //TODO: more beautiful! but this is hard
            System.exit(0);
            IzouInstanceStatus status = IzouInstanceStatus.newBuilder().setStatus(IzouInstanceStatus.Status.DISABLED).build();
            return sendMessage(status, 200);
        } else {
            IzouInstanceStatus status = IzouInstanceStatus.newBuilder().setStatus(IzouInstanceStatus.Status.DISABLED).build();
            return sendMessage(status, 200);
        }
    }

    private Response handleRunningRequests() {
        if (getMain().getState().equals(IzouInstanceStatus.Status.DISABLED)) {
            try {
                getMain().getAddOnInformationManager().setNewStateToConfig(IzouInstanceStatus.Status.RUNNING);
            } catch (IOException e) {
                error("unable to update config-file", e);
                return sendInternalServerError(e);
            }
            //TODO: more beautiful! but this is hard
            System.exit(0);
            IzouInstanceStatus status = IzouInstanceStatus.newBuilder().setStatus(IzouInstanceStatus.Status.RUNNING).build();
            return sendMessage(status, 200);
        } else {
            IzouInstanceStatus status = IzouInstanceStatus.newBuilder().setStatus(IzouInstanceStatus.Status.RUNNING).build();
            return sendMessage(status, 200);
        }
    }

    private Response handleUpdateRequest() {
        Optional<UpdateManager> updatesManager = getMain().getUpdateManager();
        if (updatesManager.isPresent()) {
            IzouInstanceStatus statusMessage = IzouInstanceStatus.newBuilder().setStatus(IzouInstanceStatus.Status.UPDATING).build();
            if (!updatesManager.get().isUpdating()) {
                updateFuture = CompletableFuture.runAsync(() -> {
                    try {
                        updatesManager.get().checkForUpdates();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return sendMessage(statusMessage, 200);
        } else {
            return sendNotFound("communication to server is not active");
        }
    }

    private Response handleException(Throwable throwable, String message, int status) {
        ErrorResponse build = ErrorResponse.newBuilder()
                .setCode(message)
                .setDetail(throwable.getMessage())
                .build();
        debug(message, throwable);
        return sendMessage(build, status);
    }

    private Response sendErrorDisabled() {
        return sendBadRequest("not allowed during disabled-state");
    }

    private Response sendNotFound(String detail) {
        ErrorResponse errorResponse = ErrorResponse.newBuilder()
                .setCode("not found")
                .setDetail(detail)
                .build();
        return sendMessage(errorResponse, 404);
    }

    private Response sendInternalServerError(Throwable cause) {
        ErrorResponse errorResponse = ErrorResponse.newBuilder()
                .setCode("internal Server Error")
                .setDetail(cause.getMessage())
                .build();
        return sendMessage(errorResponse, 500);
    }

    private Response sendBadRequest(String detail) {
        ErrorResponse errorResponse = ErrorResponse.newBuilder()
                .setCode("bad request")
                .setDetail(detail)
                .build();
        return sendMessage(errorResponse, 400);
    }

    private Response sendMessage(Message message, int status) {
        try {
            return new ResponseImpl(status, new HashMap<>(), "application/json", PRINTER.print(message).getBytes(Charset.forName("UTF-8")));
        } catch (InvalidProtocolBufferException e) {
            error("unable to print message", e);
            return sendStringMessage("Izou: unable to print message in sendMessage", 500);
        }
    }

    private Response sendStringMessage(String message, int status) {
        return new ResponseImpl(status, new HashMap<>(), "text/plain", message.getBytes(Charset.forName("UTF-8")));
    }
}
