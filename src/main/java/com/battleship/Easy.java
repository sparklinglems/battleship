package com.battleship;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class Easy extends Board implements Enemy {
    public Easy(boolean enemy, EventHandler<? super MouseEvent> handler){
        super(enemy, handler);
    }
    public boolean placeShips(Board enemyBoard) {
        Random random = new Random();
        int x = 4;
        int y = 4;
        int shipsLeft = 10;

        int shipLength = 4;
        while (shipsLeft > 0) {
            x = random.nextInt(10);
            y = random.nextInt(10);

            if (shipsLeft < 5) shipLength = 1;
            else if (shipsLeft < 8) shipLength = 2;
            else if (shipsLeft < 10) shipLength = 3;

            if (enemyBoard.placeShip(new Ship(shipLength, Math.random() < 0.5,enemyBoard,x,y), x, y)) {
                shipsLeft--;
            }
        }

        return true;
    }
    public boolean takeShot(Board playerBoard) {
        Random random = new Random();
        boolean enemyTurn = true;
        int x = 4;
        int y = 4;
        while (enemyTurn) {
            x += 1 - random.nextInt(3);
            y += 1 - random.nextInt(3);
            if (x < 0 || x > 9) x = random.nextInt(10);
            if (y < 0 || y > 9) y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
        return false;
    }
}
