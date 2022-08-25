package net.classic.remastered.client.main;



public enum MinecraftOS {
        linux("linux", 0),
        solaris("solaris", 1),
        windows("windows", 2),
        macos("macos", 3),
        unknown("unknown", 4),
        netframework("netframework", 5);

        private static final MinecraftOS[] values;

        static {
            values = new MinecraftOS[]{linux, solaris, windows, macos, netframework, unknown};
        }

        private MinecraftOS(String name, int id) {
        }
    }