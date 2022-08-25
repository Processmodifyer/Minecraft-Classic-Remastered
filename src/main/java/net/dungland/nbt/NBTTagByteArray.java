/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import net.dungland.nbt.NBTBase;

public class NBTTagByteArray
extends NBTBase {
    public byte[] byteArray;

    public NBTTagByteArray(String name) {
        super(name);
    }

    public NBTTagByteArray(String name, byte[] byteArrayInput) {
        super(name);
        this.byteArray = byteArrayInput;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.byteArray.length);
        output.write(this.byteArray);
    }

    @Override
    void load(DataInput input) throws IOException {
        int i = input.readInt();
        this.byteArray = new byte[i];
        input.readFully(this.byteArray);
    }

    @Override
    public byte getId() {
        return 7;
    }

    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        byte[] abyte = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, abyte, 0, this.byteArray.length);
        return new NBTTagByteArray(this.getName(), abyte);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(this.byteArray, ((NBTTagByteArray)other).byteArray);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }
}

