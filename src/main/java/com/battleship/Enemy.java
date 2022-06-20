package com.battleship;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
public class Enemy extends Board {

    boolean finishing;
    //shows if the AI is in finishing mode

    int oriented;
    //0 - not oriented; 1 - vertical & shoot up, 2 - horizontal & shoot right,
    // 3 - vertical & shoot down, 4 - horizontal & shoot left
    Random random = new Random();

    static Integer[][] pos = {
            {0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7, 8, 9, 0, 0, 5, 4, 0, 9, 9},
            {3, 2, 1, 0, 7, 6, 5, 4, 3, 2, 1, 0, 9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 0, 5, 0, 9, 9, 4, 9}
    };
    //coordinates of optimal first stage shots;

    int originX;
    int originY;

    class Coord {
        int x;
        int y;
        public Coord(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    ArrayList<Coord> targets;

    public Enemy(boolean enemy, EventHandler<? super MouseEvent> handler){
        super(enemy, handler);
        finishing = false;
        targets = new ArrayList<Coord>();
        for (int i = 0; i < 31; i++) {
            Coord coord = new Coord(pos[0][i],pos[1][i]);
            targets.add(coord);
        }
    }

    public boolean placeShips(Board enemyBoard) {
        Random random = new Random();
        int x;
        int y;
        int shipsLeft = 10;
        boolean vert;

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

    public boolean takeShot (Board playerBoard, Scene scene) {
        if (playerBoard.ships == 0) {
            return true;
        }
        boolean enemyTurn = true;
        Coord xy;
        int x;
        int y;

        while (enemyTurn) {
            if (finishing) {
                enemyTurn = Finish(originX, originY, playerBoard);
                if (enemyTurn) {
                    finishing = false;
                    continue;
                }
            }
            else {
                Cell cell;

                    if (!targets.isEmpty()) {
                        xy = stage0();
                        x = xy.x;
                        y = xy.y;
                        cell = playerBoard.getCell(x, y);
                    } else {
                        x = random.nextInt(10);
                        y = random.nextInt(10);
                        cell = playerBoard.getCell(x, y);
                    }


                if (cell.wasShot)
                    continue;

                if (enemyTurn = cell.Shoot() ) {
                    if (cell.ship.health == 0) continue;
                    finishing = true;
                    originX = x;
                    originY = y;
                    orientation = 0;
                    oriented = 0;
                    continue;
                }
            }


        }
        return false;
    }

    private Coord stage0() {
        while (true) {
            Random random = new Random();
            int num = random.nextInt(targets.size());
            Coord dot = targets.get(num);
            targets.remove(num);
            return dot;
        }
    }

    int orientation = 0;
    public boolean Finish(int x, int y, Board playerBoard) {
        if (playerBoard.getCell(x,y).ship.health == 0) return true;
        if (oriented == 0) {
            if (orientation == 0) {
                if (y != 0) {
                    Cell cell = playerBoard.getCell(x, y - 1);
                    if (cell.wasShot) {
                        orientation = 1;
                        return false;
                    }
                    else {
                        if (cell.Shoot()) {
                            cell.MakeRed();
                            if (cell.ship.health == 0) return true;
                            oriented = 1;
                        } else {
                            orientation = 1;
                            return false;
                        }
                    }
                } else orientation = 1;
            }
            if (orientation == 1) {
                if (x != 9) {
                    Cell cell = playerBoard.getCell(x+1, y );
                    if (cell.wasShot)
                        orientation = 2;
                    else {
                        if (cell.Shoot()) {
                            cell.MakeRed();
                            if (cell.ship.health == 0) return true;
                            oriented = 2;
                        }else {
                            orientation = 2;
                            return false;
                        }
                    }
                } else orientation = 2;
            }
            if (orientation == 2) {
                if (y != 9) {
                    Cell cell = playerBoard.getCell(x, y + 1);
                    if (cell.wasShot)
                        orientation = 3;
                    else {
                        if (cell.Shoot()) {
                            cell.MakeRed();
                            if (cell.ship.health == 0) return true;
                            oriented = 3;
                        }else {
                            orientation = 3;
                            return false;
                        }
                    }
                } else orientation = 3;
            }
            if (orientation == 3) {
                Cell cell = playerBoard.getCell(x - 1, y);
                if (cell.Shoot()) {
                    cell.MakeRed();
                    if (cell.ship.health == 0) return true;
                    oriented = 4;
                }
            }
        }
        int i = 2;
        if (oriented == 1) {
            while (true) {
                if (playerBoard.getCell(x,y).ship.health == 0) return true;
                if (y - i >= 0) {
                    Cell cell = playerBoard.getCell(x, y - i);
                    if (cell.Shoot()) {
                        cell.MakeRed();
                        if (cell.ship.health == 0) {
                            cell.ship.Finish();
                            return true;
                        }
                    } else {
                        oriented = 3;
                        return false;
                    }
                    i++;
                } else {
                    oriented = 3;
                    return false;
                }
            }
        }
        if (oriented == 2) {
            while (true) {
                if (playerBoard.getCell(x,y).ship.health == 0) return true;
                if (x + i <= 9) {
                    Cell cell = playerBoard.getCell(x + i, y);
                    if (cell.Shoot()) {
                         cell.MakeRed();
                        if (cell.ship.health == 0) {
                            cell.ship.Finish();
                            return true;
                        }
                    } else {
                        oriented=4;
                        return false;
                    }
                    i++;
                } else {
                    oriented = 4;
                    return false;
                }
            }
        }
        if (oriented == 3) {
            while (true) {
                if (playerBoard.getCell(x,y).ship.health == 0) return true;
                i = 1;
                Cell cell = playerBoard.getCell(x, y + i);
                if (cell.Shoot()) {
                    cell.MakeRed();
                    if (cell.ship.health == 0) {
                        cell.ship.Finish();
                        return true;
                    }
                    i ++;
                }
            }
        }
        if (oriented == 4) {
            while (true) {
                if (playerBoard.getCell(x,y).ship.health == 0) return true;
                i = 1;
                Cell cell = playerBoard.getCell(x - i, y);
                if (cell.Shoot()) {
                    cell.MakeRed();
                    if (cell.ship.health == 0) {
                        cell.ship.Finish();
                        return true;
                    }
                    i ++;
                }
            }
        }
        return false;
    }


}
