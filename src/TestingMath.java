import java.awt.geom.Point2D;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestingMath {

    private static final ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

    // Executor service for async logging
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static TestingFileWriting writer = new TestingFileWriting();

    public enum autoState {
        preLoad,
        cycle_one,
        cycle_two,
        cycle_three,
        cycle_four,
        finished;

        public static autoState next(autoState current) {
            autoState[] values = autoState.values();
            int nextIndex = (current.ordinal() + 1) % values.length; // Wrap around to the first enum
            return values[nextIndex];
        }
    }

    public static autoState state = autoState.preLoad;

    public static void main(String[] args) {
        double right = 82000;
        double left = -82000;
        double wheelGapCm = 16;
        double turningCircle = 3.14 * wheelGapCm;
        double trackWidth = 16;
        double cmPerTick = 0.007536;

//        System.out.println(360*0.15);

        double spoolSize = 10.676;
        double cmPerDegree = spoolSize / 360;

        double currentRailPosition = 4;

        double targetX = 125;
        double targetY = 125;
        double currentX = 100;
        double currentY = 100;

        double xError = targetX-currentX;
        double yError = targetY-currentY;

        double slope = Math.atan(yError/xError);

        double degrees = 0;
        double RobotHeading = 16;

        if (xError >= 0 && yError <= 0){
            degrees = (90-(-Math.toDegrees(slope)))+270;
        }else if (xError <= 0 && yError >= 0){
            degrees = (90-(-Math.toDegrees(slope)))+90;
        }else if (xError <= 0 && yError <= 0){
            degrees = Math.toDegrees(slope)+180;
        }else {
            degrees = Math.toDegrees(slope);
        }

        double deltaHeading = Math.toRadians(RobotHeading - degrees);

        double disToTarget = Math.hypot(xError, xError);
        double railDisToTarget = disToTarget*Math.sin(deltaHeading);

        double angleDelta = Math.abs(degrees - RobotHeading);

        double otherAngle = (180 - angleDelta)/2;
        double targetRailPosition;
        double slideTarget;

        if (deltaHeading == 0){
            targetRailPosition = 10;
            slideTarget = disToTarget-20.5;
        }else {
            targetRailPosition = 10 + (railDisToTarget * Math.abs(Math.cos(otherAngle)));
            slideTarget = (disToTarget - (railDisToTarget * Math.abs(Math.sin(otherAngle))))-20.5;
        }

        Point2D.Double targetPointGlobal = new Point2D.Double(141, 100);

        Point2D.Double RobotPosition = new Point2D.Double(110, 104);

        double angle = findAngle(targetPointGlobal, RobotPosition);

        double deltaHeadingAfter = 6.1 - angle;

        if (deltaHeadingAfter < -180) {
            deltaHeadingAfter = -(deltaHeadingAfter + 360);
        } else if (deltaHeadingAfter > 180) {
            deltaHeadingAfter = (360 - deltaHeadingAfter);
        }

//        System.out.println("Delta " + deltaHeadingAfter);
//        System.out.println("deltaHeadingAfter " + Math.toDegrees(deltaHeading));
//        System.out.println("slideTarget  " + Math.abs((railDisToTarget * Math.cos((Math.toRadians(otherAngle))))));

        double YPos = 280;
        double yExtra = 0;

        double pixelsToCMRelX = (double) 65 /300;


        if (YPos < 50) {
            yExtra = calculateAdjustment(YPos, 50, 5, 0, 0);
        }else if (YPos < 150 && YPos >= 50) {
            yExtra = calculateAdjustment(YPos, 150, 3.5, 50, 5);
        }else if(YPos >= 150 && YPos < 250){
            yExtra = calculateAdjustment(YPos, 150, 3.5, 250, 1);
        } else if (YPos >= 250) {
            yExtra = calculateAdjustment(YPos, 250, 1, 300, 0);
        }

        double relXPosition = (((300 - YPos))*pixelsToCMRelX);
        double relXPositionThing = (((300 - YPos))*pixelsToCMRelX) - yExtra;

//        System.out.println("yExtra  " + yExtra);
//        System.out.println("relXPosition  " + relXPosition);
//        System.out.println("relXPositionThing  " + Math.sin(Math.toRadians(90)));

//        calculatePosition(new Point2D.Double(130, 100), new Point2D.Double(79.5, 100), 0);

        double timeBeforeSleep = System.nanoTime() / 1_000_000.0;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double timeAfterSleep = System.nanoTime() / 1_000_000.0;

//        System.out.println("Time before sleep: " + timeBeforeSleep);
//        System.out.println("Time after sleep: " + timeAfterSleep);
//        System.out.println("Difference in milliseconds: " + (timeAfterSleep - timeBeforeSleep));

        calculatePosition(new Point2D.Double(150, 105), new Point2D.Double(100, 100), 355);


        System.out.println("State before " + state.toString());

        state = autoState.next(state);

        System.out.println("State after " + state.toString());

    }



    public static double calculateAdjustment(double d, double d1, double x1, double d2, double x2) {
        double m = (x2 - x1) / (d2 - d1);

        return m * (d - d1) + x1;
    }

    public static double calculateAngle(Point2D.Double p1, Point2D.Double p2) {
        // Calculate the differences
        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();

        // Calculate the angle in radians, where atan2 considers (deltaX, deltaY)
        double angleRadians = Math.atan2(deltaY, deltaX);

        // Convert the angle from radians to degrees
        double angleDegrees = Math.toDegrees(angleRadians);

        // Normalize the angle to make 0 at positive Y-axis and increase clockwise
        if (angleDegrees < 0) {
            angleDegrees += 180;
        }else{
            angleDegrees += 180;
        }

        return angleDegrees;
    }

    public static double findAngle(Point2D.Double targetPoint, Point2D.Double currentPoint){
        double xError = targetPoint.x-currentPoint.x;
        double yError = targetPoint.y-currentPoint.y;

        double slope = Math.atan(yError/xError);

        double degrees = 0;

        if (xError >= 0 && yError <= 0){
            degrees = (90-(-Math.toDegrees(slope)))+270;
        }else if (xError <= 0 && yError >= 0){
            degrees = (90-(-Math.toDegrees(slope)))+90;
        }else if (xError <= 0 && yError <= 0){
            degrees = Math.toDegrees(slope)+180;
        }else {
            degrees = Math.toDegrees(slope);
        }

        return degrees;
    }

    public static void positionWrapped(){

    }
    
    public static void calculatePosition(Point2D.Double target, Point2D.Double current, double heading){

        double timeBeforeSleep = System.nanoTime() / 1_000_000.0;

        Point2D.Double newErrors = rotatePosition(heading, new Point2D.Double(target.x-current.x, target.y-current.y));

        double xError = newErrors.x;
        double yError = newErrors.y;

        double targetRailPosition;
        double slideTarget;

        double robotLength = 36 * 0.5;
        double clawOffsetFromSlides = 9;

        targetRailPosition = 9.5 + yError;
        slideTarget = (xError - robotLength)-clawOffsetFromSlides;

        double podTicks = 2000;
        double wheelRadius = 2.4;
        double trackWidth = 24.2;
        double backPodOffset = 10.5;

        double cmPerDegree = ((2.0 * Math.PI) * backPodOffset) / 360;
        double cmPerDegreeV = ((2.0 * Math.PI) * (trackWidth/2)) / 360;
        double ticksPerCM = ((2.0 * Math.PI) * wheelRadius)/podTicks;

        System.out.println("Value: " + 180 * cmPerDegreeV);
        System.out.println("Value 2: " + 4165 * ticksPerCM);
        System.out.println("Value 2: " + 180 * cmPerDegree);

//        log("angle: " + Math.toDegrees(Math.atan(yError/xError)));
//        log("slideTarget: " + slideTarget);
//        log("targetRailPosition: " + targetRailPosition);
//        log("error to target: " + newErrors);

        writer.setData(Math.toDegrees(Math.atan(yError/xError)), slideTarget, targetRailPosition, newErrors.x);
        System.out.println("Time before sleep: " + timeBeforeSleep);
//        writer.writeData();

        double XErrorGlobal = (4) * Math.sin(Math.toRadians(180)) + (4) * Math.cos(Math.toRadians(180));
        double YErrorGlobal = (4) * Math.cos(Math.toRadians(180)) - (4) * Math.sin(Math.toRadians(180));

        System.out.println("XErrorGlobal XErrorGlobal XErrorGlobal: " + XErrorGlobal);
        System.out.println("YErrorGlobal YErrorGlobal YErrorGlobal: " + YErrorGlobal);

//        writer.setData(Math.toDegrees(Math.atan(yError/xError)), slideTarget, targetRailPosition, newErrors.x);
//
//        writer.setData(Math.toDegrees(Math.atan(yError/xError)), slideTarget, targetRailPosition, newErrors.x);
//        executor.submit(() -> {
//            writer.writeData();
//            writer.flushData();
//        });

        double pivotHeight = 20 + 43;

        double X = Math.sqrt((pivotHeight*pivotHeight)+(29 * 29));

        double angleInRadians = Math.acos(8 * Math.sin((80)) / X);

//        double angleInDegrees = (180 - Math.toDegrees(angleInRadians) - 60) + Math.toDegrees(Math.atan(29/pivotHeight));

        double angleArm =  190.5 - ((Math.toDegrees(angleInRadians) + Math.toDegrees(Math.atan(29/pivotHeight))-90)*0.794);

        double timeAfterSleep = System.nanoTime() / 1_000_000.0;

        System.out.println("Time before angleArm: " +  angleArm);
        System.out.println("Time after sleep: " + timeAfterSleep);
        System.out.println("Difference in milliseconds: " + (timeAfterSleep - timeBeforeSleep));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

    }

    public static Point2D.Double rotatePosition(double heading, Point2D.Double point){
        double X = (point.y) * Math.sin(Math.toRadians(heading)) + (point.x) * Math.cos(Math.toRadians(heading));
        double Y = (point.y) * Math.cos(Math.toRadians(heading)) - (point.x) * Math.sin(Math.toRadians(heading));

        return new Point2D.Double(X, Y);
    }

    private static void log(String message) {
        logQueue.add(message);  // Add message to in-memory queue
    }

    private static void processLogs() {
        // Runnable to process log messages every second
        executor.submit(() -> {
            while (true) {
                if (!logQueue.isEmpty()) {
                    StringBuilder batchLog = new StringBuilder();
                    while (!logQueue.isEmpty()) {
                        batchLog.append(logQueue.poll()).append(System.lineSeparator());
                    }
                    System.out.print(batchLog.toString());  // Print all at once
                }
                try {
                    Thread.sleep(1000);  // Process logs every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

}
