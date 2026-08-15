public class Staff {
    private String id;
    private String name;    
    private int age;
    private String role;
    /**
     * Creates a staff member after validating the supplied information.
     *
     * @param id staff ID, containing numbers only
     * @param name staff name
     * @param age staff age
     * @param role staff role
     * @throws IllegalArgumentException if any supplied information is invalid
     */
    public Staff(String id, String name, int age, String role) {
     // ID must be purely numeric 
          if (id == null || !id.matches("[0-9]+")) {
                throw new IllegalArgumentException
                ("Staff ID must contain numbers only.");
          }
         
          if (name == null || name.trim().isEmpty()) {
               throw new IllegalArgumentException("Staff name cannot be empty.");
          }
          // Staff must be an adult
          if (age < 18) {
               throw new IllegalArgumentException("Staff must be at least 18 years old.");
          }
          if (role == null || role.trim().isEmpty()) {
               throw new IllegalArgumentException("Staff role cannot be empty.");
          }
          this.id = id;
          this.name = name;        
          this.age = age;
          this.role = role;

    }
    /**
     * Returns the staff member's ID.
     *
     * @return staff ID
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the staff member's name.
     *
     * @return staff name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the staff member's age.
     *
     * @return staff age
     */
    public int getAge() {
        return age;
    }
    /**
     * Returns the staff member's role.
     *
     * @return staff role
     */
    public String getRole() {
        return role;
    }
    /**
     * Performs an inspection on any Inspectable item.
     * @param item the item to inspect
     * @return the inspection result
    */
    public String performInspection(Inspectable item) {
        if (item == null) {
            return "Cannot inspect null item";
        }
        
        System.out.println("Staff " + name + " is inspecting " + item.getInspectionName());
        
        // Step 1: Close the item for inspection
        item.closeForInspection();
        
        // Step 2: Perform the inspection (simulated)
        String result = "Inspection of " + item.getInspectionName() + 
                        " completed by " + name + ": All systems operational";
        System.out.println(result);
        
        // Step 3: Record the result on the item
        item.recordInspectionResult(result);
        
        // Step 4: Reopen the item
        item.reopenAfterInspection();
        
        return result;
    }
    
    /**
     * Returns a readable representation of the staff member.
     *
     * @return staff details
     */
    @Override
    public String toString() {
     return "Staff ID: " +id
                +" | Name: " +name
                +" | Age: " +age    
               +" | Role: " +role;          
    }

}
