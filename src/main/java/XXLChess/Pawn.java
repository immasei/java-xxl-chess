package XXLChess;

import processing.core.PImage;
import java.util.*;

/**
 * Pawn class
 */
public class Pawn extends Piece {
    /**
     * Constructor for pawn
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     */
    public Pawn(String color, String playerColor, float x, float y, PImage pic) {
        super("pawn", color, playerColor, x, y, pic, 1); //score = 1
    }

    @Override
    //update list of possible move <max 4>
    public void getMovable() { 
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();
        if (this.name.equals("pawn")) {
            //PLAYER go up x--
            if (this.color.equals(this.playerColor)) {
                //PL MOVABLE EMPTY TILE 
                boolean moveCrash = false;
                if (this.x != 0) { 
                    //<1> move 1 square forward
                    if (this.getPiece(this.x - 1, this.y) == null && !this.isDefender(this.x - 1, this.y, this.x, this.y, this.color)) {
                        xc.add(this.x - 1);
                        yc.add(this.y);
                        os.add((float) 0);
                    } else {
                        moveCrash = true; //move line crashed by obstacle
                    }
                    //<2> move 2 squares forward
                    if (this.x == 12 && this.getPiece(this.x - 2, this.y) == null && !moveCrash && this.isfirstMove && !this.isDefender(this.x - 2, this.y, this.x, this.y, this.color)) {
                        xc.add(this.x - 2);
                        yc.add(this.y);
                        os.add((float) 0);
                    }
                }
                //PL EATABLE TILE
                if (this.x != 0) { 
                    //<3> diagonal right : y++
                    if (this.y != 13 && this.getPiece(this.x - 1, this.y + 1) != null && !this.getPiece(this.x - 1, this.y + 1).getColor().equals(this.playerColor) && !this.isDefender(this.x - 1, this.y + 1, this.x, this.y, this.color)) {
                        xc.add(this.x - 1);
                        yc.add(this.y + 1);
                        os.add(this.getPiece(this.x - 1, this.y + 1).getScore());
                    } 
                    //<4> diagonal left : y--
                    if (this.y != 0 && this.getPiece(this.x - 1, this.y - 1) != null && !this.getPiece(this.x - 1, this.y - 1).getColor().equals(this.playerColor) && !this.isDefender(this.x - 1, this.y - 1, this.x, this.y, this.color)) {
                        xc.add(this.x - 1);
                        yc.add(this.y - 1);
                        os.add(this.getPiece(this.x - 1, this.y - 1).getScore());
                    }
                }   
            //AI go down x++
            } else {
                //AI MOVABLE EMPTY TILE
                boolean moveCrash = false;
                if (this.x != 13) { 
                    //<1> move 1 square forward
                    if (this.getPiece(this.x + 1, this.y) == null && !this.isDefender(this.x + 1, this.y, this.x, this.y, this.color)) {
                        xc.add(this.x + 1);
                        yc.add(this.y);
                        os.add((float) 0);
                    } else {
                        moveCrash = true; //move line crashed by obstacle
                    }
                    //<2> move 2 squares forward
                    if (this.x == 1 && this.getPiece(this.x + 2, this.y) == null && !moveCrash && this.isfirstMove && !this.isDefender(this.x + 2, this.y, this.x, this.y, this.color)) {
                        xc.add(this.x + 2);
                        yc.add(this.y);
                        os.add((float) 0);
                    }
                }
                //AI EATABLE TILE
                if (this.x != 13) { 
                    //<3> diagonal left : y++
                    if (this.y != 13 && this.getPiece(this.x + 1, this.y + 1) != null && this.getPiece(this.x + 1, this.y + 1).getColor().equals(this.playerColor) && !this.isDefender(this.x + 1, this.y + 1, this.x, this.y, this.color)) {
                        xc.add(this.x + 1);
                        yc.add(this.y + 1);
                        os.add(this.getPiece(this.x + 1, this.y + 1).getScore());
                    } 
                    //<4> diagonal right : y--
                    if (this.y != 0 && this.getPiece(this.x + 1, this.y - 1) != null && this.getPiece(this.x + 1, this.y - 1).getColor().equals(this.playerColor) && !this.isDefender(this.x + 1, this.y - 1, this.x, this.y, this.color)) {
                        xc.add(this.x + 1);
                        yc.add(this.y - 1);
                        os.add(this.getPiece(this.x + 1, this.y - 1).getScore());
                    }
                }   
            }
        //PROMOTED QUEEN MOVES
        } else {
            //bishop move
            xc.addAll(this.bishopMove().get("xc"));
            yc.addAll(this.bishopMove().get("yc"));
            os.addAll(this.bishopMove().get("os"));
            //rook move
            xc.addAll(this.rookMove().get("xc"));
            yc.addAll(this.rookMove().get("yc"));
            os.addAll(this.rookMove().get("os"));
        }
        this.xcord = xc;
        this.ycord = yc;
        this.isObstacle = os;
    }

    @Override
    public void imaginaryMove(float a, float b, float c, float d) {
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        //normal pawn
        if (this.name.equals("pawn")) {
            //PLAYER go up x--
            if (this.color.equals(this.playerColor)) {
                //PL EATABLE TILE
                if (this.x != 0) { 
                    //<3> diagonal right : y++
                    if (this.y != 13) {
                        xc.add(this.x - 1);
                        yc.add(this.y + 1);
                    } 
                    //<4> diagonal left : y--
                    if (this.y != 0) {
                        xc.add(this.x - 1);
                        yc.add(this.y - 1);
                    }
                }   
            //AI go down x++
            } else {
                //AI EATABLE TILE
                if (this.x != 13) { 
                    //<3> diagonal left : y++
                    if (this.y != 13) {
                        xc.add(this.x + 1);
                        yc.add(this.y + 1);
                    } 
                    //<4> diagonal right : y--
                    if (this.y != 0) {
                        xc.add(this.x + 1);
                        yc.add(this.y - 1);
                    }
                }   
            }
        //PROMOTED PAWN
        } else {
            //bishop imaginary move
            xc.addAll(this.bishopIM(a, b, c, d).get("imx"));
            yc.addAll(this.bishopIM(a, b, c, d).get("imy"));
            //rook imaginary move
            xc.addAll(this.rookIM(a, b, c, d).get("imx"));
            yc.addAll(this.rookIM(a, b, c, d).get("imy"));
        }
        this.imX = xc;
        this.imY = yc;
    }
}