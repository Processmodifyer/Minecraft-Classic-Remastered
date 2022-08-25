/*
 * 
 */
package net.classic.remastered.client.main;

public final class OperatingSystemLookup {
    public static final int[] lookup = new int[MinecraftOS.values().length];

    static {
        try {
            OperatingSystemLookup.lookup[MinecraftOS.linux.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OperatingSystemLookup.lookup[MinecraftOS.solaris.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OperatingSystemLookup.lookup[MinecraftOS.windows.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OperatingSystemLookup.lookup[MinecraftOS.macos.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }

    OperatingSystemLookup() {
    }
}

