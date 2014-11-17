package intellimate.izou.system;

/**
 * Used to provide identification.
 * You can obtain an Instance through IdentificationManager
 */
public class Identification {
    private Identifiable identifiable;
    Identification(Identifiable identifiable) {
        this.identifiable = identifiable;
    }

    /**
     * returns the ID of the owner of the Identification
     * @return a String containing the ID
     */
    public String getID() {
        return identifiable.getID();
    }

    /**
     * returns the Identifiable object of the Owner
     * @return a instance of Identifiable
     */
    Identifiable getIdentifiable() {
        return identifiable;
    }
}
