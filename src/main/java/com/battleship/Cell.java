package com.battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
        public int x, y;
        public Ship ship;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.WHITE);
            setStroke(Color.BLACK);
            ship = null;
        }

        public boolean Shoot() {
            //System.out.println(x + "  " + y);
                wasShot = true;

                if (ship != null) {
                    ship.hit();
                    MakeRed();
                    if (!ship.isAlive()) {
                        board.ships--;
                    }
                    return true;
                } else setFill(Color.GRAY);

            return false;

        }

        public void MakeRed(){
            wasShot = true;
            setFill(Color.FIREBRICK);
        }

        public void fin(){
            wasShot = true;
            setFill(Color.GRAY);
        }


}
