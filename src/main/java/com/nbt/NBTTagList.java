package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NBTTagList extends NBTBase {
    private List<NBTBase> tagList;
    private byte tagType;
    
    public NBTTagList() {
        this.tagList = (List<NBTBase>)new ArrayList<NBTBase>();
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        if (this.tagList.size() > 0) {
            this.tagType = ((NBTBase)this.tagList.get(0)).getType();
        }
        else {
            this.tagType = 1;
        }
        dataOutput.writeByte((int)this.tagType);
        dataOutput.writeInt(this.tagList.size());
        for (int i = 0; i < this.tagList.size(); ++i) {
            ((NBTBase)this.tagList.get(i)).writeTagContents(dataOutput);
        }
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.tagType = dataInput.readByte();
        final int int1 = dataInput.readInt();
        this.tagList = (List<NBTBase>)new ArrayList<NBTBase>();
        for (int i = 0; i < int1; ++i) {
            final NBTBase tagOfType;
            (tagOfType = NBTBase.createTagOfType(this.tagType)).readTagContents(dataInput);
            this.tagList.add(tagOfType);
        }
    }
    
    @Override
    public final byte getType() {
        return 9;
    }
    
    public final String toString() {
        final StringBuilder append = new StringBuilder().append("").append(this.tagList.size()).append(" entries of type ");
        String s = null;
        switch (this.tagType) {
            case 0: {
                s = "TAG_End";
                break;
            }
            case 1: {
                s = "TAG_Byte";
                break;
            }
            case 2: {
                s = "TAG_Short";
                break;
            }
            case 3: {
                s = "TAG_Int";
                break;
            }
            case 4: {
                s = "TAG_Long";
                break;
            }
            case 5: {
                s = "TAG_Float";
                break;
            }
            case 6: {
                s = "TAG_Double";
                break;
            }
            case 7: {
                s = "TAG_Byte_Array";
                break;
            }
            case 8: {
                s = "TAG_String";
                break;
            }
            case 9: {
                s = "TAG_List";
                break;
            }
            case 10: {
                s = "TAG_Compound";
                break;
            }
            default: {
                s = "UNKNOWN";
                break;
            }
        }
        return append.append(s).toString();
    }
    
    public final void setTag(final NBTBase nbtBase) {
        this.tagType = nbtBase.getType();
        this.tagList.add(nbtBase);
    }
    
    public final NBTBase tagAt(final int integer) {
        return (NBTBase)this.tagList.get(integer);
    }
    
    public final int tagCount() {
        return this.tagList.size();
    }
}
