package XXLChess;

import processing.core.PApplet;

/**
 * Board class
 */
public class Board {
    private int BOARD_WIDTH;
    private int CELL_SIZE;

    /**
     * Constructor for board
     * @param BOARD_WIDTH, width of board
     * @param CELL_SIZE, 48 pixels
     */
    public Board(int BOARD_WIDTH, int CELL_SIZE){
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.CELL_SIZE = CELL_SIZE;
    }

    /**
     * draw
     * @param app, papplet
     */
    public void draw(PApplet app){
        for(int x = 0; x< this.BOARD_WIDTH; x++){ // x = row
            for(int y = 0; y < this.BOARD_WIDTH; y++){ // y = col
                if((x + y) % 2 == 0)
                    app.fill(255, 229, 180); //light brown
                else
                    app.fill(204, 153, 102); //dark brown
        
                app.rect(this.CELL_SIZE*x, this.CELL_SIZE*y, this.CELL_SIZE, this.CELL_SIZE);
                app.noStroke();
            }
        }
    }
}