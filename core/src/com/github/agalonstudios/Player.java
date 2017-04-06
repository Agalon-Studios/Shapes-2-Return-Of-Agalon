package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
    private float m_stats;
    private Array<Ability> m_equippedAbilities;


    // TODO inventory, equipped items, abilities, other properties

    public Player(int h, int l, Color c) {
        super(
                32,
                new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
                Shape.SQUARE,
                h,
                150,
                800,
                l
        );

        m_fixed = false;
        m_gold = 0;
        m_xp = 0;
        m_equippedAbilities = new Array<Ability>();

        m_color = c;

        m_equippedAbilities.add(new Ability(Ability.Abilities.STRIKE));
        m_equippedAbilities.add(new Ability(Ability.Abilities.SHOT));
        m_cooldownTimers.add(0.f);
        m_cooldownTimers.add(0.f);


        // TODO add constructor for stats
    }

    @Override
    public void runCollision(Entity other) {
        // TODO make it so this isnt necessary
        if (other == this)
            return;

        //TODO
    }


    @Override
    public void update(float delta, World world) {
        super.update(delta, world);


        m_acceleration.x = HUD.hudOutputs.accelerationUpdate.x;
        m_acceleration.y = HUD.hudOutputs.accelerationUpdate.y;

        // TODO use velocity, use HUD components
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            m_shape.translate(0, 1 * delta);
            m_directionFacing = Direction.NORTH;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            m_shape.translate(0, -1 * delta);
            m_directionFacing = Direction.SOUTH;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            m_shape.translate(-1 * delta, 0);
            m_directionFacing = Direction.WEST;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            m_shape.translate(1 * delta, 0);
            m_directionFacing = Direction.EAST;
        }

        if (Gdx.input.isKeyPressed((Input.Keys.ESCAPE))) {
            ((Agalon) Gdx.app.getApplicationListener()).returnToOverworld();
            m_shape.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        }

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


    }



    @Override
    public void render() {
       super.render();
        //TODO fix this one if needed
    }

}
