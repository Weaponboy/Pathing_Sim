package Pathing;

public class TestingMath {

    public static void main(String[] args) {
        double right = 84000;
        double left = -84000;
        double wheelGapCm = 16;
        double turningCircle = 3.14 * wheelGapCm;
        double cmPerDegree = turningCircle / 360;
        double trackWidth = 16;
        double cmPerTick = 0.007536;

        System.out.println(cmPerDegree*(cmPerTick * ((left - right)/2)));
    }

}
