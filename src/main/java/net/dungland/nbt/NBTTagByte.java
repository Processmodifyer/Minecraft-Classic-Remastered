/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagByte
extends NBTBase {
    public byte data;

    public NBTTagByte(String name) {
        super(name);
    }

    public NBTTagByte(String name, byte data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeByte(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readByte();
    }

    @Override
    public byte getId() {
        return 1;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagByte tempOther = (NBTTagByte)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}

