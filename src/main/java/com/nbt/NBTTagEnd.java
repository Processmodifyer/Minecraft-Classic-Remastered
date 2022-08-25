package com.nbt;

import java.io.DataOutput;
import java.io.DataInput;

public final class NBTTagEnd extends NBTBase {
    @Override
    final void readTagContents(final DataInput dataInput) {
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) {
    }
    
    @Override
    public final byte getType() {
        return 0;
    }
    
    public final String toString() {
        return "END";
    }
}
