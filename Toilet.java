
/**
 * Represents a toilet facility that can be inspected by staff.
 * Implements the Inspectable interface to allow inspections.
 * 
 * A toilet is not an attraction but can be closed for inspection
 * and record inspection results.
 */
public class Toilet implements Inspectable {
    private final String id;
    private final String location;
    private final int capacity;
    private boolean closedForInspection;
    private String inspectionResult;
    /**
     * Creates a toilet with the given details.
     *
     * @param id unique identifier for the toilet
     * @param location location of the toilet in the park
     * @param capacity maximum number of users the toilet can accommodate
     * @throws IllegalArgumentException if ID or location is empty,
     *         or if capacity is not positive
     */
    public Toilet(String id, String location, int capacity) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.id = id;
        this.location = location;
        this.capacity = capacity;
        this.closedForInspection = false;
        this.inspectionResult = "No inspections yet.";
    }
    /**
     * Returns the toilet's unique ID.
     *
     * @return toilet ID
     */
    public String getId() { return id; }
    /**
     * Returns the toilet's location.
     *
     * @return location
     */
    public String getLocation() { return location; }
    /**
     * Returns the toilet's capacity.
     *
     * @return capacity
     */
    public int getCapacity() { return capacity; }
    /**
     * Returns the name used for inspection logging.
     *
     * @return inspection name
     */
    @Override
    public String getInspectionName() {
        return "Toilet at " + location;
    }
    /**
     * Checks if the toilet is currently closed for inspection.
     *
     * @return true if closed for inspection, false otherwise
     */
    @Override
    public boolean isClosedForInspection() {
        return closedForInspection;
    }
    /**
     * Closes the toilet for inspection.
     * While closed, the toilet cannot be used.
     */
    @Override
    public void closeForInspection() {
        closedForInspection = true;
        System.out.println("Toilet at " + location + " is now closed for inspection.");
    }
    /**
     * Reopens the toilet after inspection is complete.
     */
    @Override
    public void reopenAfterInspection() {
        closedForInspection = false;
        System.out.println("Toilet at " + location + " is now reopened after inspection.");
    }
    /**
     * Records the result of an inspection.
     *
     * @param result the inspection outcome
     */
    @Override
    public void recordInspectionResult(String result) {
        this.inspectionResult = result;
        System.out.println("Inspection result for Toilet at " + location + ": " + result);
    }
    /**
     * Returns the last recorded inspection result.
     *
     * @return last inspection result, or "Not inspected yet." if none
     */
    @Override
    public String getLastInspectionResult() {
        return inspectionResult;
    }
    /**
     * Returns a readable representation of the toilet.
     *
     * @return formatted toilet details
     */
    @Override
    public String toString() {
        return String.format("Toilet{ID: %s, Location: %s, Capacity: %d, "
                + "Status: %s, Last Inspection: %s}",
                id, location, capacity,
                closedForInspection ? "Closed" : "Open",
                inspectionResult);
    }
}
