/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dungland.nbt.NBTBase;

public class NBTTagList
extends NBTBase {
    private List<NBTBase> tagList = new ArrayList<NBTBase>();
    private byte tagType;

    public NBTTagList() {
        super("");
    }

    public NBTTagList(String data) {
        super(data);
    }

    @Override
    void write(DataOutput output) throws IOException {
        this.tagType = !this.tagList.isEmpty() ? this.tagList.get(0).getId() : (byte)1;
        output.writeByte(this.tagType);
        output.writeInt(this.tagList.size());
        for (NBTBase aTagList : this.tagList) {
            aTagList.write(output);
        }
    }

    @Override
    void load(DataInput input) throws IOException {
        this.tagType = input.readByte();
        int i = input.readInt();
        this.tagList = new ArrayList<NBTBase>();
        for (int j = 0; j < i; ++j) {
            NBTBase nbtbase = NBTBase.newTag(this.tagType, null);
            nbtbase.load(input);
            this.tagList.add(nbtbase);
        }
    }

    @Override
    public byte getId() {
        return 9;
    }

    public String toString() {
        return this.tagList.size() + " entries of type " + NBTBase.getTagName(this.tagType);
    }

    public void appendTag(NBTBase tag) {
        this.tagType = tag.getId();
        this.tagList.add(tag);
    }

    public NBTBase removeTag(int index) {
        return this.tagList.remove(index);
    }

    public NBTBase tagAt(int index) {
        return this.tagList.get(index);
    }

    public int tagCount() {
        return this.tagList.size();
    }

    @Override
    public NBTBase copy() {
        NBTTagList finalTagList = new NBTTagList(this.getName());
        finalTagList.tagType = this.tagType;
        for (NBTBase nextTag : this.tagList) {
            NBTBase nextTagByValue = nextTag.copy();
            finalTagList.tagList.add(nextTagByValue);
        }
        return finalTagList;
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagList tempOther = (NBTTagList)other;
            if (this.tagType == tempOther.tagType) {
                return this.tagList.equals(tempOther.tagList);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
}

