package com.battleship;

import javafx.scene.Parent;

public class Ship {
    int decks;
    boolean vertical = true;
    int health;
    Board board;
    int x;
    int y;

    public Ship(int decks, boolean vertical, Board board, int x, int y) {
        this.decks = decks;
        health = decks;
        this.vertical = vertical;
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public void hit() {
        health--;
        if (health == 0) finish();
    }

    private void finish(){
        boolean x0 = (x != 0);
        boolean y0 = (y != 0);
        boolean x9;
        boolean y9;
        if (vertical) {
            x9 = (x != 9);
            y9 = (y+decks != 10);
            if (y0) {
                if (x0) board.getCell(x - 1, y - 1).fin();
                board.getCell(x, y - 1).fin();
                if (x9) board.getCell(x + 1, y - 1).fin();
            }
            //bottom
            if (y9) {
                if (x0) board.getCell(x - 1, y + decks).fin();
                board.getCell(x, y + decks).fin();
                if (x9) board.getCell(x + 1, y + decks).fin();
            }
            //sides
            for (int i = 0; i < decks; i ++) {
                if (x0) board.getCell(x-1,y+i).fin();
                if (x9) board.getCell(x+1,y+i).fin();
            }
        } else {
            x9 = (x+decks != 10);
            y9 = (y != 9);
            if (x0) {
                if (y0) board.getCell(x - 1, y - 1).fin();
                board.getCell(x - 1, y).fin();
                if (y9) board.getCell(x - 1, y + 1).fin();
            }
            //bottom
            if (x9) {
                if (y0) board.getCell(x + decks, y - 1).fin();
                board.getCell(x + decks, y).fin();
                if (y9) board.getCell(x + decks, y + 1).fin();
            }
            //sides
            for (int i = 0; i < decks; i ++) {
                if (y0) board.getCell(x+i,y-1).fin();
                if (y9) board.getCell(x+i,y+1).fin();
            }
        }

    }

    public boolean isAlive() {
        return health > 0;
    }
}