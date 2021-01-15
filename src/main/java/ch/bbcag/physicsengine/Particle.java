package ch.bbcag.physicsengine;

import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Particle {
    public final Vector2d zero = new Vector2d();
    public final Vector2d gravity = new Vector2d(0, 9.81);
    public final Vector2d previousPosition = new Vector2d();
    public final Vector2d position = new Vector2d();
    public final Vector2d velocity = new Vector2d();
    public final Vector2d acceleration = new Vector2d();

    public final Vector2d tempPosition = new Vector2d();
    public final Vector2d tempVelocity = new Vector2d();
    public final Vector2d tempAcceleration = new Vector2d();

    public  List<Vector2d> forces = Arrays.asList(gravity, new Vector2d(2, 0));

    public double mass = 30;
    public boolean isDead = false;

    public Particle() {

    }

    public Particle(double mass) {
        this.mass = mass;
    }

    public void update(double t, double dt) {
        tempPosition.set(position);
        tempVelocity.set(velocity);
        tempAcceleration.set(acceleration);
        tempPosition.add(tempVelocity.mul(dt)).add(tempAcceleration.mul(dt * dt * 0.5));
        tempAcceleration.set(zero);
        tempAcceleration.set(forces.get(0));
        tempAcceleration.div(mass);
        tempVelocity.set(velocity).add(acceleration.add(tempAcceleration).mul(dt * 0.5));
        previousPosition.set(position);
        position.set(tempPosition);
        velocity.set(tempVelocity);
        acceleration.set(tempAcceleration);
    }

    public void lerp(double alpha) {
        position.mul(alpha).add(previousPosition.mul(1 - alpha));
        if(position.y > 720) {
            position.y = -mass;
        }
        if(position.x > 1280) {
            position.x = -mass;
        }
    }

    @Override
    public String toString() {
        return "Particle{" +
                "gravity=" + gravity +
                ", position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                ", tempPosition=" + tempPosition +
                ", tempVelocity=" + tempVelocity +
                ", tempAcceleration=" + tempAcceleration +
                ", forces=" + forces +
                ", mass=" + mass +
                '}';
    }
}
