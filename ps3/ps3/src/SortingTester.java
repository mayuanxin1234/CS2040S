import java.util.Arrays;

public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] testArray = new KeyValuePair[size];
        for(int j = 0; j < size; j++) {
            int randomValue = (int)(Math.random() * (size - j) + j);
            testArray[j] = new KeyValuePair(size - j, 10);

        }
        sorter.sort(testArray);
        System.out.println(Arrays.toString(testArray));
        for(int i = 0; i < size - 1; i ++) {
            if(testArray[i].getKey() > testArray[i + 1].getKey()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];
        int j = 0;
        while( j < size) {
            int randomValue = (int)(Math.random() * (size - j) + j);
            testArray[j] = new KeyValuePair(randomValue, j);
            j++;
        }
        sorter.sort(testArray);
        for(int i = 0; i < size - 1; i ++) {
            if(testArray[i].getKey() == testArray[i + 1].getKey()) {
                if(testArray[i].getValue() > testArray[i + 1].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // TODO: implement this

        int size = 1000;
        KeyValuePair[] testArray = new KeyValuePair[size + 1];
        for(int j = 0; j < size; j++) {
            int randomValue = (int)(Math.random() * (size - j) + j);
            testArray[j] = new KeyValuePair(j, randomValue);
        }
        testArray[size] = new KeyValuePair(5, 5);
        StopWatch watch = new StopWatch();
        ISort sortingObject = new SorterC();
        watch.start();
        sortingObject.sort(testArray);
        watch.stop();
        System.out.println("Time: " + watch.getTime());


/*

        ISort sortingObjectA = new SorterA();
        ISort sortingObjectB = new SorterB();
        ISort sortingObjectC = new SorterC();
        ISort sortingObjectD = new SorterD();
        ISort sortingObjectE = new SorterE();
        ISort sortingObjectF = new SorterF();
        System.out.println(checkSort(sortingObjectB, 2));

 */


    }
}
