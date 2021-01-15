package ch.bbcag.physicsengine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public int width = 1280;
    public int height = 720;
    public Canvas canvas;
    private PhysicsTimer timer = new PhysicsTimer();
    public List<Particle> particles = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (var i = 0; i < 1000; i++) {
            var m = 30 * Math.random();
            var particle = new Particle(m);
            particle.position.x = Math.random() * width;
            particle.position.y = Math.random() * height;
            particles.add(particle);
        }
        var root = new Group();
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        timer.onUpdate = (t, dt) -> {
            for (var particle : particles) {
                particle.update(t, dt);
            }
        };

        timer.onInterpolate = (alpha) -> {
            for (var particle : particles) {
                particle.lerp(alpha);
            }
        };

        timer.onRender = () -> {
            var gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(0, 0, width, height);

            for (var particle : particles) {
                gc.setFill(Color.rgb((int)particle.mass, (int)particle.mass, (int)particle.mass));
                gc.fillOval(particle.position.x, particle.position.y, particle.mass, particle.mass);
            }
        };

        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
        timer.start();
    }
}