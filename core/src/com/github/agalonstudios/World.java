package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    //protected QuadTree m_collisionTree;

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
    }

    @Override
    public void render(float delta) {
        update(delta);


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

        m_shapeRendererRef.end();
        HUD.render();
    }

    protected static void runCollisions(Array<? extends Entity> ... entityLists) {
        runCollisionsNSquared(entityLists);
    }

    protected static void runCollisionsNSquared(Array<? extends Entity> ... entityLists) {
        Array<Entity> allObjects = new Array<Entity>();

        for (Array<? extends Entity> entityList : entityLists) {
            for (Entity e : entityList) {
                allObjects.add(e);
            }
        }

        for (int e = 0; e < allObjects.size; e++) {
            for (int o = 0; o < allObjects.size; o++) {
                if (e == o)
                    continue;

                if (allObjects.get(e).overlaps(allObjects.get(o)))
                    allObjects.get(e).runCollision(allObjects.get(o));
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

    public void addCastObject(CastObject co) {
        m_castObjects.add(co);
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
