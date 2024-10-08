package Pathing.PathGeneration;


import Pathing.Follower.Vector2D;

import static Pathing.PathingUtility.UsefulMethods.getRealCoords;

public interface Blue_Points_Overlap {

    /**
     * collect white pixels from stack
     * */

    /**first position*/
    Vector2D CS1F = new Vector2D(getRealCoords(300), getRealCoords(75));
    Vector2D CC1F = new Vector2D(getRealCoords(293), getRealCoords(160));
    Vector2D CE1F = new Vector2D(getRealCoords(184), getRealCoords(181));

    //second segment
    Vector2D CS2F = CE1F;
    Vector2D CC2F = new Vector2D(getRealCoords(26), getRealCoords(202));
    Vector2D CE2F = new Vector2D(getRealCoords(40), getRealCoords(135));

    /**second segment*/
    Vector2D CS3F = CE2F;
    Vector2D CC3F = new Vector2D(getRealCoords(34), getRealCoords(181));
    Vector2D CE3F = new Vector2D(getRealCoords(76), getRealCoords(182));

    //second segment
    Vector2D CS4F = CE3F;
    Vector2D CC4F = new Vector2D(getRealCoords(305), getRealCoords(208));
    Vector2D CE4F = new Vector2D(getRealCoords(300), getRealCoords(100));

//    /**third position*/
//    Vector2D CS1T = new Vector2D(getRealCoords(300), getRealCoords(105));
//    Vector2D CC1T = new Vector2D(getRealCoords(294), getRealCoords(149));
//    Vector2D CE1T = new Vector2D(getRealCoords(181), getRealCoords(193));
//
//    //second segment
//    Vector2D CS2T = CE1T;
//    Vector2D CC2T = new Vector2D(getRealCoords(30), getRealCoords(249));
//    Vector2D CE2T = new Vector2D(getRealCoords(41), getRealCoords(135));

    /**
     * deliver points
     * */

    /**first position*/
    Vector2D DS1F = new Vector2D(CE2F.getX(), CE2F.getY());
    Vector2D DC1F = new Vector2D(getRealCoords(84), getRealCoords(181));
    Vector2D DE1F = new Vector2D(getRealCoords(179), getRealCoords(176));

    Vector2D DS2F = DE1F;
    Vector2D DC2F = new Vector2D(getRealCoords(315), getRealCoords(168));
    Vector2D DE2F = new Vector2D(getRealCoords(300), getRealCoords(90));


}
