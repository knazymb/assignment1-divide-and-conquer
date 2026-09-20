public class Main {

    public static void main(String[] args) {

        System.out.println("Divide-and-Conquer Algorithms");
        System.out.println();

        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println();
        System.out.println("Running experiments...");
        Experiment.runExperiments();
    }

    private static void testMergeSort() {

        int[] array = {
                8, 3, 5, 1, 9, 2, 7, 4, 6
        };

        MergeSorter sorter = new MergeSorter();

        sorter.sort(array);

        System.out.println("MergeSort:");
        printArray(array);

        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );

        System.out.println(
                "Max recursion depth: "
                        + sorter.getMaxRecursionDepth()
        );

        System.out.println();
    }

    private static void testQuickSort() {

        int[] array = {
                8, 3, 5, 1, 9, 2, 7, 4, 6
        };

        QuickSorter sorter = new QuickSorter();

        sorter.sort(array);

        System.out.println("QuickSort:");
        printArray(array);

        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );

        System.out.println(
                "Swaps: " + sorter.getSwaps()
        );

        System.out.println(
                "Max recursion depth: "
                        + sorter.getMaxRecursionDepth()
        );

        System.out.println();
    }

    private static void testDeterministicSelect() {

        int[] array = {
                8, 3, 5, 1, 9, 2, 7, 4, 6
        };

        int k = 4;

        DeterministicSelector selector =
                new DeterministicSelector();

        int result = selector.select(array, k);

        System.out.println("Deterministic Select:");

        System.out.println(
                "k = " + k
        );

        System.out.println(
                "k-th smallest element: " + result
        );

        System.out.println(
                "Comparisons: "
                        + selector.getComparisons()
        );

        System.out.println(
                "Swaps: "
                        + selector.getSwaps()
        );

        System.out.println();
    }

    private static void testClosestPair() {

        Point[] points = {
                new Point(0, 0),
                new Point(5, 5),
                new Point(1, 1),
                new Point(10, 10),
                new Point(2, 2)
        };

        ClosestPairSolver solver =
                new ClosestPairSolver();

        double distance =
                solver.findClosestDistance(points);

        System.out.println("Closest Pair:");

        System.out.println(
                "Closest distance: " + distance
        );

        System.out.println(
                "Comparisons: "
                        + solver.getComparisons()
        );

        System.out.println(
                "Max recursion depth: "
                        + solver.getMaxRecursionDepth()
        );

        System.out.println();
    }

    private static void printArray(int[] array) {

        for (int value : array) {
            System.out.print(value + " ");
        }

        System.out.println();
    }
}