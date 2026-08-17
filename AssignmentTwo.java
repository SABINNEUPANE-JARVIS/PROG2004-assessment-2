import java.io.*;
import java.util.*;
import java.util.concurrent.*;
/**
* This is the main program that runs our theme park management system.
* it walks through all 8 parts of the assignment one by one, showing that each featre works properly./**
*we dont have a  user interface. Instead, we print messages to the console so it can be seen exaclty whats happening at each step.
*/
 public class AssignmentTwo {
    public static void main(String[]  args) {
        System.out.println("=== Theme Park Management System ===");
        part1_DemonstratePeople();
        part2_DemonstrateAttractions();
        part3_DemonstrateWaitingLine();
        part4_DemonstrateVisitHistory();
        part5_DemonstrateOperatingAttraction();
        part6_DemonstrateParkManager(); 
        part7_DemonstratePersistence();
        part8_DemonstrateConcurrency();
        System.out.println("Demonstration Complete.   ");
    }
    //  Modelling the park's people 
    /** 
     * shows we created staff and visitor objects.
     * it also proves that visitors can be stored by age and the two visitors are considered the same if they have same id number.
    */
    private static void part1_DemonstratePeople() {
        System.out.println("Modelling the park's people.");
        Staff s1 = new Staff("001", "Tony Stark", 45, "Manager");
        Staff s2 = new Staff("002", "Steve Rogers", 32, "Ride Operator");
        Staff s3 = new Staff("003", "Carol Technician", 28, "Technician");
        System.out.println("Staff created:");
        System.out.println(" " + s1);
        System.out.println("  " + s2);
        System.out.println("  " + s3);
    // Let's test that staff must be 18 or older
        try {
            new Staff ("004", "Young Person", 16, "Helper");
        } catch (IllegalArgumentException e) {
            System.out.println("Staff validation test" + e.getMessage());
        }
        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        Visitor v4 = new Visitor("1004", "Green Brown", 35, "Day Pass");
        Visitor v5 = new Visitor("1005", "Charlie Babbage", 22, "Annual Pass");
        System.out.println("\nVisitors created:");
        System.out.println("  " + v1);
        System.out.println("  " + v2);
        System.out.println("  " + v3);
        System.out.println("  " + v4);
        System.out.println("  " + v5);

        // Put all visitors in a list and sort them by age
        List<Visitor> list = new ArrayList<>();
        list.add(v1);
        list.add(v2);
        list.add(v3);
        list.add(v4);
        list.add(v5);
        Collections.sort(list);
        System.out.println("Visitors sorted by age:");
        for (Visitor v : list) {
            System.out.println("  " + v.getName() + " - Age: " + v.getAge());
        }
        // Check that two visitors with the same ID count as the same person
        Visitor same  = new Visitor("1001", "John Smith", 25, "Day pass");
        System.out.println("Equality test (based on ID):");
        System.out.println("  v1 equals same? " + v1.equals(same));
        System.out.println("  v1 equals v2? " + v1.equals(v2));
        System.out.println("  v1 hashCode: " + v1.hashCode());
        System.out.println("  same hashCode: " + same.hashCode());
    }
    //Modelling the park's attractions
    /**
     * Creates different types of attractions – rides, shows, and even toilets.
     * Shows that rides can be inspected but shows cannot (they don't need it).
     * Also shows that staff can be assigned as operators.
     */
    private static void part2_DemonstrateAttractions() {
        System.out.println("Modelling the park's attractions");
        Staff s1 = new Staff("001", "Aliza", 45, "Manager");
        Staff s2 = new Staff("002", "Bobby", 32, "Operator");
        Staff s3 = new Staff("003", "Sabin", 28, "Technician");

        Ride ride = new Ride("R001", "Thunder Coaster", 4);
        Show show = new Show("S001", "Magic Show", 10);
        Toilet toilet = new Toilet("T001", "Main Plaza", 6);

        System.out.println("Attractions created:");
        System.out.println("  " + ride);
        System.out.println("  " + show);
        System.out.println("  " + toilet);
        //Assign operators to run the atrractions
        ride. assignOperator(s2);
        show.assignOperator(s1);
        ride.removeOperator();
        //Only rides and toilets can be inspected , shows cannot
        System.out.println("Inspections:");
        s3.performInspection(ride);
        s3.performInspection(toilet);
        System.out.println("Attempting to inspect show:");
        System.out.println("  Shows do not implement Inspectable, so they cannot be passed to performInspection.");
    //The waiting line
    /**
     * Shows that visitors join a queue and are served in the order they arrived
     * – first in, first out (FIFO). This is the waiting line for an attraction.
     */
    }
    private static void part3_DemonstrateWaitingLine() {
        System.out.println("The waiting line");
        Staff op = new Staff("002", "Bobby", 32, "Operator");
        Ride ride = new Ride("R001", "Thunder Coaster", 4);
        ride.assignOperator(op);
        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        Visitor v4 = new Visitor("1004", "Green Brown", 35, "Day Pass");
         System.out.println("Adding visitors to waiting line:");
        ride.addToWaitingLine(v1);
        ride.addToWaitingLine(v2);
        ride.addToWaitingLine(v3);
        ride.addToWaitingLine(v4);
        System.out.println("Waiting line after adding visitors:");
        ride.displayWaitingLine();
        // The first person in line gets served first
        Visitor served =  ride.removeNextVisitor();
        System.out.println("Served : " + served.getName());
        System.out.println("Witing line after serving one visitor:");
        ride.displayWaitingLine();
        // Check who's next – should be the person who joined second
        Visitor next = ride.getWaitingLine().peek();
        System.out.println("Next to be served: " + (next != null ? next.getName() : "None"));
    }
    //The visit history
     /**
     * Shows that the attraction keeps a record of everyone it has served.
     * The history can be viewed in different orders – by insertion order,
     * by age, or by name and membership type.
     */
    private static void part4_DemonstrateVisitHistory() {
        System.out.println("The visit history");
        Staff op = new Staff("002", "Bobby", 32, "Operator");
        Ride ride = new Ride("R001", "Thunder Coaster", 4);
        ride.assignOperator(op);
        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        Visitor v4 = new Visitor("1004", "Green Brown", 35, "Day Pass");
        Visitor v5 = new Visitor("1005", "Charlie Babbage", 22, "Annual Pass");
        System.out.println("Recording visitors in history:");
        ride.recordVisitor(v1);
        ride.recordVisitor(v2);
        ride.recordVisitor(v3);
        ride.recordVisitor(v4);
        ride.recordVisitor(v5);
        ride.recordVisitor(v1); // Same person can be recorded multiple times
        System.out.println("History count: " + ride.getHistoryCount());
        System.out.println("v1 in history? " + ride.isInHistory(v1));
        System.out.println("Displaying history :");
        ride.displayHistory();
        System.out.println("Displaying history (by age):");
        ride.displayHistoryByAge();
        System.out.println("Displaying history by name then membership :");
        ride.displayHistoryByNameAndMembership();
    }
    //Operating an attraction
    /**
     * Shows how attractions actually run their cycles.
     * A ride only runs if it has an operator, isn't closed for inspection,
     * and has people waiting. A show runs even if nobody is waiting –
     * an empty performance still counts as a cycle.
     */
    private static void part5_DemonstrateOperatingAttraction() {
        System.out.println("Operating an attraction"); 
        Staff op = new Staff("002", "Bobby", 32, "Operator");
        Staff tech = new Staff("003", "Sabin", 28, "Technician");
        Ride ride = new Ride("R001", "Thunder Coaster", 3);
        Show show = new Show("S001", "Magic Show", 5);

        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        Visitor v4 = new Visitor("1004", "Green Brown", 35, "Day Pass");
        Visitor v5 = new Visitor("1005", "Charlie Babbage", 22, "Annual Pass");
        // Ride tests
        System.out.println("RIDE TESTS");
        ride.addToWaitingLine(v1);
        ride.addToWaitingLine(v2);
        ride.addToWaitingLine(v3);
        ride.addToWaitingLine(v4);
        ride.addToWaitingLine(v5);

        System.out.println("1. Without operator:");   
        ride.runCycle();

        ride.assignOperator(op);
        System.out.println("2. With operator (batch 3):");
        List<Visitor> served = ride.runCycle();
        System.out.println("  Served: " + served.size());

        System.out.println("3. During inspection:");
        tech.performInspection(ride);
        ride.runCycle();
        
        System.out.println("4. After inspection:");
        ride.reopenAfterInspection();
        ride.runCycle();

        // Show tests – shows run even with empty queue
        System.out.println(" SHOW TESTS ");
        show.addToWaitingLine(v1);
        show.addToWaitingLine(v2);

        System.out.println("1. Without operator:");
        show.runCycle();

        show.assignOperator(op);
        System.out.println("2. With operator:");
        show.runCycle();

        System.out.println("3. Empty show:");
        while (show.getWaitingLine().peek() != null) {
            show.removeNextVisitor();
        }
        show.runCycle();
        System.out.println("  Cycle count after empty performance: " + show.getCycleCount());
    }
    //Managing the park 
    /**
     * The ParkManager keeps track of all attractions in one place.
     * You can look up attractions by ID, see how many people each attraction has been served,
     * and count how many distinct visitors came to the park.
     */
    private static void part6_DemonstrateParkManager() {
        System.out.println("Managing the park ");
        ParkManager park = new ParkManager("Wonderland Park");
        System.out.println("Created: " + park);

        Staff op1= new Staff("002", "Bobby", 32, "Operator");
        Staff op2 = new Staff("005", "Dave", 29, "Show Operator");
        
        Ride r1 = new Ride("R001", "Thunder Coaster", 4);
        Ride r2 = new Ride("R002", "Carousel", 6);
        Show s1 = new Show("S001", "Magic Show", 10);

        r1.assignOperator(op1);
        r2.assignOperator(op1);
        s1.assignOperator(op2);

        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        Visitor v4 = new Visitor("1004", "Green Brown", 35, "Day Pass");
        Visitor v5 = new Visitor("1005", "Charlie Babbage", 22, "Annual Pass");

        r1.addToWaitingLine(v1);
        r1.addToWaitingLine(v2);
        r2.addToWaitingLine(v3);
        r2.addToWaitingLine(v4);
        s1.addToWaitingLine(v5);

        park.registerAttraction(r1);
        park.registerAttraction(r2);
        park.registerAttraction(s1);
        park.displayAllAttractions();

        System.out.println("Lookup by ID:");
        park.getAttractionById("R001");
        park.getAttractionById("X999");

        System.out.println("Running cycles and updating park totals:");
        park.runAttractionCycle(r1);
        park.runAttractionCycle(r1);
        park.runAttractionCycle(r2);
        park.runAttractionCycle(s1);

        park.reportAttractionSeatsServed();
        park.reportDistinctVisitors();

        System.out.println("Same visitor rides again:");
        r1.addToWaitingLine(v1);
        park.runAttractionCycle(r1);
        park.reportDistinctVisitors();
    }
    //Backing up and restoring the park 
    /**
     * Shows that we can save the park's data to a file and load it back later.
     * If the file is missing or corrupted, the program handles it gracefully
     * instead of crashing.
     */
    private static void part7_DemonstratePersistence() {
        System.out.println("Backing up and restoring the park");

        ParkManager original = createTestPark();
        System.out.println("Original park:");
        System.out.println("  " + original);
        original.displayAllAttractions();

        System.out.println("Saving park data...");
        boolean saved = ParkPersistence.savePark(original);
        System.out.println("  Save successful: " + saved);

        System.out.println("Loading park data...");
        ParkManager loaded = ParkPersistence.loadPark();
        System.out.println("  Loaded: " + loaded);
        loaded.displayAllAttractions();

        System.out.println("Comparison:");
        System.out.println("  Original total served: " + original.getTotalVisitorsServed());
        System.out.println("  Loaded total served: " + loaded.getTotalVisitorsServed());
        System.out.println("  Original distinct visitors: " + original.getDistinctVisitorCount());
        System.out.println("  Loaded distinct visitors: " + loaded.getDistinctVisitorCount());

        System.out.println("Missing file test:");
        new File("park_data.txt").delete();
        ParkManager empty = ParkPersistence.loadPark();
        System.out.println("  Result: " + empty.getAttractionCount() + " attractions");

        ParkPersistence.savePark(original);
    }
    /**
     * Creates a park with some sample data so we can test saving and loading.
     */
    private static ParkManager createTestPark() {
        ParkManager park = new ParkManager("Test Park");
        Staff op1= new Staff("002", "Bobby", 32, "Operator");
        Staff op2 = new Staff("005", "Dave", 29, "Show Operator");
        Ride r1 = new Ride("R001", "Coaster", 4);
        Ride r2 = new Ride("R002", "Carousel", 6);
        Show s1 = new Show("S001", "Magic", 10);

        r1.assignOperator(op1);
        r2.assignOperator(op1);
        s1.assignOperator(op2);

        Visitor v1 = new Visitor("1001", "John Smith", 25, "Day pass");
        Visitor v2 = new Visitor("1002", "Jane Malik" , 30, "Annual pass");
        Visitor v3 = new Visitor("1003", "Bob Marley", 18, "Day pass");
        
         r1.addToWaitingLine(v1);
        r1.addToWaitingLine(v2);
        r2.addToWaitingLine(v3);
        s1.addToWaitingLine(v1);

        park.registerAttraction(r1);
        park.registerAttraction(r2);
        park.registerAttraction(s1);

        park.runAttractionCycle(r1);
        park.runAttractionCycle(r1);
        park.runAttractionCycle(r2);
        park.runAttractionCycle(s1);

        return park;
    }
    //Running the park concurrently
    /**
     * A task that runs an attraction for a set number of cycles.
     * Implements Runnable so it can be given to a thread or thread pool.
    */
    public static class AttractionRunner implements Runnable {
        private final Attraction attraction;
        private final int cycles;
        private final ParkManager park;
        public AttractionRunner(Attraction attraction, int cycles, ParkManager park){
            this.attraction = attraction;
            this.cycles = cycles;
            this.park = park; 
        }
        /**
         * This is what runs when the thread starts.
         * It runs the attraction for the specified number of cycles,
         * pausing briefly between each one to simulate real operation.
         */
        @Override
        public void run() {
            for (int i = 0; i < cycles; i++) {
                park.runAttractionCycle(attraction);
                try {
                    Thread.sleep(200 + (int)(Math.random() * 300));
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    } 
    /**
     * Shows that multiple attractions can run at the same time.
     * We use a thread pool with 3 threads, so 3 attractions run
     * simultaneously while others wait their turn.
     * 
     * The shared counter is protected with synchronized so we don't
     * lose any counts when multiple threads update it at once.
     */  
    private static void part8_DemonstrateConcurrency() {
        System.out.println("Running the park concurrently ");

        ParkManager park = new ParkManager("Concurrent Park");

        Staff op1= new Staff("002", "Bobby", 32, "Operator");
        Staff op2 = new Staff("005", "Dave", 29, "Show Operator");
        Staff op3 = new Staff("006", "Eve", 34, "Operator");
        
        Ride r1 = new Ride("R001", "Coaster", 3);
        Ride r2 = new Ride("R002", "Ferris Wheel", 4);
        Show s1 = new Show("S001", "Magic Show", 5);
        Show s2 = new Show("S002", "Concert", 8);
        Ride r3 = new Ride("R003", "Bumper Cars", 2);

        r1.assignOperator(op1);
        r2.assignOperator(op3);
        s1.assignOperator(op2);
        s2.assignOperator(op2);
        r3.assignOperator(op1);

        park.registerAttraction(r1);
        park.registerAttraction(r2);
        park.registerAttraction(s1);
        park.registerAttraction(s2);
        park.registerAttraction(r3);

        // Create 30 visitors
        List<Visitor> visitorList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            visitorList.add(new Visitor(
                String.format("%04d", 1000 + i),
                "Visitor " + (i + 1),
                18 + (i % 20),
                i % 2 == 0 ? "Day Pass" : "Annual Pass"
            ));
        }
        // Spread the visitors across all attractions
        int idx = 0 ;
        for (int i = 0; i < 8 && idx < visitorList.size(); i++) {
            r1.addToWaitingLine(visitorList.get(idx++));
        }
        for (int i = 0; i < 6 && idx < visitorList.size(); i++) {
            r2.addToWaitingLine(visitorList.get(idx++));
        }
        for (int i = 0; i < 5 && idx < visitorList.size(); i++) {
            s1.addToWaitingLine(visitorList.get(idx++));
        }
        for (int i = 0; i < 6 && idx < visitorList.size(); i++) {
            s2.addToWaitingLine(visitorList.get(idx++));
        }
        for (int i = 0; i < 5 && idx < visitorList.size(); i++) {
            r3.addToWaitingLine(visitorList.get(idx++));
        }
        System.out.println("Initial waiting counts:");
        for (Attraction a : park.getAllAttractions()) {
            System.out.println("  " + a.getName() + ": " + a.getWaitingLine().size() + " waiting");
        }
        System.out.println("Running attractions concurrently (3 at a time)...");
        ExecutorService executor = Executors.newFixedThreadPool(3);    
       // Create and start the tasks
        executor.execute(new AttractionRunner(r1, 3, park));
        executor.execute(new AttractionRunner(r2, 3, park));
        executor.execute(new AttractionRunner(r3, 3, park));
        executor.execute(new AttractionRunner(s1, 2, park));
        executor.execute(new AttractionRunner(s2, 2, park));
        // Stop accepting new tasks – the program will wait for existing ones to finish
        executor.shutdown();

        // Wait for all tasks to finish using isTerminated()
        while (!executor.isTerminated()) {
            // wait for all tasks to finish
        }
        System.out.println("All attractions finished.");
        park.displayAllAttractions();
        park.reportAttractionSeatsServed();
        park.reportDistinctVisitors();
        System.out.println(" Thread Safety Verification");
        System.out.println("  Total visitors served: " + park.getTotalVisitorsServed());
        System.out.println("  Distinct visitors: " + park.getDistinctVisitorCount());
        System.out.println("Concurrency demonstrated:");
        System.out.println(" Multiple attractions ran simultaneously");
         System.out.println("  - synchronized ensures correct shared counter updates");
        System.out.println("  - Thread.sleep() simulated variable run times");
        System.out.println("  - Named Runnable class used)");
        
    }
}