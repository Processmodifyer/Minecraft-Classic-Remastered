package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagByte extends NBTBase {
    public byte byteValue;
    
    public NBTTagByte() {
    }
    
    public NBTTagByte(final byte byte1) {
        this.byteValue = byte1;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte((int)this.byteValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.byteValue = dataInput.readByte();
    }
    
    @Override
    public final byte getType() {
        return 1;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append((int)this.byteValue).toString();
    }
}
