package com.battleship;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BattleshipMain extends Application {

    private boolean running = false;
    private Board playerBoard;
    private Easy enemy;

    private int shipsToPlace = 5;

    private boolean enemyTurn = false;

    private Random random = new Random();

    Parent createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);

        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        enemy = new Easy(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemy.ships == 0) {
                System.out.println("YOU WIN");
                //System.exit(0);
            }

            if (enemyTurn)
                enemyTurn = enemy.TakeShot(playerBoard);
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY,playerBoard,cell.x,cell.y), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    running = enemy.PlaceShips(enemy);
                }
            }
        });

        VBox vbox = new VBox(50, enemy, playerBoard);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);

        return root;
    }





    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
