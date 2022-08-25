/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.dungland.nbt.NBTBase;

public class NBTTagEnd
extends NBTBase {
    public NBTTagEnd() {
        super(null);
    }

    @Override
    void load(DataInput input) throws IOException {
    }

    @Override
    void write(DataOutput output) throws IOException {
    }

    @Override
    public byte getId() {
        return 0;
    }

    public String toString() {
        return "END";
    }

    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
}

