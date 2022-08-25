/*
 * 
 */
package net.dungland.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import net.dungland.nbt.NBTBase;

public class NBTTagIntArray
extends NBTBase {
    public int[] intArray;

    public NBTTagIntArray(String name) {
        super(name);
    }

    public NBTTagIntArray(String name, int[] intArrayInput) {
        super(name);
        this.intArray = intArrayInput;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.intArray.length);
        int[] arrn = this.intArray;
        int n = this.intArray.length;
        for (int i = 0; i < n; ++i) {
            int anIntArray = arrn[i];
            output.writeInt(anIntArray);
        }
    }

    @Override
    void load(DataInput input) throws IOException {
        int i = input.readInt();
        this.intArray = new int[i];
        for (int j = 0; j < i; ++j) {
            this.intArray[j] = input.readInt();
        }
    }

    @Override
    public byte getId() {
        return 11;
    }

    public String toString() {
        return "[" + this.intArray.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(this.getName(), aint);
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        NBTTagIntArray tempOther = (NBTTagIntArray)other;
        return this.intArray == null && tempOther.intArray == null || this.intArray != null && Arrays.equals(this.intArray, tempOther.intArray);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
}

