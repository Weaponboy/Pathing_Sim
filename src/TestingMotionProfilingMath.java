import java.util.ArrayList;

public class TestingMotionProfilingMath {

    static ArrayList<Double> motionprofile = new ArrayList<>();
    static ArrayList<Double> positions = new ArrayList<>();
    static ArrayList<Double> time = new ArrayList<>();

    static double targetPosition = 50;
    static int currentPosition = 0;

    static double maxAccel = 1400;
    static double maxVelocity = 140;
    static double acceldistance = (maxVelocity * maxVelocity) / (maxAccel * 2);
    static int lastIndex = 0;
    static double slidetime;
    static double veloToMotorPower = 1/maxVelocity;

    public static void main(String[] args) {
        genProfile(20);
    }

    public static void genProfile (double slideTarget){

        time.clear();
        motionprofile.clear();
        slidetime = 0;
        targetPosition = slideTarget;
        currentPosition = 0;

        double distanceToTarget = targetPosition - currentPosition;

        double halfwayDistance = targetPosition / 2;
        double newAccelDistance = acceldistance;

        int decelCounter = 0;

        double baseMotorVelocity = (maxVelocity) * 0.15;

        if (acceldistance > halfwayDistance){
            newAccelDistance = halfwayDistance;
        }

        double newMaxVelocity = Math.sqrt(2 * maxAccel * newAccelDistance);

        System.out.println("acceleration_distance: " + acceldistance);
        System.out.println("newMaxVelocity: " + newMaxVelocity);
        System.out.println("newAccelDistance accel: " + newAccelDistance);

        for (int i = 0; i < Math.abs(targetPosition - currentPosition); i++) {
            double targetVelocity;

            if (newAccelDistance > i && targetPosition > currentPosition) {

                int range = (int) Math.abs(newAccelDistance - i);

                double AccelSlope = (double) range / Math.abs(newAccelDistance) * 100;

                AccelSlope = ((100 - AccelSlope) * 0.01);

                targetVelocity = (newMaxVelocity * AccelSlope) + baseMotorVelocity;

                if(targetVelocity != 0){
                    slidetime += (1 / targetVelocity) * 1000;
                }

                time.add(slidetime);

                System.out.println("targetVelocity accel: " + targetVelocity);

            }else if (i + newAccelDistance > Math.abs(targetPosition - currentPosition) && targetPosition < currentPosition) {

                decelCounter++;

                int range = (int) Math.abs(newAccelDistance - decelCounter);

                double DeccelSlope = (double) range / Math.abs(newAccelDistance) * 100;

                DeccelSlope = DeccelSlope * 0.01;

                targetVelocity = (newMaxVelocity * DeccelSlope) + baseMotorVelocity;

                positions.add((double) i+1);

                System.out.println("targetVelocity dccel: " + targetVelocity);

            } else {

                targetVelocity = newMaxVelocity;

                positions.add((double) i+1);

                System.out.println("targetVelocity: " + targetVelocity);

            }

            motionprofile.add(targetVelocity);

        }

        System.out.println("slide time: " + time.size());
        System.out.println("motion profile: " + motionprofile.size());
        System.out.println("positions size: " + positions.size());
        for (int i = 0; i < positions.size(); i++){
            System.out.println("positions profile: " + positions.get(i));
        }

    }


}
