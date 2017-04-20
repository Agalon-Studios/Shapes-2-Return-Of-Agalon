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
    // a method to use with Item.getInfo
    public String weaponStatsInfo(){
        String info = "";
        info += "Damage mod: " + String.format("%.2f", damageChange) + ", ";
        info += "Speed mod: " + String.format("%.2f", speedChange) + "\n";
        info += "Knockback mod: " + String.format("%.2f", knockback) + ", ";
        info += "Defense mod: " + String.format("%.2f", defenseChange);
        return info;
    }

    public String consumeStatsInfo(){
        String info = "";
        info += "Duration: " + String.format("%.2f", duration);
        return info;
    }

    public float getSpeedChange(){return speedChange;}
    public float getDefenseChange() {return defenseChange;}
    public float getKnockback() {return knockback;}
    public float getDamageChange() { return damageChange;}
}
