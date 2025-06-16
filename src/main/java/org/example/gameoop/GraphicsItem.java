package org.example.gameoop;

import javafx.scene.canvas.GraphicsContext;
public abstract class GraphicsItem {
    protected static double canvasWidth;
    protected static double canvasHeight;

    protected double x;
    protected double y;

    public static void setCanvasSize(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void draw(GraphicsContext gc);
}
