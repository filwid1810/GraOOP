package org.example.gameoop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends GraphicsItem {

    private double width = 100;
    private double height = 20;

    public Paddle() {

        this.x = canvasWidth / 2 - width / 2;
        this.y = canvasHeight - height - 10;
    }

    public void updatePosition(double newX) {
        this.x = newX - width / 2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, width, height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
