import java.util.*;
/**
 * Keeps track of all the attractions in the park, the visitors who have visited,
 * and how many people have been served overall.
 * 
 * Uses a HashMap so we can find any attraction quickly by its ID.
 * Uses a HashSet     to count each visitor only once, even if they go on multiple rides.
 */
public class ParkManager {
    private final String parkName;
    private final  Map<String, Attraction> attractions;
    private final Set<Visitor> allVisitors;
    private int totalVisitorsServed; // counts every visit (including duplicates)
    /**
     * Sets up a new park manager with the park's name.
     *
     * @param parkName the name of the theme park
     * @throws IllegalArgumentException if the name is empty or null
     */  
    public ParkManager(String parkName) {
        if (parkName == null || parkName .trim().isEmpty())
            throw new IllegalArgumentException("Park name cannot be empty");
        this.parkName = parkName.trim();
        this.attractions = new HashMap<>();
        this.allVisitors = new HashSet<>();
        this.totalVisitorsServed = 0;
    }
    /**
     * Gets the name of the park.
     *
     * @return park name
     */
    public String getParkName() {
        return parkName;
    }
    /**
     * Gets how many attractions are registered in the park.
     *
     * @return number of attractions
     */
    public int getAttractionCount() {
        return attractions.size();
    }
    /**
     * Gets how many different visitors have come to the park.
     * Each person is only counted once, even if they visit multiple times.
     *
     * @return number of distinct visitors
     */
     public int getDistinctVisitorCount() {
        return allVisitors.size();
    }
    /**
     * Gets the total number of visitors served across all attractions.
     * This counts the same person multiple times if they go on multiple rides.
     *
     * @return total visitors served (with duplicates)
     */
    public int getTotalVisitorsServed() {
        return totalVisitorsServed;
    }
    /**
     * Adds a new attraction to the park so it can be managed.
     *
     * @param attraction the attraction to add
     * @throws IllegalArgumentException if attraction is null
     */
    public void registerAttraction(Attraction attraction) {
        if (attraction == null)
            throw new IllegalArgumentException("Attraction cannot be null");
        attractions.put(attraction.getId(), attraction);
        System.out.println("Attraction " + attraction.getName()
                + " (ID: " + attraction.getId() + ") registered with " + parkName);
    }
    /**
     * Looks up an attraction by its ID.
     * Uses HashMap so it's fast – O(1).
     *
     * @param id the ID to search for
     * @return the attraction if found, or null if not
     */
    public Attraction getAttractionById(String id) {
        Attraction a = attractions.get(id);
        System.out.println(a != null ? "Attraction found: " + a.getName()
                : "No attraction found with ID: " + id);
        return a;
    }

    /**
     * Runs one cycle of an attraction and updates the park's visitor counts.
     *
     * @param attraction the attraction to run
     * @return list of visitors who were served
     */
    public List<Visitor> runAttractionCycle(Attraction attraction) {
        List<Visitor> served = attraction.runCycle();
        for (Visitor v : served) {
            allVisitors.add(v);          // only adds if new (no duplicates)
            totalVisitorsServed++;       // counts every visit
        }
        return served;
    }

    /**
     * Prints how many people each attraction has served.
     */
    public void reportAttractionSeatsServed() {
        System.out.println("\n=== Attraction Seat Counts for " + parkName + " ===");
        for (Attraction a : attractions.values()) {
            System.out.println(a.getName() + ": " + a.getHistoryCount() + " seats served");
        }
    }

    /**
     * Prints two numbers: distinct visitors (each counted once)
     * and total seats served (same person counted multiple times if they ride again).
     */
    public void reportDistinctVisitors() {
        System.out.println("\n=== Distinct Visitor Count for " + parkName + " ===");
        System.out.println("Total distinct visitors: " + allVisitors.size());
        System.out.println("Total seats served (with duplicates): " + totalVisitorsServed);
    }

    /**
     * Prints all the attractions currently registered in the park.
     */
    public void displayAllAttractions() {
        if (attractions.isEmpty()) {
            System.out.println("No attractions registered in " + parkName);
            return;
        }
        System.out.println("\n=== Attractions in " + parkName + " ===");
        for (Attraction a : attractions.values()) {
            System.out.println(a);
        }
    }

    /**
     * Returns a copy of all the attractions.
     * We give a copy so outside code can't mess with the park's internal list.
     *
     * @return a new list containing all attractions
     */
    public Collection<Attraction> getAllAttractions() {
        return new ArrayList<>(attractions.values());
    }

    /**
     * Returns a short summary of the park manager's current state.
     *
     * @return park name, number of attractions, distinct visitors, and total served
     */
    @Override
    public String toString() {
        return String.format("ParkManager{Park: %s, Attractions: %d, "
                + "Distinct Visitors: %d, Total Served: %d}",
                parkName, attractions.size(), allVisitors.size(),
                totalVisitorsServed);
    }
}
