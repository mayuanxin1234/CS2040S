import java.util.Arrays;

public class FindKeysLowestCost implements IFindKeys {

    @Override
    public int[] findKeys(int N, int k, ITreasureExtractor treasureExtractor) {
        // TODO: Problem 2 -- Implement strategy to find correct keys with minimum cost (least number of keys used in total across all attempts)
        int[] keyArray = new int[N];
        for (int i = 0; i < N; i++) {
            keyArray[i] = 0;
        }
        int key = 0;
        for(int a = 0; a < N - 1; a++) {
            if (key == k) {
                if (treasureExtractor.tryUnlockChest(keyArray)) {
                    break;
                }
            }
            for (int i = 0; i < N; i++) {
                keyArray[i] = 0;
        }
            keyArray[a] = 1;
            key = 1;
                for (int j = 0; j < N; j++) {
                    System.out.println(Arrays.toString(keyArray));

                    if(keyArray[j] != 1) {

                        keyArray[j] = 1;
                        key++;
                        System.out.println(Arrays.toString(keyArray));

                    }
                    if (key == k) {
                        if (!treasureExtractor.tryUnlockChest(keyArray)) {
                            System.out.println(Arrays.toString(keyArray));
                            keyArray[j] = 0;
                            key--;

                        } else {

                            break;
                        }
                    }
                }
            }

        return keyArray;
    }


}
