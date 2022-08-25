/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagShort
extends NBTBase {
    public short data;

    public NBTTagShort(String name) {
        super(name);
    }

    public NBTTagShort(String name, short par2) {
        super(name);
        this.data = par2;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeShort(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readShort();
    }

    @Override
    public byte getId() {
        return 2;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagShort(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagShort tempOther = (NBTTagShort)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}

