/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.dungland.nbt.NBTBase;
import net.dungland.nbt.NBTTagByte;
import net.dungland.nbt.NBTTagByteArray;
import net.dungland.nbt.NBTTagDouble;
import net.dungland.nbt.NBTTagFloat;
import net.dungland.nbt.NBTTagInt;
import net.dungland.nbt.NBTTagIntArray;
import net.dungland.nbt.NBTTagList;
import net.dungland.nbt.NBTTagLong;
import net.dungland.nbt.NBTTagShort;
import net.dungland.nbt.NBTTagString;

public class NBTTagCompound
extends NBTBase {
    private Map<String, NBTBase> tagMap = new HashMap<String, NBTBase>();

    public NBTTagCompound() {
        super("");
    }

    public NBTTagCompound(String name) {
        super(name);
    }

    static Map<String, NBTBase> getTagMap(NBTTagCompound compound) {
        return compound.tagMap;
    }

    @Override
    void write(DataOutput output) throws IOException {
        for (NBTBase nbtbase : this.tagMap.values()) {
            NBTBase.writeNamedTag(nbtbase, output);
        }
        output.writeByte(0);
    }

    @Override
    void load(DataInput input) throws IOException {
        NBTBase nbtbase;
        this.tagMap.clear();
        while ((nbtbase = NBTBase.readNamedTag(input)).getId() != 0) {
            this.tagMap.put(nbtbase.getName(), nbtbase);
        }
    }

    public Collection<NBTBase> getTags() {
        return this.tagMap.values();
    }

    @Override
    public byte getId() {
        return 10;
    }

    public void setTag(String name, NBTBase tag) {
        this.tagMap.put(name, tag.setName(name));
    }

    public void setByte(String name, byte theByte) {
        this.tagMap.put(name, new NBTTagByte(name, theByte));
    }

    public void setShort(String name, short theShort) {
        this.tagMap.put(name, new NBTTagShort(name, theShort));
    }

    public void setInteger(String name, int theInt) {
        this.tagMap.put(name, new NBTTagInt(name, theInt));
    }

    public void setLong(String name, long theLong) {
        this.tagMap.put(name, new NBTTagLong(name, theLong));
    }

    public void setFloat(String name, float theFloat) {
        this.tagMap.put(name, new NBTTagFloat(name, theFloat));
    }

    public void setDouble(String name, double theDouble) {
        this.tagMap.put(name, new NBTTagDouble(name, theDouble));
    }

    public void setString(String name, String theString) {
        this.tagMap.put(name, new NBTTagString(name, theString));
    }

    public void setByteArray(String name, byte[] theByteArray) {
        this.tagMap.put(name, new NBTTagByteArray(name, theByteArray));
    }

    public void setIntArray(String name, int[] theIntArray) {
        this.tagMap.put(name, new NBTTagIntArray(name, theIntArray));
    }

    public void setCompoundTag(String name, NBTTagCompound theCompound) {
        this.tagMap.put(name, theCompound.setName(name));
    }

    public void setBoolean(String name, boolean theBool) {
        this.setByte(name, (byte)(theBool ? 1 : 0));
    }

    public NBTBase getTag(String name) {
        return this.tagMap.get(name);
    }

    public boolean hasKey(String name) {
        return this.tagMap.containsKey(name);
    }

    public byte getByte(String name) {
        return !this.tagMap.containsKey(name) ? (byte)0 : ((NBTTagByte)this.tagMap.get((Object)name)).data;
    }

    public short getShort(String name) {
        return !this.tagMap.containsKey(name) ? (short)0 : ((NBTTagShort)this.tagMap.get((Object)name)).data;
    }

    public int getInteger(String name) {
        return !this.tagMap.containsKey(name) ? 0 : ((NBTTagInt)this.tagMap.get((Object)name)).data;
    }

    public long getLong(String name) {
        return !this.tagMap.containsKey(name) ? 0L : ((NBTTagLong)this.tagMap.get((Object)name)).data;
    }

    public float getFloat(String name) {
        return !this.tagMap.containsKey(name) ? 0.0f : ((NBTTagFloat)this.tagMap.get((Object)name)).data;
    }

    public double getDouble(String name) {
        return !this.tagMap.containsKey(name) ? 0.0 : ((NBTTagDouble)this.tagMap.get((Object)name)).data;
    }

    public String getString(String name) {
        return !this.tagMap.containsKey(name) ? "" : ((NBTTagString)this.tagMap.get((Object)name)).data;
    }

    public byte[] getByteArray(String name) {
        return !this.tagMap.containsKey(name) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get((Object)name)).byteArray;
    }

    public int[] getIntArray(String name) {
        return !this.tagMap.containsKey(name) ? new int[0] : ((NBTTagIntArray)this.tagMap.get((Object)name)).intArray;
    }

    public NBTTagCompound getCompoundTag(String name) {
        return !this.tagMap.containsKey(name) ? new NBTTagCompound(name) : (NBTTagCompound)this.tagMap.get(name);
    }

    public NBTTagList getTagList(String name) {
        return !this.tagMap.containsKey(name) ? new NBTTagList(name) : (NBTTagList)this.tagMap.get(name);
    }

    public boolean getBoolean(String name) {
        return this.getByte(name) != 0;
    }

    public void removeTag(String name) {
        this.tagMap.remove(name);
    }

    public String toString() {
        String s = String.valueOf(this.getName()) + ":[";
        for (String s1 : this.tagMap.keySet()) {
            s = String.valueOf(s) + s1 + ":" + this.tagMap.get(s1) + ",";
        }
        return String.valueOf(s) + "]";
    }

    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }

    @Override
    public NBTBase copy() {
        NBTTagCompound finalCompound = new NBTTagCompound(this.getName());
        for (String s : this.tagMap.keySet()) {
            finalCompound.setTag(s, this.tagMap.get(s).copy());
        }
        return finalCompound;
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagCompound tempOther = (NBTTagCompound)other;
            return this.tagMap.entrySet().equals(tempOther.tagMap.entrySet());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
}

