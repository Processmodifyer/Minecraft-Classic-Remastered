/*
 * 
 */
package net.dungland.nbt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.dungland.nbt.NBTBase;
import net.dungland.nbt.NBTTagCompound;

public class CompressedStreamTools {
    public static NBTTagCompound readCompressed(InputStream stream) throws Throwable {
        NBTTagCompound compound;
        Throwable throwable = null;
        Object var3_3 = null;
        try (DataInputStream inStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream)));){
            compound = CompressedStreamTools.read(inStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        return compound;
    }

    public static void writeCompressed(NBTTagCompound tag, OutputStream stream) throws Throwable {
        Throwable throwable = null;
        Object var3_4 = null;
        try (DataOutputStream outStream = new DataOutputStream(new GZIPOutputStream(stream));){
            CompressedStreamTools.write(tag, outStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
    }

    public static NBTTagCompound decompress(byte[] buffer) throws Throwable {
        NBTTagCompound compound;
        Throwable throwable = null;
        Object var3_3 = null;
        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(buffer))));){
            compound = CompressedStreamTools.read(inputStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        return compound;
    }

    public static byte[] compress(NBTTagCompound tag) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Throwable throwable = null;
        Object var3_4 = null;
        try (DataOutputStream outStream = new DataOutputStream(new GZIPOutputStream(byteArrayOutputStream));){
            CompressedStreamTools.write(tag, outStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void safeWrite(NBTTagCompound compound, File file) throws Throwable {
        File file2 = new File(String.valueOf(file.getAbsolutePath()) + "_tmp");
        if (file2.exists()) {
            file2.delete();
        }
        CompressedStreamTools.write(compound, file2);
        if (file.exists()) {
            file.delete();
        }
        if (file.exists()) {
            throw new IOException("Failed to delete " + file);
        }
        file2.renameTo(file);
    }

    public static NBTTagCompound read(DataInput input) throws IOException {
        NBTBase content = NBTBase.readNamedTag(input);
        if (content instanceof NBTTagCompound) {
            return (NBTTagCompound)content;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
        NBTBase.writeNamedTag(compound, output);
    }

    public static void write(NBTTagCompound tag, File file) throws Throwable {
        Throwable throwable = null;
        Object var3_4 = null;
        try (DataOutputStream outStream = new DataOutputStream(new FileOutputStream(file));){
            CompressedStreamTools.write(tag, outStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
    }

    public static NBTTagCompound read(File file) throws Throwable {
        NBTTagCompound compound;
        if (!file.exists()) {
            return null;
        }
        Throwable throwable = null;
        Object var3_3 = null;
        try (DataInputStream inStream = new DataInputStream(new FileInputStream(file));){
            compound = CompressedStreamTools.read(inStream);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        return compound;
    }
}

