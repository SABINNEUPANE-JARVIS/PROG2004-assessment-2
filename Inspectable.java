public interface Inspectable {
    /**
     * Gets the name of the item being inspected.
     */
    String getInspectionName();
    /**
     * Checks if the item is currently closed for inspection.
     */
    boolean isClosedForInspection();
     /**
     * Closes the item for inspection (prevents use during inspection).
     */
    void closeForInspection();
    /**
     * Reopens the item after inspection.
     */
    void reopenAfterInspection();
    /**
     * Records the result of an inspection.
     * Called by Staff after performing the inspection.
     * 
     * @param result the inspection outcome
     */
    void recordInspectionResult(String result);
    /**
     * Returns the last recorded inspection result.
     * @return the last inspection result
     */
    String getLastInspectionResult();
}
