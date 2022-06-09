package com.battleship;

public interface Enemy {
    boolean placeShips(Board enemyBoard);
    boolean takeShot(Board playerBoard);
}
