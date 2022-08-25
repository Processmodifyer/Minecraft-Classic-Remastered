/*
 * 
 */
package net.classic.remastered.client.sound;

import net.classic.remastered.client.sound.SoundPos;
import net.classic.remastered.game.entity.Entity;
import net.dungland.util.MathHelper;

public abstract class BaseSoundPos
implements SoundPos {
    private Entity listener;

    public BaseSoundPos(Entity listener) {
        this.listener = listener;
    }

    public float getRotationDiff(float x, float y) {
        float f = 0;
        float var3 = MathHelper.sqrt((x -= this.listener.x) * x + (y -= this.listener.z) * y);
        x /= var3;
        y /= var3;
        var3 /= 2.0f;
        if (f > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = MathHelper.cos(-this.listener.yRot * ((float)Math.PI / 180) + (float)Math.PI);
        return (MathHelper.sin(-this.listener.yRot * ((float)Math.PI / 180) + (float)Math.PI) * y - var4 * x) * var3;
    }

    public float getDistanceSq(float x, float y, float z) {
        float f = 0;
        float var4 = z - this.listener.z;
        var4 = MathHelper.sqrt((x -= this.listener.x) * x + (y -= this.listener.y) * y + var4 * var4);
        var4 = 1.0f - var4 / 32.0f;
        if (f < 0.0f) {
            var4 = 0.0f;
        }
        return var4;
    }
}

