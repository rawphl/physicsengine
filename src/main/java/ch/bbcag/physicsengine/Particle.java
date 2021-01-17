package ch.bbcag.physicsengine;

import javafx.scene.paint.Color;
import org.joml.Vector2d;

public class Particle {
    public int width = 1280;
    public int height = 720;
    public final Color color = Color.color(Math.random(), Math.random(), Math.random());
    public final Vector2d zero = new Vector2d();
    public final Vector2d gravity = new Vector2d(0, 9.81);
    public final Vector2d previousPosition = new Vector2d();
    public final Vector2d position = new Vector2d();
    public final Vector2d velocity = new Vector2d();
    public final Vector2d acceleration = new Vector2d();

    public final Vector2d tempPosition = new Vector2d();
    public final Vector2d tempVelocity = new Vector2d();
    public final Vector2d tempVelocity2 = new Vector2d();
    public final Vector2d tempAcceleration = new Vector2d();

    public final Vector2d dragForce = new Vector2d();
    public double drag = 0.1;
    public double mass = 30;

    public Particle() {

    }

    public Particle(double mass) {
        this.mass = mass;
    }

    public void update(double t, double dt) {
        if (position.y < 0) {
            position.y = 0;
            velocity.y = -velocity.y;
            acceleration.y = 0;
        }
        if (position.x < 0) {
            position.x = 0;
            velocity.x = -velocity.x;
            acceleration.x = 0;
        }

        if (position.y >= height - mass) {
            position.y = height - mass;
            velocity.y = -velocity.y;
            acceleration.y = 0;
        }

        if (position.x >= width - mass) {
            position.x = width - mass;
            velocity.x = -velocity.x;
            acceleration.x = 0;
        }

        tempPosition.set(position);
        tempVelocity.set(velocity);
        tempVelocity2.set(velocity);
        tempAcceleration.set(acceleration);
        tempPosition.add(tempVelocity.mul(dt)).add(tempAcceleration.mul(dt * dt * 0.5));
        tempAcceleration.set(zero);
        tempAcceleration.set(dragForce);
        tempAcceleration.mul(tempVelocity.mul(tempVelocity2.absolute())).mul(0.5 * drag);
        tempAcceleration.div(mass);
        tempVelocity.set(velocity).add(acceleration.add(tempAcceleration).mul(dt * 0.5));
        previousPosition.set(position);
        position.set(tempPosition);
        velocity.set(tempVelocity);
        acceleration.set(tempAcceleration);
    }

    public void lerp(double alpha) {
        position.mul(alpha).add(previousPosition.mul(1 - alpha));
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
                ", mass=" + mass +
                '}';
    }
}
