package XXLChess;

import processing.core.PImage;
import java.util.*;

/**
 * Chancellor class
 */
public class Chancellor extends Piece {
    /**
     * Constructor for chancellor
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Chancellor(String color, String playerColor, float x, float y, PImage pic) {
        super("chancellor", color, playerColor, x, y, pic, (float) 8.5); //score = 8.5
    }

    @Override
    //update list of possible move
    public void getMovable() {
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();
        //knight move
        xc.addAll(this.knightMove("actl").get("xc"));
        yc.addAll(this.knightMove("actl").get("yc"));
        os.addAll(this.knightMove("actl").get("os"));
        //rook move
        xc.addAll(this.rookMove().get("xc"));
        yc.addAll(this.rookMove().get("yc"));
        os.addAll(this.rookMove().get("os"));

        this.xcord = xc;
        this.ycord = yc;
        this.isObstacle = os;
    }

    @Override
    public void imaginaryMove(float a, float b, float c, float d) {
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        //knight imaginary move
        xc.addAll(this.knightMove("img").get("xc"));
        yc.addAll(this.knightMove("img").get("yc"));
        //rook imaginary move
        xc.addAll(this.rookIM(a, b, c, d).get("imx"));
        yc.addAll(this.rookIM(a, b, c, d).get("imy"));

        this.imX = xc;
        this.imY = yc;
    }
}