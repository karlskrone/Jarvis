package org.intellimate.izou.identification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to provide identification.
 * You can obtain an Instance through IdentificationManager.
 * This object is Immutable.
 */
//TODO: belongs-to method! (to check if they originate from the same Addon. (related: internal Identification Helper to obtain Addon for Identification etc?)
final class IdentificationImpl implements Identification {
    private final Identifiable identifiable;
    private final boolean createdFromInstance;
    private final Logger fileLogger = LogManager.getLogger(this.getClass());

    private IdentificationImpl(Identifiable identifiable, boolean createdFromInstance) {
        this.identifiable = identifiable;
        this.createdFromInstance = createdFromInstance;
    }

    static Identification createIdentification (Identifiable identifiable) {
        if(identifiable == null) return null;
        return new IdentificationImpl(identifiable, false);
    }

    static Identification createIdentification (Identifiable identifiable, boolean createdFromInstance) {
        if(identifiable == null) return null;
        return new IdentificationImpl(identifiable, createdFromInstance);
    }

    /**
     * returns the ID of the owner of the Identification
     * @return a String containing the ID
     */
    @Override
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

    /**
     * this method returns whether this Identification Object was created by the owner
     * @return true if created by the owner, false if not
     */
    @Override
    public boolean isCreatedFromInstance() {
        return createdFromInstance;
    }

    /**
     * returns whether this and the other identification belong to the same Identifiable
     * @param identification an instance of identification
     * @return true if they equal, false if not
     */
    public boolean equals(Identification identification) {
        return getID().equals(identification.getID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Identifiable) {
            Identifiable that = (Identifiable) o;
            return identifiable.getID().equals(that.getID());
        } else if (o instanceof IdentificationImpl) {
            Identification that = (Identification) o;
            return equals(that);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Identification{" +
                "identifiable=" + identifiable +
                '}';
    }

    @Override
    public int hashCode() {
        return identifiable != null ? identifiable.hashCode() : 0;
    }
}
