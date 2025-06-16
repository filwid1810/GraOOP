module org.example.gameoop {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.gameoop to javafx.fxml;
    exports org.example.gameoop;
}