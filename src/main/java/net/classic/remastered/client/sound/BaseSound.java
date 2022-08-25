/*
 * 
 */
package net.classic.remastered.client.sound;

import net.classic.remastered.client.sound.ALSoundLoader;

public class BaseSound {
    private ALSoundLoader store;
    private int buffer;

    BaseSound(ALSoundLoader store, int buffer) {
        this.store = store;
        this.buffer = buffer;
    }

    public void play(float pitch, float gain) {
        this.store.playAsSound(this.buffer, pitch, gain);
    }
}

