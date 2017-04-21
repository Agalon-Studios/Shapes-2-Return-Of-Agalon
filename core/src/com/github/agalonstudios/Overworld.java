package com.github.agalonstudios;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by spr on 3/13/17.
 */
public class Overworld extends World {
    private Array<DungeonEntrance> m_dungeonEntrances;
    private Array<OverworldFlower> m_overworldFlowers;
    private Array<OverworldTree> m_trees;
    private Vector2 m_homePlate;
    private Array<ShopEntity> m_shopArray;


    // private Shop m_shop;
    // private Chest m_stashChest;

    public Overworld(Player pRef) {
        super(pRef);

        m_shopArray = new Array<ShopEntity>();
        m_shopArray.add(new ShopEntity(new Vector2(Gdx.graphics.getWidth() / 2, -100)));

        m_homePlate = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        m_dungeonEntrances = new Array<DungeonEntrance>();
        m_dungeonEntrances.add(new DungeonEntrance(Gdx.graphics.getWidth() * 1 / 3, Gdx.graphics.getHeight() * 2 / 3, 3, Theme.CAVE));
        m_dungeonEntrances.add(new DungeonEntrance(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), 3, Theme.CAVE));
        m_dungeonEntrances.add(new DungeonEntrance(Gdx.graphics.getWidth() * 2 / 3, Gdx.graphics.getHeight() * 2 / 3, 3, Theme.CAVE));


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
            placeYMax = (int) m_trees.get(i).getY();
        }

        m_dItems.add(new DroppedItem(57, new Vector2(Gdx.graphics.getWidth() * 2 / 3, Gdx.graphics.getHeight() / 2)));
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        for (DungeonEntrance de : m_dungeonEntrances) {
            if (m_playerRef.overlaps(de) && m_playerRef.getY() < de.getY()) {
                Dungeon d = new Dungeon(de.getLevel(), de.getTheme(), m_playerRef);

                if(((Agalon) Gdx.app.getApplicationListener()).getMusic().isPlaying()){
                    ((Agalon) Gdx.app.getApplicationListener()).turnOffMusic();
                    ((Agalon) Gdx.app.getApplicationListener()).setDungeonMusic(true);
                }
                else
                    ((Agalon) Gdx.app.getApplicationListener()).setDungeonMusic(false);

                ((Agalon) Gdx.app.getApplicationListener()).changeToWorld(d.currentRoom());

            }
        }

        for (OverworldFlower gf : m_overworldFlowers)
            gf.update(delta);


        for (int i = 0; i < m_castObjects.size; i++) {
            m_castObjects.get(i).update(delta);
            if (m_castObjects.get(i).done()) {
                m_castObjects.removeIndex(i);
            }
        }

        runCollisions(m_playerRefArray, m_dungeonEntrances, m_trees, m_castObjects, m_dItems, m_shopArray);
    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(104/255.f, 219/255.f, 112/255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        m_cameraRef.position.set(m_playerRef.getX() - Gdx.graphics.getWidth() / 2, m_playerRef.getY() - Gdx.graphics.getHeight() / 2, 0);
        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

        for (OverworldFlower gf : m_overworldFlowers)
            if (gf.getX() + gf.getWidth() >= m_cameraRef.position.x &&
                    gf.getX() < m_cameraRef.position.x + Gdx.graphics.getWidth() &&
                    gf.getY() + gf.getHeight() >= m_cameraRef.position.y &&
                    gf.getY() < m_cameraRef.position.y + Gdx.graphics.getHeight())
                gf.render();

        m_shapeRendererRef.end();


        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

     //   for (Traveler t : m_travelers)
       //     t.render();

        for (OverworldTree t : m_trees)
            t.render();

        for (DungeonEntrance d : m_dungeonEntrances)
            d.render();

        for (CastObject co : m_castObjects) {
            co.render();
        }

        m_shopArray.get(0).render();

        m_shapeRendererRef.end();

        super.render(delta);

        // m_shop.render();
        // m_stashChest.render();
    }

    public Vector2 getHomePlate() {
        return m_homePlate;
    }
}
