package ch.bbcag.physicsengine;

import ch.bbcag.physicsengine.rigidbody.RigidBody;
import ch.bbcag.physicsengine.simulation.PhysicsSimulation;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.joml.Vector2d;

public class TestSimulation extends PhysicsSimulation {
    public RigidBody rigidBody = new RigidBody();

    public TestSimulation(int width, int height) {
        super(width, height);
        rigidBody.addForce(PhysicsWorld.EARTH_GRAVITY);
    }

    @Override
    public void onUpdate(double t, double dt) {
        rigidBody.update(t, dt);
    }

    @Override
    public void onInterpolate(double alpha) {
        rigidBody.interpolate(alpha);
    }

    @Override
    public void onRender(GraphicsContext gc) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.PAPAYAWHIP);
        gc.fillOval(rigidBody.position.x, rigidBody.position.y, 30, 30);
    }
}
