package ch.bbcag.physicsengine.simple;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.joml.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {

    public static final Logger log = LoggerFactory.getLogger(App.class);

    public int width = 1280;
    public int height = 720;
    public long lastTime = System.nanoTime();
    public double totalTime;
    public GraphicsContext gc;
    public boolean w = false;
    public boolean a = false;
    public boolean s = false;
    public boolean d = false;
    public boolean space = false;

    public GameObject gameObject = new GameObject();

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            var dt = (now - lastTime) / 1e9;
            totalTime += dt;
            update(totalTime, dt);
            render();
        }
    };

    public void update(double t, double dt) {
        if (w) {
            gameObject.addForce(new Vector2d(0, 10));
        }

        if (a) {
            gameObject.addForce(new Vector2d(-10, 0));
        }

        if (s) {
            gameObject.addForce(new Vector2d(0, -10));
        }

        if (d) {
            gameObject.addForce(new Vector2d(10, 0));
        }

        if (space) {
            if (!gameObject.isJumping) {
                gameObject.isJumping = true;
                gameObject.addForce(new Vector2d(0, -600));
            }
        }

        if (gameObject.position.y < 0) {
            gameObject.velocity.y = 0;
            gameObject.position.y = 0;
        }

        if (gameObject.position.x + gameObject.mass > width) {
            gameObject.velocity.x = 0;
            gameObject.position.x = width - gameObject.mass;
        }

        if (gameObject.position.x < 0) {
            gameObject.velocity.x = 0;
            gameObject.position.x = 0;
        }
        if ((gameObject.position.y + gameObject.mass + 0.2) > height) {
            gameObject.velocity.y = 0;

        }
        gameObject.update(t, dt);

        if ((gameObject.position.y + gameObject.mass + 0.2) > height) {
            gameObject.velocity.y = 0;
            gameObject.position.y = height - gameObject.mass;
            gameObject.isJumping = false;
        }
    }

    public void render() {
        gc.clearRect(0, 0, width, height);
        gameObject.render(gc);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Group();
        var canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        var scene = new Scene(root, width, height);

        gameObject.position.set(0, height - gameObject.mass);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case W -> w = true;
                case A -> a = true;
                case S -> s = true;
                case D -> d = true;
                case SPACE -> space = true;
            }


        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            switch (key.getCode()) {
                case W -> w = false;
                case A -> a = false;
                case S -> s = false;
                case D -> d = false;
                case SPACE -> space = false;
            }


        });


        primaryStage.setScene(scene);
        primaryStage.show();
        timer.start();
    }
}
