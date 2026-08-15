import java.util.Comparator;
/**
 * Comparator for ordering visitors by name, then membership type.
 */
public class VisitorNameMembershipComparator implements Comparator<Visitor> {
/**
 * Compares two visitors by name first and membership type second.
 *
 * @param v1 first visitor
 * @param v2 second visitor
 * @return a negative value, zero, or a positive value depending
 *         on the ordering
 */    
    @Override
    public int compare(Visitor v1, Visitor v2) {
        int nameCmp = v1.getName().compareTo(v2.getName());
        if (nameCmp != 0) {
            return nameCmp;
        }
        return v1.getMembershipType().compareTo(v2.getMembershipType());
    }
}
