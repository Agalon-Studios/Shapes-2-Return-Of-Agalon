package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spr on 3/12/17.
 */
public abstract class World implements Screen {
    protected Player m_playerRef;
    protected Array<Wall> m_walls;
    protected OrthographicCamera m_cameraRef;
    protected ShapeRenderer m_shapeRendererRef;
    protected Array<Character> m_nonPlayerCharacters;
    protected Array<Player> m_playerRefArray;
    protected Array<CastObject> m_castObjects;
    protected Array<EffectArea> m_effectsOverTime;
    protected Array<DroppedItem> m_dItems;


    private static Array<Entity> refList = new Array<Entity>();
    private static List<Entity> returnObjects = new ArrayList<Entity>();

    // TODO come up with a standard size that works with everything we need it to.
    protected static final int MAX_WORLD_WIDTH  = 10000;
    protected static final int MAX_WORLD_HEIGHT = 10000;

    public World(Player pRef) {
        m_playerRef = pRef;
        m_playerRefArray = new Array<Player>();
        m_playerRefArray.add(pRef);
        m_cameraRef = ((Agalon) Gdx.app.getApplicationListener()).getCamera();
        m_shapeRendererRef = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        m_walls = new Array<Wall>();
        m_castObjects = new Array<CastObject>();
        m_effectsOverTime = new Array<EffectArea>();
        m_dItems = new Array<DroppedItem>();
        m_nonPlayerCharacters = new Array<Character>();

    }

    public void spawnNPC(Character character) {
        m_nonPlayerCharacters.add(character);
    }

    @Override
    public void show() {

    }

    protected void update(float delta) {
        HUD.update(delta, m_playerRef);

        m_playerRef.update(delta, this);

        for (int i = 0; i < m_effectsOverTime.size; i++) {
           m_effectsOverTime.get(i).update(delta);
            if (m_effectsOverTime.get(i).done())
                m_effectsOverTime.removeIndex(i--);
        }

        for (int i = 0; i < m_castObjects.size; i++) {
            m_castObjects.get(i).update(delta);
            if (m_castObjects.get(i).done())
                m_castObjects.removeIndex(i--);
        }

        for (int i = 0; i < m_nonPlayerCharacters.size; i++) {
            m_nonPlayerCharacters.get(i).update(delta, this);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer().begin();
        for (CastObject co : m_castObjects) {
            co.render();
        }
        ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer().end();

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);
        m_shapeRendererRef.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        for (Wall wall : m_walls) {
            if (wall.getX() + wall.getWidth() >= m_cameraRef.position.x &&
                    wall.getX() < m_cameraRef.position.x + Gdx.graphics.getWidth() &&
                    wall.getY() + wall.getHeight() >= m_cameraRef.position.y &&
                    wall.getY() < m_cameraRef.position.y + Gdx.graphics.getHeight())
                wall.render();
        }

        m_playerRef.render();



        for (EffectArea e : m_effectsOverTime) {
            e.render();
        }

        for (DroppedItem di : m_dItems) {
            di.render();
        }

        for (Character c : m_nonPlayerCharacters) {
            c.render();
        }

        m_shapeRendererRef.end();
        HUD.render(m_playerRef);
    }

    protected static void runCollisions(Array<? extends Entity> ... entityLists) {
        runCollisionsNSquared(entityLists);
    }

    protected static void runCollisionsNSquared(Array<? extends Entity> ... entityLists) {
        for (Array<? extends Entity> entityList : entityLists) {

            for (Array<? extends Entity> entityList2 : entityLists) {
                    for (int e = 0; e < entityList.size; e++) {
                        for (int o = 0; o < entityList2.size; o++) {
                            if (e == o && entityList.equals(entityList2)) continue;
                            Entity e1 = entityList.get(e);
                            Entity e2 = entityList2.get(o);
                            if (e1.isFixed() && e2.isFixed()) continue;
                            if (e1.overlaps(e2)) {
                                if (e1 instanceof EffectArea) System.out.println("woo");
                                e1.runCollision(e2);
                            }
                        }
                    }
                }
        }
    }

//    protected static void runColliionsQuadTree(Array<? extends Entity> ... entityLists) {
//        QuadTree collisionTree = new QuadTree(0, new Rectangle(0 - MAX_WORLD_WIDTH / 2 , 0 - MAX_WORLD_HEIGHT / 2,
//                MAX_WORLD_WIDTH / 2, MAX_WORLD_HEIGHT / 2));
//
//        refList.clear();
//        collisionTree.clear();
//        returnObjects.clear();
//
//        for (Array<? extends Entity> entityList : entityLists) {
//            for (Entity e : entityList) {
//                collisionTree.insert(e);
//                refList.add(e);
//            }
//        }
//
//        for (Entity e : refList) {
//            if (e.isFixed())
//                continue;
//
//            returnObjects.clear();
//            collisionTree.retrieve(returnObjects, e);
//
//            for (Entity object : returnObjects) {
//                e.runCollision(object);
//            }
//        }
//    }

    public void removeItem(DroppedItem dItem)
    {
        m_dItems.removeValue(dItem, true);
    }

    public void addItem(Item i, Vector2 position) {
        m_dItems.add(new DroppedItem(i, position));
    }

    public void addCastObject(CastObject co) {
        m_castObjects.add(co);
    }

    public void addEffectOverTime(EffectArea eot) {
        m_effectsOverTime.add(eot);
    }

    public void addCastObjects(Array<CastObject> cos) {
        m_castObjects.addAll(cos);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
