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

    private void quickSort(int[] array, int left, int right, int depth) {

        while (left < right) {

            recursiveCalls++;
            maxRecursionDepth = Math.max(maxRecursionDepth, depth);

            int pivotIndex = left + random.nextInt(right - left + 1);

            int newPivotIndex = partition(
                    array,
                    left,
                    right,
                    pivotIndex
            );

            int leftSize = newPivotIndex - left;
            int rightSize = right - newPivotIndex;

            if (leftSize < rightSize) {

                quickSort(
                        array,
                        left,
                        newPivotIndex - 1,
                        depth + 1
                );

                left = newPivotIndex + 1;

            } else {

                quickSort(
                        array,
                        newPivotIndex + 1,
                        right,
                        depth + 1
                );

                right = newPivotIndex - 1;
            }
        }
    }

    private int partition(int[] array,
                          int left,
                          int right,
                          int pivotIndex) {

        int pivot = array[pivotIndex];

        swap(array, pivotIndex, right);

        int storeIndex = left;

        for (int i = left; i < right; i++) {

            comparisons++;

            if (array[i] < pivot) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }

        swap(array, storeIndex, right);

        return storeIndex;
    }

    private void swap(int[] array, int i, int j) {

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