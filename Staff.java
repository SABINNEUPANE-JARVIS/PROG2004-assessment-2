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
     // ID must be purely numeric per the brief's requirement
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
