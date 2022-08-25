package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagLong extends NBTBase {
    public long longValue;
    
    public NBTTagLong() {
    }
    
    public NBTTagLong(final long long1) {
        this.longValue = long1;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.longValue);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        this.longValue = dataInput.readLong();
    }
    
    @Override
    public final byte getType() {
        return 4;
    }
    
    public final String toString() {
        return new StringBuilder().append("").append(this.longValue).toString();
    }
}
