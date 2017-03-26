package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;

/**
 * Created by spr on 3/13/17.
 */
public class Enemy extends Character {
    private DungeonRoom m_roomRef;
    private EnemySpawnPoint m_spawnPointRef;

    public Enemy(int x, int y, int w, int h, int mh, int ms, int l, EnemySpawnPoint sp, String ip) {
        super(x, y, w, h, mh, ms, l);
        m_spawnPointRef = sp;
    }

    @Override
    public void update(float delta) {
        if (m_health <= 0) {
            m_spawnPointRef.decrementLivingCount();
        }
    }

    @Override
    public void render(float delta) {

    }
}
