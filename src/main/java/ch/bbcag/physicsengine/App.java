package ch.bbcag.physicsengine;

import ch.bbcag.physicsengine.simulation.PhysicsSimulation;
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
    private PhysicsSimulation physicsSimulation;
    public Scene scene;
    public Stage stage;
    public Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    public AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            stage.setTitle(String.format("FPS: %.2f", physicsSimulation.getFPS()));
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        physicsSimulation = new PhysicsSimulation(width, height);
        var root = new Group();
        root.getChildren().add(physicsSimulation.canvas);
        scene = new Scene(root, width, height);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ESCAPE -> System.exit(0);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        physicsSimulation.start();
        timer.start();
    }
}
