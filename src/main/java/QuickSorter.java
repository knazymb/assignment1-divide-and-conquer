import java.util.Random;

public class QuickSorter {

    private final Random random = new Random();

    private long comparisons;
    private long swaps;
    private long recursiveCalls;
    private int maxRecursionDepth;

    public void sort(int[] array) {

        comparisons = 0;
        swaps = 0;
        recursiveCalls = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private void quickSort(
            int[] array,
            int left,
            int right,
            int depth) {

        while (left < right) {

            recursiveCalls++;

            maxRecursionDepth =
                    Math.max(maxRecursionDepth, depth);

            int pivotIndex =
                    left + random.nextInt(right - left + 1);

            int pivot = array[pivotIndex];

            int[] equalRange =
                    partition(array, left, right, pivot);

            int lessEnd = equalRange[0];
            int greaterStart = equalRange[1];

            int leftSize = lessEnd - left;
            int rightSize = right - greaterStart;

            if (leftSize < rightSize) {

                quickSort(
                        array,
                        left,
                        lessEnd - 1,
                        depth + 1
                );

                left = greaterStart + 1;

            } else {

                quickSort(
                        array,
                        greaterStart + 1,
                        right,
                        depth + 1
                );

                right = lessEnd - 1;
            }
        }
    }

    private int[] partition(
            int[] array,
            int left,
            int right,
            int pivot) {

        int less = left;
        int current = left;
        int greater = right;

        while (current <= greater) {

            comparisons++;

            if (array[current] < pivot) {

                swap(array, less, current);

                less++;
                current++;

            } else if (array[current] > pivot) {

                swap(array, current, greater);

                greater--;

            } else {

                current++;
            }
        }

        return new int[]{less, greater};
    }

    private void swap(
            int[] array,
            int i,
            int j) {

        if (i == j) {
            return;
        }

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getRecursiveCalls() {
        return recursiveCalls;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}