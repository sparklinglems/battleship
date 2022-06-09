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
        if (health == 0) {
            if (vertical) {
                //top
                board.getCell(x-1,y-1).shoot();
                board.getCell(x,y-1).shoot();
                board.getCell(x+1,y-1).shoot();
                //bottom
                board.getCell(x-1,y+decks).shoot();
                board.getCell(x,y+decks).shoot();
                board.getCell(x+1,y+decks).shoot();
                //sides
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
}