/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagInt
extends NBTBase {
    public int data;

    public NBTTagInt(String name) {
        super(name);
    }

    public NBTTagInt(String name, int data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readInt();
    }

    @Override
    public byte getId() {
        return 3;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagInt tempOther = (NBTTagInt)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}

