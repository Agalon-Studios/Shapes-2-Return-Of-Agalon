package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/12/17.
 */
public class World implements Screen {
    protected Player m_playerRef;
    protected Array<Wall> m_walls;
    protected OrthographicCamera m_cameraRef;
    protected ShapeRenderer m_shapeRendererRef;

    public World(Player pRef) {
        m_playerRef = pRef;
        m_cameraRef = ((Agalon) Gdx.app.getApplicationListener()).getCamera();
        m_shapeRendererRef = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        m_walls = new Array<Wall>();
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
            m_playerRef.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        m_cameraRef.position.set(m_playerRef.getRect().x - Gdx.graphics.getWidth() / 2, m_playerRef.getRect().y - Gdx.graphics.getHeight() / 2, 0);

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);
        m_shapeRendererRef.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        for (Wall wall : m_walls) {
            if (wall.getRect().x + wall.getRect().width >= m_cameraRef.position.x &&
                    wall.getRect().x < m_cameraRef.position.x + Gdx.graphics.getWidth() &&
                    wall.getRect().y + wall.getRect().height >= m_cameraRef.position.y &&
                    wall.getRect().y < m_cameraRef.position.y + Gdx.graphics.getHeight())
                wall.render(delta);
        }

        m_playerRef.render(delta);
        m_shapeRendererRef.end();
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
