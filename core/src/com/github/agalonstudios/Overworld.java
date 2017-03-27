package com.github.agalonstudios;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by spr on 3/13/17.
 */
public class Overworld extends World {
    private Array<DungeonEntrance> m_dungeonEntrances;
    private Array<OverworldFlower> m_overworldFlowers;
    private Array<OverworldTree> m_trees;
    private Array<Traveler> m_travelers;


    // private Shop m_shop;
    // private Chest m_stashChest;

    public Overworld(Player pRef) {
        super(pRef);

        m_dungeonEntrances = new Array<DungeonEntrance>();
        int numDungeons = MathUtils.random(10, 20);
        int placeX = MathUtils.random(-1000, -500);

        for (int i = 0; i < numDungeons; i++) {
            m_dungeonEntrances.add(new DungeonEntrance(placeX, MathUtils.random(-1000, 1000), MathUtils.random(3, 5), Theme.CAVE));
            placeX += MathUtils.random(500, 1000);
        }

        m_overworldFlowers = new Array<OverworldFlower>();
        int numFluffs = MathUtils.random(1000, 5000);
        for (int i = 0; i < numFluffs; i++) {
            m_overworldFlowers.add(new OverworldFlower(MathUtils.random(-5000, 5000), MathUtils.random(-5000, 5000), 8, 8));
        }

        m_trees = new Array<OverworldTree>();
        int numTrees = MathUtils.random(150, 300);
        int placeYMax = 5000;
        for (int i = 0; i < numTrees; i++) {
            m_trees.add(new OverworldTree(MathUtils.random(-5000, 5000), MathUtils.random(placeYMax - 128, placeYMax)));
            placeYMax = (int) m_trees.get(i).getRect().y;
        }

        m_travelers = new Array<Traveler>();
        int numTravelers = MathUtils.random(1, 50);
        for (int i = 0; i < numTravelers; i++) {
            m_travelers.add(new Traveler(MathUtils.random(-5000, 5000), MathUtils.random(-5000, 5000)));
        }

    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        for (DungeonEntrance de : m_dungeonEntrances) {
            if (m_playerRef.getRect().overlaps(de.getEntranceRect()) && m_playerRef.getRect().y < de.getEntranceRect().y) {
                Dungeon d = new Dungeon(de.getLevel(), de.getTheme(), m_playerRef);
                ((Agalon) Gdx.app.getApplicationListener()).setScreen(d.currentRoom());
            }
        }

        for (OverworldFlower gf : m_overworldFlowers)
            gf.update(delta);

        for (Traveler t : m_travelers)
            t.update(delta);

        for (int i = 0; i < m_castObjects.size; i++) {
            m_castObjects.get(i).update(delta, this);
            if (m_castObjects.get(i).done()) {
                m_castObjects.removeIndex(i);
            }
        }

        runCollisions(m_playerRefArray, m_dungeonEntrances, m_trees, m_travelers, m_castObjects);
    }

    @Override
    public void render(float delta) {
        delta *= 1;
        update(delta);
        Gdx.gl.glClearColor(104/255.f, 219/255.f, 112/255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

        for (OverworldFlower gf : m_overworldFlowers)
            if (gf.getRect().x + gf.getRect().width >= m_cameraRef.position.x &&
                    gf.getRect().x < m_cameraRef.position.x + Gdx.graphics.getWidth() &&
                    gf.getRect().y + gf.getRect().height >= m_cameraRef.position.y &&
                    gf.getRect().y < m_cameraRef.position.y + Gdx.graphics.getHeight())
                gf.render(delta);

        m_shapeRendererRef.end();
        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

        m_shapeRendererRef.end();
        super.render(delta);
        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

        for (Traveler t : m_travelers)
            t.render(delta);

        for (OverworldTree t : m_trees)
            t.render(delta);

        for (DungeonEntrance d : m_dungeonEntrances)
            d.render(delta);

        for (CastObject co : m_castObjects) {
            co.render(delta);
        }

        m_shapeRendererRef.end();



        // m_shop.render();
        // m_stashChest.render();
    }
}
