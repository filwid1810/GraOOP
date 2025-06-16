package org.example.gameoop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    public enum CrushType {
        NoCrush,
        HorizontalCrush,
        VerticalCrush
    }

    private static int gridRows,gridCols;


    private final Color color;
    private final double width,height;


    public static void setGrid(int rows, int cols) {
        gridRows = rows;
        gridCols = cols;
    }

    public Brick(int gridX, int gridY, Color color, double canvasWidth, double canvasHeight) {
        this.color = color;
        this.width = canvasWidth / gridCols;
        this.height = canvasHeight / gridRows;

        this.x = gridX * width;
        this.y = gridY * height;
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setFill(color);
        gc.fillRect(x, y, width, height);

        gc.setFill(color.darker());
        gc.fillRect(x, y + height - 4, width, 4);
        gc.fillRect(x + width - 4, y, 4, height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public CrushType checkCollision(double top, double bottom, double left, double right) {
        double brickLeft = getX();
        double brickRight = getX() + getWidth();
        double brickTop = getY();
        double brickBottom = getY() + getHeight();

        boolean overlapsHorizontally = right >= brickLeft && left <= brickRight;
        boolean overlapsVertically = bottom >= brickTop && top <= brickBottom;

        if (overlapsHorizontally && overlapsVertically) {
            double overlapLeft = right - brickLeft;
            double overlapRight = brickRight - left;
            double overlapTop = bottom - brickTop;
            double overlapBottom = brickBottom - top;

            double minHorizontalOverlap = Math.min(overlapLeft, overlapRight);
            double minVerticalOverlap = Math.min(overlapTop, overlapBottom);

            if (minHorizontalOverlap < minVerticalOverlap) {
                return CrushType.HorizontalCrush;
            } else {
                return CrushType.VerticalCrush;
            }
        }

        return CrushType.NoCrush;
    }

}
