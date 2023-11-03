package XXLChess;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    public void piece() {
        //set up
        App app = new App();
        app.loop(); 
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.settings();
        app.setup();

        //pawn
        Piece p = new Pawn("white", "white", 12, 2, app.loadImage("src\\main\\resources\\XXLChess\\w-pawn.png"));
        assertNotNull(app.getPiece(12, 2));
        p.getMovable();
        app.getPiece(12, 2).isMovable(10, 2);

        Piece p19 = new Pawn("black", "white", 1, 5, app.loadImage("src\\main\\resources\\XXLChess\\w-pawn.png"));
        assertNotNull(p19);
        p19.getMovable();

        app.mousePressed(new MouseEvent(null, 0, 1, 0, 12*48, 2*48, 82, 1));
        app.getPiece(12, 2).setSelected();
        assertEquals(app.getPiece(12, 2).selected, true);
        app.row = 12;
        app.col = 2;
        app.clicked = true;
        app.mousePressed(new MouseEvent(null, 0, 1, 0, 10*48, 2*48, 82, 1));

        //pawn moves 
        //enemy
        Piece p2 = new Pawn("black", "white", 11, 1, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p3 = new Pawn("black", "white", 11, 3, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p4 = new Pawn("black", "white", 11, 2, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));

        assertNotNull(app.getPiece(11, 1)); //diagonal left
        assertNotNull(app.getPiece(11, 3)); //diagonal right
        assertNotNull(app.getPiece(11, 2)); //block front
        assertNotNull(app.getPiece(12, 2)); //the mover

        p.getMovable();
        p4.getMovable();

        //pawn promotion ally
        p.setXY(7, 2);
        assertEquals(p.x, 7);
        p.setLastMove(6, 2);
        p.getLastMove();
        p.setSpeed(48, 1, 60);
        assertEquals(p.getLastMove(), true);
        p.setMoving();
        assertEquals(p.isMoving, true);
        p.tick(app);
        p.tick(app);
        p.setAlive();
        p.offLastMove();

        //queen move
        p2.getMovable();
        p2.setPic(app);

        //reset
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        //Queen test
        Piece q = new Queen("black", "white", 5, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-queen.png"));
        q.getMovable();
        q.imaginaryMove(4, 6, 5, 5);

        //king test
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece k = new King("white", "white", 10, 5, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece r = new Rook("white", "white", 10, 13, app.loadImage("src\\main\\resources\\XXLChess\\w-rook.png"));
        Piece r2 = new Rook("white", "white", 10, 0, app.loadImage("src\\main\\resources\\XXLChess\\w-rook.png"));
        //check castling
        k.getMovable();
        k.rightRook();
        k.leftRook();

        //check checkmate
        k.getCheckmater(10, 4, "white", 10, 5);
        Piece r3 = new Rook("black", "white", 10, 4, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));
        k.getCheckmater(10, 3, "white", 10, 5);

        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece k2 = new King("white", "white", 13, 7, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece n = new Knight("white", "white", 13, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-knight.png"));
        Piece r4 = new Rook("black", "white", 13, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));

        n.isDefender(10, 7, 13, 6, "white");
        Piece r0 = new Rook("black", "white", 13, 8, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));
        r0.blockedByAlly(10, 7, 13, 6, "white");
        n.isDefender(10, 7, 13, 6, "white");

        n.getKing("orange");

        //test terminate
        r4.setLastMove(13, 6);
        r4.terminate(n);
        r4.setSpeed(48, 1, 60);
        r4.setMoving();
        assertEquals(r4.isMoving, true);
        r4.tick(app);
        r4.tick(app);
        r4.tick(app);

        //test modified speed
        Piece a = new Amazon("black", "white", 3, 2, app.loadImage("src\\main\\resources\\XXLChess\\b-amazon.png"));
        Piece a2 = new Amazon("white", "white", 6, 8, app.loadImage("src\\main\\resources\\XXLChess\\w-amazon.png"));

        a2.isMoving = true;
        a.setLastMove(11, 12);
        a.setSpeed(3, 1, 60);
        a.setMoving();
        a.tick(app);
        assertEquals(a.isMoving, true);
        a.tick(app);

        //rook test
        Piece r5 = new Rook("black", "white", 2, 8, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));
        Piece p5 = new Pawn("white", "white", 2, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p6 = new Pawn("white", "white", 2, 10, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        r5.getMovable();

        //knight test
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece n2 = new Knight("white", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-knight.png"));
        n2.getMovable();
        Piece p7 = new Pawn("black", "white", 5, 4, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p8 = new Pawn("black", "white", 5, 8, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p9 = new Pawn("black", "white", 7, 4, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        Piece p10 = new Pawn("black", "white", 7, 8, app.loadImage("src\\main\\resources\\XXLChess\\b-pawn.png"));
        n2.getMovable();
        n2.getMax();
        n2.imaginaryMove(5, 4, 6, 6);

        //rook im move
        Piece r6 = new Rook("white", "white", 9, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-rook.png"));
        r6.rookIM(5, 4, 6, 6);
        Piece r7 = new Rook("black", "white", 8, 6, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));
        r6.rookIM(8, 8, 8, 6);
        Piece r8 = new Rook("black", "white", 9, 10, app.loadImage("src\\main\\resources\\XXLChess\\w-knight.png"));
        r6.rookIM(9, 11, 9, 10);
        Piece r9 = new Rook("black", "white", 9, 2, app.loadImage("src\\main\\resources\\XXLChess\\w-knight.png"));
        r6.rookIM(9, 1, 9, 2);
        r6.reset();

        //bishop test
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece k3 = new King("white", "white", 2, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece k4 = new King("black", "white", 12, 6, app.loadImage("src\\main\\resources\\XXLChess\\b-king.png"));


        Piece b1 = new Bishop("white", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-bishop.png"));
        Piece b2 = new Bishop("black", "white", 9, 9, app.loadImage("src\\main\\resources\\XXLChess\\b-bishop.png"));
        Piece b3 = new Bishop("black", "white", 4, 8, app.loadImage("src\\main\\resources\\XXLChess\\b-bishop.png"));
        Piece b4 = new Bishop("black", "white", 5, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-bishop.png"));
        Piece b5 = new Bishop("black", "white", 9, 3, app.loadImage("src\\main\\resources\\XXLChess\\b-bishop.png"));
        b1.getMovable();
        b2.getMovable();

        //camel test
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece c1 = new Camel("white", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-camel.png"));
        c1.imaginaryMove(0, 0, 0, 0);

        Piece c2 = new Camel("black", "white", 5, 3, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c3 = new Camel("black", "white", 5, 9, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c4 = new Camel("black", "white", 3, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c5 = new Camel("black", "white", 3, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c6 = new Camel("black", "white", 7, 3, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c7 = new Camel("black", "white", 7, 9, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c8 = new Camel("black", "white", 9, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        Piece c9 = new Camel("black", "white", 9, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-camel.png"));
        c1.imaginaryMove(0, 0, 0, 0);
        c1.getMovable();

        //getter test
        assertNotNull(c1.getIX());
        assertNotNull(c1.getIY());
        assertNotNull(c1.getName());
        assertNotNull(c1.getOS());
        assertNotNull(c1.getXC());
        assertNotNull(c1.getYC());
        assertNotNull(c1.getPic());
        assertNotNull(c1.getMax());

        //get king attacker test
        app.keyPressed(new KeyEvent(null, 0, 0, 0, ' ', 82));
        Piece k5 = new King("white", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece r10 = new Rook("black", "white", 6, 13, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));
        r10.getMovable();
        k5.getAttacker(k5);
        Piece r11 = new Rook("white", "white", 6, 5, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece r12 = new Rook("black", "white", 6, 3, app.loadImage("src\\main\\resources\\XXLChess\\b-rook.png"));

        r12.getMovable();
        r12.imaginaryMove(7, 5, 6, 5);
        r12.blockedByAlly(7, 5, 6, 5, "white");

        //test move
        Piece a3 = new Amazon("white", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-amazon.png"));
        //upright
        a3.setLastMove(5, 7);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //downleft
        a3.setLastMove(6, 6);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //upleft
        a3.setLastMove(5, 5);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //downright
        a3.setLastMove(6, 6);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //horizontal left
        a3.setLastMove(6, 5);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //horizontal right
        a3.setLastMove(6, 7);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        //vertical down
        a3.setLastMove(7, 7);
        a3.setSpeed(48, 1, 60);
        a3.setMoving();
        a3.tick(app);
        a3.tick(app);
        a3.setSelected();
        a3.terminate(r12);
        a3.draw(app);
        
        Piece g1 = new Guard("white", "white", 12, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        Piece e1 = new Pawn("black", "white", 13, 6, app.loadImage("src\\main\\resources\\XXLChess\\b-king.png"));
        Piece e2 = new Pawn("black", "white", 13, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-king.png"));
        Piece e3 = new Pawn("black", "white", 13, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-king.png"));
        Piece e4 = new Knight("black", "white", 11, 6, app.loadImage("src\\main\\resources\\XXLChess\\b-knight.png"));
        Piece e5 = new Knight("black", "white", 11, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-knight.png"));
        Piece e6 = new Knight("black", "white", 11, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-knight.png"));

        Piece e7 = new Knight("black", "white", 12, 5, app.loadImage("src\\main\\resources\\XXLChess\\b-knight.png"));
        Piece e8 = new Knight("black", "white", 12, 7, app.loadImage("src\\main\\resources\\XXLChess\\b-knight.png"));
        g1.getMovable();
        g1.isTargeted();

        //test ai
        app.playerTurn = false;
        app.AI();

        //test chancellor archbishop
        Piece c10 = new Chancellor("white", "white", 4, 4, app.loadImage("src\\main\\resources\\XXLChess\\w-chancellor.png"));
        c10.getMovable();
        Piece ac1 = new Archbishop("white", "white", 4, 4, app.loadImage("src\\main\\resources\\XXLChess\\w-archbishop.png"));
        ac1.getMovable();
        //test app
        app.draw();
        app.getKing("hihi");
        app.defend = true;
        app.draw();
        app.frameCounter = 40;
        app.draw();
        app.frameCounter = 80;
        app.draw();
        app.frameCounter = 110;
        app.draw();
        app.frameCounter = 130;
        app.draw();
        app.frameCounter = 160;
        app.draw();
        app.frameCounter = 190;
        app.draw();
        //key presses
        app.keyCode = 69;
        app.keyPressed();
        app.keyCode = 82;
        app.keyPressed();

        app.AILose = true;
        Piece k6 = new King("black", "white", 6, 6, app.loadImage("src\\main\\resources\\XXLChess\\w-king.png"));
        k6.setTargeted(true);
        app.draw();
        k6.setTargeted(false);
        app.draw();
        app.AILose = false;
        app.playerLose = true;
        app.draw();
        app.resigned = true;
        app.draw();
        app.resigned = false;
        app.playerLose = false;
        app.AILose = false;
        app.AIMove = true;
        app.playerTurn = false;
        app.counterAI = 60;
        app.draw();
        app.counterPL = 60;
        app.draw();
    }

    @Test
    public void soundTest() {
        Sound s = new Sound("files path broken");
    }
}
