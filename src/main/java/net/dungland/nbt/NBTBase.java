/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTTagByte;
import net.dungland.nbt.NBTTagByteArray;
import net.dungland.nbt.NBTTagCompound;
import net.dungland.nbt.NBTTagDouble;
import net.dungland.nbt.NBTTagEnd;
import net.dungland.nbt.NBTTagFloat;
import net.dungland.nbt.NBTTagInt;
import net.dungland.nbt.NBTTagIntArray;
import net.dungland.nbt.NBTTagList;
import net.dungland.nbt.NBTTagLong;
import net.dungland.nbt.NBTTagShort;
import net.dungland.nbt.NBTTagString;

public abstract class NBTBase {
    public static final String[] NBTTypes = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};
    private String name;

    protected NBTBase(String name) {
        this.name = name == null ? "" : name;
    }

    public static NBTBase readNamedTag(DataInput input) throws IOException {
        byte b0 = input.readByte();
        if (b0 == 0) {
            return new NBTTagEnd();
        }
        String s = input.readUTF();
        NBTBase nbtbase = NBTBase.newTag(b0, s);
        nbtbase.load(input);
        return nbtbase;
    }

    public static void writeNamedTag(NBTBase tag, DataOutput output) throws IOException {
        output.writeByte(tag.getId());
        if (tag.getId() != 0) {
            output.writeUTF(tag.getName());
            tag.write(output);
        }
    }

    public static NBTBase newTag(byte typeID, String name) {
        switch (typeID) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte(name);
            }
            case 2: {
                return new NBTTagShort(name);
            }
            case 3: {
                return new NBTTagInt(name);
            }
            case 4: {
                return new NBTTagLong(name);
            }
            case 5: {
                return new NBTTagFloat(name);
            }
            case 6: {
                return new NBTTagDouble(name);
            }
            case 7: {
                return new NBTTagByteArray(name);
            }
            case 8: {
                return new NBTTagString(name);
            }
            case 9: {
                return new NBTTagList(name);
            }
            case 10: {
                return new NBTTagCompound(name);
            }
            case 11: {
                return new NBTTagIntArray(name);
            }
        }
        return null;
    }

    public static String getTagName(byte typeID) {
        switch (typeID) {
            case 0: {
                return "TAG_End";
            }
            case 1: {
                return "TAG_Byte";
            }
            case 2: {
                return "TAG_Short";
            }
            case 3: {
                return "TAG_Int";
            }
            case 4: {
                return "TAG_Long";
            }
            case 5: {
                return "TAG_Float";
            }
            case 6: {
                return "TAG_Double";
            }
            case 7: {
                return "TAG_Byte_Array";
            }
            case 8: {
                return "TAG_String";
            }
            case 9: {
                return "TAG_List";
            }
            case 10: {
                return "TAG_Compound";
            }
            case 11: {
                return "TAG_Int_Array";
            }
        }
        return "UNKNOWN";
    }

    abstract void write(DataOutput var1) throws IOException;

    abstract void load(DataInput var1) throws IOException;

    public abstract byte getId();

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public NBTBase setName(String name) {
        this.name = name == null ? "" : name;
        return this;
    }

    public abstract NBTBase copy();

    public boolean equals(Object other) {
        if (!(other instanceof NBTBase)) {
            return false;
        }
        NBTBase tempOther = (NBTBase)other;
        return !(this.getId() != tempOther.getId() || this.name == null && tempOther.name != null || this.name != null && tempOther.name == null || this.name != null && !this.name.equals(tempOther.name));
    }

    public int hashCode() {
        return this.name.hashCode() ^ this.getId();
    }
}

