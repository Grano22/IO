package com.ksabov.cbo;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class RenderingMiddlewareManager {
    ArrayList<RenderingMiddleware> middlewares = new ArrayList<>();

    public RenderingMiddlewareManager register(RenderingMiddleware middleware) {
        middlewares.add(middleware);

        return this;
    }

    public void beforeAddObject(Actor gameObject) {
        for (RenderingMiddleware middleware : middlewares) {
            middleware.beforeAddGameObject(gameObject);
        }
    }
}
