package ch.bbcag.physicsengine.collision;

import org.joml.Vector2d;

public class Circle extends BoundingShape {
    private double radius;
    public Vector2d position = new Vector2d();

    public Circle() {}

    public Circle(double radius) {
        this.radius = radius;
    }

    public boolean collidesWith(Circle other){
        var copy = new Vector2d();
        copy.sub(other.position, copy);
        var distance = Math.sqrt(copy.x * copy.x + copy.y * copy.y);
        return distance < radius + other.radius;
    }
}
