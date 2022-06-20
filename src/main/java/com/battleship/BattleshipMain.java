package com.battleship;

import java.util.Random;
import java.util.Timer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BattleshipMain extends Application {
    boolean running = false;
    Board playerBoard;
    boolean enemyTurn = false;
    private Random random = new Random();
    Enemy enemy;
    int shipsLeft = 10;

    int shipLength = 4;

    Text statusText;
    Button restart;
    Text enemyText;
    Text playerText;
    BorderPane root;

    Parent createContent() {
        running = false;
        enemyTurn = false;
        shipsLeft = 10;

        shipLength = 4;

        //BorderPane pane = new BorderPane();
        root = new BorderPane();
        root.setPrefSize(750, 500);
        root.setStyle("-fx-background-color: #2B343E;");
        //pane.setCenter(root);

        enemy = new Enemy(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.Shoot();
            enemyText.setText("ПОЛЕ ПРОТИВНИКА (" + enemy.ships + ")");

            if (enemy.ships == 0) {
                statusText.setText("\t   ПОБЕДА!\"");
                restart.setStyle(
                                "-fx-opacity: 1;" +
                                "-fx-font-size: 25px;" +
                                "-fx-font-weight: 700;" +
                                "-fx-translate-y: 250px;" +
                                "-fx-translate-x: 50px;" +
                                "-fx-pref-height: 25px;" +
                                "-fx-pref-width: 300px"
                );
                restart.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        try {
                            start(stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            if (enemyTurn) {
                enemyTurn = enemy.takeShot(playerBoard,scene);
                if (enemyTurn) {
                    statusText.setText("\tПОРАЖЕНИЕ :(");
                    restart.setStyle(
                                    "-fx-opacity: 1;" +
                                    "-fx-font-size: 25px;" +
                                    "-fx-font-weight: 700;" +
                                    "-fx-translate-y: 250px;" +
                                    "-fx-translate-x: 50px;" +
                                    "-fx-pref-height: 25px;" +
                                    "-fx-pref-width: 300px"
                    );
                    restart.setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            try {
                                start(stage);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }

                playerBoard.update();
                playerText.setText("ВАШЕ ПОЛЕ (" + playerBoard.ships + ")");
            }
        });

        statusText = new Text("РАССТАВЬТЕ КОРАБЛИ");
        statusText.setFill(Color.WHITE);
        statusText.setStyle("-fx-font-size: 30px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-alignment: center;" +
                "-fx-translate-y: 230px;" +
                "-fx-translate-x: 30px"
        );
        //pane.getChildren().add(statusText);
        //root.setTop(statusText);
        restart = new Button("Новая игра");
        restart.setStyle(
                "-fx-opacity: 1;" +
                        "-fx-font-size: 25px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-translate-y: 250px;" +
                        "-fx-translate-x: 50px;" +
                        "-fx-pref-height: 25px;" +
                        "-fx-pref-width: 300px"
        );
        restart.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });





        Text text = new Text("" +
                "\t\t   УПРАВЛЕНИЕ:\n   ЛКМ - поставить вертикально\n   ПКМ - поставить горизонтально\n   ЛКМ/ПКМ - атаковать");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 20px;" +
                "-fx-translate-x: 30px;" +
                "-fx-translate-y: 550px");
        //root.setLeft(text);
        //System.out.println(Hard.pos[1]);
        VBox leftPanel = new VBox(5,statusText,restart,text);
        root.setLeft(leftPanel);

        enemyText = new Text("ПОЛЕ ПРОТИВНИКА (10)");
        enemyText.setFill(Color.WHITE);
        enemyText.setStyle("-fx-font-size: 20px;"
                //"-fx-translate-y: -35px;" +
                //"-fx-translate-x: 445px"
        );
        //root.setBottom(enemyText);

        playerText = new Text("ВАШЕ ПОЛЕ (10)");
        playerText.setFill(Color.WHITE);
        playerText.setStyle("-fx-font-size: 20px;"
        );



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
                    statusText.setText("\t   В  АТАКУ!");
                }
            }
        });


        VBox vbox = new VBox(10, new Text(""),playerText,playerBoard,new Text(""), enemy,enemyText,new Text(""));
        vbox.setStyle(
                "-fx-translate-x: -30px"
        );

        vbox.setAlignment(Pos.CENTER);

        root.setRight(vbox);

        return root;
    }



    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        stage = primaryStage;
    }

    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }
}
