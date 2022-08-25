/*
 * 
 */
package net.classic.remastered.network;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.classic.remastered.client.gui.ErrorScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.network.NetworkPlayer;
import net.classic.remastered.network.PacketType;
import net.classic.remastered.network.ServerConnectThread;

public class NetworkManager {
    public ByteArrayOutputStream levelData;
    public NetworkHandler netHandler;
    public Minecraft minecraft;
    public boolean successful = false;
    public boolean levelLoaded = false;
    public HashMap<Byte, NetworkPlayer> players;

    public NetworkManager(Minecraft minecraft, String server, int port, String username, String key) {
        minecraft.online = true;
        this.minecraft = minecraft;
        this.players = new HashMap();
        new ServerConnectThread(this, server, port, username, key, minecraft).start();
    }

    public void sendBlockChange(int x, int y, int z, int mode, int block) {
        this.netHandler.send(PacketType.PLAYER_SET_BLOCK, x, y, z, mode, block);
    }

    public void error(Exception e) {
        this.netHandler.close();
        ErrorScreen errorScreen = new ErrorScreen("Disconnected!", e.getMessage());
        this.minecraft.setCurrentScreen(errorScreen);
        e.printStackTrace();
    }

    public boolean isConnected() {
        return this.netHandler != null && this.netHandler.connected;
    }

    public List getPlayers() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(this.minecraft.session.username);
        for (NetworkPlayer networkPlayer : this.players.values()) {
            list.add(networkPlayer.name);
        }
        return list;
    }

    public void send(PacketType chatMessage, int i, String message) {
    }
}

