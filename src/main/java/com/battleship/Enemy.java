package com.battleship;

public interface Enemy {
    boolean PlaceShips(Board enemyBoard);
    boolean TakeShot(Board playerBoard);
}
