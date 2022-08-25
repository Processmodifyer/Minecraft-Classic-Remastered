/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagString
extends NBTBase {
    public String data;

    public NBTTagString(String name) {
        super(name);
    }

    public NBTTagString(String name, String data) {
        super(name);
        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeUTF(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readUTF();
    }

    @Override
    public byte getId() {
        return 8;
    }

    public String toString() {
        return this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagString(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        NBTTagString tempOther = (NBTTagString)other;
        return this.data == null && tempOther.data == null || this.data != null && this.data.equals(tempOther.data);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
}

