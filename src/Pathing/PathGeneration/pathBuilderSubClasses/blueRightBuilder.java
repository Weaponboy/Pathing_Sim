package Pathing.PathGeneration.pathBuilderSubClasses;

import Pathing.Follower.Vector2D;
import Pathing.PathGeneration.pathBuilderMain;

import static Pathing.PathingUtility.UsefulMethods.getRealCoords;


public class blueRightBuilder extends pathBuilderMain {

    Vector2D startPos = new Vector2D(getRealCoords(90), getRealCoords(23));

    /** control point naming key
     * don't need start position because i have sub classes for each one
     * DP = drop purple pixel, DY = drop yellow pixel, C = collect pixels, D = deliver pixels from stack
     * S = start point, C = control point, CT = control point two, E = end point
     * 1 = first segment, 2 = second segment, 3 = third segment
     * F = first prop pos, S = second prop pos, T = third prop pos
     * */

    /**if it is calling the getRealCoords method it has the correct values*/

    /**FIRST POSITION ALL POINTS*/
    /**drop purple pixel*/

    Vector2D DPS1F = startPos;
    Vector2D DPC1F = new Vector2D(getRealCoords(96), getRealCoords(138));
    Vector2D DPC21F = new Vector2D(getRealCoords(66), getRealCoords(97));
    Vector2D DPE1F = new Vector2D(getRealCoords(51), getRealCoords(93));

    //second pos
    Vector2D DPS1S = startPos;
    Vector2D DPC1S = new Vector2D(getRealCoords(95), getRealCoords(95));
    Vector2D DPE1S = new Vector2D(getRealCoords(66), getRealCoords(72));

    //third pos
    Vector2D DPS1T = startPos;
    Vector2D DPE1T = new Vector2D(getRealCoords(74), getRealCoords(74));

    /**drop yellow pixel*/

    /**drop yellow first*/
    Vector2D DYS1F = DPE1F;
    Vector2D DYC1F = new Vector2D(getRealCoords(34), getRealCoords(28));
    Vector2D DYE1F = new Vector2D(getRealCoords(97), getRealCoords(32));

    Vector2D DYS2F = DYE1F;
    Vector2D DYC2F = new Vector2D(getRealCoords(151), getRealCoords(32));
    Vector2D DYE2F = new Vector2D(getRealCoords(210), getRealCoords(32));

    Vector2D DYS3F = DYE2F;
    Vector2D DYC3F = new Vector2D(getRealCoords(191), getRealCoords(30));
    Vector2D DYE3F = new Vector2D(getRealCoords(300), getRealCoords(82));

    /**drop yellow second*/
    Vector2D DYS1S = DPE1S;
    Vector2D DYC1S = new Vector2D(getRealCoords(27), getRealCoords(36));
    Vector2D DYE1S = new Vector2D(getRealCoords(39), getRealCoords(120));

    Vector2D DYS2S = DYE1S;
    Vector2D DYC2S = new Vector2D(getRealCoords(67), getRealCoords(189));
    Vector2D DYE2S = new Vector2D(getRealCoords(236), getRealCoords(149));

    Vector2D DYS3S = DYE2S;
    Vector2D DYC3S = new Vector2D(getRealCoords(293), getRealCoords(136));
    Vector2D DYE3S = new Vector2D(getRealCoords(305), getRealCoords(102));

    /**drop yellow third*/
    Vector2D DYS1T = DPE1T;
    Vector2D DYC1T = new Vector2D(getRealCoords(95), getRealCoords(52));
    Vector2D DYE1T = new Vector2D(getRealCoords(85), getRealCoords(142));

    Vector2D DYS2T = DYE1T;
    Vector2D DYC2T = new Vector2D(getRealCoords(76), getRealCoords(198));
    Vector2D DYE2T = new Vector2D(getRealCoords(203), getRealCoords(173));

    Vector2D DYS3T = DYE2T;
    Vector2D DYC3T = new Vector2D(getRealCoords(276), getRealCoords(158));
    Vector2D DYE3T = new Vector2D(getRealCoords(300), getRealCoords(120));

    /**first position*/
    Vector2D CS1F = new Vector2D(getRealCoords(300), getRealCoords(100));
    Vector2D CC1F = new Vector2D(getRealCoords(290), getRealCoords(154));
    Vector2D CE1F = new Vector2D(getRealCoords(179), getRealCoords(152));

    //second segment
    Vector2D CS2F = CE1F;
    Vector2D CC2F = new Vector2D(getRealCoords(107), getRealCoords(149));
    Vector2D CE2F = new Vector2D(getRealCoords(84), getRealCoords(160));

    Vector2D CS3F = CE2F;
    Vector2D CC3F = new Vector2D(getRealCoords(30), getRealCoords(181));
    Vector2D CE3F = new Vector2D(getRealCoords(40), getRealCoords(120));

    public enum Position {
        left,
        center,
        right
    }

    public enum pixelColor {
        purple,
        yellow,
    }

    public enum Section {
        preload,
        collect,
        deliver
    }

    public void buildPath(Position propPosition, Section section){

        switch (section) {
            case preload:
                switch (propPosition) {
                    case left:
                        firstPositionPreloadPurple();
                        break;
                    case right:
                        thirdPositionPreloadPurple();
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

    public void buildPath(Position propPosition, Section section, pixelColor color){

        switch (section) {
            case preload:
                switch (propPosition) {
                    case left:
                        switch (color){
                            case purple:
                                firstPositionPreloadPurple();
                                break;
                            case yellow:
                                firstPositionPreloadYellow();
                                break;
                            default:
                        }
                        break;
                    case right:
                        switch (color){
                            case purple:
                                thirdPositionPreloadPurple();
                                break;
                            case yellow:
                                thirdPositionPreloadYellow();
                                break;
                            default:
                        }
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

    public void buildPathLine(Vector2D startPos, Vector2D targetPos){

        buildLineSegment(startPos, targetPos);

        pathBuilder(originalPath);

        motionProfile();
    }

    /**
     * First Position
     * */

    private void firstPositionPreloadPurple(){

        // drop purple pixel
        buildCurveSegment(DPS1F, DPC1F, DPC21F, DPE1F);

    }

    private void firstPositionPreloadYellow(){

        buildCurveSegment(DYS1F, DYC1F, DYE1F);

        buildCurveSegment(DYS2F, DYC2F, DYE2F);

        buildCurveSegment(DYS3F, DYC3F, DYE3F);

    }

    /**First Position*/
    private void Collect(){

        buildCurveSegment(CS1F, CC1F, CE1F);

        buildCurveSegment(CS2F, CC2F, CE2F);

        buildCurveSegment(CS3F, CC3F, CE3F);

    }

    private void Deliver(){

        buildCurveSegment(CE3F, CC3F, CS3F);

        buildCurveSegment(CE2F, CC2F, CS2F);

        buildCurveSegment(CE1F, CC1F, CS1F);

    }

    /**
     * Second Position
     * */

    private void secondPositionPreload(){

        // drop purple pixel
        buildCurveSegment(DPS1S, DPC1S, DPE1S);

        // drop yellow pixel
        buildCurveSegment(DYS1S, DYC1S, DYE1S);

        buildCurveSegment(DYS2S, DYC2S, DYE2S);

        buildCurveSegment(DYS3S, DYC3S, DYE3S);

    }

    /**second Position*/


    /**
     * third Position
     * */

    private void thirdPositionPreloadPurple(){

        // drop purple pixel
        buildLineSegment(DPS1T, DPE1T);

    }

    private void thirdPositionPreloadYellow(){

        buildCurveSegment(DYS1T, DYC1T, DYE1T);

        buildCurveSegment(DYS2T, DYC2T, DYE2T);

        buildCurveSegment(DYS3T, DYC3T, DYE3T);

    }

}
