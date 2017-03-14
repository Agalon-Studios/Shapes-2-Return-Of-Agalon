package com.github.agalonstudios;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/13/17.
 */
public class Overworld extends World {
    private Array<DungeonEntrance> m_dungeonEntrances;
    private Array<OverworldFlower> m_overworldFlowers;

    // private Shop m_shop;
    // private Chest m_stashChest;

    public Overworld(Player pRef) {
        super(pRef);

        m_dungeonEntrances = new Array<DungeonEntrance>();
        m_dungeonEntrances.add(new DungeonEntrance(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() + 100, 6, Theme.CAVE));

        m_overworldFlowers = new Array<OverworldFlower>();
        int numFluffs = MathUtils.random(200, 500);
        for (int i = 0; i < numFluffs; i++) {
            m_overworldFlowers.add(new OverworldFlower(MathUtils.random(-2000, 2000), MathUtils.random(-2000, 2000), 8, 8));
        }

    }

    private void update(float delta) {
        for (DungeonEntrance de : m_dungeonEntrances) {
            if (m_playerRef.getRect().overlaps(de.getRect())) {
                Dungeon d = new Dungeon(de.getLevel(), de.getTheme(), m_playerRef);
                ((Agalon) Gdx.app.getApplicationListener()).setScreen(d.currentRoom());
            }
        }

        for (OverworldFlower gf : m_overworldFlowers)
            gf.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(104/255.f, 219/255.f, 112/255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);
        for (OverworldFlower gf : m_overworldFlowers)
            gf.render(delta);
        m_shapeRendererRef.end();
        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);
        for (DungeonEntrance d : m_dungeonEntrances)
            d.render(delta);
        m_shapeRendererRef.end();

        super.render(delta);


        // m_shop.render();
        // m_stashChest.render();
    }
}
