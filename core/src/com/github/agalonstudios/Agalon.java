package com.github.agalonstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agalon extends Game {
	private ShapeRenderer m_shapeRenderer;
    private OrthographicCamera m_camera;
    private Overworld m_overworld;
    private Music m_overworldMusic;
    private Music m_dungeonMusic;
    private boolean m_whichMusic;

	@Override
	public void create () {

        setMainMusic(true);

        m_shapeRenderer = new ShapeRenderer();
        m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        m_overworld = new Overworld(new Player(100, 200, "player-image-path"));

        this.setScreen(new MainMenu(this));
	}

    public void returnToOverworld() {
        this.setScreen(m_overworld);
        Gdx.input.setInputProcessor(HUD.getStage());
    }

    public ShapeRenderer getShapeRenderer() {
        return m_shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return m_camera;
    }

    public Music getMusic() {
        if(m_whichMusic)
            return m_overworldMusic;
        else
            return m_dungeonMusic;
    }


    public void switchPlaying() {
        if(m_whichMusic)
            m_whichMusic = false;
        else
            m_whichMusic = true;
    }

    public void setMainMusic(boolean willPlay) {
        m_overworldMusic = Gdx.audio.newMusic(Gdx.files.internal("Tbone and friends.wav"));
        if(willPlay) {
            m_overworldMusic.play();
            m_overworldMusic.setLooping(true);
        }
        m_whichMusic = true;
    }

    public void setDungeonMusic(boolean willPlay) {
        m_dungeonMusic = Gdx.audio.newMusic(Gdx.files.internal("Mystic Africa Loop.wav"));
        if(willPlay) {
            m_dungeonMusic.play();
            m_dungeonMusic.setLooping(true);
        }
        m_whichMusic = false;
    }

    public void turnOffMusic() {
        if (m_whichMusic)
            m_overworldMusic.pause();
        else
            m_dungeonMusic.pause();
    }

    public void turnOnMusic() {
        if (m_whichMusic)
            m_overworldMusic.play();
        else
            m_dungeonMusic.play();
    }
}
