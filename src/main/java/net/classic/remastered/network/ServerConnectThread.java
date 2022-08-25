/*
 * 
 */
package net.classic.remastered.network;

import net.classic.remastered.client.gui.ErrorScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.network.NetworkManager;
import net.classic.remastered.network.PacketType;

public class ServerConnectThread
extends Thread {
    private String server;
    private int port;
    private String username;
    private String key;
    private Minecraft minecraft;
    private NetworkManager netManager;

    public ServerConnectThread(NetworkManager networkManager, String server, int port, String username, String key, Minecraft minecraft) {
        this.netManager = networkManager;
        this.server = server;
        this.port = port;
        this.username = username;
        this.key = key;
        this.minecraft = minecraft;
    }

    @Override
    public void run() {
        try {
            this.netManager.netHandler = new NetworkHandler(this.server, this.port);
            this.netManager.netHandler.netManager = this.netManager;
            this.netManager.netHandler.send(PacketType.IDENTIFICATION, (byte)7, this.username, this.key, 1);
            this.netManager.successful = true;
        }
        catch (Exception var3) {
            this.minecraft.online = false;
            this.minecraft.networkManager = null;
            this.minecraft.setCurrentScreen(new ErrorScreen("Failed to connect", "You failed to connect to the server. It's probably down!"));
            this.netManager.successful = false;
        }
    }
}

