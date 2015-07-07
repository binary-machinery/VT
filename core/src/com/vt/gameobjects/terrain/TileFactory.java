package com.vt.gameobjects.terrain;

/**
 * Created by Fck.r.sns on 07.07.2015.
 */
public class TileFactory {
    public static Tile create(Tile.Type type) {
        switch (type) {
            case Floor:
                return new Floor();
            case Wall:
                return new Wall();
            default:
                return null;
        }
    }
}
