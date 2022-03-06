import java.util.TreeSet;
 class Score implements Comparable<Score> {
    int id;
    int solve;
    int penalty;
    public Score(int id, int solve, int penalty) {
        this.id = id;
        this.solve = solve;
        this.penalty = penalty;
    }

    @Override
    public int compareTo(Score s) {
        if (this.solve != s.solve) {
            return s.solve - this.solve;
        } else {
            if (this.penalty != s.penalty) {
                return this.penalty - s.penalty;
            } else {
                return this.id - s.id;
            }
        }
    }
}
public class Solution {

    // TODO: Include your data structures here
    TreeSet<Score> ts = new TreeSet<Score>();
    Score[] scores;



    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        scores = new Score[numTeams];
        for (int i = 0; i < numTeams; i++) {
            scores[i] = new Score(i + 1, 0,0);
        }

    }

    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        ts.remove(scores[team - 1]);
        scores[team - 1].solve++;
        scores[team - 1].penalty += newPenalty;
        if (team != 1) {
            if (scores[0].compareTo(scores[team - 1]) > 0) {
                ts.add(scores[team - 1]);
            }


        } else {
            while (!ts.isEmpty()) {
                Score s = ts.last();
                if (scores[0].compareTo(s) < 0) {
                    ts.remove(s);
                } else {
                    break;
                }
            }


        }
        return ts.size() + 1;
    }

}
