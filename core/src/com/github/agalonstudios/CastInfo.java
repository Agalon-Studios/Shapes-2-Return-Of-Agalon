package com.github.agalonstudios;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Satya Patel on 4/18/2017.
 */

public class CastInfo {
    public final float speed;
    public final float maxDistance;
    public final Rectangle rect;
    public final EffectArea effectArea;
    public final Stats effect;
    public final Vector2 direction;
    public final EffectArea instantEffectArea;

    public CastInfo(float speed, float maxDist, Rectangle rect, EffectArea eot, EffectArea ia, Stats effect, Vector2 dir) {
        this.speed = speed;
        this.maxDistance = maxDist;
        this.rect = rect;
        this.effectArea = eot;
        this.effect = effect;
        this.direction = dir;
        this.instantEffectArea = ia;
    }

}
