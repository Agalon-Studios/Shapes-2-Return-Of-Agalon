package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/12/17.
 */
public class Player extends Character {
    private int m_gold;
    private int m_xp;
    private Sprite m_image;

    // TODO just one ability for testing, replace this with equipped abilities
    private Ability m_ability;
    private float m_cooldown;
    private float m_cooldownTimer;

    private Array<Ability> m_equippedAbilities;
    private Array<Float> m_cooldownTimers;

    // TODO inventory, equipped items, abilities, other properties

    public Player(int h, int ms, int l, String imageFP) {
        super(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2, 32, 32, h, ms, l);
        m_fixed = false;
        m_gold = 0;
        m_xp = 0;
        m_equippedAbilities = new Array<Ability>();
        m_cooldownTimers = new Array<Float>();

        m_equippedAbilities.add(new Ability(Ability.Abilities.STRIKE));
        m_equippedAbilities.add(new Ability(Ability.Abilities.SHOT));
        m_cooldownTimers.add(0.f);
        m_cooldownTimers.add(0.f);
    }

    @Override
    public void runCollision(Entity other) {
        // TODO make it so this isnt necessary
        if (other == this)
            return;

        if (other.getRect().overlaps(m_rect)) {
            revertPosition();
        }
    }

    @Override
    public void update(float delta) {
        m_revert.x = m_rect.x;
        m_revert.y = m_rect.y;

        // TODO use velocity, use HUD components
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            m_rect.y += 300 * delta;
            m_directionFacing = Direction.NORTH;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            m_rect.y -= 300 * delta;
            m_directionFacing = Direction.SOUTH;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            m_rect.x -= 300 * delta;
            m_directionFacing = Direction.WEST;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            m_rect.x += 300 * delta;
            m_directionFacing = Direction.EAST;
        }

        if (Gdx.input.isKeyPressed((Input.Keys.ESCAPE))) {
            ((Agalon) Gdx.app.getApplicationListener()).returnToOverworld();
            m_rect.x = Gdx.graphics.getWidth() / 2;
            m_rect.y = Gdx.graphics.getHeight() / 2;
        }

    }

    @Override
    public void update(float delta, World world) {
        update(delta);

        if (m_cooldownTimers.get(0) <= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                m_equippedAbilities.get(0).cast(this, world);
                m_cooldownTimers.set(0, m_equippedAbilities.get(0).getCoolDown());
            }
        }

        if (m_cooldownTimers.get(1) <= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                m_equippedAbilities.get(1).cast(this, world);
                m_cooldownTimers.set(1, m_equippedAbilities.get(0).getCoolDown());
            }
        }

        for (int i = 0; i < m_cooldownTimers.size; i++) {
            m_cooldownTimers.set(i, m_cooldownTimers.get(i) - delta);
        }

    }

    @Override
    public void render(float delta) {
        // TODO use sprite
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(69 / 255.f, 116 / 255.f, 68 / 255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(93 / 255.f, 156 / 255.f, 91 / 255.f, 1);
        sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);
    }

}
