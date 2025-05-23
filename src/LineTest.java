import Pathing.Follower.mecanumFollower;
import Pathing.PathGeneration.pathBuilderSubClasses.blueLeftBuilder;
import Pathing.PathingUtility.PathingPower;
import Pathing.PathingUtility.RobotPower;
import org.w3c.dom.ranges.Range;

import java.awt.font.NumericShaper;

public class LineTest {

    public static Sim sim;

    public static blueLeftBuilder pathFirst = new blueLeftBuilder();

    static TestingFileWriting file = new TestingFileWriting();

    static mecanumFollower follower = new mecanumFollower();

    static double targetHeading = 0;

    public static void main(String[] args) throws InterruptedException {

        sim = new Sim(28, 28, 220,180,180,120);

        pathFirst.buildPath(blueLeftBuilder.Position.right,blueLeftBuilder.Section.preload);

        pathFirst.calculateHeadings();

        for (int i = 0; i < pathFirst.headingTargets.size(); i++){
            System.out.println("heading targets" + Math.toDegrees(pathFirst.headingTargets.get(i)));
        }

        follower.setPath(pathFirst.followablePath, pathFirst.pathingVelocity);

        Thread.sleep(2000);
        
        sim.resetLoopTime();

        while (Math.abs(sim.getRobotX() - 218) > 2 || Math.abs(sim.getRobotY() - 236) > 2 || Math.abs(sim.getRobotHeading() - targetHeading) > 1){

            if (Math.abs(sim.getRobotX() - 218) > 2 || Math.abs(sim.getRobotY() - 236) > 2 || Math.abs(sim.getRobotHeading() - targetHeading) > 1){

//                if (Math.abs(sim.getRobotX() - 209) < 5 && Math.abs(sim.getRobotY() - 90) < 5){
//                    targetHeading = 20;
//                }

//                if (Math.abs(sim.getRobotX() - 240) < 3 && Math.abs(sim.getRobotY() - 103) < 3){
//                    targetHeading = 270;
//                }

                RobotPower path = follower.followPathAuto(targetHeading, sim.getRobotHeading(), sim.getRobotX(), sim.getRobotY(), sim.getxVelocity(), sim.getYVelocity());

//                System.out.println("power Line");
//                System.out.println(path.getVertical());
//                System.out.println(path.getHorizontal());
//                System.out.println(path.getPivot());
//
                System.out.println("position Line");
                System.out.println(sim.getRobotX());
                System.out.println(sim.getRobotY());

                if (path.getPivot() > 0){
                    sim.setHeadingPower(-Math.min(path.getPivot(), 1));
                }else {
                    sim.setHeadingPower(-Math.max(path.getPivot(), -1));
                }

                if (path.getVertical() > 0){
                    sim.setXpower(Math.min(path.getVertical(), 1));
                }else {
                    sim.setXpower(Math.max(path.getVertical(), -1));
                }

                if (path.getHorizontal() > 0){
                    sim.setYPower(Math.min(path.getHorizontal(), 1));
                }else {
                    sim.setYPower(Math.max(path.getHorizontal(), -1));
                }

            }else {
                sim.setYPower(0);
                sim.setXpower(0);
            }

            sim.update();
            file.setData(sim.getLoopTime(), sim.getRobotX(), sim.getRobotY(), sim.getRobotHeading());
            file.writeData();
            if (follower.getCurrentIndex() < follower.pathLength()-1){
                targetHeading = (Math.toDegrees(pathFirst.headingTargets.get(follower.getCurrentIndex())));
            }

//            System.out.println("follower " + follower.getCurrentIndex());
        }

    }

}
