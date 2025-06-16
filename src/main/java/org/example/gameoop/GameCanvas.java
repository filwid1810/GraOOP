package org.example.gameoop;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;


public class GameCanvas extends Canvas {

    private GraphicsContext gc;
    private Ball ball;
    private Paddle paddle;
    private AnimationTimer timer;
    private List<Brick> bricks = new ArrayList<>();

    private long lastTime = 0;

    public GameCanvas(double width, double height) {
        super(width, height);
        gc = this.getGraphicsContext2D();
        setFocusTraversable(true);
        GraphicsItem.setCanvasSize(width, height);

        paddle = new Paddle();
        ball = new Ball();
        ball.setPosition(new javafx.geometry.Point2D(width / 2, height / 2));

        loadLevel();
        startGameLoop();
    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                    if (lastTime == 0) {
                        lastTime = now;
                        return;
                    }

                    double elapsedSeconds = (now - lastTime) / 1_000_000_000.0;
                    lastTime = now;

                    ball.updatePosition(elapsedSeconds);

                    if (shouldBallBounceHorizontally()) {
                        ball.bounceHorizontally();
                    }

                    if (shouldBallBounceVertically()) {
                        ball.bounceVertically();
                    }


                    if (shouldBallBounceFromPaddle()) {
                        if (ball.getBottom() >= paddle.getY() &&
                                ball.getTop() < paddle.getY() + paddle.getHeight() &&
                                ball.getX() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth()) {

                            double relativeIntersectX = ball.getX() - (paddle.getX() + paddle.getWidth() / 2);
                            double hitPosition = relativeIntersectX / (paddle.getWidth() / 2);

                            hitPosition = Math.max(-1, Math.min(1, hitPosition));

                            ball.bounceFromPaddle(hitPosition);

                            ball.setPosition(new Point2D(ball.getX(), paddle.getY() - ball.getRadius()));
                        }
                    }
                List<Brick> toRemove = new ArrayList<>();

                for (Brick brick : bricks) {
                    Brick.CrushType type = brick.checkCollision(
                            ball.getTop(),
                            ball.getBottom(),
                            ball.getLeft(),
                            ball.getRight()
                    );

                    switch (type) {
                        case HorizontalCrush -> ball.bounceHorizontally();
                        case VerticalCrush -> ball.bounceVertically();
                        default -> {}
                    }

                    if (type != Brick.CrushType.NoCrush) {
                        toRemove.add(brick);
                    }
                }

                bricks.removeAll(toRemove);
                    draw();
                }


        };
        timer.start();
    }

    public void draw() {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, getWidth(), getHeight());

            for (Brick brick : bricks) {
                brick.draw(gc);
            }

            ball.draw(gc);
            paddle.draw(gc);
        }

        public Paddle getPaddle() {
        return paddle;
    }


    private boolean shouldBallBounceHorizontally() {
        double ballX = ball.getX();
        double radius = ball.getRadius();

        return ballX - radius <= 0 || ballX + radius >= getWidth();
    }

    private boolean shouldBallBounceVertically() {
        double ballY = ball.getY();
        double radius = ball.getRadius();

        return ballY - radius <= 0;
    }

    private boolean shouldBallBounceFromPaddle() {
        double ballBottom = ball.getY() + ball.getRadius();
        double paddleTop = paddle.getY();
        double paddleLeft = paddle.getX();
        double paddleRight = paddle.getX() + paddle.getWidth();

        return ballBottom >= paddleTop &&
                ballBottom <= paddleTop + ball.getRadius() &&
                ball.getX() >= paddleLeft &&
                ball.getX() <= paddleRight &&
                ball.getMoveVector().getY() > 0;
    }

    private void loadLevel() {
        int rows = 20;
        int cols = 10;
        Brick.setGrid(rows, cols);

        double canvasWidth = getWidth();
        double canvasHeight = getHeight();

        Color[] colors = {
                Color.RED, Color.ORANGE, Color.YELLOW,
                Color.GREEN, Color.BLUE, Color.PURPLE
        };

        for (int row = 2; row <= 7; row++) {
            Color color = colors[row - 2];
            for (int col = 0; col < cols; col++) {
                Brick brick = new Brick(col, row, color, canvasWidth, canvasHeight);
                bricks.add(brick);
            }
        }
    }

}
