import java.io.*;
/**
 * Saves and loads park data to/from a text file.
 * Handles missing/corrupt files gracefully.
 */
public class ParkPersistence {
    private static final String DATA_FILE = "park_data.txt";
    private static final String DELIMITER = ",";
     /**
     * Saves the park data to a file.
     * Uses BufferedWriter with write() and newLine().
     *
     * @param park the park to save
     * @return true if save was successful, false otherwise
     */
    public static boolean savePark(ParkManager park) {
        if (park == null) {
            System.out.println("Cannot save null park");
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write(park.getParkName());
            writer.newLine();
            for (Attraction attraction : park.getAllAttractions()) {
                String line = formatAttractionForSave(attraction);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Park data saved successfully to" + DATA_FILE);
            return true;

        }catch (IOException e) {
            System.out.println("Error saving park data: " + e.getMessage());
            return false;
        }
    }    /**
     * Formats an attraction as a single CSV line.
     * Uses plain String concatenation.
     */
        private static String formatAttractionForSave(Attraction attraction) {
            String line = "";
        line += attraction.getClass().getSimpleName() + DELIMITER;
        line += attraction.getId() + DELIMITER;
        line += attraction.getName() + DELIMITER;
        line += attraction.getBatchSize() + DELIMITER;
        line += attraction.getCycleCount() + DELIMITER;
        // Operator info
        Staff op = attraction.getOperator();
        if (op !=null) {
            line+= op.getId() + DELIMITER;
            line += op.getName() + DELIMITER;
            line += op.getAge() + DELIMITER;
            line += op.getRole() + DELIMITER;
        }else{
            line +="NONE" + DELIMITER;
            line += "NONE" + DELIMITER;
            line += "0" + DELIMITER;
            line += "NONE" + DELIMITER;
        }
        // Waiting line visitors
        line += attraction.getWaitingLine().size() + DELIMITER;
        for (Visitor v : attraction.getWaitingLine()) {
            line += v.getId() + DELIMITER;
            line += v.getName() + DELIMITER;
            line += v.getAge() + DELIMITER;
            line += v.getMembershipType() + DELIMITER;
        }
        // History visitors
        line += attraction.getVisitHistory().size() + DELIMITER;
        for (Visitor v : attraction.getVisitHistory()) {
            line += v.getId() + DELIMITER;
            line += v.getName() + DELIMITER;
            line += v.getAge() + DELIMITER;
            line += v.getMembershipType() + DELIMITER;
        }
        return line;
    }
    /**
     * Loads park data from a file.
     * Handles missing/corrupt files gracefully.
     * Skips malformed lines rather than crashing.
     *
     * @return ParkManager with loaded data, or a new empty ParkManager if load fails
     */
    public static ParkManager loadPark() {
        File file = new File(DATA_FILE);
         if (!file.exists()) {
            System.out.println("No saved data file found. Starting with empty park.");
            return new ParkManager("Default Park");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line =  reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                System.out.println("Data file is empty. Starting with empty park");
                return new ParkManager("Default park");
            }
            ParkManager park  = new ParkManager(line.trim());
            int lineNumber = 2;
            while ((line =  reader.readLine()) !=null) {
                try {
                    Attraction attraction = parseAttraction(line);
                    if (attraction != null) {
                        park.registerAttraction(attraction);
                    }
                } catch (Exception e) {
                    System.out.println("Warning Skipping corrupt attaction record at line" + lineNumber + ": " + e.getMessage());
                }
                lineNumber++;
            }
            System.out.println("Park data loaded successfully from" + DATA_FILE);
            return park;
        } catch (IOException e) {
            System.out.println("Error loadinf park data: " + e.getMessage());
            System.out.println("Satring with empty park. ");
            return new ParkManager("Default park");
        }
    }
    /**
     * Parses an attraction from a CSV line.
     *
     * @param line the line to parse
     * @return the parsed attraction
     * @throws IllegalArgumentException if the line is corrupted
     */
    private static Attraction parseAttraction(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length < 9) {
            throw new IllegalArgumentException("Invalid attraction data format ");
        }
        int idx = 0;
        String type = parts[idx++];
        String id = parts[idx++];
        String name = parts[idx++];
        int batchSize = Integer.parseInt(parts[idx++]);
        Integer.parseInt(parts[idx++]); // cycleCount - read but not restored
        Staff operator = null;
        String opId = parts[idx++];
         if (!"NONE".equals(opId)) {
            String opName = parts[idx++];
            int opAge = Integer.parseInt(parts[idx++]);
            String opRole = parts[idx++];
            operator = new Staff(opId, opName, opAge, opRole);
        } else {
            idx += 3;
        }
        Attraction attraction;
        if ("Ride".equals(type)) {
            attraction = new Ride(id, name, batchSize);
        } else if ("Show".equals(type)) {
            attraction = new Show(id, name, batchSize);
        } else {
            throw new IllegalArgumentException("Unknown attraction type: " + type);
        }
        int waitCount = Integer.parseInt(parts[idx++]);
        for (int i = 0; i < waitCount; i++) {
            String vId = parts[idx++];
            String vName = parts[idx++];
            int vAge = Integer.parseInt(parts[idx++]);
            String vMembership = parts[idx++];
            Visitor visitor = new Visitor(vId, vName, vAge, vMembership);
            attraction.addToWaitingLine(visitor);
        }
        int historyCount = Integer.parseInt(parts[idx++]);
        for (int i = 0; i < historyCount; i++) {
            String vId = parts[idx++];
            String vName = parts[idx++];
            int vAge = Integer.parseInt(parts[idx++]);
            String vMembership = parts[idx++];
            Visitor visitor = new Visitor(vId, vName, vAge, vMembership);
            attraction.recordVisitor(visitor);
        }
        if (operator != null) {
            attraction.assignOperator(operator);
        }

        return attraction;
    }
}