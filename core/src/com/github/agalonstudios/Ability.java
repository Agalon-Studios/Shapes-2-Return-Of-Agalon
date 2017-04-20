package com.github.agalonstudios;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by spr on 3/20/17.
 */
public class Ability {
    private Stats m_effect;
    private EffectArea m_effectArea;
    private EffectArea m_initialEffect;
    private float m_maxCastDistance;
    private float m_cooldown;
    private int m_staminaCost;
    private AbilityType m_ability;

    public enum AbilityType {
        STRIKE, CLEAVE, SNIPE, FLAME_BURST, HEAL, ICE_ARROW
    }

    public enum Type {
        SELF, // self heal, self buff
        SELF_AREA_OF_EFFECT, // put down a bubble at your loc
        PROJECTILE, // shoot things
        PROJECTILE_AREA_OF_EFFECT, // shoot things that put down an aoe on impact
        DROP_AREA_OF_EFFECT, // drop an aoe
        SUMMON // might not need this, this might go in self?
    }


    private Type m_type;

    public Ability(AbilityType a) {
        m_ability = a;
        switch (m_ability) {
            case CLEAVE:
                m_type = Type.SELF_AREA_OF_EFFECT;
                break;
            case SNIPE:
                m_type = Type.PROJECTILE;
                break;
            case FLAME_BURST:
                m_type = Type.DROP_AREA_OF_EFFECT;
                m_cooldown = 5f;
                m_maxCastDistance = 800;
                m_effectArea = new EffectArea(new Stats(1, 0, 0, 0, 0, 0, 0, 0), 5 , 2, new Vector2(), 300, a);
                m_effect = null;
                m_initialEffect = new EffectArea(new Stats(10, 0, 0, 0, 0, 0, 50, 0), 0, 0, new Vector2(), 300, a);
                m_staminaCost = 30;

                break;
            case HEAL:
                m_type = Type.SELF;
                break;
            case ICE_ARROW:
                m_type = Type.PROJECTILE_AREA_OF_EFFECT;
                break;
            case STRIKE:
                m_type = Type.SELF_AREA_OF_EFFECT;
                break;
        }
    }

    public Ability(Stats e, Type t) {
        m_effect = e;
        m_type = t;
    }

    public void cast(Player casterRef, Vector2 abilityVector) {
        // TODO generalize

        World worldRef = (World) ((Agalon) Gdx.app.getApplicationListener()).getScreen();

        switch (m_type) {
            case DROP_AREA_OF_EFFECT:
                abilityVector.x = casterRef.getCentroidX() + getRange() * abilityVector.x;
                abilityVector.y = casterRef.getCentroidY() + getRange() * abilityVector.y;

                EffectArea copyEffectArea = copyEffectArea(abilityVector);
                EffectArea copyInitialEffect = copyInitialEffect(abilityVector);

                copyEffectArea.setPosition(abilityVector.x, abilityVector.y);
                copyInitialEffect.setPosition(abilityVector.x, abilityVector.y);
                worldRef.addEffectOverTime(copyEffectArea);
                worldRef.addEffectOverTime(copyInitialEffect);
                break;
        }
    }

    private EffectArea copyInitialEffect(Vector2 abilityVector) {
        return new EffectArea(m_initialEffect.getStats(), m_initialEffect.getCount(),
                m_initialEffect.getDuration(), abilityVector, m_initialEffect.getRadius(), getAbilityType());
    }

    private EffectArea copyEffectArea(Vector2 abilityVector) {
        return new EffectArea(m_effectArea.getStats(), m_effectArea.getCount(),
                m_effectArea.getDuration(), abilityVector, m_effectArea.getRadius(), getAbilityType());
    }

    public Type getType() {
        return  m_type;
    }

    public float getRange() {
        return m_maxCastDistance;
    }

    public float getAreaofEffect() {
        if (m_initialEffect != null) return m_initialEffect.getRadius();
        return m_effectArea.getRadius();
    }

    public int getStaminaCost() {
        return m_staminaCost;
    }
    public AbilityType getAbilityType() {
        return m_ability;
    }

    public void cast(Character casterRef) {

    }

    public float getCoolDown() {
        return m_cooldown;
    }
}
