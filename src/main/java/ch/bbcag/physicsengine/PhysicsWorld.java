package ch.bbcag.physicsengine;

import ch.bbcag.physicsengine.rigidbody.RigidBody;
import ch.bbcag.physicsengine.simulation.RenderedPhysicsSimulation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhysicsWorld extends RenderedPhysicsSimulation {
    public static final Vector2d EARTH_GRAVITY = new Vector2d(0, 9.81);
    private final int width;
    private final int height;
    private List<RigidBody> bodies = new ArrayList<>();
    public Random r = new Random();

    public PhysicsWorld(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
        System.out.println("width = " + width + ", height = " + height);
    }

    @Override
    public void onRender(GraphicsContext gc) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.FUCHSIA);
        bodies.forEach(b -> {
            gc.setFill(Color.FUCHSIA);
            gc.fillOval(b.position.x, b.position.y, b.mass, b.mass);
        });
    }

    public void addBody(RigidBody body) { bodies.add(body); }

    public List<RigidBody> getBodies() {
        return List.copyOf(bodies);
    }

    @Override
    public void onUpdate(double t, double dt) {
        bodies.stream().forEach(b -> {
            if(b.position.y + b.mass > height) {
                b.linearVelocity.y *= -1;
            }

            if(b.position.y < 0) {
                b.linearVelocity.y *= -1;
            }

            if(b.position.x + b.mass > width) {
                b.linearVelocity.x *= -1;
            }

            if(b.position.x < 0) {
                b.linearVelocity.x *= -1;
            }
            b.update(t, dt);
        });
    }

    @Override
    public void onInterpolate(double alpha) {
        bodies.stream().forEach(b -> b.interpolate(alpha));
    }
}
