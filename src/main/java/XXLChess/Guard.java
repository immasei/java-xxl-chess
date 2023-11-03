package XXLChess;

import processing.core.PImage;
import java.util.*;

/**
 * Guard class
 */
public class Guard extends Piece {
    /**
     * Constructor for guard
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Guard(String color, String playerColor, float x, float y, PImage pic) {
        super("guard", color, playerColor, x, y, pic, 5); //score = 5
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
        //king move
        xc.addAll(this.kingMove(this.name).get("xc"));
        yc.addAll(this.kingMove(this.name).get("yc"));
        os.addAll(this.kingMove(this.name).get("os"));

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
        //king imaginary move
        xc.addAll(this.kingIM(a, b, c, d).get("imx"));
        yc.addAll(this.kingIM(a, b, c, d).get("imy"));

        this.imX = xc;
        this.imY = yc;
    }
}