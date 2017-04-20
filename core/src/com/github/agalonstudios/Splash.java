package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by spr on 2/6/17.
 */
public class Splash implements Screen {
    Texture m_logo;
    Texture m_background;
    final Agalon game;
    float timer;

    public Splash(final Agalon g) {
        m_logo = new Texture(Gdx.files.internal("logo.png"));
        m_background = new Texture(Gdx.files.internal("splashbkgd.png"));

        game = g;
        timer = 3.0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(59/255f, 201/255f, 232/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(m_background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().draw(m_logo, Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() / 2 - Gdx.graphics.getWidth() / 6,
                Gdx.graphics.getWidth() / 3, Gdx.graphics.getWidth() / 3);


        game.getBatch().end();

        update();
    }

    void update() {
        timer -= Gdx.graphics.getDeltaTime();
        if (timer <= 0) {
            game.setScreen(new MainMenu(game));
        }
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
        m_logo.dispose();
    }
}
