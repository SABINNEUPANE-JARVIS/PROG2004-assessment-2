/**
 * Represents a visitor to the park.
 * Visitors can be ordered according to their age.
 */
public class Visitor implements Comparable<Visitor> {
     private String id;
    private String name;
    private int age;
    private String membershipType;
    /**
     * Creates a Visitor object.
     *
     * @param id visitor ID
     * @param name visitor name
     * @param age visitor age
     * @param membershipType visitor membership type
     */
    public Visitor(String id, String name, int age,
                   String membershipType) {
        // The visitor ID determines whether two Visitor objects represent the same person.
        if (id == null || !id.matches("[0-9]+")) {
            throw new IllegalArgumentException(
                    "Visitor ID must contain numbers only.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Visitor name cannot be empty.");
        }
  
        if (age < 0) {
            throw new IllegalArgumentException(
                    "Visitor age cannot be negative.");
        }

        if (membershipType == null
                || membershipType.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Membership type cannot be empty.");
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    } 
      /**
     * Returns the  Visitor ID.
     *
     * @return Visitor ID
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the Visitor name.
     *
     * @return Visitor name
     */
    public String getName() {
        return name;
    } 
    /**
     * Returns the Visitor's age.
     *
     * @return Visitor age
     */
    public int getAge() {
        return age; 
    }
    /**
     * Returns the visitor membership type.
     *
     * @return membership type
     */
    public String getMembershipType() {
        return membershipType;
    }
     /**
     * Compares visitors according to their age.
     *
     * @param other visitor to compare with
     * @return negative, zero, or positive value depending on age
     */
    @Override
    public int compareTo(Visitor other) {
        return Integer.compare(this.age, other.age);
    }

    /**
     * Checks whether two visitors represent the same person.
     * Visitor ID is used as the unique identity.
     *
     * @param obj object to compare
     * @return true if both visitors have the same ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
        return false;
        }

        Visitor other = (Visitor) obj;
        return this.id.equals(other.id);
    }

    /**
     * Returns a hash code based on the visitor ID.
     *
     * @return hash code for this visitor
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Returns a readable representation of the visitor.
     *
     * @return visitor details
     */
    @Override
    public String toString() {
        return "Visitor ID: " + id
                + " | Name: " + name
                + " | Age: " + age
                + " | Membership: " + membershipType;
    }
   
} 