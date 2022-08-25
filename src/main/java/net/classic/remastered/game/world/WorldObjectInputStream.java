package net.classic.remastered.game.world;

import java.util.*;
import java.io.*;

public final class WorldObjectInputStream extends ObjectInputStream
{
    private Set<String> classes;
    
    public WorldObjectInputStream(final InputStream var1) throws IOException {
        super(var1);
        (this.classes = new HashSet<String>()).add("Player$1");
        this.classes.add("Creeper$1");
        this.classes.add("Skeleton$1");
    }
    
    @Override
    protected final ObjectStreamClass readClassDescriptor() {
        try {
            final ObjectStreamClass var1 = super.readClassDescriptor();
            return this.classes.contains(var1.getName()) ? ObjectStreamClass.lookup(Class.forName(var1.getName())) : var1;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
