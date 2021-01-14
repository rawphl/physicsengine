package ch.bbcah.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class App extends Application {
    private PhysicsTimer timer = new PhysicsTimer();
    public Circle circle = new Circle();;
    public Circle lastCircle  = new Circle();;

    public Circle circle2  = new Circle();;
    public Circle lastCircle2  = new Circle();;

    public Canvas canvas;
    public int width = 1600;
    public int height = 1200;
    public int hwidth = width/2;
    public int hheight = height/2;
    private int velocity = 300;
    private int radius = 40;
    public int frames = 0;
    private double time = 0;
    public double lastFrameTime;

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Group();
        canvas = new Canvas(width, height);
        circle.setRadius(radius);
        circle.setCenterY(radius * 2);
        lastCircle = new Circle();

        circle2.setRadius(radius);
        circle2.setCenterY(hheight);
        lastCircle2 = new Circle();

        root.getChildren().add(canvas);

        timer.onUpdate = (t, dt) -> {
            lastCircle2.setCenterX(circle2.getCenterX());
            lastCircle2.setCenterY(circle2.getCenterY());
            circle2.setCenterX(circle2.getCenterX() + velocity * dt);

            lastCircle.setCenterX(circle.getCenterX());
            lastCircle.setCenterY(circle.getCenterY());
            circle.setCenterX(circle2.getCenterX() + velocity * dt);

            if(circle.getCenterX() >= width - radius || circle2.getCenterX() >= width - radius) {
               velocity *= -1;
            }


            if(circle.getCenterX() <= 0 || circle2.getCenterX() <= 0) {
                velocity *= -1;
            }
        };

        timer.onInterpolate = (alpha) -> {
            circle.setCenterX(circle.getCenterX() * alpha + lastCircle.getCenterX() * (1.0 - alpha));
        };

        timer.onRender = () -> {
            var gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.RED);
            gc.fillOval(circle.getCenterX(), circle.getCenterY(), radius, radius);
            gc.setFill(Color.BLUE);
            gc.fillOval(circle2.getCenterX(), circle2.getCenterY(), radius, radius);

            var currentTime = System.nanoTime() / 1e9;
            var duration = currentTime - lastFrameTime;
            time += duration;

            if(time > 1) {
                primaryStage.setTitle("FPS:" + frames);
                frames = 0;
                time = 0;
            }
            lastFrameTime = currentTime;
            frames++;
        };

        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
        timer.start();
    }
}