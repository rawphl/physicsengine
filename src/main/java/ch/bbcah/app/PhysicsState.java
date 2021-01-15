package ch.bbcah.app;

import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class PhysicsState {
    public static PhysicsState lerp(PhysicsState current, PhysicsState previous, double alpha) {
        var newState = new PhysicsState();
        newState.position.set(current.position);
        newState.position.mul(alpha).add(previous.position.mul(1 - alpha));
        return newState;
    }

    public static Vector2d GRAVITY = new Vector2d(0, -9.81);
    public Vector2d position = new Vector2d();
    public Vector2d velocity = new Vector2d(20, 30);
    public Vector2d acceleration = new Vector2d();
    public List<Vector2d> forces = new ArrayList<>();
    public int mass = 15;

    public void update(double t, double dt) {
        position.add(velocity.mul(dt)).add(acceleration.mul(dt * dt * 0.5));
        var a = accelerationFromForces();
        velocity.set(velocity.add(acceleration.add(a).mul(dt * 0.5)));
        acceleration.set(a);
    }

    private Vector2d accelerationFromForces() {
        var sumOfForces = new Vector2d();
        forces.add(GRAVITY);
        for(var force : forces) {
            sumOfForces.add(force);
        }
        return sumOfForces.div(mass);
    }
}
