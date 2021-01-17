package ch.bbcag.physicsengine.simulation;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class RenderedPhysicsSimulation extends AbstractPhysicsSimulation {
    private int targetFPS = 120;
    private double fps = 0;
    private int frameCounter;
    private boolean isPaused;
    public Canvas canvas;
    public int width;
    public int height;

    public RenderedPhysicsSimulation(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new Canvas(width, height);
    }

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void start() {
            isPaused = false;
            super.start();
        }

        @Override
        public void handle(long now) {
            RenderedPhysicsSimulation.this.stepSimulation(now);
        }

        @Override
        public void stop() {
            isPaused = true;
            super.stop();
        }
    };

    public RenderedPhysicsSimulation() {
        super();
    }

    public RenderedPhysicsSimulation(int targetFPS, double dt, int targetUPS) {
        super(dt, targetUPS);
        this.targetFPS = targetFPS;
    }

    public RenderedPhysicsSimulation(int targetFPS) {
        this.targetFPS = targetFPS;
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }

    public abstract void onRender(GraphicsContext gc);

    public void stepSimulation(long currentTime) {
        var frameTime = currentTime - getLastTime();
        fps = (1d / frameTime) * 1e9;
        onRender(canvas.getGraphicsContext2D());
        if (isPaused) return;
        super.stepSimulation(currentTime);
    }

    public double getFPS() {
        return fps;
    }
}
