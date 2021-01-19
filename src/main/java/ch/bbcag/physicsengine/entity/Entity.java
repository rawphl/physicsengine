package ch.bbcag.physicsengine.entity;

import ch.bbcag.physicsengine.collision.BoundingShape;
import ch.bbcag.physicsengine.rigidbody.RigidBody;
import javafx.scene.canvas.GraphicsContext;

import java.util.UUID;

public class Entity extends RigidBody implements Updatetable, Interpolatetable, Renderable {

    @Override
    public void render(GraphicsContext gc) {

    }
}
