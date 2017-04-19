package com.github.agalonstudios;


import com.badlogic.gdx.Gdx;

/**
 * Created by spr on 3/20/17.
 */
public class CastObject extends Entity {
    protected Character m_casterRef;

    private boolean m_isDone;
    private CastInfo m_castInfo;
    private float m_distanceTraveled;

    public CastObject(Character casterRef, CastInfo ci) {
        super(ci.rect.x, ci.rect.y, ci.rect.width, ci.rect.height);
        m_castInfo = ci;
        m_casterRef = casterRef;
        setPosition(
                casterRef.getX() + casterRef.getWidth() / 2,
                casterRef.getY() + casterRef.getHeight() / 2
        );
    }

    @Override
    public void runCollision(Entity other) {
        if (!other.equals(m_casterRef)) {
            if (other instanceof Character) {
                m_isDone = true;
                ((Character) other).apply(m_castInfo.effect);
            }
        }
    }

    public void update(float delta) {
        World world = ((World) ((Agalon) Gdx.app.getApplicationListener()).getScreen());




        if (m_distanceTraveled >= m_castInfo.maxDistance) m_isDone = true;

        if(m_isDone) {
            if (m_castInfo.instantEffectArea != null) {
                m_castInfo.instantEffectArea.setPosition(getX(), getY());
                world.addEffectOverTime(m_castInfo.instantEffectArea);
            }

            if (m_castInfo.effectArea != null) {
                m_castInfo.effectArea.setPosition(getX(), getY());
                world.addEffectOverTime(m_castInfo.effectArea);
            }

            dispose();
        }
    }




    private void dispose() {
        //TODO delete texture or w/e
    }

    public boolean done() {
        return m_isDone;
    }
}
