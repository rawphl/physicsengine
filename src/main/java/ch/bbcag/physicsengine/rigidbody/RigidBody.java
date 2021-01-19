package ch.bbcag.physicsengine.rigidbody;

import ch.bbcag.physicsengine.PhysicsWorld;
import ch.bbcag.physicsengine.entity.Interpolatetable;
import ch.bbcag.physicsengine.entity.Updatetable;
import org.joml.Vector2d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RigidBody implements Updatetable, Interpolatetable {
    public static final Logger log = LoggerFactory.getLogger(RigidBody.class);
    private double mass = 1;
    public final Vector2d position = new Vector2d();
    public final Vector2d velocity = new Vector2d();
    public final Vector2d acceleration = new Vector2d();
    public final Vector2d totalForce = new Vector2d();

    private final Vector2d tmpPosition = new Vector2d();
    private final Vector2d tmpVelocity = new Vector2d();
    private final Vector2d tmpAcceleration = new Vector2d();
    private final Vector2d tmpAcceleration2 = new Vector2d();
    public final Vector2d previousPosition = new Vector2d();
    public final List<Vector2d> forces = new ArrayList<>();

    public RigidBody() {

    }

    public void addForce(Vector2d force) {
        forces.add(force);
    }

    @Override
    public void update(double t, double dt) {
        previousPosition.set(position);
        velocity.mul(dt, tmpVelocity);
        acceleration.mul(dt * dt * 0.5, tmpAcceleration);
        position.add(tmpVelocity).add(tmpAcceleration);

        tmpAcceleration.set(getTotalForce());

        tmpVelocity.set(velocity);
        acceleration.add(tmpAcceleration, tmpAcceleration2);
        tmpVelocity.add(tmpAcceleration2.mul(dt * 0.5));

        position.set(tmpPosition);
        velocity.set(tmpVelocity);
        acceleration.set(tmpAcceleration);

        log.info(String.format("pp: %s, p: %s, v: %s", previousPosition, position, velocity));
    }

    private Vector2d getTotalForce() {
        totalForce.set(0);
        return forces.stream().reduce(totalForce, (acc, curr) -> acc.add(curr)).div(mass);
    }

    @Override
    public void interpolate(double alpha) {
        //position.mul(alpha).add(previousPosition.mul(1 - alpha));
    }
}
