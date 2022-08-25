/*
 * 
 */
package net.classic.remastered.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import net.classic.remastered.network.NetworkManager;
import net.classic.remastered.network.PacketType;

public final class NetworkHandler {
    public volatile boolean connected;
    public SocketChannel channel;
    public ByteBuffer in = ByteBuffer.allocate(0x100000);
    public ByteBuffer out = ByteBuffer.allocate(0x100000);
    public NetworkManager netManager;
    private Socket sock;
    private boolean unused = false;
    private byte[] stringBytes = new byte[64];

    public NetworkHandler(String var1, int var2) {
        try {
            this.channel = SocketChannel.open();
            this.channel.connect(new InetSocketAddress(var1, var2));
            this.channel.configureBlocking(false);
            System.currentTimeMillis();
            this.sock = this.channel.socket();
            this.connected = true;
            this.in.clear();
            this.out.clear();
            this.sock.setTcpNoDelay(true);
            this.sock.setTrafficClass(24);
            this.sock.setKeepAlive(false);
            this.sock.setReuseAddress(false);
            this.sock.setSoTimeout(100);
            this.sock.getInetAddress().toString();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void close() {
        try {
            if (this.out.position() > 0) {
                this.out.flip();
                this.channel.write(this.out);
                this.out.compact();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.connected = false;
        try {
            this.channel.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.sock = null;
        this.channel = null;
    }

    public final void send(PacketType var1, Object ... var2) {
        if (this.connected) {
            this.out.put(var1.opcode);
            for (int var3 = 0; var3 < var2.length; ++var3) {
                Class var10001 = var1.params[var3];
                Object var4 = var2[var3];
                Class var5 = var10001;
                NetworkHandler var6 = this;
                if (!this.connected) continue;
                try {
                    int var8;
                    byte[] var9;
                    if (var5 == Long.TYPE) {
                        var6.out.putLong((Long)var4);
                        continue;
                    }
                    if (var5 == Integer.TYPE) {
                        var6.out.putInt(((Number)var4).intValue());
                        continue;
                    }
                    if (var5 == Short.TYPE) {
                        var6.out.putShort(((Number)var4).shortValue());
                        continue;
                    }
                    if (var5 == Byte.TYPE) {
                        var6.out.put(((Number)var4).byteValue());
                        continue;
                    }
                    if (var5 == Double.TYPE) {
                        var6.out.putDouble((Double)var4);
                        continue;
                    }
                    if (var5 == Float.TYPE) {
                        var6.out.putFloat(((Float)var4).floatValue());
                        continue;
                    }
                    if (var5 != String.class) {
                        if (var5 != byte[].class) continue;
                        var9 = (byte[])var4;
                        if (var9.length < 1024) {
                            var9 = Arrays.copyOf(var9, 1024);
                        }
                        var6.out.put(var9);
                        continue;
                    }
                    var9 = ((String)var4).getBytes("UTF-8");
                    Arrays.fill(var6.stringBytes, (byte)32);
                    for (var8 = 0; var8 < 64 && var8 < var9.length; ++var8) {
                        var6.stringBytes[var8] = var9[var8];
                    }
                    for (var8 = var9.length; var8 < 64; ++var8) {
                        var6.stringBytes[var8] = 32;
                    }
                    var6.out.put(var6.stringBytes);
                    continue;
                }
                catch (Exception var7) {
                    this.netManager.error(var7);
                }
            }
        }
    }

    public Object readObject(Class var1) {
        if (!this.connected) {
            return null;
        }
        try {
            if (var1 == Long.TYPE) {
                return this.in.getLong();
            }
            if (var1 == Integer.TYPE) {
                return this.in.getInt();
            }
            if (var1 == Short.TYPE) {
                return this.in.getShort();
            }
            if (var1 == Byte.TYPE) {
                return this.in.get();
            }
            if (var1 == Double.TYPE) {
                return this.in.getDouble();
            }
            if (var1 == Float.TYPE) {
                return Float.valueOf(this.in.getFloat());
            }
            if (var1 == String.class) {
                this.in.get(this.stringBytes);
                return new String(this.stringBytes, "UTF-8").trim();
            }
            if (var1 == byte[].class) {
                byte[] var3 = new byte[1024];
                this.in.get(var3);
                return var3;
            }
            return null;
        }
        catch (Exception var2) {
            this.netManager.error(var2);
            return null;
        }
    }
}

