package net.classic.remastered.client.sound;

import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class SoundPool {
    private Random rand;
    private Map nameToSoundPoolEntriesMapping;
    private int numberOfSoundPoolEntries;
    
    public SoundPool() {
        this.rand = new Random();
        this.nameToSoundPoolEntriesMapping = (Map)new HashMap();
        this.numberOfSoundPoolEntries = 0;
    }
    
    public final SoundPoolEntry addSound(String string, final File file) {
        try {
            final String string2 = string;
            for (string = string.substring(0, string.indexOf(".")); Character.isDigit(string.charAt(string.length() - 1)); string = string.substring(0, string.length() - 1)) {}
            string = string.replaceAll("/", ".");
            if (!this.nameToSoundPoolEntriesMapping.containsKey(string)) {
                this.nameToSoundPoolEntriesMapping.put(string, new ArrayList());
            }
            final SoundPoolEntry soundPoolEntry = new SoundPoolEntry(string2, file.toURI().toURL());
            ((List)this.nameToSoundPoolEntriesMapping.get(string)).add(soundPoolEntry);
            ++this.numberOfSoundPoolEntries;
            return soundPoolEntry;
        }
        catch (MalformedURLException ex) {
            final Throwable t = (Throwable)ex;
            ex.printStackTrace();
            throw new RuntimeException(t);
        }
    }
    
    public final SoundPoolEntry getRandomSoundFromSoundPool(final String string) {
        final List list;
        if ((list = (List)this.nameToSoundPoolEntriesMapping.get(string)) == null) {
            return null;
        }
        return (SoundPoolEntry)list.get(this.rand.nextInt(list.size()));
    }
}
