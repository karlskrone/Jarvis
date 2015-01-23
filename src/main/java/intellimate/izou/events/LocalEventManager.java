package intellimate.izou.events;

import intellimate.izou.system.Identifiable;
import intellimate.izou.system.Identification;
import intellimate.izou.system.IdentificationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is used to manage local events.
 */
public class LocalEventManager implements Runnable, Identifiable{
    //here are all the Instances which fire events stored
    private final ConcurrentHashMap<Identification, EventCaller> callers = new ConcurrentHashMap<>();
    //the queue where all the Events are stored
    private final BlockingQueue<Event> events = new LinkedBlockingQueue<>(1);
    //if false, run() will stop
    private boolean stop = false;
    private Optional<EventPublisher> eventPublisher = Optional.empty();
    private final Logger fileLogger = LogManager.getLogger(this.getClass());

    public LocalEventManager(EventDistributor eventDistributor) {
        IdentificationManager identificationManager = IdentificationManager.getInstance();
        identificationManager.registerIdentification(this);
        Optional<Identification> id = identificationManager.getIdentification(this);
        if(!id.isPresent()) {
            fileLogger.fatal("Unable to obtain ID for " + getID());
        } else {
            eventPublisher = eventDistributor.registerEventPublisher(id.get());
        }
    }

    /**
     * Registers with the EventManager to fire an event.
     *
     * Note: the same Event can be fired from multiple sources.
     * Method is thread-safe.
     *
     * @param identification the Identification of the the instance
     * @return an Optional, empty if already registered
     */
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public Optional<EventCaller> registerCaller(Identification identification) throws IllegalArgumentException{
        if(identification == null ||
            callers.containsKey(identification)) return Optional.empty();
        EventCaller eventCaller = new EventCaller(events);
        callers.put(identification, eventCaller);
        return Optional.of(eventCaller);
    }

    /**
     * Unregister with the EventManager.
     *
     * Method is thread-safe.
     *
     * @param identification the Identification of the the instance
     */
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void unregisterCaller(Identification identification) {
        if(!callers.containsKey(identification)) return;
        callers.get(identification).locevents = null;
        callers.remove(identification);
    }

    /**
     * this method actually used to fire an event.
     *
     * @param event the fired Event
     */
    private void fireEvent(Event event) throws InterruptedException {
        if(eventPublisher.isPresent()) eventPublisher.get().fire(event);
    }

    @Override
    public void run() {
        stop = false;
        while (!stop) {
            try {
                fireEvent(events.take());
            } catch (InterruptedException e) {
                fileLogger.warn(e);
                continue;
            }
        }
    }

    /**
     * returns the BlockingQueue containing the events.
     *
     * @return an instance of BlockingQueue
     */
    public BlockingQueue<Event> getEvents() {
        return events;
    }

    /**
     * Should stop the EventManager.
     *
     * The method run() is a while-loop that repeats itself as long as a variable isn't true. This sets the variable true
     * but does NOT interrupt the execution! If its not processing, it is waiting for an event, so this Thread still may
     * not stop without interruption.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void stop() {
        stop = true;
    }

    /**
     * An ID must always be unique.
     * A Class like Activator or OutputPlugin can just provide their .class.getCanonicalName()
     * If you have to implement this interface multiple times, just concatenate unique Strings to
     * .class.getCanonicalName()
     *
     * @return A String containing an ID
     */
    @Override
    public String getID() {
        return LocalEventManager.class.getCanonicalName();
    }

    /**
     * Class used to fire events.
     *
     * To fire events a class must register with registerCaller, then this class will be returned.
     * Use fire() to fire the event;
     */
    @SuppressWarnings("SameParameterValue")
    public final class EventCaller implements EventCallable {
        private BlockingQueue<Event> locevents;
        //private, so that this class can only constructed by EventManager
        private EventCaller(BlockingQueue<Event> events) {
            this.locevents  = events;
        }

        /**
         * This method is used to fire the event.
         *
         * @throws intellimate.izou.events.MultipleEventsException an Exception will be thrown if there are currently other events fired
         */
        public void fire(Event event) throws intellimate.izou.events.MultipleEventsException {
            if(locevents == null) return;
            if(locevents.isEmpty()) {
                locevents.add(event);
            } else {
                throw new intellimate.izou.events.MultipleEventsException();
            }
        }
    }

    /**
     * Exception thrown if there are multiple Events fired at the same time.
     */
    @SuppressWarnings("WeakerAccess")
    @Deprecated
    public static class MultipleEventsException extends Exception {
        public MultipleEventsException() {
            super("Multiple Events fired at the same time");
        }
    }
}