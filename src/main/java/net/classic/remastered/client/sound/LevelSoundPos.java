/*
 * 
 */
package net.classic.remastered.client.sound;

import net.classic.remastered.client.sound.BaseSoundPos;
import net.classic.remastered.game.entity.Entity;

public final class LevelSoundPos
extends BaseSoundPos {
    private float x;
    private float y;
    private float z;

    public LevelSoundPos(float x, float y, float z, Entity listener) {
        super(listener);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getRotationDiff() {
        return super.getRotationDiff(this.x, this.z);
    }

    @Override
    public float getDistanceSq() {
        return super.getDistanceSq(this.x, this.y, this.z);
    }
}

