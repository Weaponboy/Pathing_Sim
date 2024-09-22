package Pathing.PathGeneration.pathBuilderSubClasses;


import Pathing.Follower.Vector2D;
import Pathing.PathGeneration.pathBuilderMain;

import static Pathing.PathingUtility.UsefulMethods.getRealCoords;

public class blueLeftBuilder extends pathBuilderMain {

    /** control point naming key
     * don't need start position because i have sub classes for each one
     * DP = drop purple pixel, DY = drop yellow pixel, C = collect pixels, D = deliver pixels from stack
     * S = start point, C = control point, CT = control point two, E = end point
     * 1 = first segment, 2 = second segment, 3 = third segment
     * F = first prop pos, S = second prop pos, T = third prop pos
     * */

    /**if it is calling the getRealCoords method it has the correct values*/

    /**
     * drop purple pixel
     * */

    //first pos
    Vector2D DPS1F = new Vector2D(getRealCoords(210), getRealCoords(23));
    Vector2D DPC1F = new Vector2D(getRealCoords(241), getRealCoords(45));
    Vector2D DPCT1F = new Vector2D(getRealCoords(188), getRealCoords(83));
    Vector2D DPE1F = new Vector2D(getRealCoords(305), getRealCoords(82.5));

    //second pos
    Vector2D DPS1S = new Vector2D(getRealCoords(210), getRealCoords(23));
    Vector2D DPC1S = new Vector2D(getRealCoords(225), getRealCoords(76));
    Vector2D DPCT1S = new Vector2D(getRealCoords(170), getRealCoords(65));
    Vector2D DPE1S = new Vector2D(getRealCoords(305), getRealCoords(94));

    Vector2D DPS1T = new Vector2D(getRealCoords(210), getRealCoords(23));
    Vector2D DPC1T = new Vector2D(getRealCoords(210), getRealCoords(100));
    Vector2D DPCT1T = new Vector2D(getRealCoords(180), getRealCoords(100));
    Vector2D DPE1T = new Vector2D(getRealCoords(305), getRealCoords(105));

    /**
     * drop yellow pixel
     * */

    //drop yellow pixel first
    Vector2D DYS1F = new Vector2D(DPE1F.getX(), DPE1F.getY());
    Vector2D DYC1F = new Vector2D(getRealCoords(242), getRealCoords(26));
    Vector2D DYE1F = new Vector2D(getRealCoords(300), getRealCoords(82.5));

    //drop yellow pixel second
    Vector2D DYS1S = new Vector2D(DPE1S.getX(), DPE1S.getY());
    Vector2D DYC1S = new Vector2D(getRealCoords(210), getRealCoords(35));
    Vector2D DYE1S = new Vector2D(getRealCoords(300), getRealCoords(97.5));

    //drop yellow pixel first
    Vector2D DYS1T = new Vector2D(DPE1F.getX(), DPE1F.getY());
    Vector2D DYE1T = new Vector2D(getRealCoords(300), getRealCoords(110));

    /**first position*/
    Vector2D CS1F = new Vector2D(getRealCoords(300), getRealCoords(100));
    Vector2D CC1F = new Vector2D(getRealCoords(284), getRealCoords(147));
    Vector2D CE1F = new Vector2D(getRealCoords(179), getRealCoords(152));

    //second segment
    Vector2D CS2F = CE1F;
    Vector2D CC2F = new Vector2D(getRealCoords(107), getRealCoords(149));
    Vector2D CE2F = new Vector2D(getRealCoords(84), getRealCoords(150));

    Vector2D CS3F = CE2F;
    Vector2D CC3F = new Vector2D(getRealCoords(60), getRealCoords(150));
    Vector2D CE3F = new Vector2D(getRealCoords(42), getRealCoords(150));

    public enum Position {
        left,
        center,
        right
    }

    public enum pathSplit {
        first,
        second
    }

    public enum Section {
        preload,
        collect,
        deliver
    }

    public void buildPathLine(Vector2D startPos, Vector2D targetPos){

        buildLineSegment(startPos, targetPos);

        pathBuilder(originalPath);

        motionProfile();

    }

    public void buildPath(Section section){

        switch (section) {
            case collect:
                Collect();
                break;
            case deliver:
                Deliver();
                break;
            default:
        }

        pathBuilder(originalPath);

        motionProfile();
    }

    public void buildPath(Position propPosition, Section section){

        switch (section) {
            case preload:
                switch (propPosition) {
                    case left:
                        firstPositionPreload();
                        break;
                    case right:
                        thirdPositionPreload();
                        break;
                    case center:
                        secondPositionPreload();
                        break;
                    default:
                }
                break;
            case collect:
                Collect();
                break;
            case deliver:
                Deliver();
                break;
            default:
        }

        pathBuilder(originalPath);

        motionProfile();
    }

    public void buildPath(Position propPosition, Section section, redRightBuilder.pathSplit pathsplit){

        switch (section) {
            case preload:
                switch (propPosition) {
                    case left:
                        firstPositionPreload();
                        break;
                    case right:
                        thirdPositionPreload();
                        break;
                    case center:
                        secondPositionPreload();
                        break;
                    default:
                }
                break;
            case collect:
                Collect();
                break;
            case deliver:
                Deliver();
                break;
            default:
        }

        pathBuilder(originalPath);

        motionProfile();
    }

    /**First Position*/
    private void firstPositionPreload(){

        // drop purple pixel
        buildCurveSegment(DPS1F, DPC1F, DPCT1F, DPE1F);

    }

    /**First Position*/
    private void Collect(){
        buildCurveSegment(new Vector2D(0, 0), new Vector2D(40, 300), new Vector2D(60, 20));

//
//        buildCurveSegment(CS1F, CC1F, CE1F);
//
//        buildCurveSegment(CS2F, CC2F, CE2F);
//
//        buildCurveSegment(CS3F, CC3F, CE3F);

    }

    private void Deliver(){

        buildCurveSegment(CE3F, CC3F, CS3F);

        buildCurveSegment(CE2F, CC2F, CS2F);

        buildCurveSegment(CE1F, CC1F, CS1F);

    }

    /**second Position*/
    private void secondPositionPreload(){

        // drop purple pixel
        buildCurveSegment(DPS1S, DPC1S, DPCT1S, DPE1S);

    }

    /**third Position*/
    private void thirdPositionPreload(){

        // drop purple pixel
//        buildCurveSegment(DPS1T, DPC1T, DPCT1T, DPE1T);

        buildCurveSegment(new Vector2D(341, 324), new Vector2D(195, 286), new Vector2D(210, 238));

//        buildLineSegment(new Vector2D(210, 23), new Vector2D(210, 180));

    }


}
