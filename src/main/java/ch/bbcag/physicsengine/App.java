package ch.bbcag.physicsengine;

import ch.bbcag.physicsengine.rigidbody.RigidBody;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    public int width = 1280;
    public int height = 720;
    public Scene scene;
    public Stage stage;
    public Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
    public PhysicsWorld world = new PhysicsWorld(width, height);

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            stage.setTitle(String.format("t: %.2f, FPS: %.2f", world.getTime(), world.getFPS()));
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        var root = new Group();
        world = new PhysicsWorld(width, height);
        root.getChildren().add(world.canvas);

        scene = new Scene(root, width, height);
        world.scene = scene;
        var body = new RigidBody(1);
        world.addBody(body);

        for(var i = 0; i < 500; i++) {
            var b = new RigidBody(10 * Math.random());
            b.linearVelocity.x = 1 * Math.random();
            b.linearVelocity.y = 1 * Math.random();
            b.position.x = width * Math.random();
            b.position.y = height * Math.random();
            world.addBody(b);
        }


        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case W -> body.linearVelocity.y += 1;
                case A -> body.linearVelocity.x -= 1;
                case S -> body.linearVelocity.y -= 1;
                case D -> body.linearVelocity.x += 1;
                case SPACE -> body.linearVelocity.mul(0.1);
                case ESCAPE -> System.exit(0);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        world.start();
        timer.start();
    }
}
