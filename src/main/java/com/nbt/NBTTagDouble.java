package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagDouble extends NBTBase {
    private double doubleValue;
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.doubleValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.doubleValue = dataInput.readDouble();
    }
    
    @Override
    public final byte getType() {
        return 6;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append(this.doubleValue).toString();
    }
}
