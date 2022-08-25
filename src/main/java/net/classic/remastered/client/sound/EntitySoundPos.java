/*
 * 
 */
package net.classic.remastered.client.sound;

import net.classic.remastered.client.sound.BaseSoundPos;
import net.classic.remastered.game.entity.Entity;

public class EntitySoundPos
extends BaseSoundPos {
    private Entity source;

    public EntitySoundPos(Entity source, Entity listener) {
        super(listener);
        this.source = source;
    }

    @Override
    public float getRotationDiff() {
        return super.getRotationDiff(this.source.x, this.source.z);
    }

    @Override
    public float getDistanceSq() {
        return super.getDistanceSq(this.source.x, this.source.y, this.source.z);
    }
}

