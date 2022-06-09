module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.battleship to javafx.fxml;
    exports com.example.battleship;
}