package XXLChess;

import processing.core.PImage;

/**
 * Knight class
 */
public class Knight extends Piece {
    /**
     * Constructor for knight
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Knight(String color, String playerColor, float x, float y, PImage pic) {
        super("knight", color, playerColor, x, y, pic, 2); //score = 2
    }

    @Override
    //update list of possible move <max 8>
    public void getMovable() { 
        this.xcord = this.knightMove("actl").get("xc");
        this.ycord = this.knightMove("actl").get("yc");
        this.isObstacle = this.knightMove("actl").get("os");
    }

    @Override
    public void imaginaryMove(float a, float b, float c, float d) {
        this.imX = this.knightMove("img").get("xc");
        this.imY = this.knightMove("img").get("yc");
    }
}