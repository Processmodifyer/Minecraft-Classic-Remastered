/*
 * 
 */
package net.classic.remastered.client.main;

import java.io.File;

public enum NewOS {
    LINUX("linux", 0),
    SOLARIS("solaris", 1),
    WINDOWS("windows", 2){

        @Override
        public File getMinecraftFolder(String home, String folder) {
            String appData = System.getenv("APPDATA");
            if (appData != null) {
                return new File(appData, String.valueOf(folder) + '/');
            }
            return new File(home, String.valueOf(folder) + '/');
        }
    }
    ,
    MAC_OS_X("macos", 3){

        @Override
        public File getMinecraftFolder(String home, String folder) {
            return new File(home, "Library/Application Support/" + folder);
        }
    }
    ,
    UNKNOWN("unknown", 4);

    public static final NewOS[] values;
    public final String folderName;
    public final int id;

    static {
        values = new NewOS[]{LINUX, SOLARIS, WINDOWS, MAC_OS_X, UNKNOWN};
    }

    private NewOS(String folderName, int id) {
        this.folderName = folderName;
        this.id = id;
    }

    public File getMinecraftFolder(String home, String folder) {
        return new File(home, String.valueOf(folder) + '/');
    }

    public static NewOS detect() {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win")) {
            return WINDOWS;
        }
        if (s.contains("mac")) {
            return MAC_OS_X;
        }
        if (s.contains("solaris") || s.contains("sunos")) {
            return SOLARIS;
        }
        if (s.contains("linux") || s.contains("unix")) {
            return LINUX;
        }
        return UNKNOWN;
    }

    /* synthetic */ NewOS(String string, int n, String string2, int n2, NewOS newOS) {
        this(string2, n2);
    }
}

