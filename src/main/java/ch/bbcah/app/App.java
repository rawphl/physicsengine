package ch.bbcah.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class App extends Application {
    private PhysicsTimer timer = new PhysicsTimer();

    public Canvas canvas;
    public int width = 800;
    public int height = 600;
    public int hwidth = width/2;
    public int hheight = height/2;

    public int frames = 0;
    private double time = 0;
    public double lastFrameTime;
    public List<Particle> particles = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        for(var i = 0; i < 9; i++) {
            var particle = new Particle();
            particle.currentState.position.x = i * (width / 10);
            particles.add(particle);
        }
        var root = new Group();
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        timer.onUpdate = (t, dt) -> {
            for(var particle : particles) {
                particle.update(t, dt);
            }
        };

        timer.onInterpolate = (alpha) -> {
            for(var particle : particles) {
                particle.lerp(alpha);
            }
        };

        timer.onRender = () -> {
            var gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.PAPAYAWHIP);
            for(var particle : particles) {
                gc.fillOval(particle.currentState.position.x, particle.currentState.position.y, 30, 30);
            }

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