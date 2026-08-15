import java.util.*;
/**
 * Abstract base for all attraction. Provides waiting line, history,
 * operator assignment, and batch serving.
 */
public abstract class Attraction {
    private final String id;
    private final String name;
    private Staff operator;
    private final int batchSize;
    private int cycleCount;
     // FIFO waiting line using Queue
    private final Queue<Visitor> waitingLine;
     // History allows duplicates (same visitor may ride multiple times)
    private final List<Visitor> visitHistory;
    /**
     * Creates an attraction with the given details.
     *
     * @param id unique attraction ID
     * @param name attraction name
     * @param batchSize maximum number of visitors served in one cycle
     * @throws IllegalArgumentException if the ID or name is empty,
     *         or if the batch size is not positive
     */
    public Attraction(String id, String name, int batchSize){
        if (id == null || id.trim().isEmpty()) 
            throw new IllegalArgumentException("Id cannot be empty");
        
        if (name == null || name.trim().isEmpty()) 
            throw new IllegalArgumentException("Name cannot be empty");
        
        if (batchSize <= 0) 
            throw new IllegalArgumentException("Batch size must be positive");
        this.id = id;
        this.name = name.trim();
        this.batchSize = batchSize;
        this.waitingLine = new LinkedList<>();
        this.visitHistory = new ArrayList<>();
        this.cycleCount = 0;
    } 
     // Getters  
    /**
     * Returns the attraction ID.
     *
     * @return attraction ID
     */ 
    public String getId() { return id;}
    /**
     * Returns the attraction name.
     *
     * @return attraction name
     */
    public String getName() { return name;}
    /**
     * Returns the operator assigned to the attraction.
     *
     * @return operator
     */
    public Staff getOperator() { return operator;}  
    /**
     * Returns the maximum number of visitors served in one cycle.
     *
     * @return batch size
     */
    public int getBatchSize() { return batchSize;}
    /**
     * Returns the number of cycles completed.
     *
     * @return cycle count
     */
    public int getCycleCount() { return cycleCount;}
    /**
     * Returns the waiting line for the attraction.
     *
     * @return queue containing waiting visitors
     */
    public Queue<Visitor> getWaitingLine() { return waitingLine;}
    /**
     * Returns the visit history for the attraction.
     *
     * @return list containing visitors who have been served
     */
    public List<Visitor> getVisitHistory() { return visitHistory;}

    // Waiting line operations
    /**
     * Adds a visitor to the end of the waiting line.
     *
     * @param visitor visitor joining the waiting line
     * @throws IllegalArgumentException if the visitor is null
     */
    public void addToWaitingLine(Visitor visitor) {
        if (visitor == null) 
            throw new IllegalArgumentException("Visitor cannot be null");
        waitingLine.offer(visitor);
        System.out.println("Visitor " + visitor.getName() + " joined the line for " + name);
        }
    /**
     * Removes and returns the next visitor in the waiting line.
     * The visitor who joined first is removed first.
     *
     * @return next visitor in the queue, or null if the line is empty
     */
    public Visitor removeNextVisitor() {
            Visitor v = waitingLine.poll();
            if (v != null) {
                System.out.println("Visitor " + v.getName() + " removed from waiting line for " + name);
            }
            return v;
        }
    /**
     * Displays all visitors currently waiting in the attraction's queue.
     */
    public void displayWaitingLine() {
            if (waitingLine.isEmpty()) {
                System.out.println("No visitors waiting for " + name);
                return;
            }
            System.out.println("Waiting line for " + name + ":");
            for (Visitor v : waitingLine) 
                System.out.println("  " + v);
        }
    // History operations
    /**
     * Adds a visitor to the attraction's visit history.
     *
     * @param visitor visitor who has been served
     * @throws IllegalArgumentException if the visitor is null
     */
    public void recordVisitor(Visitor visitor) {
        if (visitor == null) 
            throw new IllegalArgumentException("Visitor cannot be null");
        visitHistory.add(visitor);
        System.out.println("Visitor " + visitor.getName() + " recorded in history for " + name);
    }
    /**
     * Checks whether a visitor appears in the visit history.
     *
     * @param visitor visitor to search for
     * @return true if the visitor is in the history, otherwise false
     */
    public boolean isInHistory(Visitor visitor) {
        return visitHistory.contains(visitor);
    }
    /**
     * Returns the number of visitors recorded in the visit history.
     *
     * @return number of recorded visits
     */
    public int getHistoryCount() {
        return visitHistory.size();
    }
    /**
     * Displays all visitors in the order they were recorded.
     */
    public void displayHistory() {
        if (visitHistory.isEmpty()) {
                System.out.println("No visitors recorded in history for " + name );
                return;
        }
        System.out.println("Visit history for " + name + ":");
        for (Visitor v : visitHistory) 
            System.out.println("  " + v);
    }
    /**
     * Displays history ordered by age (uses Visitor.compareTo).
     */
    public void displayHistoryByAge() {
        if (visitHistory.isEmpty()) {
            System.out.println("No visitors recorded in history for " + name );
            return;
        }
        List<Visitor> sorted = new ArrayList<>(visitHistory);
        Collections.sort(sorted);
        System.out.println("Visit history for " + name + " ordered by age:");
        for (Visitor v : sorted) 
            System.out.println("  " + v);
            
        }
    
    /**
     * Displays history ordered by name, then membership type.
     * Uses a separate Comparator class (VisitorNameMembershipComparator).
     */
    public void displayHistoryByNameAndMembership() {
        if (visitHistory.isEmpty()) {
            System.out.println("No visitors recorded in history for " + name );
            return;
        }
        List<Visitor> sorted = new ArrayList<>(visitHistory);
        Collections.sort(sorted, new VisitorNameMembershipComparator());
        System.out.println("Visit history for " + name + " ordered by name and membership type:");
        for (Visitor v : sorted) 
            System.out.println("  " + v);
            
        }    
    //Operator management
    /**
     * Assigns a staff member as the operator of this attraction.
     *
     * @param staff staff member who will operate the attraction
     * @throws IllegalArgumentException if the staff member is null
     */
    public void assignOperator(Staff staff) {
        if (staff == null) 
            throw new IllegalArgumentException("Staff cannot be null");
        this.operator = staff;
        System.out.println("Staff " + staff.getName() + " assigned as operator for " + name);
    }
    /**
     * Removes the current operator from the attraction.
     */
    public void removeOperator() {
        if (operator !=null) {
            System.out.println("Operator " + operator.getName() + " removed from " + name);
            operator = null;
        }
    }
    /**
     * Subclasses provide their own rules for running.
     * Returns the visitors served during this run.
     */
    public abstract List<Visitor> runCycle();
    /**
     * Runs a group of visitors up to the given batch size.
     * Checks that an operator is available before starting.
     */  
    protected List<Visitor> serveBatch() {
        // Must have an operator to run    
        if (operator == null) {
            System.out.println("Cannot run: no operator assigned.");
            return new ArrayList<>();
        }
        List<Visitor> served = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            Visitor v = removeNextVisitor();
            if (v == null) break;
            recordVisitor(v);
            served.add(v);
        }
        if(!served.isEmpty()) {
            cycleCount++;
            System.out.println(name + " Completed cycle " +cycleCount + " served " + served.size() + " visitors.");
        }
        return served;
    } 
    /**
     * Returns a readable representation of the attraction,
     * including its operator, waiting visitors, served visitors,
     * and cycle count.
     *
     * @return formatted attraction details
     */
    @Override
    public String toString() {
        return String.format("Attraction{ID: %s, Name: %s, Operator: %s, "
                + "Waiting: %d, Served: %d, Cycles: %d}",
                id, name, operator != null ? operator.getName() : "None",
                waitingLine.size(), visitHistory.size(), cycleCount);
    }
} 