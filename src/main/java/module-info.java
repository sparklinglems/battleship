module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.battleship to javafx.fxml;
    exports com.battleship;
}