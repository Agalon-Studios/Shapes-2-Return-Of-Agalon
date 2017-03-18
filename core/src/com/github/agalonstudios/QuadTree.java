package com.github.agalonstudios;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spr on 3/16/17.
 */
public class QuadTree {
    private final int MAX_OBJECTS = 5;
    private final int MAX_LEVELS = 10;

    private int m_level;
    private List<Entity> m_objects;
    private Rectangle m_bounds;
    private QuadTree[] m_nodes;

    public QuadTree(int l, Rectangle b) {
        m_level = l;
        m_objects = new ArrayList<Entity>();
        m_bounds = b;
        m_nodes = new QuadTree[4];
    }

    public void clear() {
        m_objects.clear();

        for (int i = 0; i < m_nodes.length; i++) {
            if (m_nodes[i] != null) {
                m_nodes[i].clear();
                m_nodes[i] = null;
            }
        }
    }

    private void split() {
        int subWidth = (int) (m_bounds.width / 2);
        int subHeight = (int) (m_bounds.height / 2);
        int x = (int) m_bounds.x;
        int y = (int) m_bounds.y;

        m_nodes[0] = new QuadTree(m_level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight)); // NE
        m_nodes[1] = new QuadTree(m_level + 1, new Rectangle(x, y, subWidth, subHeight)); // NW
        m_nodes[2] = new QuadTree(m_level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight)); // SW
        m_nodes[3] = new QuadTree(m_level + 1, new Rectangle(x + subWidth, x + subHeight, subWidth, subHeight)); // SE
    }

    private int getIndex(Rectangle rect) {
        int index = -1;
        float verticalMidpoint = m_bounds.x + (m_bounds.width / 2);
        float horizontalMidpoint = m_bounds.y + (m_bounds.height / 2);
        boolean topQuadrant = rect.y < horizontalMidpoint && rect.y + rect.height < horizontalMidpoint;
        boolean bottomQuadrant = rect.y > horizontalMidpoint;

        if (rect.x < verticalMidpoint && rect.x + rect.width < verticalMidpoint) {
            if (topQuadrant)
                index = 1;
            else if (bottomQuadrant)
                index = 2;
        }
        else if (rect.x > verticalMidpoint) {
            if (topQuadrant)
                index = 0;
            else if (bottomQuadrant)
                index = 3;
        }

        return index;
    }

    public void insert(Entity e) {
        if (m_nodes[0] != null) {
            int index = getIndex(e.getRect());

            if (index != -1) {
                m_nodes[index].insert(e);
                return;
            }
        }

        m_objects.add(e);

        if (m_objects.size() > MAX_OBJECTS && m_level < MAX_LEVELS) {
            if (m_nodes[0] == null)
                split();

            int i = 0;
            while (i < m_objects.size()) {
                int index = getIndex(m_objects.get(i).getRect());
                if (index != -1) {
                    m_nodes[index].insert(m_objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }

    public void retrieve(List<Entity> returnObjects, Entity e) {
        int index = getIndex(e.getRect());

        if (index != -1 && m_nodes[0] != null) {
            m_nodes[index].retrieve(returnObjects, e);
        }

        for (int i = 0; i < m_objects.size(); i++) {
            if (m_objects.get(i) == e)
                continue;
            returnObjects.add(m_objects.get(i));
        }

        returnObjects.addAll(m_objects);
    }

}
