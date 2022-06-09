package com.battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }

        public boolean shoot() {
            if (!(x < 0 || x > 9 ||  y < 0 || y > 9)) {
                wasShot = true;
                setFill(Color.BLACK);

                if (ship != null) {
                    ship.hit();
                    setFill(Color.RED);
                    if (!ship.isAlive()) {
                        board.ships--;
                    }
                    return true;
                }
            }
            return false;

        }


}
