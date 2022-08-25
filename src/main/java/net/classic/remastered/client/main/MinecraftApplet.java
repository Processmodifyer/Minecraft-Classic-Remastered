/*
 * 
 */
package net.classic.remastered.client.main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;

import net.classic.remastered.game.player.inventory.CreativeInventoryStorage;

public class MinecraftApplet
extends Applet {
    private static final long serialVersionUID = 1L;
    private Canvas canvas;
    private Minecraft minecraft;
    private Thread thread = null;

    @Override
    public void init() {
        this.canvas = new MinecraftApplet$1(this);
        boolean fullscreen = false;
        if (this.getParameter("fullscreen") != null) {
            fullscreen = this.getParameter("fullscreen").equalsIgnoreCase("true");
        }
        this.minecraft = new Minecraft(this.canvas, this, this.getWidth(), this.getHeight(), fullscreen);
        this.minecraft.host = this.getDocumentBase().getHost();
        if (this.getDocumentBase().getPort() > 0) {
            this.minecraft.host = String.valueOf(this.minecraft.host) + ":" + this.getDocumentBase().getPort();
        }
        if (this.getParameter("username") != null && this.getParameter("sessionid") != null) {
            this.minecraft.session = new CreativeInventoryStorage(this.getParameter("username"), this.getParameter("sessionid"));
            if (this.getParameter("mppass") != null) {
                this.minecraft.session.mppass = this.getParameter("mppass");
            }
            this.minecraft.session.haspaid = this.getParameter("haspaid").equalsIgnoreCase("true");
        }
        if (this.getParameter("loadmap_user") != null && this.getParameter("loadmap_id") != null) {
            this.minecraft.levelName = this.getParameter("loadmap_user");
            this.minecraft.levelId = Integer.parseInt(this.getParameter("loadmap_id"));
        } else if (this.getParameter("server") != null && this.getParameter("port") != null) {
            String server = this.getParameter("server");
            int port = Integer.parseInt(this.getParameter("port"));
            this.minecraft.server = server;
            this.minecraft.port = port;
        }
        this.minecraft.levelLoaded = true;
        this.setLayout(new BorderLayout());
        this.add((Component)this.canvas, "Center");
        this.canvas.setFocusable(true);
        this.validate();
    }

    public void startGameThread() {
        if (this.thread == null) {
            this.thread = new Thread(this.minecraft);
            this.thread.start();
        }
    }

    @Override
    public void start() {
        this.minecraft.waiting = false;
    }

    @Override
    public void stop() {
        this.minecraft.waiting = true;
    }

    @Override
    public void destroy() {
        this.stopGameThread();
    }

    public void stopGameThread() {
        if (this.thread != null) {
            this.minecraft.running = false;
            try {
                this.thread.join(1000L);
            }
            catch (InterruptedException var3) {
                this.minecraft.shutdown();
            }
            this.thread = null;
        }
        
        /*
         * 
         */
    }
        public class MinecraftApplet$1
        extends Canvas {
            private static final long serialVersionUID = 1L;
            private MinecraftApplet applet;

            public MinecraftApplet$1(MinecraftApplet minecraftApplet) {
                this.applet = minecraftApplet;
            }

            @Override
            public synchronized void addNotify() {
                super.addNotify();
                this.applet.startGameThread();
            }

            @Override
            public synchronized void removeNotify() {
                this.applet.stopGameThread();
                super.removeNotify();
            }
        }


    }


