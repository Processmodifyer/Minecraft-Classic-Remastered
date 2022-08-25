package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagFloat extends NBTBase {
    public float floatValue;
    
    public NBTTagFloat() {
    }
    
    public NBTTagFloat(final float float1) {
        this.floatValue = float1;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.floatValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.floatValue = dataInput.readFloat();
    }
    
    @Override
    public final byte getType() {
        return 5;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append(this.floatValue).toString();
    }
}
