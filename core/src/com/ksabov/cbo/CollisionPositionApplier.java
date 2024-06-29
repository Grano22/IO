package com.ksabov.cbo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.List;
import java.util.function.Function;

public class CollisionPositionApplier {
    public Function<MoveToAction, MoveToAction> apply(List<Rectangle> boundedRects) {
        return new Function<MoveToAction, MoveToAction>() {
            @Override
            public MoveToAction apply(MoveToAction movement) {
                for (Rectangle boundedRect : boundedRects) {
                    if (new Rectangle(movement.getX(), movement.getY(), movement.getActor().getWidth(), movement.getActor().getHeight()).overlaps(boundedRect)) {
                        movement.setPosition(movement.getStartX(), movement.getStartY());
                        break;
                    }
                }

                return movement;
            }
        };
    }
}
