package ch.bbcag.physicsengine.rigidbody;

import ch.bbcag.physicsengine.PhysicsWorld;
import org.joml.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RigidBody {
    public static final Logger log = LoggerFactory.getLogger(RigidBody.class);

    private List<Vector2d> forces = new ArrayList<>();
    public double mass = 1;
    public final Vector2d position = new Vector2d();
    public final Vector2d previousPosition = new Vector2d();
    public final Vector2d linearVelocity = new Vector2d();
    public final Vector2d force = new Vector2d(PhysicsWorld.EARTH_GRAVITY).mul(1.0 / mass);

    public RigidBody(double mass) {
        this.mass = mass;
    }

    public void update(double t, double dt) {
        var a = accelerationFromForces();
        linearVelocity.add(a.mul(dt));
        previousPosition.set(position);
        position.add(linearVelocity);
    }

    public void interpolate(double alpha) {
        position.mul(alpha).add(previousPosition.mul(1.0 - alpha));
    }

    private Vector2d accelerationFromForces() {
        return force;
    }
}
