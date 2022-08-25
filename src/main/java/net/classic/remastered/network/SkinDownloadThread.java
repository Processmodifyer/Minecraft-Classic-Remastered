/*
 * 
 */
package net.classic.remastered.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

import net.classic.remastered.network.NetworkPlayer;

public class SkinDownloadThread
extends Thread {
    private NetworkPlayer player;

    public SkinDownloadThread(NetworkPlayer networkPlayer) {
        this.player = networkPlayer;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)new URL("http://www.minecraft.net/skin/" + this.player.name + ".png").openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.connect();
            if (connection.getResponseCode() == 404) {
                return;
            }
            try {
                this.player.newTexture = ImageIO.read(connection.getInputStream());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

