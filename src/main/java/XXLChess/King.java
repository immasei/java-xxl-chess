package XXLChess;

import processing.core.PImage;
import java.util.*;

/**
 * King class
 */
public class King extends Piece {
    /**
     * Constructor for king
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public King(String color, String playerColor, float x, float y, PImage pic) {
        super("king", color, playerColor, x, y, pic, 99); //score = 99
    }

    @Override
    //update list of possible move
    public void getMovable() {
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        //king move
        xc.addAll(this.kingMove(this.name).get("xc"));
        yc.addAll(this.kingMove(this.name).get("yc"));
        os.addAll(this.kingMove(this.name).get("os"));

        //castling
        boolean rookLeft = false;
        boolean rookRight = false;
        //HORIZONTAL LEFT
        float yR = this.y;
        while (yR > 0) {
            yR--;
            //IS ALLY
            if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                //ITS ROOK & FIRST MOVE
                if (this.getPiece(this.x, yR).getName().equals("rook") && this.getPiece(this.x, yR).getFirstMove()) {
                    rookLeft = true;
                    this.leftRook = yR;
                }
            }
        }
        //HORIZONTAL RIGHT
        yR = this.y;
        while (yR < 13) {
            yR++;
            //IS ALLY
            if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                if (this.getPiece(this.x, yR).getName().equals("rook") && this.getPiece(this.x, yR).getFirstMove()) {
                    rookRight = true;
                    this.rightRook = yR;
                }
            }
        }
        //CASTLING MOVE
        if (this.isfirstMove) {
            if (rookLeft && this.getPiece(this.x, this.y - 1) == null && this.getPiece(this.x, this.y - 2) == null && !this.isAttackable(this.x, this.y - 2, this.color, this.x, this.leftRook)) {
                xc.add(this.x);
                yc.add(this.y - 2);
                os.add((float) 0);
            }
            if (rookRight && this.getPiece(this.x, this.y + 1) == null && this.getPiece(this.x, this.y + 2) == null && !this.isAttackable(this.x, this.y + 2, this.color, this.x, this.rightRook) ) {
                xc.add(this.x);
                yc.add(this.y + 2);
                os.add((float) 0);
            }
        }
        this.xcord = xc;
        this.ycord = yc;
        this.isObstacle = os;
    }

    @Override
    //we dont get close to enemy king (1 piece distance)
    public void imaginaryMove(float a, float b, float c, float d) {
        //king imaginary move
        this.imX = this.kingIM(a, b, c, d).get("imx");
        this.imY = this.kingIM(a, b, c, d).get("imy");
    }
}