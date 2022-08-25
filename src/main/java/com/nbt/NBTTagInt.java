package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagInt extends NBTBase {
    public int intValue;
    
    public NBTTagInt() {
    }
    
    public NBTTagInt(final int integer) {
        this.intValue = integer;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.intValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.intValue = dataInput.readInt();
    }
    
    @Override
    public final byte getType() {
        return 3;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append(this.intValue).toString();
    }
}
