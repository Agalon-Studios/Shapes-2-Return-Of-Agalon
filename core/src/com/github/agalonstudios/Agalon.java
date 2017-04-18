package com.github.agalonstudios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Agalon extends Game {
	private ExtendedShapeRenderer m_shapeRenderer;
    private OrthographicCamera m_camera;
    private Overworld m_overworld;
    private Music m_overworldMusic;
    private Music m_dungeonMusic;
    private boolean m_whichMusic;
    private Player m_player;
    private World m_currentWorld;

	@Override
    public void create () {
        loadPlayer();

        setMainMusic(true);

        ScreenScale.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        m_shapeRenderer = new ExtendedShapeRenderer();

        m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.setScreen(new MainMenu(this));
        //this.setScreen(m_overworld);
    }

    private void loadPlayer() {
        // if savedata load from save data
    }

    public void changeToWorld(World w) {
        m_currentWorld = w;
        this.setScreen(w);
        Gdx.input.setInputProcessor(HUD.getStage());
    }

    public void backToWorld() {
        this.setScreen(m_currentWorld);
        Gdx.input.setInputProcessor(HUD.getStage());
    }

    public void returnToOverworld() {
        this.setScreen(m_overworld);
        m_currentWorld = m_overworld;
        this.turnOffMusic();
        this.setMainMusic(true);
        Gdx.input.setInputProcessor(HUD.getStage());
    }

    public void createOverworld(Player player) {
        m_player = player;
        m_overworld = new Overworld(player);
        m_currentWorld = m_overworld;
    }

    public ExtendedShapeRenderer getShapeRenderer() {
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

    public boolean getOverworldType(){
        if(m_currentWorld instanceof Overworld)
            return true;
        else
            return false;
    }

}