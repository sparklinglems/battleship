package com.battleship;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class Normal extends Board implements Enemy{

    boolean finishing;
    Random random = new Random();

    public Normal (boolean enemy, EventHandler<? super MouseEvent> handler){
        super(enemy, handler);
        finishing = false;
    }

    public boolean placeShips(Board enemyBoard) {
        return true;
    }

    public boolean takeShot (Board playerBoard) {

        return false;
    }
}
