package ch.bbcag.physicsengine.simulation;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractPhysicsSimulation {
    public Executor ec = Executors.newSingleThreadExecutor();
    private int targetUPS = 15;
    private double t = 0.0;
    private double dt = 0.001;
    private double maxFrameLength = dt * targetUPS;
    private double accumulator = 0.0;
    private long lastTime;
    private boolean didUpdate = false;
    private boolean isStarted = false;

    public AbstractPhysicsSimulation() {
    }

    public AbstractPhysicsSimulation(double dt) {
        setTimeStep(dt);
    }

    public AbstractPhysicsSimulation(double dt, int targetUPS) {
        this.targetUPS = targetUPS;
        setTimeStep(dt);
    }

    public abstract void onUpdate(double t, double dt);

    public abstract void onInterpolate(double alpha);

    public void stepSimulation(long currentTime) {
        ec.execute(() -> update(currentTime));
    }

    private void update(long currentTime) {
        if (!isStarted) {
            lastTime = System.nanoTime();
            isStarted = true;
        }
        var frameTime = (currentTime - lastTime) / 1e9;
        if (frameTime > maxFrameLength) {
            frameTime = maxFrameLength;
        }
        lastTime = currentTime;
        accumulator += frameTime;

        while (accumulator >= dt) {
            onUpdate(t, dt);
            didUpdate = true;
            t += dt;
            accumulator -= dt;
        }

        var alpha = accumulator / dt;
        if (didUpdate) onInterpolate(alpha);
    }

    public double getTimeStep() {
        return dt;
    }

    public void setTimeStep(double dt) {
        this.dt = dt;
        this.maxFrameLength = dt * targetUPS;
    }

    public double getTargetUPS() {
        return targetUPS;
    }

    public void setTargetUPS(int targetUPS) {
        this.targetUPS = targetUPS;
        setTimeStep(dt);
    }

    public double getTime() {
        return t;
    }

    public long getLastTime() {
        return lastTime;
    }

    @Override
    public String toString() {
        return "PhysicsSimulation{" +
                "targetUPS=" + targetUPS +
                ", t=" + t +
                ", dt=" + dt +
                ", isStarted=" + isStarted +
                '}';
    }
}
