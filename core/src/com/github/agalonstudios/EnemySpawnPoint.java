package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by spr on 3/14/17.
 */
public class EnemySpawnPoint extends Entity {
    private int m_enemiesAlive;
    private int m_enemiesToSpawn;
    private boolean m_spawned;
    private EnemyType m_enemyType;

    public EnemySpawnPoint(EnemySpawnPointItem item, int x, int y) {
        super(x, y, item.getRadius() * 2, item.getRadius() * 2);
        m_enemiesAlive = 0;
        m_enemiesToSpawn = item.getCount();
        m_enemyType = item.getEnemyType();
        m_spawned = false;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(195 / 255.f, 197 / 255.f, 50 / 255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(243 / 255.f, 245 / 255.f, 46 / 255.f, 1);
        sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);
    }

    public void spawnEnemies(World world) {
        if (m_spawned)
            return;

        switch (m_enemyType) {
            case ENEMY_1:
                for (int i = 0; i < m_enemiesToSpawn; i++)
                    world.spawnNPC(new Enemy1(m_rect.x + MathUtils.random(-20, 20), m_rect.y + MathUtils.random(-20, 20), 1, this));
                break;
            case ENEMY_2:
                for (int i = 0; i < m_enemiesToSpawn; i++)
                    world.spawnNPC(new Enemy2(m_rect.x + MathUtils.random(-20, 20), m_rect.y + MathUtils.random(-20, 20), 1, this));
                break;
        }
        m_spawned = true;

    }

    public int getLiveCount() {
        return m_enemiesAlive;
    }

    public boolean allEnemiesKilled() {
        return m_enemiesAlive == 0;
    }

    public void decrementLivingCount() {
        m_enemiesAlive--;
    }
}