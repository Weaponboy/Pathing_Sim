package Pathing.Follower;

import Pathing.PathingUtility.PathingPower;
import Pathing.PathingUtility.PathingVelocity;
import Pathing.PathingUtility.RobotPower;
import RobotUtilities.PIDController;

import java.util.ArrayList;



public class mecanumFollower {

    double yI = 0;
    double xI = 0;

    boolean gotToEnd = false;

    PIDController xerror = new PIDController(0.09, 0, 0.00);
    PIDController yerror = new PIDController(0.09, 0, 0.0);

    PIDController xerrorC = new PIDController(0.01, 0, 0.05);
    PIDController yerrorC = new PIDController(0.01, 0, 0.05);

    PIDController headingPID = new PIDController(0.06, 0, 0.000);

    FollowPath pathfollow;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int pathLength() {
        return pathfollow.followablePath.size();
    }

    int currentIndex = 0;

    public void setPath(ArrayList<Vector2D> trajectory, ArrayList<PathingVelocity> pathingVelocity){
        pathfollow = new FollowPath(trajectory, pathingVelocity);
    }

    public void setPath(ArrayList<Vector2D> trajectory, ArrayList<PathingVelocity> pathingVelocity, ArrayList<Vector2D> curve){
        pathfollow = new FollowPath(trajectory, pathingVelocity, curve);
    }

    public void resetClosestPoint(Vector2D robotPos){
        pathfollow.getClosestPositionOnPathFullPath(robotPos);
    }

    public PathingPower getFullPathingPower(Vector2D robotPos, double XVelo, double YVelo){

        Vector2D error;
        PathingPower actualPathingPower = new PathingPower();

        double kyfull = 0.0079;
        double kxfull = 0.0053;

        double ky = 0.0053;
        double kx = 0.00346;

        PathingVelocity pathingVelocity;
        int closestPos = pathfollow.getClosestPositionOnPath(robotPos);
        pathingVelocity = pathfollow.getTargetVelocity(closestPos);

        currentIndex = closestPos;

        double curveY = Math.abs(YVelo*1.2)/2;
        double curveX = Math.abs(XVelo*1.2)/2;

        int index = closestPos + 40;

        if (index > pathfollow.pathCurve.size()-1){
            index = pathfollow.pathCurve.size()-1;
        }

        double XpowerCurve;
        double YpowerCurve;

        XpowerCurve = pathfollow.pathCurve.get(index).getX()*curveX;
        YpowerCurve = pathfollow.pathCurve.get(index).getY()*curveY;

        System.out.println("X curve " + XpowerCurve);
        System.out.println("Y curve " + YpowerCurve);

        error = pathfollow.getErrorToPath(robotPos, closestPos);

        double xDist = error.getX();
        double yDist = error.getY();

        double xPowerC = xerror.calculate(xDist);
        double yPowerC = yerror.calculate(yDist);

        double veloXDef = pathingVelocity.getXVelocity() - XVelo;
        double veloYDef = pathingVelocity.getYVelocity() - YVelo;

        double vertical = kxfull * (pathingVelocity.getXVelocity()+veloXDef);
        double horizontal = kyfull * (pathingVelocity.getYVelocity()+veloYDef);

        if(horizontal > 1){
            vertical = kx * (pathingVelocity.getXVelocity()+veloXDef);
            horizontal = ky * (pathingVelocity.getYVelocity()+veloYDef);
        }

        double Xdenominator = Math.max(Math.abs(vertical) + Math.abs(xPowerC) + Math.abs(XpowerCurve), 1);
        double Ydenominator = Math.max(Math.abs(horizontal) + Math.abs(yPowerC) + Math.abs(YpowerCurve), 1);

        actualPathingPower.set((vertical+xPowerC+XpowerCurve)/Xdenominator, (horizontal+yPowerC+YpowerCurve)/Ydenominator);

        return actualPathingPower;
    }

    public PathingPower getCorrectivePowerAtEnd(Vector2D robotPos, Vector2D targetPos, double heading){

        Vector2D error;
        PathingPower correctivePower = new PathingPower();

        error = new Vector2D( targetPos.getX() - robotPos.getX(),  targetPos.getY() - robotPos.getY());

        double xDist = error.getX();
        double yDist = error.getY();

        double robotRelativeXError = yDist * Math.sin(Math.toRadians(heading)) + xDist * Math.cos(Math.toRadians(heading));
        double robotRelativeYError = yDist * Math.cos(Math.toRadians(heading)) - xDist * Math.sin(Math.toRadians(heading));

        double xPower = xerrorC.calculate(robotRelativeXError);
        double yPower = yerrorC.calculate(robotRelativeYError);

        correctivePower.set(xPower, yPower);

        return correctivePower;
    }

    public RobotPower followPathAuto(double targetHeading, double H, double X, double Y, double XV, double YV){

        Vector2D robotPositionVector = new Vector2D();

        Vector2D targetPoint = pathfollow.getPointOnFollowable(pathfollow.getLastPoint());

        robotPositionVector.set(X, Y);

        PathingPower correctivePower = new PathingPower();
        PathingPower pathingPower;

        pathingPower = getFullPathingPower(robotPositionVector, XV, YV);

        double Xpower = correctivePower.getVertical() + pathingPower.getVertical();
        double Ypower = correctivePower.getHorizontal() + pathingPower.getHorizontal();

        return new RobotPower(Xpower, Ypower, getTurnPower(targetHeading, H));
    }

    public double getTurnPower(double targetHeading, double currentHeading){

        double turnPower;

        double rotdist = (targetHeading - currentHeading);

        if (rotdist < -180) {
            rotdist = (360 + rotdist);
        } else if (rotdist > 180) {
            rotdist = (rotdist - 360);
        }

        turnPower = headingPID.calculate(-rotdist);

        return turnPower;
    }
}
