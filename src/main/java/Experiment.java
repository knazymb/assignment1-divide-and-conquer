import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    private static final int[] SIZES = {
            100,
            500,
            1000,
            5000,
            10000
    };

    private static final Random RANDOM = new Random();

    public static void runExperiments() {

        File resultsFile = new File("results/results.csv");

        try (FileWriter writer = new FileWriter(resultsFile)) {

            writer.write(
                    "algorithm,input_type,n,time_ns,comparisons,swaps,recursive_calls,max_recursion_depth\n"
            );

            for (int n : SIZES) {

                // MergeSort
                runSortingExperiment(
                        writer,
                        "MergeSort",
                        "random",
                        createRandomArray(n)
                );

                runSortingExperiment(
                        writer,
                        "MergeSort",
                        "sorted",
                        createSortedArray(n)
                );

                runSortingExperiment(
                        writer,
                        "MergeSort",
                        "reverse",
                        createReverseArray(n)
                );

                runSortingExperiment(
                        writer,
                        "MergeSort",
                        "duplicates",
                        createDuplicateArray(n)
                );

                // QuickSort
                runSortingExperiment(
                        writer,
                        "QuickSort",
                        "random",
                        createRandomArray(n)
                );

                runSortingExperiment(
                        writer,
                        "QuickSort",
                        "sorted",
                        createSortedArray(n)
                );

                runSortingExperiment(
                        writer,
                        "QuickSort",
                        "reverse",
                        createReverseArray(n)
                );

                runSortingExperiment(
                        writer,
                        "QuickSort",
                        "duplicates",
                        createDuplicateArray(n)
                );

                // Deterministic Select
                runSelectExperiment(
                        writer,
                        n,
                        "random"
                );

                // Closest Pair
                runClosestPairExperiment(
                        writer,
                        n
                );
            }

            System.out.println(
                    "Experiments completed successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error writing results: " + e.getMessage()
            );
        }
    }

    private static void runSortingExperiment(
            FileWriter writer,
            String algorithm,
            String inputType,
            int[] originalArray) throws IOException {

        int[] array = originalArray.clone();

        long start = System.nanoTime();

        long comparisons;
        long swaps;
        long recursiveCalls;
        int maxDepth;

        if (algorithm.equals("MergeSort")) {

            MergeSorter sorter = new MergeSorter();

            sorter.sort(array);

            comparisons = sorter.getComparisons();
            swaps = 0;
            recursiveCalls = sorter.getRecursiveCalls();
            maxDepth = sorter.getMaxRecursionDepth();

        } else {

            QuickSorter sorter = new QuickSorter();

            sorter.sort(array);

            comparisons = sorter.getComparisons();
            swaps = sorter.getSwaps();
            recursiveCalls = sorter.getRecursiveCalls();
            maxDepth = sorter.getMaxRecursionDepth();
        }

        long end = System.nanoTime();

        long time = end - start;

        writer.write(
                algorithm + "," +
                        inputType + "," +
                        array.length + "," +
                        time + "," +
                        comparisons + "," +
                        swaps + "," +
                        recursiveCalls + "," +
                        maxDepth + "\n"
        );
    }

    private static void runSelectExperiment(
            FileWriter writer,
            int n,
            String inputType) throws IOException {

        int[] array = createRandomArray(n);

        int k = n / 2;

        DeterministicSelector selector =
                new DeterministicSelector();

        long start = System.nanoTime();

        selector.select(array, k);

        long end = System.nanoTime();

        long time = end - start;

        writer.write(
                "DeterministicSelect" + "," +
                        inputType + "," +
                        n + "," +
                        time + "," +
                        selector.getComparisons() + "," +
                        selector.getSwaps() + "," +
                        selector.getRecursiveCalls() + "," +
                        selector.getMaxRecursionDepth() + "\n"
        );
    }

    private static void runClosestPairExperiment(
            FileWriter writer,
            int n) throws IOException {

        Point[] points = createRandomPoints(n);

        ClosestPairSolver solver =
                new ClosestPairSolver();

        long start = System.nanoTime();

        solver.findClosestDistance(points);

        long end = System.nanoTime();

        long time = end - start;

        writer.write(
                "ClosestPair" + "," +
                        "random" + "," +
                        n + "," +
                        time + "," +
                        solver.getComparisons() + "," +
                        0 + "," +
                        solver.getRecursiveCalls() + "," +
                        solver.getMaxRecursionDepth() + "\n"
        );
    }

    private static int[] createRandomArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = RANDOM.nextInt(1_000_000);
        }

        return array;
    }

    private static int[] createSortedArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = i;
        }

        return array;
    }

    private static int[] createReverseArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = n - i;
        }

        return array;
    }

    private static int[] createDuplicateArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = RANDOM.nextInt(10);
        }

        return array;
    }

    private static Point[] createRandomPoints(int n) {

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {

            double x =
                    RANDOM.nextDouble() * 1_000_000;

            double y =
                    RANDOM.nextDouble() * 1_000_000;

            points[i] =
                    new Point(x, y);
        }

        return points;
    }
}