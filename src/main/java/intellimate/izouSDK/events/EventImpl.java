package intellimate.izouSDK.events;

import intellimate.izou.events.*;
import intellimate.izou.resource.ListResourceProvider;
import intellimate.izou.resource.Resource;
import intellimate.izou.identification.Identification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class represents an Event.
 * This class is immutable! for every change it will return an new instance!
 */
public class EventImpl implements Event {
    /**
     * Use this type when other AddOns should react to this Event.
     */
    public static final String RESPONSE = EventImpl.class.getCanonicalName() + "Response";
    /**
     * Use this type when other AddOns should just notice (they needn't).
     */
    public static final String NOTIFICATION = EventImpl.class.getCanonicalName() + "Notification";
    //common Events-Descriptors:
    /**
     * Event for a Welcome with maximum response.
     *
     * Every component that can contribute should contribute to this Event.
     * @deprecated see <a href="https://github.com/intellimate/Izou/wiki/Common-IDs">new online documentation</a>
     */
    @Deprecated
    public static final String FULL_WELCOME_EVENT = "izou.FullResponse";
    /**
     * Event for a Welcome with major response.
     *
     * Every component that is import should contribute to this Event.
     * @deprecated see <a href="https://github.com/intellimate/Izou/wiki/Common-IDs">new online documentation</a>
     */
    @Deprecated
    @SuppressWarnings("UnusedDeclaration")
    public static final String MAJOR_WELCOME_EVENT = "izou.MajorResponse";
    /**
     * Event for a Welcome with minor response.
     *
     * Only components that have information of great importance should contribute to this event.
     * @deprecated see <a href="https://github.com/intellimate/Izou/wiki/Common-IDs">new online documentation</a>
     */
    @Deprecated
    @SuppressWarnings("UnusedDeclaration")
    public static final String MINOR_WELCOME_EVENT = "izou.MinorResponse";
    private final String type;
    private final Identification source;
    private final List<String> descriptors;
    private final ListResourceProvider listResourceContainer = new ListResourceProvider();
    private final EventBehaviourController eventBehaviourController = new EventBehaviourControllerImpl(this);
    private final Logger fileLogger = LogManager.getLogger(this.getClass());

    /**
     * Creates a new Event Object
     * @param type the Type of the Event, try to use the predefined Event types
     * @param source the source of the Event, most likely a this reference.
     */
    private EventImpl(String type, Identification source, List<String> descriptors) {
        this.type = type;
        this.source = source;
        this.descriptors = descriptors;
    }

    /**
     * Creates a new Event Object
     * @param type the Type of the Event, try to use the predefined Event types
     * @param source the source of the Event, most likely a this reference.
     * @return an Optional, that may be empty if type is null or empty or source is null
     */
    public static Optional<Event> createEvent(String type, Identification source) {
        if(type == null || type.isEmpty()) return Optional.empty();
        if(source == null) return Optional.empty();
        return Optional.of(new EventImpl(type, source, new ArrayList<String>()));
    }

    /**
     * The ID of the Event.
     * It describes the Type of the Event.
     * @return A String containing an ID
     */
    @Override
    public String getID() {
    return type;
    }

    /**
     * The type of the Event.
     * It describes the Type of the Event.
     * @return A String containing an ID
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * returns the Source of the Event, e.g. the object who fired it.
     * @return an identifiable
     */
    @Override
    public Identification getSource() {
        return source;
    }

    /**
     * returns all the Resources the Event currently has
     * @return an instance of ListResourceContainer
     */
    @Override
    public ListResourceProvider getListResourceContainer() {
        return listResourceContainer;
    }

    /**
     * adds a Resource to the Container
     * @param resource an instance of the resource to add
     * @return the resulting Event (which is the same instance)
     */
    @Override
    public Event addResource(Resource resource) {
        listResourceContainer.addResource(resource);
        return this;
    }

    /**
     * adds a List of Resources to the Container
     * @param resources a list containing all the resources
     */
    @Override
    public Event addResources(List<Resource> resources) {
        listResourceContainer.addResource(resources);
        return this;
    }

    /**
     * returns a List containing all the Descriptors.
     * @return a List containing the Descriptors
     */
    @Override
    public List<String> getDescriptors() {
        return descriptors;
    }

    /**
     * returns a List containing all the Descriptors and the type.
     * @return a List containing the Descriptors
     */
    @Override
    public List<String> getAllIformations() {
        List<String> information = new LinkedList<>(descriptors);
        information.add(type);
        return information;
    }

    /**
     * sets the Descriptors (but not the Event-Type).
     * <p>
     * Replaces all existing descriptors.
     * Since Event is immutable, it will create a new Instance.
     * </p>
     * @param descriptors a List containing all the Descriptors
     * @return the resulting Event (which is the same instance)
     */
    public Event setDescriptors(List<String> descriptors) {
        return new EventImpl(getType(), getSource(), descriptors);
    }

    /**
     * sets the Descriptors (but not the Event-Type).
     * @param descriptor a String describing the Event.
     * @return the resulting Event (which is the same instance)
     */
    public Event addDescriptor(String descriptor) {
        List<String> newDescriptors = new ArrayList<>();
        newDescriptors.addAll(descriptors);
        newDescriptors.add(descriptor);
        return new EventImpl(getType(), getSource(), newDescriptors);
    }

    /**
     * replaces the Descriptors
     * @param descriptors a list containing the Descriptors.
     * @return the resulting Event (which is the same instance)
     */
    public Event replaceDescriptors(List<String> descriptors) {
        return new EventImpl(getType(), getSource(), descriptors);
    }

    /**
     * returns whether the event contains the specific descriptor.
     * this method also checks whether it matches the type.
     * @param descriptor a String with the ID of the Descriptor
     * @return boolean when the Event contains the descriptor, false when not.
     */
    @Override
    public boolean containsDescriptor(String descriptor) {
        return descriptors.contains(descriptor) || type.equals(descriptor);
    }

    /**
     * returns the associated EventBehaviourController
     * @return an instance of EventBehaviourController
     */
    @Override
    public EventBehaviourController getEventBehaviourController() {
        return eventBehaviourController;
    }

    /**
     * applies the consumer to the Event
     * <p>
     * can be used for logging.
     * </p>
     * @param consumer the consumer
     * @return this Event
     */
    public Event peek (Consumer<Event> consumer) {
        consumer.accept(this);
        return this;
    }

    /**
     * maps this event to T
     * @param function the function to map
     * @param <T> the return type
     * @return T
     */
    public <T> T map (Function<Event, T> function) {
        return function.apply(this);
    }

    /**
     * Tries to fire the Event.
     * <p>
     * if calling failed, it will call onError. If onError returns true, it will wait 100 milli-seconds an retries
     * firing. OnError will be called with the parameters: this Event and a counter which increments for every try.
     * If onError returns false, the MultipleEventsException will be thrown.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onError this method will be called when an error occurred
     * @deprecated use {@link #fire(intellimate.izou.events.EventCallable, java.util.function.BiFunction)} instead
     * @throws intellimate.izou.events.MultipleEventsException when the method fails to fire the event and onError
     *                              returns false
     */
    @Deprecated
    public void tryFire (EventCallable eventCallable, BiFunction<Event, Integer, Boolean> onError)
            throws MultipleEventsException {
        tryFire(eventCallable, onError, null);
    }

    /**
     * tries to fire the Event.
     * <p>
     * if calling failed, it will call onError. If onError returns true, it will wait 100 milli-seconds an retries
     * firing. OnError will be called with the parameters: this Event and a counter which increments for every try.
     * If onError returns false, the MultipleEventsException will be thrown.
     * if calling succeeded, it will call onSuccess.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onError this method will be called when an error occurred
     * @param onSuccess this method will be called when firing succeeded
     * @deprecated use {@link #fire(EventCallable, java.util.function.BiFunction, java.util.function.Consumer, java.util.function.Consumer)}
     *              instead
     * @throws intellimate.izou.events.MultipleEventsException when the method fails to fire the event and onError
     *                              returns false
     */
    @Deprecated
    public void tryFire (EventCallable eventCallable, BiFunction<Event, Integer, Boolean> onError,
                                                        Consumer<Event> onSuccess) throws MultipleEventsException {
        boolean success = false;
        int count = 0;
        while (!success) {
            try {
                eventCallable.fire(this);
                success = true;
            } catch (MultipleEventsException e) {
                count++;
                boolean retry = onError.apply(this, count);
                if (!retry)
                    throw e;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        onSuccess.accept(this);
    }

    /**
     * tries to fire the Event.
     * <p>
     * if calling failed, it will call onError. If onError returns true, it will wait 100 milli-seconds an retries
     * firing. OnError will be called with the parameters: this Event and a counter which increments for every try.
     * If onError returns false, the method will return.
     * if calling failed (onError returns false), it will call onFailure.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onError this method will be called when an error occurred
     * @param onFailure this method will be called when onError returned false
     */
    public void fire (EventCallable eventCallable, BiFunction<Event, Integer, Boolean> onError,
                      Consumer<Event> onFailure) {
        fire(eventCallable, onError, onFailure, null);
    }

    /**
     * tries to fire the Event.
     * <p>
     * if calling failed, it will call onError. If onError returns true, it will wait 100 milli-seconds an retries
     * firing. OnError will be called with the parameters: this Event and a counter which increments for every try.
     * If onError returns false, the method will return.
     * if calling succeeded, it will call onSuccess.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onError this method will be called when an error occurred
     * @param onFailure this method will be called when onError returned false
     * @param onSuccess this method will be called when firing succeeded
     */
    public void fire (EventCallable eventCallable, BiFunction<Event, Integer, Boolean> onError,
                      Consumer<Event> onFailure, Consumer<Event> onSuccess) {
        boolean success = false;
        int count = 0;
        while (!success) {
            try {
                eventCallable.fire(this);
                success = true;
            } catch (MultipleEventsException e) {
                count++;
                boolean retry = false;
                if (onError != null)
                    retry = onError.apply(this, count);
                if (!retry) {
                    if (onFailure != null)
                        onFailure.accept(this);
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    fileLogger.error("interrupted while trying to fire event", e);
                }
            }
        }
        if (onSuccess != null)
            onSuccess.accept(this);
    }

    /**
     * tries to fire the Event.
     * <p>
     * if calling failed, it will call onError. If onError returns true, it will wait 100 milli-seconds an retries
     * firing. OnError will be called with the parameters: this Event and a counter which increments for every try.
     * If onError returns false, the method will return.
     * if calling succeeded, it will call onSuccess.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onError this method will be called when an error occurred
     */
    public void fire (EventCallable eventCallable, BiFunction<Event, Integer, Boolean> onError) {
        fire(eventCallable, onError, null, null);
    }

    /**
     * tries to fire the Event.
     * <p>
     * if calling failed, it will call onError.
     * </p>
     * @param eventCallable the EventCaller used to fire
     * @param onFailure this method will be called when an error occurred
     */
    public void fire (EventCallable eventCallable, Consumer<Event> onFailure) {
        fire(eventCallable, null, onFailure, null);
    }
}
