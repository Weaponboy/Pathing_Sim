import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class TestingFileWriting {

    public static long startTime = System.currentTimeMillis();

    public static double loopTime = 0;

    public static double X = 0;
    public static double Y = 0;
    public static double Heading = 0;

    public static double XVelocity = 0;
    public static double YVelocity = 0;
    public static double HeadingVelocity = 0;

    public static double LeftPod = 0;
    public static double RightPod = 0;
    public static double CenterPod = 0;

    static FileWriter fWriter;

    static {
        try {
            fWriter = new FileWriter(
                    "C:/Users/josha/Documents/testWrite.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TestingFileWriting(){

        try {
            fWriter.write( "Github commit: " + "2024/06/15:Testing_May");
            fWriter.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setData(double lt, double x, double y, double heading, double xV, double yV, double headingV, double lp, double rp, double cp){
        loopTime = lt;

        X = x;
        Y = y;
        Heading = heading;

        XVelocity = xV;
        YVelocity = yV;
        HeadingVelocity = headingV;

        LeftPod = lp;
        RightPod = rp;
        CenterPod = cp;

    }

    public void setData(double lt, double x, double y, double heading, double xV, double yV, double headingV){
        loopTime = lt;

        X = x;
        Y = y;
        Heading = heading;

        XVelocity = xV;
        YVelocity = yV;
        HeadingVelocity = headingV;

        LeftPod = 0;
        RightPod = 0;
        CenterPod = 0;

    }

    public void setData(double lt, double x, double y, double heading){
        loopTime = lt;

        X = x;
        Y = y;
        Heading = heading;

        XVelocity = 0;
        YVelocity = 0;
        HeadingVelocity = 0;

        LeftPod = 0;
        RightPod = 0;
        CenterPod = 0;

    }

    public void writeData(){

        try {

            fWriter.write( "loop time: " + loopTime);
            fWriter.write(System.lineSeparator());

            fWriter.write( "X Position:" + X);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Y Position:" + Y);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Heading Position:" + Heading);
            fWriter.write(System.lineSeparator());

            fWriter.write( "X Velocity:" + XVelocity);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Y Velocity:" + YVelocity);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Heading Velocity:" + HeadingVelocity);
            fWriter.write(System.lineSeparator());

            fWriter.write( "Left Pod:" + LeftPod);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Right Pod:" + RightPod);
            fWriter.write(System.lineSeparator());
            fWriter.write( "Center Pod:" + CenterPod);
            fWriter.write(System.lineSeparator());

            fWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static long getTime(){
        return System.currentTimeMillis() - startTime;
    }

}
