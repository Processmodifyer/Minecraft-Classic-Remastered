/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagLong
extends NBTBase {
    public long data;

    public NBTTagLong(String name) {
        super(name);
    }

    public NBTTagLong(String name, long data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeLong(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readLong();
    }

    @Override
    public byte getId() {
        return 4;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagLong tempOther = (NBTTagLong)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }
}

