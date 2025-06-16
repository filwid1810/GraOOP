package org.example.gameoop;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {

    private double radius = 10;
    private Point2D moveVector;

    public Ball() {

        moveVector = new Point2D(1, -1).normalize();
    }

    public void setPosition(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public void updatePosition(double elapsedSeconds) {
        double speed = 300;
        double dx = moveVector.getX() * speed * elapsedSeconds;
        double dy = moveVector.getY() * speed * elapsedSeconds;
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public Point2D getMoveVector() {
        return moveVector;
    }

    public double getRadius() {
        return radius;
    }
    public void bounceHorizontally() {
        Point2D vec = moveVector;
        moveVector = new Point2D(-vec.getX(), vec.getY());
    }

    public void bounceVertically() {
        Point2D vec = moveVector;
        moveVector = new Point2D(vec.getX(), -vec.getY());
    }
    public double getTop() {
        return getY() - getRadius();
    }


    public double getBottom() {
        return getY() + getRadius();
    }

    public double getLeft() {
        return getX() - getRadius();
    }

    public double getRight() {
        return getX() + getRadius();
    }
    public void bounceFromPaddle(double hitPosition) {

        double maxBounceAngle = Math.toRadians(75);
        double bounceAngle = hitPosition * maxBounceAngle;
        double speed = moveVector.magnitude();

        double newX = speed * Math.sin(bounceAngle);
        double newY = -speed * Math.cos(bounceAngle);

        moveVector = new Point2D(newX, newY).normalize();
    }

}
