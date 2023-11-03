package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

/**
 * Piece class
 */
public abstract class Piece {
    /**
     * The list contain all chess pieces created
     */
    protected static ArrayList<Piece> allPiece = new ArrayList<Piece>();

    /**
     * The list contains all possible x moves
     */
    protected ArrayList<Float> xcord = new ArrayList<Float>();

    /**
     * The list contains all possible y moves
     */
    protected ArrayList<Float> ycord = new ArrayList<Float>();

    /**
     * The list contains all value of possible moves, empty tile is 0
     */
    protected ArrayList<Float> isObstacle = new ArrayList<Float>();

    /**
     * Name of chess piece
     */
    protected String name;

    /**
     * Color of chess piece
     */
    protected String color;

    /**
     * Player piece color
     */
    protected String playerColor;

    /**
     * Row where chess piece located
     */
    protected float x;

    /**
     * Column where chess piece located
     */
    protected float y;

    /**
     * Sprites
     */
    protected PImage pic;

    /**
     * Value of chess piece
     */
    protected float score;

    /**
     * Marks if piece is selected by player
     */
    protected boolean selected;
    
    /**
     * Marks if piece is eaten
     */
    protected boolean isAlive;
    
    /**
     * Marks if piece is moving
     */
    protected boolean isMoving;
    
    /**
     * Marks if pawn is promoted to queen
     */
    protected boolean isPromoted;
    
    /**
     * Marks if piece has its first move
     */
    protected boolean isfirstMove;
    
    /**
     * Pixels added to horizontal move per frame
     */
    protected float speedX;

    /**
     * Pixels added to vertical move per frame
     */
    protected float speedY;

    /**
     * Destination row where piece is moving to 
     */
    protected float xDST;

    /**
     * Destination column where piece is moving to 
     */
    protected float yDST;

    /**
     * Source row where piece is moving from 
     */
    protected float xLastMove;

    /**
     * Source column where piece is moving from 
     */
    protected float yLastMove;

    /**
     * Marks if piece has its last move
     */
    protected boolean lastMove;

    /**
     * Marks if piece has a moving destination
     */
    protected boolean DST;

    /**
     * Wait time before AI move its piece
     */
    protected int be4newMove;
    
    /**
     * Piece to be eaten by current piece
     */
    protected Piece terminated;

    /**
     * Castling: column where left rook located
     */
    protected float leftRook;    
    
    /**
     * Castling: column where right rook located
     */
    protected float rightRook; 

    /**
     * Marks if piece is checkmated
     */
    protected boolean checkMated;
    
    /**
     * Marks if piece is checked
     */
    protected boolean isCheck;

    /**
     * Marks if piece is checked for AI
     */
    protected boolean targeted;

    /**
     * Sound of piece capturing
     */
    protected Sound capture;

    /**
     * Sound of piece moving
     */
    protected Sound move;

    /**
     * Message print out to right bar
     */
    public String text;

    /**
     * The list contains all imaginary x moves
     */
    protected ArrayList<Float> imX = new ArrayList<Float>();
    
    /**
     * The list contains all imaginary y moves
     */
    protected ArrayList<Float> imY = new ArrayList<Float>();

    /**
     * Constructor for a chess piece, requires a name, color, player piece color, piece location at row x and column y, piece sprite and piece value  
     * @param name of piece
     * @param color of piece
     * @param playerColor as in config file
     * @param x where piece located (row)
     * @param y where piece located (column)
     * @param pic of piece
     * @param score of piece
     */
    public Piece(String name, String color, String playerColor, float x, float y, PImage pic, float score) {
        Piece.allPiece.add(this);
        this.name = name;
        this.color = color;
        this.playerColor = playerColor;
        this.x = x;
        this.y = y;
        this.pic = pic;
        this.score = score;
        this.selected = false;
        this.isAlive = true;
        this.isMoving = false;
        this.isPromoted = false;
        this.isfirstMove = true;
        this.lastMove = false;
        this.DST = false;
        this.be4newMove = 0;
        this.terminated = null;
        this.checkMated = false;
        this.isCheck = false;
        this.targeted = false;
        this.text = "";
        this.capture = new Sound("src/main/resources/XXLChess/capture.wav");
        this.move = new Sound("src/main/resources/XXLChess/move.wav");
    }

    /**
     * Get row where piece located
     * @return x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Get column where piece located
     * @return y
     */
    public float getY() {
        return this.y;
    }
    
    /**
     * If tile at (a, b) is occupied return the piece object
     * otherwise if tile is empty return null
     * @param a, row a
     * @param b, column b
     * @return p or null
     */
    public Piece getPiece(float a, float b) {
        for (Piece p: Piece.allPiece) {
            if (p.getX() == a && p.getY() == b && p.isAlive() == true) {
                return p;
            }
        }
        return null;
    }

    /**
     * Set current piece location to tile (a, b)
     * @param a, row a
     * @param b, column b
     */
    public void setXY(float a, float b) {
        this.x = a;
        this.y = b;
    }

    /**
     * Get piece name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set piece name
     * @param name, piece name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get piece value
     * @return score
     */
    public float getScore() {
        return this.score;
    }

    /**
     * Set piece value
     * @param score, piece value
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Get piece color
     * @return color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Get piece sprites
     * @return pic
     */
    public PImage getPic() {
        return this.pic;
    }

    /**
     * Check if piece has its first move
     * @return isfirstMove
     */
    public boolean getFirstMove() {
        return this.isfirstMove;
    }

    /**
     * Get column where right rook is located
     * @return rightRook
     */
    public float rightRook() {
        return this.rightRook;
    }

    /**
     * Get column where left rook is located
     * @return rightRook
     */
    public float leftRook() {
        return this.leftRook;
    }

    /**
     * Change piece sprites for pawn promotion
     * If piece color is white load the white queen
     * Otherwise, load the black queen
     * @param app, papplet
     */
    public void setPic(PApplet app) {
        if (this.color.equals("white")) {
            this.pic = app.loadImage("src/main/resources/XXLChess/w-queen.png");
        } else {
            this.pic = app.loadImage("src/main/resources/XXLChess/b-queen.png");
        }
    }

    /**
     * Get all possible x moves
     * @return xcord
     */
    public ArrayList<Float> getXC() {
        return this.xcord;
    }
    
    /**
     * Get all possible y moves
     * @return ycord
     */
    public ArrayList<Float> getYC() {
        return this.ycord;
    }
   
    /**
     * Get all value of possible moves
     * @return isObstacle
     */
    public ArrayList<Float> getOS() {
        return this.isObstacle;
    }
    
    /**
     * Update all possible moves
     */
    public abstract void getMovable();

    /**
     * Update all imaginary moves
     * in case where a piece is moving from (c, d) to (a, b)
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param c, source row
     * @param d, source column
     */
    public abstract void imaginaryMove(float a, float b, float c, float d);

    /**
     * Get the index of the most worthy move
     * If piece has no possible move, return -1
     * @return i or 0 or -1
     */
    public int getMax() {
        this.getMovable();
        if (this.getOS().size() == 0) {
            return -1;
        } else {
            for (int i = 0; i < this.getOS().size(); i++) {
                if (Collections.max(this.isObstacle) == this.getOS().get(i)) {
                    return i;
                }
            }
            return 0;
        }
    }

    /**
     * Check if piece is moving
     * @return isMoving
     */
    public boolean isMoving() {
        return this.isMoving;
    }
    
    /**
     * Set piece to moving
     * If it's piece first move, isfirstMove is set to false
     */
    public void setMoving() {
        this.isMoving = true;
        this.isfirstMove = false;
    }

     /**
     * Set piece moving speed
     * if given speed not allowed piece to move in max allowed time
     * speed is increased so that piece move exactly in max allowed time
     * otherwise, speed is kept
     * @param currentSpeed, given speed in config file
     * @param maxMoveTime, max time allowed per move
     * @param FPS, frame per sec
     */
    public void setSpeed(float currentSpeed, float maxMoveTime, int FPS) {
        //max frames within time allowed
        float maxFrames = maxMoveTime * FPS;
        //distance x and y, in pixels 
        float distX = (float) Math.sqrt(Math.abs(Math.pow(xLastMove*48 - xDST*48, 2)));
        float distY = (float) Math.sqrt(Math.abs(Math.pow(yLastMove*48 - yDST*48, 2)));
        //current frames from current speed
        float xFrames = distX / currentSpeed;
        float yFrames = distY / currentSpeed;
        float currentFrames = Math.max(xFrames, yFrames);
        //not within time allowed
        if (currentFrames > maxFrames) {
            this.speedX = (float) (distX / (maxFrames/2)) / 48;
            this.speedY = (float) (distY / (maxFrames/2)) / 48;
        //within time allowed
        } else {
            this.speedX = (float) (distX / (currentFrames/1.5)) / 48;
            this.speedY = (float) (distY / (currentFrames/1.5)) / 48;
        }
    }

    /**
     * Check if piece can move to tile (a, b)
     * return true if movable, else return false
     * @param a, row
     * @param b, column
     * @return true or false
     */
    public boolean isMovable(float a, float b) {
        if (this.xcord.size() == this.ycord.size()) {
            for (int i = 0; i < this.xcord.size(); i++) {
                if (this.xcord.get(i) == a && this.ycord.get(i) == b) {
                    return true;
                }
            }    
        }
        return false;
    }

    /**
     * Check if king is attackable if it moves to (a, b)
     * In case where king is moving from (c, d) to (a, b), can 1 enemy access (a, b)
     * return true if king will be in danger, otherwise false
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param c, source row
     * @param d, source column
     * @return true or false
     */
    public boolean isCheck(float a, float b, float c, float d) {
        //w/o the enemy king, the possible moves are
        this.imaginaryMove(a, b, c, d);
        for (int i = 0; i < this.imX.size(); i++) {
            if (this.imX.get(i) == a && this.imY.get(i) == b) {
                return true;
            }
        }       
        return false;
    }

    /**
     * Check if king is attackable if it moves to (a, b)
     * In case where king is moving from (c, d) to (a, b), can any enemy access (a, b)
     * return true if king will be in danger, otherwise false
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param color, color of king decide to move
     * @param c, source row
     * @param d, source column
     * @return true or false
     */
    public boolean isAttackable(float a, float b, String color, float c, float d) {
        for (Piece p: Piece.allPiece) {
            if (p.isCheck(a, b, c, d) && p.isAlive() && !p.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all piece contribute to king checkmate
     * Get checkmater of tile (a, b) if king move from (c, d)
     * return 1 checkmater if any, otherwise return null
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param color, color of king decide to move
     * @param c, source row
     * @param d, source column
     * @return p or null
     */
    public Piece getCheckmater(float a, float b, String color, float c, float d) {
        for (Piece p: Piece.allPiece) {
            if (p.isCheck(a, b, c, d) && p.isAlive() && !p.getColor().equals(color)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Check if a piece is king defender vs 1 enemy
     * if king is not checked, piece move from (c, d) to (a, b), will king be in danger
     * if king is checked, piece move from (c, d) to (a, b), will king will have another attacker
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param c, source row
     * @param d, source column
     * @param cl, color of piece decide to move
     * @return true or false
     */
    public boolean DefenderNotMovable(float a, float b, float c, float d, String cl) {
        float xking = this.getKing(cl).getX();
        float yking = this.getKing(cl).getY();

        this.imaginaryMove(a, b, c, d);
        for (int i = 0; i < this.imX.size(); i++) {
            //if player color
            if (this.imX.get(i) == xking && this.imY.get(i) == yking && !this.getKing(cl).isChecked()) {
                return true;
            } else if (this.imX.get(i) == xking && this.imY.get(i) == yking && this.getKing(cl).isChecked()) {
                boolean found = false;
                for (Piece attacker : this.getAttacker(this.getKing(cl))) {
                    if (this.getX() == attacker.getX() && this.getY() == attacker.getY()) {
                        found = true;
                        break;
                    }
                }
                if (found) { continue; }
                else { return true; }
            }
        }       
        return false;
    }

    /**
     * Check if a piece is king defender vs all enemy
     * piece move from (c, d) to (a, b), will king be in danger
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param c, source row
     * @param d, source column
     * @param cl, color of piece decide to move
     * @return true or false
     */
    public boolean isDefender(float a, float b, float c, float d, String cl) {
        for (Piece p: Piece.allPiece) {
            //Defender in (c, d) move to (a, b) makes kings in (xking, yking) attackable by enemy
            if (!p.getColor().equals(cl) && p.isAlive()) {        
                if (p.getX() == a && p.getY() == b) {
                    continue; 
                }
                if (p.DefenderNotMovable(a, b, c, d, cl)) {
                    return true; 
                }
            }
        }
        return false;
    }

    /**l
     * Get piece King of 1 color
     * return null if cant find the king with given color, else return king
     * @param color, color of king
     * @return p or null
     */
    public Piece getKing(String color) {
        for (Piece p: Piece.allPiece) {
            if (p.getName().equals("king") && p.getColor().equals(color)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get list of king attacker(s)
     * @param king, king piece
     * @return attacker
     */
    public ArrayList<Piece> getAttacker(Piece king) {
        ArrayList<Piece> attacker = new ArrayList<Piece>();
        float xking = king.getX();
        float yking = king.getY();
    
        for (Piece p: Piece.allPiece) {
            if (p.isMovable(xking, yking) && p.isAlive() && !p.getColor().equals(king.getColor())) {
                attacker.add(p);
            }
        }
        return attacker;
    }

    /**
     * Check if a piece in (c, d) move to (a, b), will king still be under attack
     * @param a, imaginary destination row
     * @param b, imaginary destination column
     * @param c, source row
     * @param d, source column
     * @param cl, color of piece decide to move
     * @return true or false
     */
    public boolean blockedByAlly(float a, float b, float c, float d, String cl) {
        float xking = this.getKing(cl).getX();
        float yking = this.getKing(cl).getY();

        this.imaginaryMove(a, b, c, d);
        for (int i = 0; i < this.imX.size(); i++) {
            if (this.imX.get(i) == xking && this.imY.get(i) == yking) {
                return false;
            }
        }       
        return true;
    }

    /**
     * Get all possible imaginary move x
     * @return imX
     */
    public ArrayList<Float> getIX() {
        return this.imX;
    }
   
    /**
     * Get all possible imaginary move y
     * @return imY
     */
    public ArrayList<Float> getIY() {
        return this.imY;
    }

    /**
     * Set piece to be eaten by current piece
     * @param piece, piece to eaten
     */
    public void terminate(Piece piece) {
        this.terminated = piece;
    }

    /**
     * Check if piece is eaten
     * @return isAlive
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Kill current piece
     */
    public void setAlive() {
        this.isAlive = false;
    }

    /**
     * Piece selected by player
     */
    public void setSelected() {
        if(this.selected == false) {
            this.selected = true;
        } else {
            this.selected = false;  
        }
    }

    /**
     * Check if piece last move is still highlighted
     * @return true or false
     */
    public boolean getLastMove() {
        return this.lastMove && this.DST;
    }
    
    /**
     * Turn off highlight for piece last move
     */
    public void offLastMove() {
        this.lastMove = false;
        this.DST = false;
    }
    
    /**
     * Turn on highlight for piece last move
     * Update destination where piece is moving to
     * Update source where piece moving from
     * @param a, destination row
     * @param b, destination column
     */
    public void setLastMove(float a, float b) {
        this.xLastMove = this.x; //where its from
        this.yLastMove = this.y;
        this.xDST = a;           //destination
        this.yDST = b;
        this.lastMove = true;    //turn on highlight
        this.DST = true; 
    }

    /**
     * Set piece checked state to given param
     * @param state, checked state
     */
    public void setChecked(boolean state) {
        this.isCheck = state;
    }

    /**
     * Set piece checked state to given param for coloring purpose
     * @param state, checked state
     */
    public void setTargeted(boolean state) {
        this.targeted = state;
    }

    /**
     * Check if piece is checked
     * @return isCheck, checked state
     */
    public boolean isChecked() {
        return this.isCheck;
    }

    /**
     * Check if piece is checked (color purpose)
     * @return isTargeted, checked state
     */
    public boolean isTargeted() {
        return this.targeted;
    }

    /**
     * Set message to display on right bar
     * @param message, text
     */
    public void setText(String message) {
        this.text = message;
    }

    /**
     * Delete all piece created from board
     */
    public void reset() {
        Piece.allPiece.removeAll(Piece.allPiece);
    }

    /**
     * Draw board, chesspiece, highlighting
     * @param app, papplet
     */
    public void draw(PApplet app) {
        if (this.isAlive) {
            //HIGHLIGHT CHECKMATED KING
            if (this.targeted) {
                if (this.name.equals("king")) {
                    app.fill(204, 0, 0); //RED
                } else {
                    app.fill(248, 161, 92); //dark red
                }
                app.rect(48*this.y, 48*this.x, 48, 48);
            }

            //HIGHLIGHT MOVE PATH
            //  <1> destination
            if (this.DST && !this.isCheck && !this.targeted) {
                if((this.xDST + this.yDST) % 2 != 0)
                    app.fill(204, 204, 102); //dark yellow
                else
                    app.fill(204, 204, 153); //light yellow
                app.rect(48*this.yDST, 48*this.xDST, 48, 48);

                //relate to order of object created in Piece.allPiece
                //if eating piece create after the eaten piece   
                //while both highlight position of eaten piece
                //first the eaten piece draw highlight & itself
                //second the eating piece only draw highlight
                //this causes eaten piece to be hidden by highlight of eating piece
                if (this.terminated != null) {
                    //if eating piece create after the eaten piece   
                    if(Piece.allPiece.indexOf(this) > Piece.allPiece.indexOf(this.terminated)) {
                        if (this.terminated.isAlive()) 
                            //after highlight draw image of eaten piece
                            app.image(this.terminated.getPic(), 48*this.yDST, 48*this.xDST, 48, 48);
                    }
                }
            } 
            //  <2> where its from
            if (this.lastMove && !this.isCheck && !this.targeted) {
                if((this.xLastMove + this.yLastMove) % 2 != 0)
                    app.fill(204, 204, 102); //dark yellow
                else
                    app.fill(204, 204, 153); //light yellow
                app.rect(48*this.yLastMove, 48*this.xLastMove, 48, 48);
            }

            //HIGHLIGHT RELEVANT OF SELECTED PIECE
            if (this.selected) {
                //ONLY AVAILABLE FOR PLAYER
                if (this.color.equals(this.playerColor)){
                    //SELECTED PIECE
                    app.fill(93, 148, 93); //dark green
                    app.rect(48*this.y, 48*this.x, 48, 48);  

                    //SUGGESTED MOVE
                    for(int i = 0; i < this.xcord.size(); i++){
                        //MOVABLE TILE = <0>
                        if (this.isObstacle.get(i) == 0) {
                            if((this.ycord.get(i) + this.xcord.get(i)) % 2 != 0)
                                app.fill(176, 201, 221); //dark blue
                            else
                                app.fill(220, 240, 255); //light blue
                            app.rect(48*this.ycord.get(i), 48*this.xcord.get(i), 48, 48);
                        //EATABLE TILE = <tile value>
                        } else { 
                            //highlight enemy
                            if (!this.getPiece(this.xcord.get(i), this.ycord.get(i)).getColor().equals(this.playerColor)) {
                                if((this.ycord.get(i) + this.xcord.get(i)) % 2 != 0)
                                    app.fill(240, 127, 111); //dark red
                                else
                                    app.fill(246, 148, 121); //light red
                                app.rect(48*this.ycord.get(i), 48*this.xcord.get(i), 48, 48);
                                app.image(this.getPiece(this.xcord.get(i), this.ycord.get(i)).getPic(), 48*this.ycord.get(i), 48*this.xcord.get(i), 48, 48);
                            } //ignore ally
                        } 
                    }
                }
            }
            app.textSize(13);
            app.fill(255, 255, 255);
            app.text(this.text, 679, 220);

            //IMAGE
            app.image(this.pic, 48*this.y, 48*this.x, 48, 48);
        }
    }

    /**
     * Update current piece position while moving
     * @param app, papplet
     */
    public void tick(PApplet app) {
        //IS ALIVE
        if (this.isAlive) {
            //IS MOVING
            if (this.isMoving) {  
                //IF AI TURN && PLAYER IS STILL FINISH MOVING
                if (!this.color.equals(this.playerColor)) {
                    for (Piece p: Piece.allPiece) {
                        //PLAYER IS STILL MOVING
                        if (p.getColor().equals(this.playerColor) && p.isMoving() && p.isAlive())
                            return;
                    }
                } 
                //WAIT TIME BEFORE AI MOVE
                if (this.be4newMove != 7 && !this.color.equals(this.playerColor)) {
                    this.be4newMove++;
                    return;
                }

                // //REACH DESTINATION
                if (Math.round(this.x) == this.xDST && Math.round(this.y) == this.yDST) {
                    this.isMoving = false;            //stop moving
                    this.setXY(this.xDST, this.yDST); //set new coord
                    if (this.terminated != null) {    //if we are eating a piece
                        this.terminated.setAlive();   //piece to be eaten die
                        this.capture.play();
                    } else {
                        this.move.play();
                    }
                    this.be4newMove = 0;              //reset AI wait time before making a move
                    this.terminated = null;           //reset piece to be eaten
                    //pawn promoted
                    if (this.name.equals("pawn")) {
                        if (this.x == 6 && this.color.equals(this.playerColor) || (this.x == 7 && !this.color.equals(this.playerColor))) {
                            this.setName("queen");
                            this.setPic(app);
                            this.setScore((float) 9.5);
                        }
                    }
                    this.getMovable();                //update possible move
                //HAVENT REACH DESTINATION
                } else {         
                    if (this.x == this.xDST) {          //same row
                        if (this.y < this.yDST) {                               //move to right
                            this.y += this.speedY;
                        } else {                                                //move to left
                            this.y -= this.speedY;
                        }
                    } else if (this.y == this.yDST) {   //same col
                        if (this.x < this.xDST) {
                            this.x += this.speedX;                           //move down
                        } else {
                            this.x -= this.speedX;                           //move up
                        }
                    } else {                            //move diagonal
                        if (this.x < this.xDST && this.y < this.yDST) {         //down right
                            this.x += this.speedX;
                            this.y += this.speedY;
                        } else if (this.x < this.xDST && this.y > this.yDST) {  //down left
                            this.x += this.speedX;
                            this.y -= this.speedY;
                        } else if (this.x > this.xDST && this.y > this.yDST) {  //up left
                            this.x -= this.speedX;
                            this.y -= this.speedY;
                        } else if (this.x > this.xDST && this.y < this.yDST) {  //up right
                            this.x -= this.speedX;
                            this.y += this.speedY;
                        }
                    }
                }
            //IS NOT MOVING
            } else {
                //update most current possible move
                this.getMovable();
            } 
        }
    }

    /**
     * Get Rook move, max is 26 moves
     * @return move, list of list of rook possible moves
     */
    public HashMap<String, ArrayList<Float>> rookMove() {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        //GO VERTICAL y static
        //VERTICAL UP
        float xR = this.x;
        while (xR > 0) {
            xR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, this.y) != null && !this.getPiece(xR, this.y).getColor().equals(this.color)) {
                if (!this.isDefender(xR, this.y, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(this.y);
                    os.add(this.getPiece(xR, this.y).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, this.y) != null && this.getPiece(xR, this.y).getColor().equals(this.color)) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, this.y, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(this.y);
                    os.add((float) 0);
                }
            }
        } 
        //VERTICAL DOWN
        xR = this.x;
        while (xR < 13) {
            xR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, this.y) != null && !this.getPiece(xR, this.y).getColor().equals(this.color)) {
                if (!this.isDefender(xR, this.y, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(this.y);
                    os.add(this.getPiece(xR, this.y).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, this.y) != null && this.getPiece(xR, this.y).getColor().equals(this.color)) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, this.y, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(this.y);
                    os.add((float) 0);
                }
            }
        }
        //GO HORIZONTAL x static
        //HORIZONTAL LEFT
        float yR = this.y;
        while (yR > 0) {
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(this.x, yR) != null && !this.getPiece(this.x, yR).getColor().equals(this.color)) {
                if (!this.isDefender(this.x, yR, this.x, this.y, this.color)) {
                    xc.add(this.x);
                    yc.add(yR);
                    os.add(this.getPiece(this.x, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(this.x, yR, this.x, this.y, this.color)) {
                    xc.add(this.x);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        //HORIZONTAL RIGHT
        yR = this.y;
        while (yR < 13) {
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(this.x, yR) != null && !this.getPiece(this.x, yR).getColor().equals(this.color)) {
                if (!this.isDefender(this.x, yR, this.x, this.y, this.color)) {
                    xc.add(this.x);
                    yc.add(yR);
                    os.add(this.getPiece(this.x, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(this.x, yR, this.x, this.y, this.color)) {
                    xc.add(this.x);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        move.put("xc", xc);
        move.put("yc", yc);
        move.put("os", os);
        return move;
    }

    /**
     * Get knight move, max is 8 moves
     * @param mode, imaginary move or actual move
     * @return move, list of list of knight possible moves
     */
    public HashMap<String, ArrayList<Float>> knightMove(String mode) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();
        if (this.x - 1 >= 0) {
            //<1> (-1, -2)
            if (this.y - 2 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x - 1, this.y - 2) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 1, this.y - 2).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y - 2, this.x, this.y, this.color))) {
                            xc.add(this.x - 1);
                            yc.add(this.y - 2);
                            os.add(this.getPiece(this.x - 1, this.y - 2).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y - 2, this.x, this.y, this.color))) {
                        xc.add(this.x - 1);
                        yc.add(this.y - 2);
                        os.add((float) 0);
                    }
                }
            } //<2> (-1, +2)
            if (this.y + 2 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x - 1, this.y + 2) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 1, this.y + 2).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y + 2, this.x, this.y, this.color))) {
                            xc.add(this.x - 1);
                            yc.add(this.y + 2);
                            os.add(this.getPiece(this.x - 1, this.y + 2).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y + 2, this.x, this.y, this.color))) {
                        xc.add(this.x - 1);
                        yc.add(this.y + 2);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x - 2 >= 0) {
            //<3> (-2, -1)
            if (this.y - 1 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x - 2, this.y - 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 2, this.y - 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 2, this.y - 1, this.x, this.y, this.color))) {
                            xc.add(this.x - 2);
                            yc.add(this.y - 1);
                            os.add(this.getPiece(this.x - 2, this.y - 1).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 2, this.y - 1, this.x, this.y, this.color))) {
                        xc.add(this.x - 2);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }//<4> (-2, +1)
            if (this.y + 1 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x - 2, this.y + 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 2, this.y + 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 2, this.y + 1, this.x, this.y, this.color))) {
                            xc.add(this.x - 2);
                            yc.add(this.y + 1);
                            os.add(this.getPiece(this.x - 2, this.y + 1).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 2, this.y + 1, this.x, this.y, this.color))) {
                        xc.add(this.x - 2);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x + 1 <= 13) {
            //<5> (+1, -2)
            if (this.y - 2 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x + 1, this.y - 2) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 1, this.y - 2).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y - 2, this.x, this.y, this.color))) {
                            xc.add(this.x + 1);
                            yc.add(this.y - 2);
                            os.add(this.getPiece(this.x + 1, this.y - 2).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y - 2, this.x, this.y, this.color))) {
                        xc.add(this.x + 1);
                        yc.add(this.y - 2);
                        os.add((float) 0);
                    }
                }
            }//<6> (+1, +2)
            if (this.y + 2 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x + 1, this.y + 2) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 1, this.y + 2).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y + 2, this.x, this.y, this.color))) {
                            xc.add(this.x + 1);
                            yc.add(this.y + 2);
                            os.add(this.getPiece(this.x + 1, this.y + 2).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y + 2, this.x, this.y, this.color))) {
                        xc.add(this.x + 1);
                        yc.add(this.y + 2);
                        os.add((float) 0);
                    }
                }
            }
        }

        if (this.x + 2 <= 13) {
            //<7> (+2, -1)
            if (this.y - 1 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x + 2, this.y - 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 2, this.y - 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 2, this.y - 1, this.x, this.y, this.color))) {
                            xc.add(this.x + 2);
                            yc.add(this.y - 1);
                            os.add(this.getPiece(this.x + 2, this.y - 1).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 2, this.y - 1, this.x, this.y, this.color))) {
                        xc.add(this.x + 2);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }//<8> (+2, +1)
            if (this.y + 1 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x + 2, this.y + 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 2, this.y + 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 2, this.y + 1, this.x, this.y, this.color))) {
                            xc.add(this.x + 2);
                            yc.add(this.y + 1);
                            os.add(this.getPiece(this.x + 2, this.y + 1).getScore());
                        }
                    }
                //NOT BLOCKED
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 2, this.y + 1, this.x, this.y, this.color))) {
                        xc.add(this.x + 2);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        move.put("xc", xc);
        move.put("yc", yc);
        move.put("os", os);
        return move;
    }

    /**
     * Get bishop move, max is 26 moves
     * @return move, list of list of bishop possible moves
     */
    public HashMap<String, ArrayList<Float>> bishopMove() {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc= new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        float xR = this.x;
        float yR = this.y;
        //<1> DIAGONAL UP RIGHT: x-- | y++
        while (xR > 0 && yR < 13) {
            xR--;
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add(this.getPiece(xR, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        //RESET 2
        xR = this.x;
        yR = this.y;
        //<2> DIAGONAL UP LEFT: x-- | y-- 
        while (xR > 0 && yR > 0) {
            xR--;
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add(this.getPiece(xR, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        //RESET 3
        xR = this.x;
        yR = this.y;
        //<3> DIAGONAL DOWN RIGHT: x++ | y++
        while (xR < 13 && yR < 13) {
            xR++;
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add(this.getPiece(xR, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        //RESET 4
        xR = this.x;
        yR = this.y;
        //<4> DIAGONAL DOWN LEFT: x++ | y--
        while (xR < 13 && yR > 0) {
            xR++;
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add(this.getPiece(xR, yR).getScore());
                }
                break;
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                break;
            //NOT BLOCKED
            } else {
                if (!this.isDefender(xR, yR, this.x, this.y, this.color)) {
                    xc.add(xR);
                    yc.add(yR);
                    os.add((float) 0);
                }
            }
        }
        move.put("xc", xc);
        move.put("yc", yc);
        move.put("os", os);
        return move;
    }

    /**
     * Get king move, max is 8 moves, not count castling
     * @param name, king move or guard move
     * @return move, list of list of king possible moves
     */
    public HashMap<String, ArrayList<Float>> kingMove(String name) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc= new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        if (this.x - 1 >= 0) {
            //<1> (-1, +0)
            //SAFE TILE
            if ((!this.isAttackable(this.x - 1, this.y, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x - 1, this.y, this.x, this.y, this.color) && name.equals("guard"))) {
                //BLOCKED BY ENEMY
                if (this.getPiece(this.x - 1, this.y) != null && !this.color.equals(this.getPiece(this.x - 1, this.y).getColor())) {
                    xc.add(this.x - 1);
                    yc.add(this.y);
                    os.add(this.getPiece(this.x - 1, this.y).getScore());
                //BLOCKED BY ALLY
                } else if (this.getPiece(this.x - 1, this.y) != null && this.color.equals(this.getPiece(this.x - 1, this.y).getColor())) {
                    ;;//do nothing
                //NOT BLOCKED
                } else {
                    xc.add(this.x - 1);
                    yc.add(this.y);
                    os.add((float) 0);
                }
            }
            //<2> (-1, -1)
            if (this.y - 1 >= 0) {
                //SAFE TILE
                if ((!this.isAttackable(this.x - 1, this.y - 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x - 1, this.y - 1, this.x, this.y, this.color) && name.equals("guard"))) {
                    //BLOCKED BY ENEMY
                    if (this.getPiece(this.x - 1, this.y - 1) != null && !this.color.equals(this.getPiece(this.x - 1, this.y - 1).getColor())) {
                        xc.add(this.x - 1);
                        yc.add(this.y - 1);
                        os.add(this.getPiece(this.x - 1, this.y - 1).getScore());
                    //BLOCKED BY ALLY
                    } else if (this.getPiece(this.x - 1, this.y - 1) != null && this.color.equals(this.getPiece(this.x - 1, this.y - 1).getColor())) {
                        ;;//do nothing
                    //NOT BLOCKED
                    } else {
                        xc.add(this.x - 1);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }
            //<3> (-1, +1)
            if (this.y + 1 <= 13) {
                //SAFE TILE
                if ((!this.isAttackable(this.x - 1, this.y + 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x - 1, this.y + 1, this.x, this.y, this.color) && name.equals("guard"))) {
                    //BLOCKED BY ENEMY
                    if (this.getPiece(this.x - 1, this.y + 1) != null && !this.color.equals(this.getPiece(this.x - 1, this.y + 1).getColor())) {
                        xc.add(this.x - 1);
                        yc.add(this.y + 1);
                        os.add(this.getPiece(this.x - 1, this.y + 1).getScore());
                    //BLOCKED BY ALLY
                    } else if (this.getPiece(this.x - 1, this.y + 1) != null && this.color.equals(this.getPiece(this.x - 1, this.y + 1).getColor())) {
                        ;;//do nothing
                    //NOT BLOCKED
                    } else {
                        xc.add(this.x - 1);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x + 1 <= 13) {
            //<4> (+1, +0)
            //SAFE TILE
            if ((!this.isAttackable(this.x + 1, this.y, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x + 1, this.y, this.x, this.y, this.color) && name.equals("guard"))) {
                //BLOCKED BY ENEMY
                if (this.getPiece(this.x + 1, this.y) != null && !this.color.equals(this.getPiece(this.x + 1, this.y).getColor())) {
                    xc.add(this.x + 1);
                    yc.add(this.y);
                    os.add(this.getPiece(this.x + 1, this.y).getScore());
                //BLOCKED BY ALLY
                } else if (this.getPiece(this.x + 1, this.y) != null && this.color.equals(this.getPiece(this.x + 1, this.y).getColor())) {
                    ;;//do nothing
                //NOT BLOCKED
                } else {
                    xc.add(this.x + 1);
                    yc.add(this.y);
                    os.add((float) 0);
                }
            }
            //<5> (+1, -1)
            if (this.y - 1 >= 0) {
                //SAFE TILE
                if ((!this.isAttackable(this.x + 1, this.y - 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x + 1, this.y - 1, this.x, this.y, this.color) && name.equals("guard"))) {
                    //BLOCKED BY ENEMY
                    if (this.getPiece(this.x + 1, this.y - 1) != null && !this.color.equals(this.getPiece(this.x + 1, this.y - 1).getColor())) {
                        xc.add(this.x + 1);
                        yc.add(this.y - 1);
                        os.add(this.getPiece(this.x + 1, this.y - 1).getScore());
                    //BLOCKED BY ALLY
                    } else if (this.getPiece(this.x + 1, this.y - 1) != null && this.color.equals(this.getPiece(this.x + 1, this.y - 1).getColor())) {
                        ;;//do nothing
                    //NOT BLOCKED
                    } else {
                        xc.add(this.x + 1);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }
            //<6> (+1, +1)
            if (this.y + 1 <= 13) {
                //SAFE TILE
                if ((!this.isAttackable(this.x + 1, this.y + 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x + 1, this.y + 1, this.x, this.y, this.color) && name.equals("guard"))) {
                    //BLOCKED BY ENEMY
                    if (this.getPiece(this.x + 1, this.y + 1) != null && !this.color.equals(this.getPiece(this.x + 1, this.y + 1).getColor())) {
                        xc.add(this.x + 1);
                        yc.add(this.y + 1);
                        os.add(this.getPiece(this.x + 1, this.y + 1).getScore());
                    //BLOCKED BY ALLY
                    } else if (this.getPiece(this.x + 1, this.y + 1) != null && this.color.equals(this.getPiece(this.x + 1, this.y + 1).getColor())) {
                        ;;//do nothing
                    //NOT BLOCKED
                    } else {
                        xc.add(this.x + 1);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        //<7> (+0, -1)
        if (this.y - 1 >= 0) {
            //SAFE TILE
            if ((!this.isAttackable(this.x, this.y - 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x, this.y - 1, this.x, this.y, this.color) && name.equals("guard"))) {
                //BLOCKED BY ENEMY
                if (this.getPiece(this.x, this.y - 1) != null && !this.color.equals(this.getPiece(this.x, this.y - 1).getColor())) {
                    xc.add(this.x);
                    yc.add(this.y - 1);
                    os.add(this.getPiece(this.x, this.y - 1).getScore());
                //BLOCKED BY ALLY
                } else if (this.getPiece(this.x, this.y - 1) != null && this.color.equals(this.getPiece(this.x, this.y - 1).getColor())) {
                    ;;//do nothing
                //NOT BLOCKED
                } else {
                    xc.add(this.x);
                    yc.add(this.y - 1);
                    os.add((float) 0);
                }
            }
        }
        //<8> (+0, +1)
        if (this.y + 1 <= 13) {
            //SAFE TILE
            if ((!this.isAttackable(this.x, this.y + 1, this.color, this.x, this.y) && name.equals("king")) || (!this.isDefender(this.x, this.y + 1, this.x, this.y, this.color) && name.equals("guard"))) {
                //BLOCKED BY ENEMY
                if (this.getPiece(this.x, this.y + 1) != null && !this.color.equals(this.getPiece(this.x, this.y + 1).getColor())) {
                    xc.add(this.x);
                    yc.add(this.y + 1);
                    os.add(this.getPiece(this.x, this.y + 1).getScore());
                //BLOCKED BY ALLY
                } else if (this.getPiece(this.x, this.y + 1) != null && this.color.equals(this.getPiece(this.x, this.y + 1).getColor())) {
                    ;;//do nothing
                //NOT BLOCKED
                } else {
                    xc.add(this.x);
                    yc.add(this.y + 1);
                    os.add((float) 0);
                }
            }
        }
        move.put("xc", xc);
        move.put("yc", yc);
        move.put("os", os);
        return move;
    }

    /**
     * Get camel move, max is 8 moves
     * @param mode, imaginary move or actual move
     * @return move, list of list of camel possible moves
     */
    public HashMap<String, ArrayList<Float>> camelMove(String mode) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();
        ArrayList<Float> os = new ArrayList<Float>();

        if (this.x - 1 >= 0) {
            //<1> (-1, -3)
            if (this.y - 3 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x - 1, this.y - 3) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 1, this.y - 3).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y - 3, this.x, this.y, this.color))) {
                            xc.add(this.x - 1);
                            yc.add(this.y - 3);
                            os.add(this.getPiece(this.x - 1, this.y - 3).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y - 3, this.x, this.y, this.color))) {
                        xc.add(this.x - 1);
                        yc.add(this.y - 3);
                        os.add((float) 0);
                    }
                }
            }
            //<2> (-1, +3)
            if (this.y + 3 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x - 1, this.y + 3) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 1, this.y + 3).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y + 3, this.x, this.y, this.color))) {
                            xc.add(this.x - 1);
                            yc.add(this.y + 3);
                            os.add(this.getPiece(this.x - 1, this.y + 3).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 1, this.y + 3, this.x, this.y, this.color))) {
                        xc.add(this.x - 1);
                        yc.add(this.y + 3);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x - 3 >= 0) {
            //<3> (-3, -1)
            if (this.y - 1 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x - 3, this.y - 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 3, this.y - 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 3, this.y - 1, this.x, this.y, this.color))) {
                            xc.add(this.x - 3);
                            yc.add(this.y - 1);
                            os.add(this.getPiece(this.x - 3, this.y - 1).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 3, this.y - 1, this.x, this.y, this.color))) {
                        xc.add(this.x - 3);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }
            //<4> (-3, +1)
            if (this.y + 1 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x - 3, this.y + 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x - 3, this.y + 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 3, this.y + 1, this.x, this.y, this.color))) {
                            xc.add(this.x - 3);
                            yc.add(this.y + 1);
                            os.add(this.getPiece(this.x - 3, this.y + 1).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x - 3, this.y + 1, this.x, this.y, this.color))) {
                        xc.add(this.x - 3);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x + 1 <= 13) {
            //<5> (+1, -3)
            if (this.y - 3 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x + 1, this.y - 3) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 1, this.y - 3).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y - 3, this.x, this.y, this.color))) {
                            xc.add(this.x + 1);
                            yc.add(this.y - 3);
                            os.add(this.getPiece(this.x + 1, this.y - 3).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y - 3, this.x, this.y, this.color))) {
                        xc.add(this.x + 1);
                        yc.add(this.y - 3);
                        os.add((float) 0);
                    }
                }
            }
            //<6> (+1, +3)
            if (this.y + 3 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x + 1, this.y + 3) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 1, this.y + 3).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y + 3, this.x, this.y, this.color))) {
                            xc.add(this.x + 1);
                            yc.add(this.y + 3);
                            os.add(this.getPiece(this.x + 1, this.y + 3).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 1, this.y + 3, this.x, this.y, this.color))) {
                        xc.add(this.x + 1);
                        yc.add(this.y + 3);
                        os.add((float) 0);
                    }
                }
            }
        }
        if (this.x + 3 <= 13) {
            //<7> (+3, -1)
            if (this.y - 1 >= 0) {
                //BLOCKED
                if (this.getPiece(this.x + 3, this.y - 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 3, this.y - 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 3, this.y - 1, this.x, this.y, this.color))) {
                            xc.add(this.x + 3);
                            yc.add(this.y - 1);
                            os.add(this.getPiece(this.x + 3, this.y - 1).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 3, this.y - 1, this.x, this.y, this.color))) {
                        xc.add(this.x + 3);
                        yc.add(this.y - 1);
                        os.add((float) 0);
                    }
                }
            }
            //<8> (+3, +1)
            if (this.y + 1 <= 13) {
                //BLOCKED
                if (this.getPiece(this.x + 3, this.y + 1) != null) {
                    //BY ENEMY
                    if (!this.color.equals(this.getPiece(this.x + 3, this.y + 1).getColor())) {
                        if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 3, this.y + 1, this.x, this.y, this.color))) {
                            xc.add(this.x + 3);
                            yc.add(this.y + 1);
                            os.add(this.getPiece(this.x + 3, this.y + 1).getScore());
                        }
                    }
                //NOT BLOCKED 
                } else {
                    if (mode.equals("img") || (mode.equals("actl") && !this.isDefender(this.x + 3, this.y + 1, this.x, this.y, this.color))) {
                        xc.add(this.x + 3);
                        yc.add(this.y + 1);
                        os.add((float) 0);
                    }
                }
            }
        }
        move.put("xc", xc);
        move.put("yc", yc);
        move.put("os", os);
        return move;
    }

    /**
     * Get rook imaginary move, max is 26 moves
     * if a piece move from (c, d) to (a, b), what are possible moves of current rook
     * @param a, destination row
     * @param b, destination column
     * @param c, source row
     * @param d, source row
     * @return move, list of list of rook possible imaginary moves
     */
    public HashMap<String, ArrayList<Float>> rookIM(float a, float b, float c, float d) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();

        //GO VERTICAL y static
        //VERTICAL DOWN
        float xR = this.x;
        while (xR > 0) {
            xR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, this.y) != null && !this.getPiece(xR, this.y).getColor().equals(this.color)) {
                xc.add(xR);
                yc.add(this.y);
                //ignore (c, d)
                if (xR == c && this.y == d) { 
                    continue; 
                } else { 
                    break; 
                }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, this.y) != null && this.getPiece(xR, this.y).getColor().equals(this.color)) {
                //ALLY eaten
                if (xR == a && this.y == b) { 
                    xc.add(xR);
                    yc.add(this.y);
                }
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(this.y);
                //blocked by (a, b)
                if (xR == a && this.y == b) { break; }
            }
        } 
        //VERTICAL UP
        xR = this.x;
        while (xR < 13) {
            xR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, this.y) != null && !this.getPiece(xR, this.y).getColor().equals(this.color)) {
                xc.add(xR);
                yc.add(this.y);
                //ignore (a, b)
                if (xR == c && this.y == d) { 
                    continue; 
                } else { 
                    break; 
                }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, this.y) != null && this.getPiece(xR, this.y).getColor().equals(this.color)) {
                //ALLY eaten
                if (xR == a && this.y == b) { 
                    xc.add(xR);
                    yc.add(this.y);
                }
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(this.y);
                //blocked by (c, d)
                if (xR == a && this.y == b) { break; }
            }
        }
        //GO HORIZONTAL x static
        //HORIZONTAL LEFT
        float yR = this.y;
        while (yR > 0) {
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(this.x, yR) != null && !this.getPiece(this.x, yR).getColor().equals(this.color)) {
                xc.add(this.x);
                yc.add(yR);
                //ignore (c, d)
                if (this.x == c && yR == d) { 
                    continue; 
                } else { 
                    break; 
                }
            //BLOCKED BY ALLY
            } else if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                //ALLY eaten
                if (this.x == a && yR == b) { 
                    xc.add(this.x);
                    yc.add(yR);
                }
                break;
            //NOT BLOCKED
            } else {
                xc.add(this.x);
                yc.add(yR);
                //blocked by (c, d)
                if (this.x == a && yR == b) { break; }
            }
        }
        //HORIZONTAL RIGHT
        yR = this.y;
        while (yR < 13) {
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(this.x, yR) != null && !this.getPiece(this.x, yR).getColor().equals(this.color)) {
                xc.add(this.x);
                yc.add(yR);
                //ignore (c, d)
                if (this.x == c && yR == d) { 
                    continue; 
                } else { 
                    break; 
                }
            //BLOCKED BY ALLY
            } else if (this.getPiece(this.x, yR) != null && this.getPiece(this.x, yR).getColor().equals(this.color)) {
                //ALLY eaten
                if (this.x == a && yR == b) { 
                    xc.add(this.x);
                    yc.add(yR);
                }
                break;
            //NOT BLOCKED
            } else {
                xc.add(this.x);
                yc.add(yR);
                //blocked by (a, b)
                if (this.x == a && yR == b) { break; }
            }
        }
        move.put("imx", xc);
        move.put("imy", yc);
        return move;
    }

     /**
     * Get bishop imaginary move, max is 26 moves
     * if a piece move from (c, d) to (a, b), what are possible moves of current bishop
     * @param a, destination row
     * @param b, destination column
     * @param c, source row
     * @param d, source row
     * @return move, list of list of bishop possible imaginary moves
     */
    public HashMap<String, ArrayList<Float>> bishopIM(float a, float b, float c, float d) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc= new ArrayList<Float>();
    
        float xR = this.x;
        float yR = this.y;

        //<1> DIAGONAL UP RIGHT: x-- | y++
        while (xR > 0 && yR < 13) {
            xR--;
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                xc.add(xR);
                yc.add(yR);
                //ignore (c, d)
                if (xR == c && yR == d) { 
                    continue;
                } else { break; }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                //ALLY eaten
                if (xR == a && yR == b) {
                    xc.add(xR);
                    yc.add(yR);
                } 
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(yR);
                //blocked by (a, b)
                if (xR == a && yR == b) { break; }
            }
        }
        //RESET 2
        xR = this.x;
        yR = this.y;
        //<2> DIAGONAL UP LEFT: x-- | y-- 
        while (xR > 0 && yR > 0) {
            xR--;
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                xc.add(xR);
                yc.add(yR);
                //ignore (c, d)
                if (xR == c && yR == d) { 
                    continue;
                } else { break; }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                //ALLY eaten
                if (xR == a && yR == b) {
                    xc.add(xR);
                    yc.add(yR);
                } 
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(yR);
                //blocked by (a, b)
                if (xR == a && yR == b) { break; }
            }
        }
        //RESET 3
        xR = this.x;
        yR = this.y;
        //<3> DIAGONAL DOWN RIGHT: x++ | y++
        while (xR < 13 && yR < 13) {
            xR++;
            yR++;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                xc.add(xR);
                yc.add(yR);
                //ignore (c, d)
                if (xR == c && yR == d) { 
                    continue;
                } else { break; }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                //ALLY eaten
                if (xR == a && yR == b) {
                    xc.add(xR);
                    yc.add(yR);
                } 
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(yR);
                //blocked by (a, b)
                if (xR == a && yR == b) { break; }
            }
        }
        //RESET 4
        xR = this.x;
        yR = this.y;
        //<4> DIAGONAL DOWN LEFT: x++ | y--
        while (xR < 13 && yR > 0) {
            xR++;
            yR--;
            //BLOCKED BY ENEMY
            if (this.getPiece(xR, yR) != null && !this.color.equals(this.getPiece(xR, yR).getColor())) {
                xc.add(xR);
                yc.add(yR);
                //ignore (c, d)
                if (xR == c && yR == d) { 
                    continue;
                } else { break; }
            //BLOCKED BY ALLY
            } else if (this.getPiece(xR, yR) != null && this.color.equals(this.getPiece(xR, yR).getColor())) {
                //ALLY eaten
                if (xR == a && yR == b) {
                    xc.add(xR);
                    yc.add(yR);
                } 
                break;
            //NOT BLOCKED
            } else {
                xc.add(xR);
                yc.add(yR);
                //blocked by (a, b)
                if (xR == a && yR == b) { break; }
            }
        }
        move.put("imx", xc);
        move.put("imy", yc);
        return move;
    }

     /**
     * Get king imaginary move, max is 8 moves
     * if a piece move from (c, d) to (a, b), what are possible moves if current king
     * @param a, destination row
     * @param b, destination column
     * @param c, source row
     * @param d, source row
     * @return move, list of list of king possible imaginary moves
     */
    public HashMap<String, ArrayList<Float>> kingIM(float a, float b, float c, float d) {
        HashMap<String, ArrayList<Float>> move = new HashMap<String, ArrayList<Float>>();
        ArrayList<Float> xc = new ArrayList<Float>();
        ArrayList<Float> yc = new ArrayList<Float>();

        if (this.x - 1 >= 0) {
            //<1> (-1, +0)
            xc.add(this.x - 1);
            yc.add(this.y);
            //<2> (-1, -1)
            if (this.y - 1 >= 0) {
                xc.add(this.x - 1);
                yc.add(this.y - 1);
            }     
            //<3> (-1, +1)
            if (this.y + 1 <= 13) {
                xc.add(this.x - 1);
                yc.add(this.y + 1);
            }
        }
        if (this.x + 1 <= 13) {
            //<4> (+1, +0)
            xc.add(this.x + 1);
            yc.add(this.y);
            //<5> (+1, -1)
            if (this.y - 1 >= 0) {     
                xc.add(this.x + 1);
                yc.add(this.y - 1);
            }
            //<6> (+1, +1)
            if (this.y + 1 <= 13) {
                xc.add(this.x + 1);
                yc.add(this.y + 1);
            }
        }
        if (this.y - 1 >= 0) {
            //<7> (+0, -1)
            xc.add(this.x);
            yc.add(this.y - 1);
        }
        if (this.y + 1 <= 13) {
            //<8> (+0, +1)
            xc.add(this.x);
            yc.add(this.y + 1);
        }
        move.put("imx", xc);
        move.put("imy", yc);
        return move;
    }
}