package XXLChess;

import processing.core.PImage;

/**
 * Bishop class
 */
public class Bishop extends Piece {
    /**
     * Constructor for bishop
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Bishop(String color, String playerColor, float x, float y, PImage pic) {
        super("bishop", color, playerColor, x, y, pic, (float) 3.625); //score = 3.625
    }

    @Override
    //update list of possible move <max 26>
    public void getMovable() { 
        this.xcord = this.bishopMove().get("xc");
        this.ycord = this.bishopMove().get("yc");
        this.isObstacle = this.bishopMove().get("os");
    }

    @Override
    //if enemy king in current position (a, b) move to another position
    //can that position in danger by our bishop 
    public void imaginaryMove(float a, float b, float c, float d) {
        //bishop imaginary move
        this.imX = this.bishopIM(a, b, c, d).get("imx");
        this.imY = this.bishopIM(a, b, c, d).get("imy");
    }
}