package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagShort extends NBTBase {
    public short shortValue;
    
    public NBTTagShort() {
    }
    
    public NBTTagShort(final short short1) {
        this.shortValue = short1;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort((int)this.shortValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.shortValue = dataInput.readShort();
    }
    
    @Override
    public final byte getType() {
        return 2;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append((int)this.shortValue).toString();
    }
}
