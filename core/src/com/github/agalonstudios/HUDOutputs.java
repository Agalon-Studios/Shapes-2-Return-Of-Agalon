package com.github.agalonstudios;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.agalonstudios.Ability.AbilityType;
/**
 * Created by Satya Patel on 2/23/2017.
 */

public class HUDOutputs {
    public Vector2 accelerationUpdate;
    public Vector2 abilityReleasePosition;
    public Array<AbilityType> currentAbilities;
    public int currentCastAbility;

    public HUDOutputs() {
        accelerationUpdate = new Vector2(0, 0);
    }



}