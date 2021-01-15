package ch.bbcah.app;

public class Particle {
    public PhysicsState previousState = new PhysicsState();
    public PhysicsState currentState = new PhysicsState();

    public void update(double t, double dt) {
        currentState.update(t, dt);
    }

    public void lerp(double alpha) {
        var newState = PhysicsState.lerp(currentState, previousState, alpha);
        previousState = currentState;
        currentState = newState;
    }
}
