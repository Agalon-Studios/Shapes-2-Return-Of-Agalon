package com.github.agalonstudios;

/**
 * Created by spr on 3/12/17.
 */
public class Stats {
    public final int m_meleeDamage;
    public final int m_rangeDamage;
    public final int m_mageDamage;
    public final float m_speedChange;
    public final float m_defenseChange;
    public final float m_damageChange;
    public final float m_knockback;

    public Stats(int md, int rd, int gd, float sc, float dx, float mc, float kb) {
        m_meleeDamage = md;
        m_rangeDamage = rd;
        m_mageDamage = gd;
        m_speedChange = sc;
        m_defenseChange = dx;
        m_damageChange = mc;
        m_knockback = kb;
    }

}
