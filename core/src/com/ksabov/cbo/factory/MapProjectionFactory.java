package com.ksabov.cbo.factory;

import com.ksabov.cbo.MapGenerationDefinition;
import com.ksabov.cbo.map.TiledMapProjection;

public class MapProjectionFactory {
    public TiledMapProjection create(MapGenerationDefinition mapGenerationDefinition) {
        return new TiledMapProjection(mapGenerationDefinition.mapWidth(), mapGenerationDefinition.mapHeight(), mapGenerationDefinition.tileSize());
    }
}
