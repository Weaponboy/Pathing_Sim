import javax.swing.*;
import java.awt.*;

public class Sim extends JFrame {

    JLabel background;
    RotatableRectangle shapePanel;

    public static long startTime = System.currentTimeMillis();

    //position
    private double robotX;
    private double robotY;

    public double getLoopTime() {
        loopTime = getTime();
        return loopTime;
    }

    private double loopTime;

    public double getRobotHeading() {
        return robotHeading;
    }

    private double robotHeading = 0;

    //constants
    private final double maxXVelocity;
    private final double maxYVelocity;
    private final double maxHeadingVelocity = 240;

    private final double maxXAcceleration;
    private final double maxYAcceleration;
    private final double maxHeadingAcceleration = 190;

    private double robotWeightKg = 14.5;

    //real time values
    private double Xpower;
    private double YPower;

    public void setHeadingPower(double headingPower) {
        HeadingPower = headingPower;
    }

    private double HeadingPower;

    private double xVelocity;
    private double yVelocity;
    private double hVelocity;

    //constructor
    public Sim(double startX, double startY, double maxXVelocity, double maxYVelocity, double maxXAcceleration, double maxYAcceleration) {

        this.maxXVelocity = maxXVelocity;
        this.maxYVelocity = maxYVelocity;

        this.maxXAcceleration = maxXAcceleration;
        this.maxYAcceleration = maxYAcceleration;

        this.robotX = (720 - (startX * 2)) - 40;
        this.robotY = startY * 2 - 40;

        setTitle("Pathing SIM FTC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(735, 758);

        setLocationRelativeTo(null);

        setResizable(false);

        shapePanel = new RotatableRectangle((int) robotY, (int) robotX, robotHeading);
        setBackground();

        add(shapePanel);
        setVisible(true);
        add(background);

    }

    public void setBackground() {
        background = new JLabel();

        int width = 720;
        int height = 720;

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\pathing sim\\src\\ImageBackround\\centerstage.png");

        // Resize the image to fit the size of the JFrame
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        background.setIcon(new ImageIcon(image));

        background.setBounds(0, 0, width, height);
    }

    private void setRobotPos(int newX, int newY, double heading) {

        robotHeading = heading;

        robotX = 720 - newX * 2 - 40;
        robotY = newY * 2 - 40;

        shapePanel.setPosition((int) robotY, (int) robotX);
        shapePanel.setRotation(robotHeading);
    }

    public void resetLoopTime(){
        startTime = System.currentTimeMillis();
    }

    public void update(){

        double lastXVelo = xVelocity;
        double lastYVelo = yVelocity;
        double lastHeadingVelo = hVelocity;

        double maxXVelo = getXPower()*maxXVelocity;
        double maxYVelo = getYPower()*maxYVelocity;
        double maxHeadingVelo = getHeadingPower()*maxHeadingVelocity;

        double looptime = getTime();

        double accelXVelo = ((getXPower()*maxXAcceleration)/1000)*looptime;
        double accelYVelo = ((getYPower()*maxYAcceleration)/1000)*looptime;
        double accelHeadingVelo = ((getHeadingPower()*maxHeadingAcceleration)/1000)*looptime;

        if (getXPower() > 0){
            xVelocity = Math.min((lastXVelo+accelXVelo), maxXVelo);
        }else {
            xVelocity = Math.max((lastXVelo+accelXVelo), maxXVelo);
        }

        if (getYPower() > 0){
            yVelocity = Math.min((lastYVelo+accelYVelo), maxYVelo);
        }else {
            yVelocity = Math.max((lastYVelo+accelYVelo), maxYVelo);
        }

        if (getHeadingPower() > 0){
            hVelocity = Math.min((lastHeadingVelo+accelHeadingVelo), maxHeadingVelo);
        }else {
            hVelocity = Math.max((lastHeadingVelo+accelHeadingVelo), maxHeadingVelo);
        }

//        System.out.println("velo Line");
//        System.out.println(xVelocity);
//        System.out.println(yVelocity);
//        System.out.println(hVelocity);

        double newX = (((xVelocity-lastXVelo/2)+lastXVelo)/1000)*looptime;
        double newY = (((yVelocity-lastYVelo/2)+lastYVelo)/1000)*looptime;

        double deltaHeading = (((hVelocity-lastHeadingVelo/2)+lastHeadingVelo)/1000)*looptime;

        robotX += -(newX * 2);
        robotY += newY * 2;
        robotHeading += deltaHeading;

        if (robotHeading > 360){
            robotHeading = robotHeading - 360;
        } else if (robotHeading < 0) {
            robotHeading = 360 + robotHeading;
        }

        shapePanel.setPosition((int) robotY, (int) robotX);
        shapePanel.setRotation(robotHeading);

        startTime = System.currentTimeMillis();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public double getRobotX() {
        return 360 - ((robotX+40) *0.5);
    }

    public double getRobotY() {
        return  ((robotY+40) *0.5);
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public double getXPower() {
        return Xpower;
    }

    public void setXpower(double xpower) {
        Xpower = xpower;
    }

    public double getYPower() {
        return YPower;
    }

    public double getHeadingPower() {
        return HeadingPower;
    }


    public void setYPower(double YPower) {
        this.YPower = YPower;
    }

    public static long getTime(){
        return System.currentTimeMillis() - startTime;
    }


}
