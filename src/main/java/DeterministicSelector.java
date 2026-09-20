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

        return selectRange(array, 0, array.length - 1, k, 1);
    }

    private int selectRange(int[] array,
                            int left,
                            int right,
                            int k,
                            int depth) {

        recursiveCalls++;
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left == right) {
            return array[left];
        }

        if (right - left + 1 <= 5) {
            insertionSort(array, left, right);
            return array[k];
        }

        int pivot = medianOfMedians(array, left, right, depth);

        int[] equalRange = partition(
                array,
                left,
                right,
                pivot
        );

        int lessEnd = equalRange[0];
        int greaterStart = equalRange[1];

        if (k < lessEnd) {
            return selectRange(
                    array,
                    left,
                    lessEnd - 1,
                    k,
                    depth + 1
            );
        }

        if (k > greaterStart) {
            return selectRange(
                    array,
                    greaterStart + 1,
                    right,
                    k,
                    depth + 1
            );
        }

        return array[k];
    }

    private int medianOfMedians(int[] array,
                                int left,
                                int right,
                                int depth) {

        int size = right - left + 1;

        int numberOfGroups = (size + 4) / 5;

        for (int i = 0; i < numberOfGroups; i++) {

            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);

            insertionSort(array, groupLeft, groupRight);

            int median = groupLeft +
                    (groupRight - groupLeft) / 2;

            swap(array, left + i, median);
        }

        int medianLeft = left;
        int medianRight = left + numberOfGroups - 1;
        int medianIndex = medianLeft + numberOfGroups / 2;

        return selectRange(
                array,
                medianLeft,
                medianRight,
                medianIndex,
                depth + 1
        );
    }

    private int[] partition(int[] array,
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