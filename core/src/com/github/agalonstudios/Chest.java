package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/14/17.
 */
public class Chest extends Entity {
    // private Inventory m_inventory;

    public Chest(ChestItem item, int x, int y) {
        super(Wall.HEIGHT/2, new Vector2(x + Wall.HEIGHT / 4, y + Wall.HEIGHT /4), Shape.SQUARE);
        m_color = new Color(182 / 255.f, 139 / 255.f, 89 / 255.f, 1);
    }



    @Override
    public void runCollision(Entity other) {

    }

    @Override
    public void render() {
        super.render();
       // sr.setColor(255 / 255.f, 246 / 255.f, 64 / 255.f, 1);
       // sr.rect(m_rect.x + m_rect.width / 4 - camera.position.x, m_rect.y + m_rect.height / 4 - camera.position.y, m_rect.width / 2, m_rect.height / 2);
    }
}
