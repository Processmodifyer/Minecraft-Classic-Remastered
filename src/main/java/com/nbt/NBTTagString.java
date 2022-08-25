package com.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NBTTagString extends NBTBase {
    public String stringValue;
    
    public NBTTagString() {
    }
    
    public NBTTagString(final String string) {
        this.stringValue = string;
    }
    
    @Override
    final void writeTagContents(final DataOutput dataOutput) throws IOException {
        final byte[] bytes = this.stringValue.getBytes("UTF-8");
        dataOutput.writeShort(bytes.length);
        dataOutput.write(bytes);
    }
    
    @Override
    final void readTagContents(final DataInput dataInput) throws IOException {
        final byte[] array = new byte[dataInput.readShort()];
        dataInput.readFully(array);
        this.stringValue = new String(array, "UTF-8");
    }
    
    @Override
    public final byte getType() {
        return 8;
    }
    
    public final String toString() {
        return "" + this.stringValue;
    }
}
