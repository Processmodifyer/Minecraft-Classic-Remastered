/*
 * 
 */
package net.classic.remastered.client.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.SourceDataLine;

import net.classic.remastered.client.settings.GameSettings;
import net.classic.remastered.client.sound.Audio;
import net.classic.remastered.client.sound.AudioInfo;
import net.classic.remastered.client.sound.Sound;
import net.classic.remastered.client.sound.SoundPos;

public final class SoundPlayer
implements Runnable {
    public boolean running = false;
    public SourceDataLine dataLine;
    private List audioQueue = new ArrayList();
    public GameSettings settings;

    public SoundPlayer(GameSettings var1) {
        this.settings = var1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void play(Audio var1) {
        if (this.running) {
            List var2 = this.audioQueue;
            List list = this.audioQueue;
            synchronized (list) {
                this.audioQueue.add(var1);
            }
        }
    }

    public final void play(AudioInfo var1, SoundPos var2) {
        this.play(new Sound(var1, var2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void run() {
        int[] var1 = new int[4410];
        int[] var2 = new int[4410];
        byte[] var3 = new byte[17640];
        while (this.running) {
            int var13;
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException var10) {
                var10.printStackTrace();
            }
            Arrays.fill(var1, 0, 4410, 0);
            Arrays.fill(var2, 0, 4410, 0);
            boolean var4 = true;
            int[] var5 = var2;
            int[] var6 = var1;
            List var12 = this.audioQueue;
            List var7 = this.audioQueue;
            List list = this.audioQueue;
            synchronized (list) {
                for (int var8 = 0; var8 < var12.size(); ++var8) {
                    if (((Audio)var12.get(var8)).play(var6, var5, 4410)) continue;
                    var12.remove(var8--);
                }
            }
            if (!this.settings.music && !this.settings.sound) {
                for (var13 = 0; var13 < 4410; ++var13) {
                    var3[var13 << 2] = 0;
                    var3[(var13 << 2) + 1] = 0;
                    var3[(var13 << 2) + 2] = 0;
                    var3[(var13 << 2) + 3] = 0;
                }
            } else {
                for (var13 = 0; var13 < 4410; ++var13) {
                    int var15 = var1[var13];
                    int var14 = var2[var13];
                    if (var15 < -32000) {
                        var15 = -32000;
                    }
                    if (var14 < -32000) {
                        var14 = -32000;
                    }
                    if (var15 >= 32000) {
                        var15 = 32000;
                    }
                    if (var14 >= 32000) {
                        var14 = 32000;
                    }
                    var3[var13 << 2] = (byte)(var15 >> 8);
                    var3[(var13 << 2) + 1] = (byte)var15;
                    var3[(var13 << 2) + 2] = (byte)(var14 >> 8);
                    var3[(var13 << 2) + 3] = (byte)var14;
                }
            }
            this.dataLine.write(var3, 0, 17640);
        }
    }
}

