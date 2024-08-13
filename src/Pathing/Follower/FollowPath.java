package Pathing.Follower;


import Pathing.PathingUtility.PathingVelocity;

import java.util.ArrayList;
import java.util.List;

public class FollowPath {

    ArrayList<Vector2D> followablePath = new ArrayList<>();

    ArrayList<PathingVelocity> pathingVelocity = new ArrayList<>();

    ArrayList<Vector2D> pathCurve = new ArrayList<>();


    int lastPointOnPath;

    public FollowPath(ArrayList<Vector2D> followablePath, ArrayList<PathingVelocity> pathingVelocity){
        this.followablePath = followablePath;
        this.pathingVelocity = pathingVelocity;
        this.pathCurve = calculateCurvatureVectors(followablePath);
    }

    public FollowPath(ArrayList<Vector2D> followablePath, ArrayList<PathingVelocity> pathingVelocity, ArrayList<Vector2D> pathCurve){
        this.followablePath = followablePath;
        this.pathingVelocity = pathingVelocity;
        this.pathCurve = calculateCurvatureVectors(followablePath);
    }

    private static ArrayList<Vector2D> computeFirstDerivatives(ArrayList<Vector2D> points) {
        int numPoints = points.size();
        ArrayList<Vector2D> derivatives = new ArrayList<>();

        for (int i = 0; i < numPoints - 1; i++) {
            double deltaX = points.get(i + 1).getX() - points.get(i).getX();
            double deltaY = points.get(i + 1).getY() - points.get(i).getY();

            derivatives.add(new Vector2D(deltaX, deltaY));
        }

        derivatives.add(derivatives.get(derivatives.size() - 1));

        return derivatives;
    }

    public ArrayList<Vector2D> computeSecondDerivatives(ArrayList<Vector2D> derivatives) {
        int numPoints = derivatives.size();
        ArrayList<Vector2D> secondDerivatives = new ArrayList<>();

        for (int i = 0; i < numPoints - 1; i++) {
            double deltaX = derivatives.get(i + 1).getX() - derivatives.get(i).getX();
            double deltaY = derivatives.get(i + 1).getY() - derivatives.get(i).getY();
            secondDerivatives.add(new Vector2D(deltaX, deltaY));
        }
        secondDerivatives.add(secondDerivatives.get(secondDerivatives.size() - 1));  // Handling last point second derivative
        return secondDerivatives;
    }

    public ArrayList<Double> calculateScalarCurvature(ArrayList<Vector2D> points) {
        ArrayList<Vector2D> firstDerivatives = computeFirstDerivatives(points);
        ArrayList<Vector2D> secondDerivatives = computeSecondDerivatives(firstDerivatives);
        ArrayList<Double> curvatureValues = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            double xPrime = firstDerivatives.get(i).getX();
            double yPrime = firstDerivatives.get(i).getY();
            double xDoublePrime = secondDerivatives.get(i).getX();
            double yDoublePrime = secondDerivatives.get(i).getY();

            double numerator = xPrime * yDoublePrime - yPrime * xDoublePrime;
            double denominator = Math.pow(xPrime * xPrime + yPrime * yPrime, 1.5);

            double curvature = 0;
            if (denominator != 0) {
                curvature = numerator / denominator;
            }
            curvatureValues.add(curvature);
        }
        curvatureValues.add(curvatureValues.get(curvatureValues.size() - 1));  // Handle the last point

        return curvatureValues;
    }

    public ArrayList<Vector2D> calculateCurvatureVectors(ArrayList<Vector2D> points) {
        ArrayList<Vector2D> firstDerivatives = computeFirstDerivatives(points);
        ArrayList<Double> scalarCurvatures = calculateScalarCurvature(points);
        ArrayList<Vector2D> curvatureVectors = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            double xPrime = firstDerivatives.get(i).getX();
            double yPrime = firstDerivatives.get(i).getY();
            double curvature = scalarCurvatures.get(i);

            // Normalize the first derivative
            double norm = Math.sqrt(xPrime * xPrime + yPrime * yPrime);
            double perpX = -yPrime / norm;
            double perpY = xPrime / norm;

            // Curvature vector is the scalar curvature times the perpendicular vector
            curvatureVectors.add(new Vector2D(perpX * curvature, perpY * curvature));
        }
        curvatureVectors.add(curvatureVectors.get(curvatureVectors.size() - 1));  // Handle the last point

        return curvatureVectors;
    }
//
//    public ArrayList<Double> calculateCurvature(ArrayList<Vector2D> points) {
//        ArrayList<Vector2D> firstDerivatives = computeFirstDerivatives(points);
//        ArrayList<Vector2D> secondDerivatives = computeSecondDerivatives(firstDerivatives);
//        ArrayList<Double> curvatureValues = new ArrayList<>();
//
//        for (int i = 0; i < points.size() - 1; i++) {
//            double xPrime = firstDerivatives.get(i).getX();
//            double yPrime = firstDerivatives.get(i).getY();
//            double xDoublePrime = secondDerivatives.get(i).getX();
//            double yDoublePrime = secondDerivatives.get(i).getY();
//
//            double numerator = xPrime * yDoublePrime - yPrime * xDoublePrime;
//            double denominator = Math.pow(xPrime * xPrime + yPrime * yPrime, 1.5);
//
//            double curvature = 0;
//            if (denominator != 0) {
//                curvature = numerator / denominator;
//            }
//            curvatureValues.add(curvature);
//        }
//        curvatureValues.add(curvatureValues.get(curvatureValues.size() - 1));  // Handle the last point
//
//        return curvatureValues;
//
//    }
//
//    public ArrayList<Vector2D> calculateCurvatureVectors(ArrayList<Vector2D> points) {
//        ArrayList<Vector2D> firstDerivatives = computeFirstDerivatives(points);
//        ArrayList<Vector2D> secondDerivatives = computeSecondDerivatives(firstDerivatives);
//        ArrayList<Vector2D> curvatureVectors = new ArrayList<>();
//
//        for (int i = 0; i < points.size() - 1; i++) {
//            double xPrime = firstDerivatives.get(i).getX();
//            double yPrime = firstDerivatives.get(i).getY();
//            double xDoublePrime = secondDerivatives.get(i).getX();
//            double yDoublePrime = secondDerivatives.get(i).getY();
//
//            double denominator = Math.pow(xPrime * xPrime + yPrime * yPrime, 1.5);
//
//            double kappaX = 0;
//            double kappaY = 0;
//            if (denominator != 0) {
//                kappaX = (yDoublePrime * xPrime - xDoublePrime * yPrime) / denominator;
//                kappaY = (xDoublePrime * yPrime - yDoublePrime * xPrime) / denominator;
//            }
//            curvatureVectors.add(new Vector2D(kappaX, kappaY));
//        }
//        curvatureVectors.add(curvatureVectors.get(curvatureVectors.size() - 1));  // Handle the last point
//
//        return curvatureVectors;
//    }

//    public ArrayList<Vector2D> calculatePerpendicularVectors(ArrayList<Vector2D> derivatives) {
//        int numPoints = derivatives.size();
//        ArrayList<Vector2D> perpendicularVectors = new ArrayList<>();
//
//        for (int i = 0; i < numPoints; i++) {
//            double magnitude = derivatives.get(i).getNorm();
//
//            if (magnitude > 0.1) {
//                // Normalize the derivative vector
//                Vector2D normalizedDerivative = new Vector2D(
//                        derivatives.get(i).getX() / magnitude,
//                        derivatives.get(i).getY() / magnitude
//                );
//
//                // Swap x and y components and negate one of them to get perpendicular vectors
//                double perpendicularX = -normalizedDerivative.getY();
//                double perpendicularY = normalizedDerivative.getX();
//
//                perpendicularVectors.add(new Vector2D(perpendicularX, perpendicularY));
//            } else {
//                // If the magnitude is too small, set the perpendicular vector to zero
//                perpendicularVectors.add(new Vector2D(0, 0));
//            }
//        }
//
//        return perpendicularVectors;
//    }

    public double findAngle(PathingVelocity targetVelocity){

        double magnitude = Math.sqrt(targetVelocity.getXVelocity() * targetVelocity.getXVelocity() + targetVelocity.getYVelocity() * targetVelocity.getYVelocity());

        double radians = Math.atan2(targetVelocity.getYVelocity(), targetVelocity.getXVelocity());

        double degrees = Math.toDegrees(radians);

        if (degrees < 0) {
            degrees += 360;
        }

        return degrees;
    }

    public Vector2D findPoint(){
        Vector2D point = new Vector2D();
        return point;
    }

    public double calculateTotalDistance() {

        double totalDistance = 0.0;

        for (int i = 0; i < followablePath.size() - 1; i++) {
            Vector2D point1 = followablePath.get(i);
            Vector2D point2 = followablePath.get(i + 1);
            totalDistance += calculateDistance(point1, point2);
        }
        return totalDistance;
    }

    public double calculateDistancePointToPoint(Vector2D robotPos, Vector2D endpoint) {

        int index = 0;

        int startIndex = getClosestPositionOnPath(robotPos);

        int endIndex = followablePath.size()-1;

        List<Vector2D> subList = followablePath.subList(startIndex, endIndex);

        ArrayList<Vector2D> sectionToLook = new ArrayList<>(subList);

        double totalDistance = 0.0;

        for (int i = 0; i < sectionToLook.size() - 1; i++) {
            Vector2D point1 = sectionToLook.get(i);
            Vector2D point2 = sectionToLook.get(i + 1);
            totalDistance += calculateDistance(point1, point2);
        }
        return totalDistance;
    }

    public double calculateDistance(Vector2D point1, Vector2D point2) {
        double dx = point2.getX() - point1.getX();
        double dy = point2.getY() - point1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int getClosestPositionOnPath(Vector2D robotPos) {

        int index = 0;

        double minDistance = Double.MAX_VALUE;

        for (Vector2D pos : followablePath) {

            double distance = Math.sqrt(
                    Math.pow(robotPos.getX() - pos.getX(), 2) +
                            Math.pow(robotPos.getY() - pos.getY(), 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                index = followablePath.indexOf(pos);
            }

        }

        lastPointOnPath = index;

        if (index+10 > followablePath.size()-1){

        }else {
            index += 10;
        }

        return index;
    }

    public int getClosestPositionOnPathFullPath(Vector2D robotPos) {

        int index = 0;

        double minDistance = Double.MAX_VALUE;

        for (Vector2D pos : followablePath) {

            double distance = Math.sqrt(
                    Math.pow(robotPos.getX() - pos.getX(), 2) +
                            Math.pow(robotPos.getY() - pos.getY(), 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                index = followablePath.indexOf(pos);
            }
        }

        lastPointOnPath = index;

        return index;
    }

    public Vector2D getErrorToPath(Vector2D robotPos, int index) {

        Vector2D error = new Vector2D();

        Vector2D position = getPointOnFollowable(index);

//        double lookaheadDistance = Math.abs(Math.hypot(position.getX() - robotPos.getX(), position.getY() - robotPos.getY()));
//
//        index += (int)lookaheadDistance;

        if (index < followablePath.size()-1){
            position = followablePath.get(index);
        }else {
            position = followablePath.get(followablePath.size()-1);
        }

        error.set(position.getX() - robotPos.getX(), position.getY() - robotPos.getY());

        return error;
    }

    public PathingVelocity getTargetVelocity(int index){

        PathingVelocity targetVelocity = new PathingVelocity();

        if (index > pathingVelocity.size()-1){
            targetVelocity.set(0, 0);
        }else {
            targetVelocity.set(pathingVelocity.get(index).getXVelocity(), pathingVelocity.get(index).getYVelocity());
        }

        return targetVelocity;
    }

    public Vector2D getPointOnFollowable(int index){
        return followablePath.get(index);
    }

    public int getLastPoint(){
        int one;

        if (followablePath.size() == 0){
            one = 0;
        }else {
            one = followablePath.size()-1;
        }

        return one;
    }

}
