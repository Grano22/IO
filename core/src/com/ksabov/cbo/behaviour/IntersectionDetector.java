package com.ksabov.cbo.behaviour;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Disposable;
import com.ksabov.cbo.GameObjectCollection;
import com.ksabov.cbo.TiledMapProjection;
import org.w3c.dom.css.Rect;

import java.lang.ref.WeakReference;
import java.util.Objects;

// Intersection Detector, ColissionManager
public class IntersectionDetector implements Disposable {
    //private final Map<String, BoundingBox> coliders = new HashMap<>();
    private final WeakReference<GameObjectCollection> gameObjects;
    //private final WeakReference<TiledMapProjection> mapProjection;

    public IntersectionDetector(WeakReference<GameObjectCollection> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Intersectional willIntersectWith(Intersectional actor, MoveToAction action) {
        if (gameObjects.get() == null) return null;

        for (Actor gameObject: Objects.requireNonNull(gameObjects.get())) {
            if (gameObject.equals(actor)) {
                continue;
            }

            if (gameObject instanceof Intersectional) {
                Intersectional intersectionalObject = (Intersectional)gameObject;

                if (intersectionalObject.getBoundingRect() == null) {
                    continue;
                }

                Rectangle currentBoundingRect = actor.getBoundingRect();
                Rectangle nextBoundingRect = new Rectangle(
                     currentBoundingRect.x + Math.abs(action.getX() - action.getStartX()),
                     currentBoundingRect.y + Math.abs(action.getY() - action.getStartY()),
                        currentBoundingRect.width,
                        currentBoundingRect.height
                );
                if (nextBoundingRect.overlaps(intersectionalObject.getBoundingRect())) {
                    return intersectionalObject;
                }
            }
        }

        return null;
    }

    public Intersectional isIntersectingWith(Intersectional actor) {
        if (gameObjects.get() == null) return null;

        for (Actor gameObject: Objects.requireNonNull(gameObjects.get())) {
            if (gameObject.equals(actor)) {
                continue;
            }

            if (gameObject instanceof Intersectional) {
                Intersectional intersectionalObject = (Intersectional)gameObject;
                if (actor.getBoundingRect().contains(intersectionalObject.getBoundingRect())) {
                    return intersectionalObject;
                }
            }
        }

        return null;
    }

    @Override
    public void dispose() {

    }
}
