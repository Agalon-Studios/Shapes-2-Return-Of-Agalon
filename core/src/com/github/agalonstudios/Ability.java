package com.github.agalonstudios;

/**
 * Created by spr on 3/20/17.
 */
public class Ability {
    private Stats m_effect;
    private int m_castObjectAmount;
    private int m_cooldown;


    public enum Type {
        SELF, // self heal, self buff
        SELF_AREA_OF_EFFECT, // put down a bubble at your loc
        PROJECTILE, // shoot things
        PROJECTILE_AREA_OF_EFFECT, // shoot things that put down an aoe on impact
        DROP_AREA_OF_EFFECT, // drop an aoe
        SUMMON // might not need this, this might go in self?
    }

    private Type m_type;

    public Ability(Stats e) {
        // make SELF
    }


    public Ability(Stats e, Type t) {
        m_effect = e;
        m_type = t;
    }

    public void cast(Character casterRef, World worldRef) {
        // TODO generalize
        worldRef.addCastObject(new StrikeCastObject(casterRef));
    }
}
