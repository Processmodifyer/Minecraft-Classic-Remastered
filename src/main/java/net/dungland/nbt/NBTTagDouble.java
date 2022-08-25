/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagDouble
extends NBTBase {
    public double data;

    public NBTTagDouble(String name) {
        super(name);
    }

    public NBTTagDouble(String name, double data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeDouble(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readDouble();
    }

    @Override
    public byte getId() {
        return 6;
    }

    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagDouble tempOther = (NBTTagDouble)other;
            return this.data == tempOther.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(i ^ i >>> 32);
    }
}

