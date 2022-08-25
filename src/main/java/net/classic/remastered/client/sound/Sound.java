/*
 * 
 */
package net.classic.remastered.client.sound;

import net.classic.remastered.client.sound.Audio;
import net.classic.remastered.client.sound.AudioInfo;
import net.classic.remastered.client.sound.SoundPos;

public final class Sound
implements Audio {
    private AudioInfo info;
    private SoundPos pos;
    private float pitch = 0.0f;
    private float volume = 1.0f;
    private static short[] data = new short[1];

    public Sound(AudioInfo var1, SoundPos var2) {
        this.info = var1;
        this.pos = var2;
        this.pitch = var2.getRotationDiff();
        this.volume = var2.getDistanceSq() * var1.volume;
    }

    @Override
    public final boolean play(int[] var1, int[] var2, int var3) {
        int var4;
        if (data.length < var3) {
            data = new short[var3];
        }
        boolean var5 = (var4 = this.info.update(data, var3)) > 0;
        float var6 = this.pos.getRotationDiff();
        float var7 = this.pos.getDistanceSq() * this.info.volume;
        int var8 = (int)((this.pitch > 0.0f ? 1.0f - this.pitch : 1.0f) * this.volume * 65536.0f);
        int var9 = (int)((this.pitch < 0.0f ? 1.0f + this.pitch : 1.0f) * this.volume * 65536.0f);
        int var10 = (int)((var6 > 0.0f ? 1.0f - var6 : 1.0f) * var7 * 65536.0f);
        int var11 = (int)((var6 < 0.0f ? var6 + 1.0f : 1.0f) * var7 * 65536.0f);
        if ((var10 -= var8) == 0 && (var11 -= var9) == 0) {
            if (var8 >= 0 || var9 != 0) {
                int var12 = var8;
                int var13 = var9;
                for (int var14 = 0; var14 < var4; ++var14) {
                    int n = var14;
                    var1[n] = var1[n] + (data[var14] * var12 >> 16);
                    int n2 = var14;
                    var2[n2] = var2[n2] + (data[var14] * var13 >> 16);
                }
            }
        } else {
            for (int var12 = 0; var12 < var4; ++var12) {
                int var13 = var8 + var10 * var12 / var3;
                int var14 = var9 + var11 * var12 / var3;
                int n = var12;
                var1[n] = var1[n] + (data[var12] * var13 >> 16);
                int n3 = var12;
                var2[n3] = var2[n3] + (data[var12] * var14 >> 16);
            }
        }
        this.pitch = var6;
        this.volume = var7;
        return var5;
    }
}

