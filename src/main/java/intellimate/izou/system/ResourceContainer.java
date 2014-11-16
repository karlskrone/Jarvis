package intellimate.izou.system;

import java.util.LinkedList;
import java.util.List;

/**
 * This interface is used to provide resources
 */
public interface ResourceContainer {

    /**
     * checks whether it can provide the resource
     * beware that the implementation may vary.
     * @param resource the resource to provide
     * @return true if the container can provide the resource
     */
    abstract boolean providesResource(Resource resource);

    /**
     * checks whether there are any resources registered from the source
     * beware that the implementation may vary.
     * @param sourceID the ID of the source
     * @return true if the container has resources from the source
     */
    abstract boolean containsResourcesFromSource(String sourceID);

    /**
     * checks whether the ResourceContainer can provide at least ONE resource
     * beware that the implementation may vary.
     * @param resourcesID a list containing sources
     * @return true if the ResourceContainer can provide at least one resource
     */
    abstract boolean providesResource(List<String> resourcesID);

    /**
     * returns all EXISTING resources for the ID.
     * If there are no resources for the ID the ID will get skipped
     * beware that the implementation may vary.
     * @param resourceIDs an Array containing the resources
     * @return a list of resources found
     */
    abstract LinkedList<Resource> provideResource(String[] resourceIDs);

    /**
     * returns the resource (if existing)
     * beware that the implementation may vary.
     * @param resourceID the ID of the resource
     * @return
     */
    abstract Resource provideResource(String resourceID);
}
