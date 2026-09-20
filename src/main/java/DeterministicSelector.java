public class DeterministicSelector {

    private long comparisons;
    private long swaps;
    private long recursiveCalls;
    private int maxRecursionDepth;

    public int select(int[] array, int k) {

        comparisons = 0;
        swaps = 0;
        recursiveCalls = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        return select(array, 0, array.length - 1, k, 1);
    }

    private int select(int[] array,
                       int left,
                       int right,
                       int k,
                       int depth) {

        recursiveCalls++;
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left == right) {
            return array[left];
        }

        int pivotIndex = medianOfMedians(array, left, right);

        pivotIndex = partition(
                array,
                left,
                right,
                pivotIndex
        );

        if (k == pivotIndex) {
            return array[k];
        }

        if (k < pivotIndex) {
            return select(array, left, pivotIndex - 1, k, depth + 1);
        }

        return select(array, pivotIndex + 1, right, k, depth + 1);
    }

    private int medianOfMedians(int[] array, int left, int right) {

        int size = right - left + 1;

        if (size <= 5) {
            insertionSort(array, left, right);
            return left + size / 2;
        }

        int numberOfGroups = (size + 4) / 5;

        for (int i = 0; i < numberOfGroups; i++) {

            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);

            insertionSort(array, groupLeft, groupRight);

            int median = groupLeft +
                    (groupRight - groupLeft) / 2;

            swap(array, left + i, median);
        }

        int medianOfMediansIndex =
                left + numberOfGroups / 2;

        return select(
                array,
                left,
                left + numberOfGroups - 1,
                medianOfMediansIndex,
                1
        ) == array[medianOfMediansIndex]
                ? medianOfMediansIndex
                : findIndex(
                array,
                left,
                left + numberOfGroups - 1,
                select(
                        array,
                        left,
                        left + numberOfGroups - 1,
                        medianOfMediansIndex,
                        1
                )
        );
    }

    private int findIndex(int[] array,
                          int left,
                          int right,
                          int value) {

        for (int i = left; i <= right; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return left;
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

    private void insertionSort(int[] array,
                               int left,
                               int right) {

        for (int i = left + 1; i <= right; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >= left) {

                comparisons++;

                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
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