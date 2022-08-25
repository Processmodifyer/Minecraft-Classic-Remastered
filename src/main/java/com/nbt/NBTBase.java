package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
    private String key;
    
    public NBTBase() {
        this.key = null;
    }
    
    abstract void writeTagContents(final DataOutput dataOutput) throws IOException;
    
    abstract void readTagContents(final DataInput dataInput) throws IOException;
    
    public abstract byte getType();
    
    public final String getKey() {
        if (this.key == null) {
            return "";
        }
        return this.key;
    }
    
    public final NBTBase setKey(final String string) {
        this.key = string;
        return this;
    }
    
    public static NBTBase readTag(final DataInput dataInput) throws IOException {
        final byte byte1;
        if ((byte1 = dataInput.readByte()) == 0) {
            return new NBTTagEnd();
        }
        final NBTBase tagOfType = createTagOfType(byte1);
        final byte[] array = new byte[dataInput.readShort()];
        dataInput.readFully(array);
        tagOfType.key = new String(array, "UTF-8");
        tagOfType.readTagContents(dataInput);
        return tagOfType;
    }
    
    public static void writeTag(final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte((int)nbtBase.getType());
        if (nbtBase.getType() == 0) {
            return;
        }
        final byte[] bytes = nbtBase.getKey().getBytes("UTF-8");
        dataOutput.writeShort(bytes.length);
        dataOutput.write(bytes);
        nbtBase.writeTagContents(dataOutput);
    }
    
    public static NBTBase createTagOfType(final byte byte1) {
        switch (byte1) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            default: {
                return null;
            }
        }
    }
}
