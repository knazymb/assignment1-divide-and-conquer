import java.util.Arrays;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Set;

public class ClosestPairSolver {

    private long comparisons;
    private long recursiveCalls;
    private int maxRecursionDepth;

    public double findClosestDistance(Point[] points) {

        comparisons = 0;
        recursiveCalls = 0;
        maxRecursionDepth = 0;

        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] pointsByX = points.clone();

        Arrays.sort(
                pointsByX,
                Comparator.comparingDouble(point -> point.x)
        );

        Point[] pointsByY = points.clone();

        Arrays.sort(
                pointsByY,
                Comparator.comparingDouble(point -> point.y)
        );

        return closestPair(
                pointsByX,
                pointsByY,
                1
        );
    }

    private double closestPair(
            Point[] pointsByX,
            Point[] pointsByY,
            int depth) {

        recursiveCalls++;

        maxRecursionDepth =
                Math.max(maxRecursionDepth, depth);

        int n = pointsByX.length;

        if (n <= 3) {
            return bruteForce(pointsByX);
        }

        int middle = n / 2;

        Point[] leftByX =
                Arrays.copyOfRange(
                        pointsByX,
                        0,
                        middle
                );

        Point[] rightByX =
                Arrays.copyOfRange(
                        pointsByX,
                        middle,
                        n
                );

        Point middlePoint =
                pointsByX[middle];

        Set<Point> leftPoints =
                java.util.Collections.newSetFromMap(
                        new IdentityHashMap<>()
                );

        for (Point point : leftByX) {
            leftPoints.add(point);
        }

        Point[] leftByY =
                new Point[leftByX.length];

        Point[] rightByY =
                new Point[rightByX.length];

        int leftIndex = 0;
        int rightIndex = 0;

        for (Point point : pointsByY) {

            comparisons++;

            if (leftPoints.contains(point)) {

                leftByY[leftIndex++] = point;

            } else {

                rightByY[rightIndex++] = point;
            }
        }

        double leftDistance =
                closestPair(
                        leftByX,
                        leftByY,
                        depth + 1
                );

        double rightDistance =
                closestPair(
                        rightByX,
                        rightByY,
                        depth + 1
                );

        double distance =
                Math.min(
                        leftDistance,
                        rightDistance
                );

        Point[] strip =
                new Point[n];

        int stripSize = 0;

        for (Point point : pointsByY) {

            comparisons++;

            if (Math.abs(
                    point.x - middlePoint.x
            ) < distance) {

                strip[stripSize++] = point;
            }
        }

        for (int i = 0; i < stripSize; i++) {

            for (int j = i + 1;
                 j < stripSize &&
                         strip[j].y - strip[i].y < distance;
                 j++) {

                comparisons++;

                double currentDistance =
                        strip[i].distanceTo(strip[j]);

                if (currentDistance < distance) {
                    distance = currentDistance;
                }
            }
        }

        return distance;
    }

    private double bruteForce(Point[] points) {

        double minDistance =
                Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1;
                 j < points.length;
                 j++) {

                comparisons++;

                double distance =
                        points[i].distanceTo(points[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getRecursiveCalls() {
        return recursiveCalls;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}