package com.github.agalonstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agalon extends Game {
	private ExtendedShapeRenderer m_shapeRenderer;
    private OrthographicCamera m_camera;
    private Overworld m_overworld;

	@Override
	public void create () {
        m_shapeRenderer = new ExtendedShapeRenderer();
        m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        m_overworld = new Overworld(new Player(100, 200, "player-image-path"));

        this.setScreen(m_overworld);
	}

    public void returnToOverworld() {
        this.setScreen(m_overworld);
    }

    public ExtendedShapeRenderer getShapeRenderer() {
        return m_shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return m_camera;
    }
}
