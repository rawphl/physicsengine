package ch.bbcah.app;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhysicsTimer extends AnimationTimer {
    public Executor ec = Executors.newSingleThreadExecutor();

    public static interface UpdateHandler {
        public void call(double t, double dt);
    }

    public static interface InterpolationHandler {
        public void call(double alpha);
    }

    public static interface RenderHandler {
        public void call();
    }


    private int targetFPS;
    private int targetUPS = 25;
    private long lastTime;

    private double t = 0.0;
    private double dt = 0.001;
    public double maxFrameLength = 0.25;
    private double accumulator = 0.0;
    private boolean quit = false;
    public UpdateHandler onUpdate;
    public InterpolationHandler onInterpolate;
    public RenderHandler onRender;
    public boolean didUpdate = false;

    @Override
    public void start() {
        super.start();
        lastTime = System.nanoTime();
    }

    @Override
    public void handle(long currentTime) {
        ec.execute(() -> {
            didUpdate = false;
            var frameTime = (currentTime - lastTime) / 1e9;
            if (frameTime > maxFrameLength) {
                frameTime = maxFrameLength;
            }
            lastTime = currentTime;
            accumulator += frameTime;

            while (accumulator >= dt) {
                onUpdate.call(t, dt);
                didUpdate = true;
                t += dt;
                accumulator -= dt;
            }

            var alpha = accumulator / dt;
            if(didUpdate) onInterpolate.call(alpha);
            Platform.runLater(() -> {
                onRender.call();
            });
        });
    }
}
