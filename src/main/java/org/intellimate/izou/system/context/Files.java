package org.intellimate.izou.system.context;

import org.intellimate.izou.identification.Identification;
import org.intellimate.izou.identification.IllegalIDException;
import org.intellimate.izou.system.file.FileSubscriber;
import org.intellimate.izou.system.file.ReloadableFile;
import ro.fortsoft.pf4j.AddonAccessible;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Leander Kurscheidt
 * @version 1.0
 */
@AddonAccessible
public interface Files {
    /**
     * Use this method to register a file with the watcherService
     *
     * @param dir directory of file
     * @param fileType the name/extension of the file
     *                 IMPORTANT: Please try to always enter the full name with extension of the file (Ex: "test.txt"),
     *                 it would be best if the fileType is the full file name, and that the file name is clearly
     *                 distinguishable from other files.
     *                 For example, the property files are stored with the ID of the addon they belong too. That way
     *                 every property file is easily distinguishable.
     * @param reloadableFile object of interface that file belongs to
     * @throws java.io.IOException exception thrown by watcher service
     */
    void registerFileDir(Path dir, String fileType, ReloadableFile reloadableFile) throws IOException;

    /**
     * Writes default file to real file
     * The default file would be a file that can be packaged along with the code, from which a real file (say a
     * properties file for example) can be loaded. This is useful because there are files (like property files0 that
     * cannot be shipped with the package and have to be created at runtime. To still be able to fill these files, you
     * can create a default file (usually txt) from which the content, as mentioned above, can then be loaded into the
     * real file.
     *
     * @param defaultFilePath path to default file (or where it should be created)
     * @param realFilePath path to real file (that should be filled with content of default file)
     * @return true if operation has succeeded, else false
     */
    boolean writeToFile(String defaultFilePath, String realFilePath);

    /**
     * Creates a default File in case it does not exist yet. Default files can be used to load other files that are
     * created at runtime (like properties file)
     *
     * @param defaultFilePath path to default file.txt (or where it should be created)
     * @param initMessage the string to write in default file
     * @throws java.io.IOException is thrown by bufferedWriter
     */
    void createDefaultFile(String defaultFilePath, String initMessage) throws IOException;

    /**
     * Registers a {@link FileSubscriber} with a {@link ReloadableFile}. So when the {@code reloadableFile} is
     * reloaded, the fileSubscriber will be notified. Multiple file subscribers can be registered with the same
     * reloadable file.
     *
     * @param reloadableFile the reloadable file that should be observed
     * @param fileSubscriber the fileSubscriber that should be notified when the reloadable file is reloaded
     * @param identification the Identification of the requesting instance
     * @throws IllegalIDException not yet implemented
     */
    void register(ReloadableFile reloadableFile, FileSubscriber fileSubscriber, Identification identification) throws IllegalIDException;

    /**
     * Registers a {@link FileSubscriber} so that whenever any file is reloaded, the fileSubscriber is notified.
     *
     * @param fileSubscriber the fileSubscriber that should be notified when the reloadable file is reloaded
     * @param identification the Identification of the requesting instance
     * @throws IllegalIDException not yet implemented
     */
    void register(FileSubscriber fileSubscriber, Identification identification) throws IllegalIDException;

    /**
     * Unregisters all instances of fileSubscriber found.
     *
     * @param fileSubscriber the fileSubscriber to unregister
     */
    void unregister(FileSubscriber fileSubscriber);

    /**
     * gets the File pointing towards the location of the lib-folder
     * @return the File
     */
    File getLibLocation();

    /**
     * gets the File pointing towards the location of the resource-folder
     * @return the File
     */
    File getResourceLocation();

    /**
     * gets the File pointing towards the location of the properties-folder
     * @return the File
     */
    File getPropertiesLocation();

    /**
     * gets the File pointing towards the location of the logs-folder
     * @return the File
     */
    File getLogsLocation();
}
