package net.classic.remastered.client.sound;

import java.net.URL;

public final class SoundPoolEntry {
    public String soundName;
    public URL soundUrl;
    
    public SoundPoolEntry(final String string, final URL url) {
        this.soundName = string;
        this.soundUrl = url;
    }
}
