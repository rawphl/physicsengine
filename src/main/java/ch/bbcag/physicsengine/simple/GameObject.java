package ch.bbcag.physicsengine.simple;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    public static final Vector2d EARTH_GRAVITY = new Vector2d(0, 9.81);
    public static final Vector2d MARIO_WORLD_GRAVITY = new Vector2d(0, 68);
    public static final Vector2d ZERO = new Vector2d(0, 0);
    public double mass = 69;
    public Vector2d velocity = new Vector2d();
    public Vector2d position = new Vector2d();
    public List<Vector2d> forces = new ArrayList<>();
    public boolean isJumping = false;

    public void addForce(Vector2d force) {
        forces.add(force);
    }

    public void update(double t, double dt) {
        // F = ma = Kraft = Masse * Beschleunigung
        // a = F/m = F = Summe aller Kräfte, die auf den Körper einwirken siehe accelerationFromForces
        // a(t) - Beschleunigung = Änderung der Geschwindigkeit pro Zeitschrit
        // v(t) - Geschwindigkeit = Änderung der Position pro Zeitschrit
        // p(t) - Position
        var a = accelerationFromForces();
        velocity.add(a.mul(0.5 * dt * dt));
        position.add(velocity);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Paint.valueOf("#FF00FF"));
        gc.fillOval(position.x, position.y, mass, mass);
    }

    private Vector2d accelerationFromForces() {
        var totalForce = new Vector2d(isJumping ? MARIO_WORLD_GRAVITY : ZERO);

        for(var force : forces) {
            totalForce.add(force);
        }
        forces.clear();
        return totalForce.mul(1.0 / mass);
    }
}
