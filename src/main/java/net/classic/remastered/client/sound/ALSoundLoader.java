/*
 * 
 */
package net.classic.remastered.client.sound;


import net.classic.remastered.client.sound.BaseSound;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

public class ALSoundLoader {
    private static final ALSoundLoader store = new ALSoundLoader();
    private boolean sounds;
    private boolean soundWorks;
    private int sourceCount;
    private HashMap loaded = new HashMap();
    private IntBuffer sources;
    private int nextSource;
    private boolean inited = false;

    private ALSoundLoader() {
    }

    public void setSoundsOn(boolean sounds) {
        if (this.soundWorks) {
            this.sounds = sounds;
        }
    }

    public boolean soundsOn() {
        return this.sounds;
    }

    public void init() {
        this.inited = true;
        try {
            AL.create();
            this.soundWorks = true;
            this.sounds = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.soundWorks = false;
            this.sounds = false;
        }
        if (this.soundWorks) {
            this.sourceCount = 8;
            this.sources = BufferUtils.createIntBuffer(8);
            AL10.alGenSources(this.sources);
            if (AL10.alGetError() != 0) {
                this.sounds = false;
                this.soundWorks = false;
            }
        }
    }

    void playAsSound(int buffer, float pitch, float gain) {
        if (this.soundWorks && this.sounds) {
            ++this.nextSource;
            if (this.nextSource > 7) {
                this.nextSource = 1;
            }
            AL10.alSourceStop(this.sources.get(this.nextSource));
            AL10.alSourcei(this.sources.get(this.nextSource), 4105, buffer);
            AL10.alSourcef(this.sources.get(this.nextSource), 4099, pitch);
            AL10.alSourcef(this.sources.get(this.nextSource), 4106, gain);
            AL10.alSourcePlay(this.sources.get(this.nextSource));
        }
    }

    public BaseSound getOgg(String ref) throws IOException {
        if (!this.soundWorks) {
            return new BaseSound(this, 0);
        }
        if (!this.inited) {
            throw new RuntimeException("Can't load sounds until SoundLoader is init()");
        }
        int buffer = -1;
        if (this.loaded.get(ref) != null) {
            buffer = (Integer)this.loaded.get(ref);
        } else {
            System.out.println("Loading: " + ref);
            try {
                IntBuffer buf = BufferUtils.createIntBuffer(1);
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ref);
                AL10.alGenBuffers(buf);
                this.loaded.put(ref, new Integer(buf.get(0)));
                buffer = buf.get(0);
            }
            catch (Exception e) {
                e.printStackTrace();
                Sys.alert("Error", "Failed to load: " + ref + " - " + e.getMessage());
                System.exit(0);
            }
        }
        if (buffer == -1) {
            throw new IOException("Unable to load: " + ref);
        }
        return new BaseSound(this, buffer);
    }

    public static ALSoundLoader get() {
        return store;
    }
}

