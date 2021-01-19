package ch.bbcag.physicsengine.rigidbody;

import org.joml.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RigidBody {
    public static final Logger log = LoggerFactory.getLogger(RigidBody.class);
    private double mass = 1;
    public final Vector2d position = new Vector2d();
    public final Vector2d previousPosition = new Vector2d();
    public final Vector2d velocity = new Vector2d();
    public final Vector2d acceleration = new Vector2d();
    public final Vector2d totalForce = new Vector2d();

    private final Vector2d tmp = new Vector2d();

    public final List<Vector2d> forces = new ArrayList<>();

    public RigidBody() {

    }

    public void applyForce(Vector2d force) {
        forces.add(force);
    }

    public void update(double t, double dt) {
        tmp.set(accelerationFromForces());
        acceleration.set(tmp);
        velocity.add(tmp.mul(dt));
        tmp.set(velocity);
        previousPosition.set(position);
        position.add(tmp.mul(dt));
        log.info(String.format("pp: %s, p: %s, v: %s", previousPosition, position, velocity));
        forces.clear();
        totalForce.set(0);
    }

    private Vector2d accelerationFromForces() {
        return forces.stream().reduce(totalForce, (acc, curr) -> acc.add(curr)).div(mass);
    }

    public void interpolate(double alpha) {
        position.mul(alpha).add(previousPosition.mul(1 - alpha));
    }
}
