package com.github.agalonstudios;

import java.util.HashMap;

/**
 * Created by spr on 3/18/17.
 */
public class EntityManager {
    private static HashMap<Integer, Entity> m_map = new HashMap<Integer, Entity>();
    private static int m_nextValidEntityID = 0;

    public static void registerEntity(Entity e) {
        m_map.put(e.getID(), e);
    }

    public static int getNextValidEntityID() {
        m_nextValidEntityID++;
        return m_nextValidEntityID;
    }
}
