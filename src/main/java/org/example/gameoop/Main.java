package org.example.gameoop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameCanvas gameCanvas = new GameCanvas(800, 600);
        gameCanvas.draw();

        StackPane root = new StackPane(gameCanvas);
        Scene scene = new Scene(root, 800, 600);


        scene.setOnMouseMoved(e -> {
            double mouseX = e.getX();
            gameCanvas.getPaddle().updatePosition(mouseX);
        });

        primaryStage.setTitle("FAJNA Gra");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
