import java.sql.SQLOutput;
import java.util.TreeSet;
import java.util.Comparator;
class Quest implements Comparable<Quest> {
    public long E;
    public long G;
    public Quest(long energy, long value) {
        this.E = energy;
        this.G = value;
    }

    @Override
    public int compareTo(Quest q) {
        if (this.E > q.E) {
            return 1;
        } else if (this.E == q.E) {
            if (this.G > G) {
                return 1;
            } else if (this.G == q.G) {
                return 0;
            }
            return -1;
        }
        return -1;
    }
}
class questComparator implements Comparator<Quest> {

    public int compare(Quest q1, Quest q2) {
        return q1.compareTo(q2);
    }

}
public class Solution {
    // TODO: Include your data structures here
    TreeSet<Quest> ts = new TreeSet<Quest>();


    public Solution() {
        // TODO: Construct/Initialise your data structures here
    }

    void add(long energy, long value) {
        // TODO: Implement your insertion operation here
        Quest q = new Quest(energy, value);
        ts.add(q);
    }

    long query(long remainingEnergy) {
        // TODO: Implement your query operation here
        Quest q = new Quest(remainingEnergy, 0);
        if (ts.lower(q) == null) {
            return 0;
        }
        Quest q1 = ts.lower(q);
        long energy = q1.E;
        long gold = q1.G;
        Quest q2 = new Quest(energy,gold);
        ts.remove(q2);
        return gold + query(remainingEnergy - energy);
    }

}
