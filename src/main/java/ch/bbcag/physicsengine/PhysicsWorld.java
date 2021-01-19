package ch.bbcag.physicsengine;

import ch.bbcag.physicsengine.rigidbody.RigidBody;
import ch.bbcag.physicsengine.simulation.RenderedPhysicsSimulation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class PhysicsWorld extends RenderedPhysicsSimulation {
    public static final Vector2d EARTH_GRAVITY = new Vector2d(0, 9.81);
    private final int width;
    private final int height;
    private List<RigidBody> bodies = new ArrayList<>();
    private Vector2d tmp = new Vector2d();
    private Vector2d tmp2 = new Vector2d();

    public PhysicsWorld(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void onRender(GraphicsContext gc) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.RED);
        bodies.forEach(b -> {
            gc.fillOval(b.position.x, b.position.y, 30, 30);
        });
    }

    public void addBody(RigidBody body) { bodies.add(body); }

    public List<RigidBody> getBodies() {
        return List.copyOf(bodies);
    }

    @Override
    public void onUpdate(double t, double dt) {
        bodies.stream().forEach(b -> {
            if(b.position.y + 30 >= height) {
                b.velocity.y = -b.velocity.y;
            }

            if(b.position.y - 30 <= 0) {
                b.velocity.y = -b.velocity.y;
            }

            tmp.set(b.velocity);
            tmp2.set(b.velocity);
            tmp.mul(tmp2.absolute()).mul(0.5).mul(0.1);
            b.applyForce(new Vector2d(EARTH_GRAVITY));
            b.update(t, dt);
        });
    }

    @Override
    public void onInterpolate(double alpha) {
        bodies.stream().forEach(b -> b.interpolate(alpha));
    }
}
