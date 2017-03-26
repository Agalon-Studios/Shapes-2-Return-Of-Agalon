package com.github.agalonstudios;

/**
 * Created by spr on 3/20/17.
 */
public class CastObject extends Entity {
    protected Character m_casterRef;
    protected Stats m_effect;

    public CastObject(Character casterRef, int x, int y, int w, int h) {
        super(x, y, w, h);
        m_casterRef = casterRef;
    }

    @Override
    public void runCollision(Entity other) {
        // apply to other, destroy self if needed, etc
    }

    public void update(float delta, World world) {
        ;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {

    }

    public boolean done() {
        return false;
    }
}
