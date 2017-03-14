package com.github.agalonstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agalon extends Game {
	private ShapeRenderer m_shapeRenderer;
    private OrthographicCamera m_camera;
	
	@Override
	public void create () {
        m_shapeRenderer = new ShapeRenderer();
        m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // TODO note: A Dungeon is currently the highlest level, so the player is made here.
        // TODO       Later, the player will be made at the highest level, i.e. the Overworld
        // TODO       or something, since that will have multiple dungeons, and we only want
        // TODO       to have one player throughout the entire thing.
        // TODO       (this is also true for the shape renderer and the camera, probably.
        Dungeon d = new Dungeon(8, Theme.CAVE, new Player(100, 200, 13, "player-image-path"));
        // TODO remove
        d.printPlanGrid();
        this.setScreen(d.currentRoom());
	}

    public ShapeRenderer getShapeRenderer() {
        return m_shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return m_camera;
    }
}
