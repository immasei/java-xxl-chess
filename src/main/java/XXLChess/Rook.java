package XXLChess;

import processing.core.PImage;

/**
 * Rook class
 */
public class Rook extends Piece {
    /**
     * Constructor for rook
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Rook(String color, String playerColor, float x, float y, PImage pic) {
        super("rook", color, playerColor, x, y, pic, (float) 5.25); //score = 5.25
    }

    @Override
    //update list of possible move <max 26>
    public void getMovable() {
        this.xcord = this.rookMove().get("xc");
        this.ycord = this.rookMove().get("yc");
        this.isObstacle = this.rookMove().get("os");
    }

    @Override
    //if enemy king in position (a, b) move to another position
    //can that position in danger by our rook 
    public void imaginaryMove(float a, float b, float c, float d) {
        //rook imaginary move
        this.imX = this.rookIM(a, b, c, d).get("imx");
        this.imY = this.rookIM(a, b, c, d).get("imy");
    }
}