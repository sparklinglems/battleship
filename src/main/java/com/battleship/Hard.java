package com.battleship;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Random;

public class Hard extends Board implements Enemy{

    int stage;
    static Integer[][] pos = {
            {0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7, 8, 9, 0, 0, 5, 4, 0, 9, 9},
            {3, 2, 1, 0, 7, 6, 5, 4, 3, 2, 1, 0, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 0, 5, 0, 9, 9, 4, 9}
    };
    int listLeft = 31;
    boolean finishing;

    ArrayList<Integer[]> targets;

    public Hard (boolean enemy, EventHandler<? super MouseEvent> handler){
        super(enemy, handler);
        stage = 0;
        finishing = false;
        ArrayList<Integer[]> targets = new ArrayList<Integer[]>();
        for (int i = 0; i < 31; i++) targets.add(new Integer[]{pos[0][i],pos[1][i]});
    }

    public boolean placeShips(Board enemyBoard) {
        Random random = new Random();
        int x = 4;
        int y = 4;
        int shipsLeft = 10;
        boolean vert = true;

        int shipLength = 4;
        while (shipsLeft > 0) {
            if (shipsLeft < 5) shipLength = 1;
            else if (shipsLeft < 8) shipLength = 2;
            else if (shipsLeft < 10) shipLength = 3;

            if (shipLength > 2) {
                if (Math.random() < 0.5) {
                    vert = true;
                    if (Math.random() < 0.5) {
                        x = 0;
                    }
                    else {
                        x = 9;
                    }
                    y = random.nextInt(8);
                }
                else {
                    vert = false;
                    if (Math.random() < 0.5) {
                        y = 0;
                    }
                    else {
                        y = 9;
                    }
                    x = random.nextInt(8);
                }
                if (enemyBoard.placeShip(new Ship(shipLength, vert, enemyBoard, x, y), x, y))
                shipsLeft--;
            } else {
                x = 1 + random.nextInt(8);
                y = 1 + random.nextInt(8);

                Ship shipp;
                if (enemyBoard.placeShip(shipp = new Ship(shipLength, Math.random() < 0.5, enemyBoard, x, y), x, y)) {
                    callCells(shipp);
                    shipsLeft--;
                }
            }
        }
        return true;
    }

    public boolean takeShot(Board playerBoard) {
        boolean enemyTurn = true;
        int x = 4;
        int y = 4;
        while (enemyTurn) {
            if (stage == 0) {
                Integer[] temp = stage0(targets);
                x = temp[0];
                y = temp[1];
            }
            else stage1();

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot) {
                if (cell.ship.health != 0) finishing = true;
                continue;
            }

            enemyTurn = cell.Shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
        return false;
    }

    public Integer[] stage0(ArrayList<Integer[]> targets) {
        Random random = new Random();
        int num = random.nextInt(listLeft);
        Integer[] dot = new Integer[]{targets.get(0)[num],targets.get(1)[num]};
        return dot;
    }

    public void stage1() {

    }

}
