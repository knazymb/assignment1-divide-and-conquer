import java.util.Arrays;
import java.util.Random;

public class AlgorithmTest {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println();
        System.out.println("All tests passed!");
    }

    private static void testMergeSort() {

        System.out.println("Testing MergeSort...");

        testSorting(
                "MergeSort",
                createRandomArray(100),
                true
        );

        testSorting(
                "MergeSort",
                createSortedArray(100),
                true
        );

        testSorting(
                "MergeSort",
                createReverseArray(100),
                true
        );

        testSorting(
                "MergeSort",
                createDuplicateArray(100),
                true
        );

        testSorting(
                "MergeSort",
                new int[]{},
                true
        );

        testSorting(
                "MergeSort",
                new int[]{42},
                true
        );

        System.out.println("MergeSort passed.");
    }

    private static void testQuickSort() {

        System.out.println("Testing QuickSort...");

        testSorting(
                "QuickSort",
                createRandomArray(100),
                false
        );

        testSorting(
                "QuickSort",
                createSortedArray(100),
                false
        );

        testSorting(
                "QuickSort",
                createReverseArray(100),
                false
        );

        testSorting(
                "QuickSort",
                createDuplicateArray(100),
                false
        );

        testSorting(
                "QuickSort",
                new int[]{},
                false
        );

        testSorting(
                "QuickSort",
                new int[]{42},
                false
        );

        System.out.println("QuickSort passed.");
    }

    private static void testSorting(
            String algorithm,
            int[] input,
            boolean useMergeSort) {

        int[] expected = input.clone();

        Arrays.sort(expected);

        int[] actual = input.clone();

        if (useMergeSort) {

            MergeSorter sorter = new MergeSorter();
            sorter.sort(actual);

        } else {

            QuickSorter sorter = new QuickSorter();
            sorter.sort(actual);
        }

        if (!Arrays.equals(actual, expected)) {

            throw new AssertionError(
                    algorithm + " failed.\n"
                            + "Expected: "
                            + Arrays.toString(expected)
                            + "\nActual: "
                            + Arrays.toString(actual)
            );
        }
    }

    private static void testDeterministicSelect() {

        System.out.println(
                "Testing Deterministic Select..."
        );

        for (int test = 1; test <= 100; test++) {

            int size = 1 + RANDOM.nextInt(100);

            int[] array =
                    createRandomArray(size);

            int k =
                    RANDOM.nextInt(size);

            int[] sorted = array.clone();

            Arrays.sort(sorted);

            int expected = sorted[k];

            DeterministicSelector selector =
                    new DeterministicSelector();

            int actual =
                    selector.select(array, k);

            if (actual != expected) {

                throw new AssertionError(
                        "Deterministic Select failed on test "
                                + test
                                + ". Expected: "
                                + expected
                                + ", actual: "
                                + actual
                );
            }
        }

        System.out.println(
                "Deterministic Select passed 100 tests."
        );
    }

    private static void testClosestPair() {

        System.out.println(
                "Testing Closest Pair..."
        );

        for (int test = 1; test <= 50; test++) {

            int size =
                    2 + RANDOM.nextInt(100);

            Point[] points =
                    createRandomPoints(size);

            ClosestPairSolver solver =
                    new ClosestPairSolver();

            double actual =
                    solver.findClosestDistance(points);

            double expected =
                    bruteForceClosestPair(points);

            if (Math.abs(actual - expected) > 1e-9) {

                throw new AssertionError(
                        "Closest Pair failed on test "
                                + test
                                + ". Expected: "
                                + expected
                                + ", actual: "
                                + actual
                );
            }
        }

        System.out.println(
                "Closest Pair passed 50 tests."
        );
    }

    private static double bruteForceClosestPair(
            Point[] points) {

        double minDistance =
                Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1;
                 j < points.length;
                 j++) {

                double distance =
                        points[i].distanceTo(points[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    private static int[] createRandomArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] =
                    RANDOM.nextInt(1000);
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
            array[i] =
                    RANDOM.nextInt(5);
        }

        return array;
    }

    private static Point[] createRandomPoints(int n) {

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {

            double x =
                    RANDOM.nextDouble() * 1000;

            double y =
                    RANDOM.nextDouble() * 1000;

            points[i] =
                    new Point(x, y);
        }

        return points;
    }
}