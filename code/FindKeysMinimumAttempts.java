import java.util.Arrays;

public class FindKeysMinimumAttempts implements IFindKeys {
    @Override
    public int[] findKeys(int N, int k, ITreasureExtractor treasureExtractor) {
        // TODO: Problem 1 -- Implement strategy to find correct keys with minimum attempts
            int[] keyArray = new int[N];
        for(int i = 0; i < N ; i++) {

            keyArray[i] = 1;

        }
        int key = N;
            for(int j = 0; j < N; j++) {
                keyArray[j] = 0;
                key--;
                if(key < k) {
                    keyArray[j] = 1;
                    break;
                }
                if (!treasureExtractor.tryUnlockChest(keyArray)) {
                    keyArray[j] = 1;
                    key++;
                } else {
                    keyArray[j] = 0;
                }

        }

        return keyArray;
    }
}
