package com.battleship;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;

public class BattleshipMain extends Application {

    private boolean running = false;
    private Board playerBoard;
    //private Easy enemy;
    private int shipsToPlace = 5;
    private boolean enemyTurn = false;

    private Random random = new Random();

    Timer timer = new Timer();

    Normal enemy;

    int shipsLeft = 10;
    int shipLength = 4;


    Parent createContent() {

        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);
        root.setStyle("-fx-background-color: #2B343E;");

        //root.setRight(new Text("\nLMB - place vertically\nRMB - place horizontally     ").setStroke(Color.WHITE););
        //System.out.println(Hard.pos[1]);
        Text text = new Text("abc");
        text.setTextOrigin(VPos.TOP);
        text.setLayoutX(10);
        text.setLayoutY(11);
        text.setFill(Color.BLACK);
        text.setOpacity(0.5);
        text.setStyle("-fx-font-size: 20px;");


        enemy = new Normal(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.Shoot();

            if (enemy.ships == 0) {
                System.out.println("YOU WIN");
                //System.exit(0);
            }

            if (enemyTurn) {
                enemyTurn = enemy.takeShot(playerBoard);
                playerBoard.update();
            }
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();

            if (shipsLeft < 5) shipLength = 1;
            else if (shipsLeft < 8) shipLength = 2;
            else if (shipsLeft < 10) shipLength = 3;
            if (playerBoard.placeShip(new Ship(shipLength, event.getButton() == MouseButton.PRIMARY,playerBoard,cell.x,cell.y), cell.x, cell.y)) {
                if (--shipsLeft == 0) {
                    running = enemy.placeShips(enemy);
                }
            }
        });

        VBox vbox = new VBox(40, enemy, playerBoard);

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
