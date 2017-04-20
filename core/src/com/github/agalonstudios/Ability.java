package com.github.agalonstudios;


import com.badlogic.gdx.Gdx;

import static com.github.agalonstudios.Ability.AbilityType.CLEAVE;
import static com.github.agalonstudios.Ability.AbilityType.CLEAVE;
import static com.github.agalonstudios.Ability.AbilityType.ICE_ARROW;

/**
 * Created by spr on 3/20/17.
 */
public class Ability {
    private Stats m_effect;
    private EffectArea m_effectArea;
    private EffectArea m_initialEffect;
    private float m_maxCastDistance;
    private float m_cooldown;
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
                m_maxCastDistance = 400;
                m_effectArea = new EffectArea(new Stats(1, 0, 0, 0, 0, 0, 0, 0), 5, 2, 300, a);
                m_effect = null;
                m_initialEffect = new EffectArea(new Stats(10, 0, 0, 0, 0, 0, 50, 0), 0, 0, 300, a);
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

    public void cast(Player casterRef, HUDOutputs ho) {
        // TODO generalize

        World worldRef = (World) ((Agalon) Gdx.app.getApplicationListener()).getScreen();

        switch (m_type) {
            case DROP_AREA_OF_EFFECT:
               // m_effectArea.setPosition(ho.abilityReleasePosition.x, ho.abilityReleasePosition.y);
               // m_initialEffect.setPosition(ho.abilityReleasePosition.x, ho.abilityReleasePosition.y);
                worldRef.addEffectOverTime(m_effectArea);
                worldRef.addEffectOverTime(m_initialEffect);
                break;

        }
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

    public AbilityType getAbilityType() {
        return m_ability;
    }

    public void cast(Character casterRef) {

    }

    public float getCoolDown() {
        return m_cooldown;
    }
}
