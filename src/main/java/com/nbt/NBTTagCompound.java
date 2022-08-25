package com.nbt;

import java.io.DataInput;
import java.util.Iterator;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class NBTTagCompound extends NBTBase {
    private Map<String, NBTBase> tagMap;
    
    public NBTTagCompound() {
        this.tagMap = (Map<String, NBTBase>)new HashMap<String, NBTBase>();
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        final Iterator<NBTBase> iterator = this.tagMap.values().iterator();
        while (iterator.hasNext()) {
            NBTBase.writeTag((NBTBase)iterator.next(), dataOutput);
        }
        dataOutput.writeByte(0);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.tagMap.clear();
        NBTBase tag;
        while ((tag = NBTBase.readTag(dataInput)).getType() != 0) {
            this.tagMap.put(tag.getKey(), tag);
        }
    }
    
    @Override
    public final byte getType() {
        return 10;
    }
    
    public final void setTag(final String string, final NBTBase j) {
        this.tagMap.put(string, j.setKey(string));
    }
    
    public final void setByte(final String string, final byte byte2) {
        this.tagMap.put(string, new NBTTagByte(byte2).setKey(string));
    }
    
    public final void setShort(final String string, final short short2) {
        this.tagMap.put(string, new NBTTagShort(short2).setKey(string));
    }
    
    public final void setInteger(final String string, final int integer) {
        this.tagMap.put(string, new NBTTagInt(integer).setKey(string));
    }
    
    public final void setLong(final String string, final long long2) {
        this.tagMap.put(string, new NBTTagLong(long2).setKey(string));
    }
    
    public final void setFloat(final String string, final float float2) {
        this.tagMap.put(string, new NBTTagFloat(float2).setKey(string));
    }
    
    public final void setString(final String string1, final String string2) {
        this.tagMap.put(string1, new NBTTagString(string2).setKey(string1));
    }
    
    public final void setByteArray(final String string, final byte[] arr) {
        this.tagMap.put(string, new NBTTagByteArray(arr).setKey(string));
    }
    
    public final void setCompoundTag(final String string, final NBTTagCompound c) {
        this.tagMap.put(string, c.setKey(string));
    }
    
    public final void setBoolean(final String string, final boolean boolean2) {
        this.setByte(string, (byte)(boolean2 ? 1 : 0));
    }
    
    public final boolean hasKey(final String string) {
        return this.tagMap.containsKey(string);
    }
    
    public final byte getByte(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagByte)this.tagMap.get(string)).byteValue;
    }
    
    public final short getShort(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagShort)this.tagMap.get(string)).shortValue;
    }
    
    public final int getInteger(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0;
        }
        return ((NBTTagInt)this.tagMap.get(string)).intValue;
    }
    
    public final long getLong(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0L;
        }
        return ((NBTTagLong)this.tagMap.get(string)).longValue;
    }
    
    public final float getFloat(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return 0.0f;
        }
        return ((NBTTagFloat)this.tagMap.get(string)).floatValue;
    }
    
    public final String getString(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return "";
        }
        return ((NBTTagString)this.tagMap.get(string)).stringValue;
    }
    
    public final byte[] getByteArray(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new byte[0];
        }
        return ((NBTTagByteArray)this.tagMap.get(string)).byteArray;
    }
    
    public final NBTTagCompound getCompoundTag(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagCompound();
        }
        return (NBTTagCompound)this.tagMap.get(string);
    }
    
    public final NBTTagList getTagList(final String string) {
        if (!this.tagMap.containsKey(string)) {
            return new NBTTagList();
        }
        return (NBTTagList)this.tagMap.get(string);
    }
    
    public final boolean getBoolean(final String string) {
        return this.getByte(string) != 0;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append(this.tagMap.size()).append(" entries").toString();
    }
    
    public final boolean emptyNBTMap() {
        return this.tagMap.isEmpty();
    }
}
