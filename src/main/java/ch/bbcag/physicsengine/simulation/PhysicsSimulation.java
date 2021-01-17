package ch.bbcag.physicsengine.simulation;

import ch.bbcag.physicsengine.Particle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulation extends RenderedPhysicsSimulation {
    public List<Particle> particles = new ArrayList<>();

    public PhysicsSimulation(int width, int height) {
        super(width, height);
        for (var i = 0; i < 12000; i++) {
            var m = 15 * Math.random();
            var particle = new Particle(m);
            particle.position.x = Math.random() * width;
            particle.position.y = Math.random() * height;
            particle.velocity.x = Math.random() * width;
            particle.velocity.y = Math.random() * height;
            particles.add(particle);
        }
    }

    @Override
    public void onUpdate(double t, double dt) {
        for (var particle : particles) {
            particle.update(t, dt);
        }
    }

    @Override
    public void onInterpolate(double alpha) {
        for (var particle : particles) {
            particle.lerp(alpha);
        }
    }

    @Override
    public void onRender(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        for (var particle : particles) {
            gc.setFill(particle.color);
            gc.fillOval(particle.position.x, particle.position.y, particle.mass, particle.mass);
        }
    }
}
