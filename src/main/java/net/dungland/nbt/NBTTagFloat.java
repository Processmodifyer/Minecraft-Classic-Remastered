/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagFloat
extends NBTBase {
    public float data;

    public NBTTagFloat(String name) {
        super(name);
    }

    public NBTTagFloat(String name, float data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readFloat();
    }

    @Override
    public byte getId() {
        return 5;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagFloat tempOther = (NBTTagFloat)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
}

