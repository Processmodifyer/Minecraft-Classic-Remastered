/*
 * 
 */
package net.classic.remastered.network;

public class PacketType {
    public static final PacketType[] packets = new PacketType[256];
    public static final PacketType IDENTIFICATION = new PacketType(Byte.TYPE, String.class, String.class, Byte.TYPE);
    public static final PacketType LEVEL_INIT;
    public static final PacketType LEVEL_DATA;
    public static final PacketType LEVEL_FINALIZE;
    public static final PacketType PLAYER_SET_BLOCK;
    public static final PacketType BLOCK_CHANGE;
    public static final PacketType SPAWN_PLAYER;
    public static final PacketType POSITION_ROTATION;
    public static final PacketType POSITION_ROTATION_UPDATE;
    public static final PacketType POSITION_UPDATE;
    public static final PacketType ROTATION_UPDATE;
    public static final PacketType DESPAWN_PLAYER;
    public static final PacketType CHAT_MESSAGE;
    public static final PacketType DISCONNECT;
    public static final PacketType UPDATE_PLAYER_TYPE;
    public int length;
    private static int nextOpcode;
    public byte opcode = (byte)nextOpcode++;
    public Class[] params;

    static {
        new PacketType(new Class[0]);
        LEVEL_INIT = new PacketType(new Class[0]);
        LEVEL_DATA = new PacketType(Short.TYPE, byte[].class, Byte.TYPE);
        LEVEL_FINALIZE = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE);
        PLAYER_SET_BLOCK = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);
        BLOCK_CHANGE = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE);
        SPAWN_PLAYER = new PacketType(Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);
        POSITION_ROTATION = new PacketType(Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);
        POSITION_ROTATION_UPDATE = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE);
        POSITION_UPDATE = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE);
        ROTATION_UPDATE = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE);
        DESPAWN_PLAYER = new PacketType(Byte.TYPE);
        CHAT_MESSAGE = new PacketType(Byte.TYPE, String.class);
        DISCONNECT = new PacketType(String.class);
        UPDATE_PLAYER_TYPE = new PacketType(Byte.TYPE);
        nextOpcode = 0;
    }

    private PacketType(Class ... classes) {
        PacketType.packets[this.opcode] = this;
        this.params = new Class[classes.length];
        int length = 0;
        for (int classNumber = 0; classNumber < classes.length; ++classNumber) {
            Class class_;
            this.params[classNumber] = class_ = classes[classNumber];
            if (class_ == Long.TYPE) {
                length += 8;
                continue;
            }
            if (class_ == Integer.TYPE) {
                length += 4;
                continue;
            }
            if (class_ == Short.TYPE) {
                length += 2;
                continue;
            }
            if (class_ == Byte.TYPE) {
                ++length;
                continue;
            }
            if (class_ == Float.TYPE) {
                length += 4;
                continue;
            }
            if (class_ == Double.TYPE) {
                length += 8;
                continue;
            }
            if (class_ == byte[].class) {
                length += 1024;
                continue;
            }
            if (class_ != String.class) continue;
            length += 64;
        }
        this.length = length;
    }
}

