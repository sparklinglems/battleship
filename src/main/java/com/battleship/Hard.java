package com.battleship;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Hard extends Board implements Enemy{

    public Hard (boolean enemy, EventHandler<? super MouseEvent> handler){
        super(enemy, handler);
    }

}
