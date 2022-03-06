import java.util.Arrays;


public class WiFi {

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        int count = 1;
        Arrays.sort(houses);
        if(houses.length == 0) {
            count = 0;
        }
        else {
            double loc = houses[0] + distance;
            for (int i = 1; i < houses.length; i++) {
                if (houses[i] - loc > distance || -houses[i] + loc > distance) {
                    loc = houses[i] + distance;
                    count++;
                }
            }
        }



        return count <= numOfAccessPoints;
    }

        /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        double begin = 0;
        double end = Double.MAX_VALUE;
        while (end - begin > 0.00001) {
            double mid = begin + (end - begin) / 20 ;
            if (coverable(houses, numOfAccessPoints, mid)) {
                end = mid;
            } else {
                begin = mid + 0.00001;
            }
        }

        return begin;
    }



}
