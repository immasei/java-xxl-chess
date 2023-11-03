package XXLChess;

import processing.core.PImage;
import java.util.*;

/**
 * Camel class
 */
public class Camel extends Piece {
    /**
     * Constructor for camel
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Camel(String color, String playerColor, float x, float y, PImage pic) {
        super("camel", color, playerColor, x, y, pic, (float) 2); //score = 2
    }

    @Override
    //update list of possible move <max 8>
    public void getMovable() {
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        //camel move
        xc.addAll(this.camelMove("actl").get("xc"));
        yc.addAll(this.camelMove("actl").get("yc"));
        os.addAll(this.camelMove("actl").get("os"));

        this.xcord = xc;
        this.ycord = yc;
        this.isObstacle = os;
    }

    @Override
    public void imaginaryMove(float a, float b, float c, float d) {
        this.imX = this.camelMove("img").get("xc");
        this.imY = this.camelMove("img").get("yc");
    }
}