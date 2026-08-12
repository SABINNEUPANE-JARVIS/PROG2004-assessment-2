public class Staff {
    private String id;
    private String name;    
    private int age;
    private String role;
    /**
     * Creates a staff member.
     *
     * @param id staff ID
     * @param name staff name
     * @param age staff age
     * @param role staff role
     */
    public Staff(String id, String name, int age, String role) {
       if (id ==null || !id.matches("[0-9]+")) {
            throw new IllegalArgumentException
            ("Staff ID must contain numbers only.");
       }
       if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be empty.");
       }
       if (age <= 18) {
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
}
