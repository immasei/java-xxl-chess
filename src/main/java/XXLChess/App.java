package XXLChess;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.event.MouseEvent;
import java.io.*;
import java.util.*;

/**
 * APP
 */
public class App extends PApplet {
    /**
     * size of sprite
     */
    public static final int SPRITESIZE = 480;

    /**
     * cell size
     */
    public static final int CELLSIZE = 48;

    /**
     * size of right bar
     */
    public static final int SIDEBAR = 120;

    /**
     * board width
     */
    public static final int BOARD_WIDTH = 14;

    /**
     * window width = 792
     */
    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;

    /**
     * window height = 672
     */
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE;

    /**
     * Frame per sec
     */
    public static final int FPS = 60;
	
    /**
     * config file
     */
    public String configPath;

    /**
     * chess board
     */
    private Board board;

    /**
     * board layout chess piece allocation
     */
    public String layout;

    /**
     * color of player piece
     */
    public String playerColor;

    /**
     * default move speed
     */
    public float movement_speed;

    /**
     * max time allowed for movement
     */
    public float max_movement_time;

    /**
     * player count time
     */
    public int timePL;

    /**
     * ai count time
     */
    public int timeAI;

    /**
     * player time increment
     */
    public int incrementPL;

    /**
     * ai time increment
     */
    public int incrementAI;

    /**
     * mouse click state by player
     */
    public boolean clicked;

    /**
     * mouse x
     */
    public float row;

    /**
     * mouse y
     */
    public float col;

    /**
     * ai chosen source x
     */
    public float aiR;

    /**
     * ai chosen source y
     */
    public float aiC;

    /**
     * ai chosen destination x
     */
    public float aiNEWR;

    /**
     * ai chosen destination y
     */
    public float aiNEWC;

    /**
     * current turn of player
     */
    public boolean playerTurn;

    /**
     * marks if player is moving
     */
    public boolean playerMove;

    /**
     * marks if ai is moving
     */
    public boolean AIMove;

    /**
     * warning sound if attemp to legal move when king checked
     */

    public Sound warn;

    /**
     * marks if player is losed
     */
    public boolean playerLose;

    /**
     * marks if AI is losed
     */
    public boolean AILose;

    /**
     * marks if player has resigned
     */
    public boolean resigned;

    /**
     * marks if player must defend now
     */
    public boolean defend;

    /**
     * player time counter
     */
    public int counterPL;

    /**
     * aitime counter
     */
    public int counterAI;

    /**
     * defend time counter
     */
    public int frameCounter;

    /**
     * App class
     */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() { //called once
        frameRate(FPS);
		//LOAD CONFIG
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.layout = conf.getString("layout");
        this.playerColor = conf.getString("player_colour"); 
        this.movement_speed = conf.getFloat("piece_movement_speed");
        this.max_movement_time = conf.getFloat("max_movement_time");
        
        JSONObject time_control = conf.getJSONObject("time_controls"); 
        this.timePL = time_control.getJSONObject("player").getInt("seconds");
        this.incrementPL = time_control.getJSONObject("player").getInt("increment");

        this.timeAI = time_control.getJSONObject("cpu").getInt("seconds");
        this.incrementAI = time_control.getJSONObject("cpu").getInt("increment"); 

        //board layout
        this.board = new Board(BOARD_WIDTH, CELLSIZE);

        //mouseClick
        this.clicked = false;
        if (this.playerColor.equals("white")) {
            this.playerTurn = true;
        } else {
            this.playerTurn = false;
        }
        this.playerMove = false;
        this.AIMove = false;
        this.warn = new Sound("src/main/resources/XXLChess/notify.wav");

        this.playerLose = false;
        this.AILose = false;
        this.resigned = false;
        this.defend = false;
        this.counterPL = 1;
        this.counterAI = 1;

        this.frameCounter = 0;

        //LOAD IMAGE
        try{
            File f = new File(layout);
            Scanner sc = new Scanner(f);
            int x = 0; // x = row

            while(sc.hasNextLine()){
                String line = sc.nextLine();

                for(int y = 0; y < line.length(); y++) { //y = col
                    char letter = line.charAt(y);
                    if(letter == 'P') {     //P
                        Piece p = new Pawn("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-pawn.png"));
                    } if(letter == 'p') {
                        Piece p = new Pawn("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-pawn.png"));
                    } if(letter == 'R') {   //R
                        Piece p = new Rook("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-rook.png"));
                    } if(letter == 'r') {
                        Piece p = new Rook("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-rook.png"));
                    } if(letter == 'N') {   //N
                        Piece p = new Knight("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-knight.png"));
                    } if(letter == 'n') {
                        Piece p = new Knight("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-knight.png"));
                    } if(letter == 'B') {   //B
                        Piece p = new Bishop("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-bishop.png"));
                    } if(letter == 'b') {
                        Piece p = new Bishop("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-bishop.png"));
                    } if(letter == 'H') {   //H
                        Piece p = new Archbishop("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-archbishop.png"));
                    } if(letter == 'h') {
                        Piece p = new Archbishop("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-archbishop.png"));
                    } if(letter == 'C') {   //C
                        Piece p = new Camel("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-camel.png"));
                    } if(letter == 'c') {
                        Piece p = new Camel("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-camel.png"));
                    } if(letter == 'G') {   //G
                        Piece p = new Guard("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-knight-king.png"));
                    } if(letter == 'g') {
                        Piece p = new Guard("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-knight-king.png"));
                    } if(letter == 'A') {   //A
                        Piece p = new Amazon("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-amazon.png"));
                    } if(letter == 'a') {
                        Piece p = new Amazon("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-amazon.png"));
                    } if(letter == 'K') {   //K
                        Piece p = new King("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-king.png"));
                    } if(letter == 'k') {
                        Piece p = new King("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-king.png"));
                    } if(letter == 'E') {   //E
                        Piece p = new Chancellor("black", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-chancellor.png"));
                    } if(letter == 'e') {
                        Piece p = new Chancellor("white", this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-chancellor.png"));
                    } if(letter == 'Q') {   //Q
                        Piece p = new Queen("black",  this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/b-queen.png"));
                    } if(letter == 'q') {
                        Piece p = new Queen("white",  this.playerColor, x, y, this.loadImage("src/main/resources/XXLChess/w-queen.png"));
                    }
                }
                x++;
            }
                sc.close();
        } catch(FileNotFoundException e) {
            return;
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed() {
        //e = resigned
        if (this.keyCode == 69) {
            this.playerLose = true;
            this.resigned = true;
        }
        //r = reset
        if (this.keyCode == 82) {
            this.getKing(this.playerColor).reset();
            this.setup();
        }
    }

    /**
     * Receive mouse pressed signal from the mouse.
    */
    @Override
    public void mousePressed(MouseEvent e) {
        // TURN IS FINISED BUT AI IS STILL FINISH MOVING
        // CHECK IF AI IS STILL MOVING
        for (Piece d : Piece.allPiece) {
            if (!d.getColor().equals(this.playerColor) && d.isAlive() && d.isMoving()) {
                //Player is still moving
                this.AIMove = true;
                break;
            } else {
                this.AIMove = false;
            }
        }
        //PLAYER TURN
        if (this.playerTurn && !this.AIMove) {
            //MOVE SELECTED PIECE
            if(this.clicked == true) {
                this.clicked = false;
                //TILE SELECTED
                float newR = e.getY() / CELLSIZE;
                float newC = e.getX() / CELLSIZE;

                //DESELECT CURRENT MOVE
                this.getPiece(row, col).setSelected();

                //INVALID MOVE
                if (!this.getPiece(row, col).isMovable(newR, newC)) {
                    //RESELECT NEW PIECE
                    if (row != newR || col != newC) {   
                        if (this.getPiece(newR, newC) != null && this.getPiece(newR, newC).getColor().equals(playerColor)) {
                            this.getPiece(newR, newC).setSelected();
                            this.clicked = true;
                        }
                        row = newR;
                        col = newC;
                    }

                //VALID MOVE
                } else {
                    //KING CHECKED
                    Piece king = this.getKing(this.playerColor);
                    if (king.isChecked()) {
                        //many attackers
                        if (this.getAttacker(king).size() > 1 && row != king.getX() && col != king.getY()) {
                            this.warn.play();
                            this.defend = true;
                            this.frameCounter = 0;
                            return;
                        //1 attacker
                        } else {
                            Piece attacker = this.getAttacker(king).get(0);
                            //king is moving
                            if (row == king.getX() && col == king.getY()) {
                                this.defend = false;
                            //eat the attacker
                            } else if (newR == attacker.getX() && newC == attacker.getY()) {
                                this.defend = false;
                            //further check
                            } else {
                                //not blocking enemy
                                if (!attacker.blockedByAlly(newR, newC, row, col, this.playerColor)) {
                                    this.warn.play();
                                    this.defend = true;
                                    this.frameCounter = 0;
                                    return;
                                }
                            }
                        }
                    }
                    //DEHIGHLIGHT AI LAST MOVE
                    for (Piece chess: Piece.allPiece) {
                        if (chess.getLastMove()) {
                            chess.offLastMove();
                        }
                    }
                    //TILE OCCUPIED
                    this.getPiece(row, col).setLastMove(newR, newC);
                    if (this.getPiece(newR, newC) != null) { 
                        this.getPiece(row, col).terminate(this.getPiece(newR, newC));
                        this.getPiece(row, col).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                        this.getPiece(row, col).setMoving();
                    //TILE EMPTY
                    } else {
                        this.getPiece(row, col).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                        this.getPiece(row, col).setMoving();
                    }
                    //castling
                    if (this.getPiece(row, col).getName().equals("king") && Math.abs(newC - col) == 2) {
                        if (newC == col - 2) {
                            this.getPiece(row, this.getPiece(row, col).leftRook()).setLastMove(row, col - 1);
                            this.getPiece(row, this.getPiece(row, col).leftRook()).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                            this.getPiece(row, this.getPiece(row, col).leftRook()).setMoving();
                        } else if (newC == col + 2) {
                            this.getPiece(row, this.getPiece(row, col).rightRook()).setLastMove(row, col + 1);
                            this.getPiece(row, this.getPiece(row, col).rightRook()).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                            this.getPiece(row, this.getPiece(row, col).rightRook()).setMoving();
                        } 
                    }
                    //FINISH PLAYER TURN
                    this.playerTurn = false;
                    this.timePL += this.incrementPL;
                }                   
            //SELECT PIECE
            } else {
                //TILE SELECTED
                row = e.getY() / CELLSIZE;
                col = e.getX() / CELLSIZE;
                //NEW PIECE SELECTED
                //SHOW MOVE SUGGESTIONS
                if (this.getPiece(row, col) != null && this.getPiece(row, col).getColor().equals(playerColor)) {
                    this.getPiece(row, col).setSelected();
                    this.clicked = true;
                }    
            }
        }
    }

    /**
     * AI makes a move
    */
    public void AI() {
        // TURN IS FINISED BUT PLAYER IS STILL FINISH MOVING
        // CHECK IF PLAYER IS STILL MOVING
        for (Piece d : Piece.allPiece) {
            if (d.getColor().equals(this.playerColor) && d.isAlive() && d.isMoving()) {
                //Player is still moving
                this.playerMove = true;
                break;
            } else {
                this.playerMove = false;
            }
        }

        //ONLY START AI MOVE IF 
        //AI TURN + PLAYER MOVE/ WAIT TIME HAS FINISHED
        if (!this.playerTurn && !playerMove) {
            //AI CHECKMATED
            String AIcol;
            if (this.playerColor.equals("white")) { AIcol = "black"; }
            else { AIcol = "white"; }
            if (this.checkMated(AIcol)) {
                this.AILose = true;
                return;
            }

            ArrayList<Piece> enemy = new ArrayList<Piece>();
            //CHOOSE ENEMY PIECE TO MOVE 
            for (Piece p: Piece.allPiece) {
                p.getMovable(); //force to update in 1 draw.
                if (!p.getColor().equals(this.playerColor) && p.isAlive()) {
                    enemy.add(p);
                }
            }
            if (enemy.size() == 0) {
                System.out.println("Player Win");
                return;
            } else {
                Collections.shuffle(enemy);
            }

            //PRIORITY: EATABLE > MOVABLE;
            ArrayList<Float> top = new ArrayList<Float>();
            for (Piece ai: enemy) {
                ai.getMovable();
                if (ai.getOS().size() != 0 && ai.isAlive())
                    top.add(Collections.max(ai.getOS()));
            }

            //IDENTIFY CHOSEN PIECE
            for (Piece ai: enemy) {
                //HAS MOVE
                //current priece has move
                if (top.size() != 0 && ai.getOS().size() != 0 && ai.isAlive()) {
                    if (Collections.max(ai.getOS()) == Collections.max(top)) { //errrror
                        aiR = ai.getX();
                        aiC = ai.getY();
                        break;
                    }
                //current piece has no move
                } else if (top.size() != 0 && ai.getOS().size() == 0) {
                    continue;
                //NO MORE MOVE
                } else {
                    System.out.println("stalemate");
                    return;
                }
            }

            if (this.getPiece(aiR, aiC) == null) {
                for (Piece p : enemy) {
                    p.getMovable();
                    if (p.isAlive() && p.getOS().size() != 0) {
                        aiR = p.getX();
                        aiC = p.getY();
                    }
                }
            }
            
            //CHOOSE DESTINATION
            int index = this.getPiece(aiR, aiC).getMax();
            // System.out.println(index);
            aiNEWR = this.getPiece(aiR, aiC).getXC().get(index);
            aiNEWC = this.getPiece(aiR, aiC).getYC().get(index);

            //DEHIGHLIGHT PLAYER LAST MOVE
            for (Piece chess: Piece.allPiece) {
                if (chess.getLastMove()) {
                    chess.offLastMove();
                }
            }

            this.getPiece(aiR, aiC).setLastMove(aiNEWR, aiNEWC);
            //TILE OCCUPIED
            if (this.getPiece(aiNEWR, aiNEWC) != null) {
                this.getPiece(aiR, aiC).terminate(this.getPiece(aiNEWR, aiNEWC));
                this.getPiece(aiR, aiC).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                this.getPiece(aiR, aiC).setMoving();
            //TILE EMPTY
            } else {
                this.getPiece(aiR, aiC).setSpeed(this.movement_speed, this.max_movement_time, FPS);
                this.getPiece(aiR, aiC).setMoving();
            }
            //FINISH AI TURN
            this.playerTurn = true; 
            this.timeAI += this.incrementAI;
        }
    }   

    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() { //called everytime render/ sec
        //time
        if (this.timePL <= 0) { this.playerLose = true; }
        if (this.timeAI <= 0) { this.AILose = true; } 

        String AIcol;
        if (this.playerColor.equals("white")) { AIcol = "black"; }
        else { AIcol = "white"; }
        //PLAYER CHECKMATED
        if (!this.playerLose) {
            this.playerLose = this.checkMated(this.playerColor);
        }

        //AI CHECKMATED
        if (this.AILose) {
            if (this.getKing(AIcol).isTargeted()) {
                if (this.timeAI > 0) {
                    this.getKing(this.playerColor).setText("You won by\ncheckmate!\n\nPress 'r' to restart\nthe game");
                    this.checkmater(AIcol);
                } else {
                    this.getKing(this.playerColor).setText("You won on time!\n\nPress 'r' to restart\nthe game");
                }
            } else {
                this.getKing(this.playerColor).setText("Stalemate\n- draw\n\nPress 'r' to restart\nthe game");
            }
        }
        //PLAYER CHECKMATED
        else if (this.playerLose) {
            if (this.getKing(this.playerColor).isChecked()) {
                if (!this.resigned && this.timePL > 0) {
                    this.getKing(this.playerColor).setText("You lost by\ncheckmate!\n\nPress 'r' to restart\nthe game");
                    this.checkmater(this.playerColor);
                } else if (this.timePL > 0) {
                    this.getKing(this.playerColor).setText("You resigned!\n\nPress 'r' to restart\nthe game");
                } else {
                    this.getKing(this.playerColor).setText("You lost on time!\n\nPress 'r' to restart\nthe game");
                }
            } else {
                if (!this.resigned) {
                    this.getKing(this.playerColor).setText("Stalemate\n- draw\n\nPress 'r' to restart\nthe game");
                } else {
                    this.getKing(this.playerColor).setText("You resigned!\n\nPress 'r' to restart\nthe game");
                }
            }
        }
        //GAME CONTINUE
        else {
            //PLAYER IN CHECK
            //have at least 1 king attacker
            if (this.getAttacker(this.getKing(this.playerColor)).size() != 0  && !playerMove && !AIMove) {
                this.getKing(this.playerColor).setChecked(true);
                this.getKing(this.playerColor).setTargeted(true);
                this.getKing(this.playerColor).setText("Check!");
            //0 attacker
            } else {
                this.getKing(this.playerColor).setChecked(false);
                this.getKing(this.playerColor).setTargeted(false);
                this.getKing(this.playerColor).setText("");
            }
            //AI IN CHECK
            if (this.getAttacker(this.getKing(AIcol)).size() != 0) {
                this.getKing(AIcol).setTargeted(true);
            } else {
                this.getKing(AIcol).setTargeted(false);
            }

            //YOU MUST DEFEND KING
            if (this.defend) {
                this.frameCounter++;
                this.getKing(this.playerColor).setText("You must defend\nyour king");
                //WARNING FLASH
                if (this.frameCounter <= 30) {
                    this.getKing(this.playerColor).setTargeted(true);
                } else if (this.frameCounter <= 60) {
                    this.getKing(this.playerColor).setTargeted(false);
                } else if (this.frameCounter <= 90) {
                    this.getKing(this.playerColor).setTargeted(true);
                } else if (this.frameCounter <= 120) {
                    this.getKing(this.playerColor).setTargeted(false);
                } else if (this.frameCounter <= 150) {
                    this.getKing(this.playerColor).setTargeted(true);
                } else if (this.frameCounter <= 180) {
                    this.getKing(this.playerColor).setTargeted(false);
                } else {
                    this.getKing(this.playerColor).setTargeted(true);
                }
            }
        }

        this.background(255, 0, 0);
        this.board.draw(this);
        this.AI();

        if (!this.playerTurn || this.AIMove) {        
            this.counterAI++;
        }
        if (this.playerTurn || this.playerMove) {
            this.counterPL++;
        }

        if (this.counterAI == FPS && !this.AILose && !this.playerLose) {
            this.timeAI--;
            this.counterAI = 1;
        }
        if (this.counterPL == FPS && !this.AILose && !this.playerLose) {
            this.timePL--;
            this.counterPL = 1;
        }

        for (Piece p: Piece.allPiece) {
            p.tick(this);
        }
        //draw enemy first
        for (Piece p: Piece.allPiece) {
            if (!p.getColor().equals(this.playerColor))
                p.draw(this);
        }
        //so that player suggestion move (blue) can override last move of enemy (yellow)
        for (Piece p: Piece.allPiece) {
            if (p.getColor().equals(this.playerColor))
                p.draw(this);
        }
        this.textSize(30);
        this.fill(255, 255, 255);

        this.text(String.format("%d:%02d", timePL/60, timePL - ((timePL/60)*60)), 685, 635);
        this.text(String.format("%d:%02d", timeAI/60, timeAI - ((timeAI/60)*60)), 685, 60);

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
     * Get list of king attacker
     * @param king, piece king
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
     * Check if king of current color is checkmated
     * @param col, color of king
     * @return true or false
     */
    public boolean checkMated(String col) {
        boolean lose = true;
        //LOSE CONDITION
        for (Piece p: Piece.allPiece) {
            if (col.equals(this.playerColor)) {
                this.getKing(this.playerColor).setChecked(false);
            }
            p.getMovable();
            if (p.getColor().equals(col) && p.isAlive()) {
                if (p.getXC().size() != 0 || p.getYC().size() != 0) {
                    lose = false;
                }
            }
        }
        if (col.equals(this.playerColor) && this.getAttacker(this.getKing(this.playerColor)).size() != 0) {
            this.getKing(this.playerColor).setChecked(true);
            this.getKing(this.playerColor).setTargeted(true);
        }       
        return lose;
    }

    /**
     * Highlight all piece contributed to checkmate
     * @param col, color of king
     */
    public void checkmater(String col) {
        Piece king = this.getKing(col);
        //HIGHLIGHT ATTACKERS
        for (Piece p: this.getAttacker(king)) {
            p.setTargeted(true);
        }
        //HIGHLIGHT PIECE CONTRIBUTING TO CHECKMATED
        king.imaginaryMove(0, 0, 0, 0);
        ArrayList<Float> imx = king.getIX();
        ArrayList<Float> imy = king.getIY();

        for (int i = 0; i < imx.size(); i++) {
            king.getCheckmater(imx.get(i), imy.get(i), col, king.getX(), king.getY()).setTargeted(true);
        }
    }

	/**
     * Main class
     * @param args, nothing
     */
    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
