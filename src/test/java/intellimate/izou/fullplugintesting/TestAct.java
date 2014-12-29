package intellimate.izou.fullplugintesting;

import intellimate.izou.activator.Activator;
import intellimate.izou.events.Event;
import intellimate.izou.events.LocalEventManager;
import intellimate.izou.system.Identification;
import intellimate.izou.system.IdentificationManager;
import intellimate.izou.system.Context;

import java.util.Optional;

/**
 * Created by julianbrendl on 10/7/14.
 */
@SuppressWarnings("SameParameterValue")
public class TestAct extends Activator {
    private boolean start;

    public TestAct(Context context) {
        super(context);
        start = true;
    }

    public void setStart(boolean input) {
        start = input;
    }

    @Override
    public void activatorStarts() throws InterruptedException{
        boolean firedEvent = false;
        while (!firedEvent) {
            if(start) {
                start = false;
                System.out.println("1");
                Optional<Identification> id = IdentificationManager.getInstance().getIdentification(this);
                if(!id.isPresent()) return;
                Optional<Event> event = Event.createEvent("1", id.get());
                if(!event.isPresent()) return;
                try {
                    this.fireEvent(event.get());
                    firedEvent = true;
                } catch (LocalEventManager.MultipleEventsException e) {
                    e.printStackTrace();
                }
                start = false;
            }
            else {
                Thread.sleep(20);
            }
        }

    }

    @Override
    public boolean terminated(Exception e) {
        return false;
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
        return TestAct.class.getCanonicalName();
    }
}