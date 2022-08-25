package net.classic.remastered.game.world;

import java.net.*;
import java.util.zip.*;

import net.classic.remastered.client.main.StopGameException;

import java.io.*;

public final class WorldIO
{
    private ProgressBarDisplay progressBar;
    
    public WorldIO(final ProgressBarDisplay var1) {
        this.progressBar = var1;
    }
    
    public final boolean save(final World var1, final File var2) {
        try {
            final FileOutputStream var3 = new FileOutputStream(var2);
            save(var1, var3);
            var3.close();
            return true;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            if (this.progressBar != null) {
                this.progressBar.setText("Failed!");
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            return false;
        }
    }
    
    public final World load(final File var1) throws StopGameException {
        try {
            final FileInputStream var2 = new FileInputStream(var1);
            final World var3 = this.load(var2);
            var2.close();
            return var3;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            if (this.progressBar != null) {
                this.progressBar.setText("Failed!");
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            return null;
        }
    }
    
    public final boolean saveOnline(final World var1, final String var2, final String var3, String var4, final String var5, final int var6) throws StopGameException {
        if (var4 == null) {
            var4 = "";
        }
        if (this.progressBar != null && this.progressBar != null) {
            this.progressBar.setTitle("Saving level");
        }
        try {
            if (this.progressBar != null && this.progressBar != null) {
                this.progressBar.setText("Compressing..");
            }
            final ByteArrayOutputStream var7 = new ByteArrayOutputStream();
            save(var1, var7);
            var7.close();
            final byte[] var8 = var7.toByteArray();
            if (this.progressBar != null && this.progressBar != null) {
                this.progressBar.setText("Connecting..");
            }
            final HttpURLConnection var9;
            (var9 = (HttpURLConnection)new URL("http://" + var2 + "/level/save.html").openConnection()).setDoInput(true);
            var9.setDoOutput(true);
            var9.setRequestMethod("POST");
            final DataOutputStream var10;
            (var10 = new DataOutputStream(var9.getOutputStream())).writeUTF(var3);
            var10.writeUTF(var4);
            var10.writeUTF(var5);
            var10.writeByte(var6);
            var10.writeInt(var8.length);
            if (this.progressBar != null) {
                this.progressBar.setText("Saving..");
            }
            var10.write(var8);
            var10.close();
            final BufferedReader var11;
            if (!(var11 = new BufferedReader(new InputStreamReader(var9.getInputStream()))).readLine().equalsIgnoreCase("ok")) {
                if (this.progressBar != null) {
                    this.progressBar.setText("Failed: " + var11.readLine());
                }
                var11.close();
                Thread.sleep(1000L);
                return false;
            }
            var11.close();
            return true;
        }
        catch (Exception var12) {
            var12.printStackTrace();
            if (this.progressBar != null) {
                this.progressBar.setText("Failed!");
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            return false;
        }
    }
    
    public final World loadOnline(final String var1, final String var2, final int var3) throws StopGameException {
        if (this.progressBar != null) {
            this.progressBar.setTitle("Loading level");
        }
        try {
            if (this.progressBar != null) {
                this.progressBar.setText("Connecting..");
            }
            final HttpURLConnection var4;
            (var4 = (HttpURLConnection)new URL("http://" + var1 + "/level/load.html?id=" + var3 + "&user=" + var2).openConnection()).setDoInput(true);
            if (this.progressBar != null) {
                this.progressBar.setText("Loading..");
            }
            final DataInputStream var5;
            if ((var5 = new DataInputStream(var4.getInputStream())).readUTF().equalsIgnoreCase("ok")) {
                return this.load(var5);
            }
            if (this.progressBar != null) {
                this.progressBar.setText("Failed: " + var5.readUTF());
            }
            var5.close();
            Thread.sleep(1000L);
            return null;
        }
        catch (Exception var6) {
            var6.printStackTrace();
            if (this.progressBar != null) {
                this.progressBar.setText("Failed!");
            }
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException ex) {}
            return null;
        }
    }
    
    public final World load(final InputStream var1) throws StopGameException {
        if (this.progressBar != null) {
            this.progressBar.setTitle("Loading level");
        }
        if (this.progressBar != null) {
            this.progressBar.setText("Reading..");
        }
        try {
            final DataInputStream var2;
            if ((var2 = new DataInputStream(new GZIPInputStream(var1))).readInt() != 656127880) {
                return null;
            }
            final byte var3;
            if ((var3 = var2.readByte()) > 2) {
                return null;
            }
            if (var3 <= 1) {
                final String var4 = var2.readUTF();
                final String var5 = var2.readUTF();
                final long var6 = var2.readLong();
                final short var7 = var2.readShort();
                final short var8 = var2.readShort();
                final short var9 = var2.readShort();
                final byte[] var10 = new byte[var7 * var8 * var9];
                var2.readFully(var10);
                var2.close();
                final World var11;
                (var11 = new World()).setData(var7, var9, var8, var10);
                var11.name = var4;
                var11.creator = var5;
                var11.createTime = var6;
                return var11;
            }
            final WorldObjectInputStream var13;
            final World var12;
            (var12 = (World)(var13 = new WorldObjectInputStream(var2)).readObject()).initTransient();
            var13.close();
            return var12;
        }
        catch (Exception var14) {
            var14.printStackTrace();
            System.out.println("Failed to load level: " + var14.toString());
            return null;
        }
    }
    
    public static void save(final World var0, final OutputStream var1) {
        try {
            final DataOutputStream var2;
            (var2 = new DataOutputStream(new GZIPOutputStream(var1))).writeInt(656127880);
            var2.writeByte(2);
            final ObjectOutputStream var3;
            (var3 = new ObjectOutputStream(var2)).writeObject(var0);
            var3.close();
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    public static byte[] decompress(final InputStream var0) {
        try {
            final DataInputStream var2;
            final byte[] var = new byte[(var2 = new DataInputStream(new GZIPInputStream(var0))).readInt()];
            var2.readFully(var);
            var2.close();
            return var;
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
