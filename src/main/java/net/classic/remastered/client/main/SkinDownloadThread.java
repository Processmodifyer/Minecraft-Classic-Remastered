/*
 * 
 */
package net.classic.remastered.client.main;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

import net.classic.remastered.game.player.Player;

public class SkinDownloadThread
extends Thread {
    private Minecraft minecraft;

    public SkinDownloadThread(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void run() {
        if (this.minecraft.session != null) {
            HttpURLConnection connection = null;
            try {
                //connection = (HttpURLConnection)new URL("http://www.minecraft.net/skin/" + this.minecraft.session.username + ".png").openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(false);
                connection.connect();
                if (connection.getResponseCode() != 404) {
                    Player.newTexture = ImageIO.read(connection.getInputStream());
                    return;
                }
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}

