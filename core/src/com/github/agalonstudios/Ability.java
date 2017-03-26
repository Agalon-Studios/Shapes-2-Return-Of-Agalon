package com.github.agalonstudios;

/**
 * Created by spr on 3/20/17.
 */
public class Ability {
    private Stats m_effect;
    private int m_castObjectAmount;
    private int m_cooldown;
    private Abilities m_ability;


    public enum Abilities {
        STRIKE, SHOT
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

    public Ability(Abilities a) {
        m_ability = a;
    }

    public Ability(Stats e) {
        // make SELF
    }

    public Ability(Stats e, Type t) {
        m_effect = e;
        m_type = t;
    }

    public void cast(Character casterRef, World worldRef) {
        // TODO generalize

        switch (m_ability) {
            case STRIKE:
                worldRef.addCastObject(new StrikeCastObject(casterRef));
                break;
            case SHOT:
               // worldRef.addCastObject(new ShotCastObject(casterRef));
                break;
        }
    }

    public float getCoolDown() {
        return m_cooldown;
    }
}
