
import java.util.Arrays;
class InversionCounter {
    public static long countSwaps(int[] arr) {
        int[] temp = new int[arr.length];
        return sort(arr, temp,0, arr.length - 1);
    }

    public static long sort(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            long counter = 0;
            counter = sort(arr, temp, left, middle) + sort(arr,temp, middle + 1, right) +
                    mergeHelper(arr, temp, left, middle, middle + 1, right);
            return counter;
        } else {
            return 0;
        }
    }
    public static long mergeHelper(int[] arr, int[] temp, int left1, int right1, int left2, int right2) {
        int index = left1;
        int left = left1;
        long counter = 0;
        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] <= arr[left2]) {
                temp[index] = arr[left1];
                left1++;
            } else {
                counter += (right1 - left1 + 1);
                temp[index] = arr[left2];
                left2++;
            }
            index++;
        }
        while (left1 <= right1) {
            temp[index] = arr[left1];
            left1++;
            index++;
        }
        while (left2 <= right2) {
            temp[index] = arr[left2];
            left2++;
            index++;
        }
        for (int i = left; i <= right2; i++) {
            arr[i] = temp[i];
        }
        return counter;
    }
    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int[] temp = new int[arr.length];
        return mergeHelper(arr, temp, left1, right1, left2, right2);
    }


}