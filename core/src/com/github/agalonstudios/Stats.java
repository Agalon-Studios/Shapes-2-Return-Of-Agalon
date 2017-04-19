package com.github.agalonstudios;

/**
 * Created by spr on 3/12/17.
 */
public class Stats {
    public final int meleeDamage;
    public final int rangeDamage;
    public final int mageDamage;
    public final float speedChange;
    public final float defenseChange;
    public final float damageChange;
    public final float knockback;
    public final float duration;

    public Stats(int md, int rd, int gd, float sc, float dx, float mc, float kb, float dur) {
        meleeDamage = md;
        rangeDamage = rd;
        mageDamage = gd;
        speedChange = sc;
        defenseChange = dx;
        damageChange = mc;
        knockback = kb;
        duration = dur;
    }

}
