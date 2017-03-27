package com.github.agalonstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agalon extends Game {
	private ShapeRenderer m_shapeRenderer;
    private OrthographicCamera m_camera;
    private Overworld m_overworld;

	@Override
	public void create () {
        setScreen(new MainMenu(this));

        m_shapeRenderer = new ShapeRenderer();
        m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //m_overworld = new Overworld(new Player(100, 200, "player-image-path"));
	}


    public void returnToOverworld() {
        this.setScreen(m_overworld);
    }

    public ShapeRenderer getShapeRenderer() {
        return m_shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return m_camera;
    }
}
