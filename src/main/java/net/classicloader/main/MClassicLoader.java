package net.classicloader.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.main.MinecraftApplet;

public class MClassicLoader {
    public static void main(String[] args) {
    	MClassicLoader minecraftStandalone = new MClassicLoader();
        minecraftStandalone.startMinecraft();
    }

    public void startMinecraft() {
        MinecraftFrame minecraftFrame = new MinecraftFrame();
        minecraftFrame.startMinecraft();
    }

    private class MinecraftFrame
    extends JFrame {
        private Minecraft minecraft;

        public MinecraftFrame() {
            this.setSize(854, 480);
            this.setDefaultCloseOperation(3);
            this.setLayout(new BorderLayout());
            this.addWindowListener(new WindowAdapter(){

                @Override
                public void windowClosing(WindowEvent e) {
                    ((MinecraftFrame)MinecraftFrame.this).minecraft.running = false;
                }
            });
        }

        public void startMinecraft() {
            LoaderApplet2 applet = new LoaderApplet2();
            MinecraftCanvas canvas = new MinecraftCanvas();
            this.minecraft = new Minecraft(canvas, applet, this.getWidth(), this.getHeight(), false);
            canvas.setMinecraft(this.minecraft);
            canvas.setSize(this.getSize());
            this.add((Component)canvas, "Center");
            canvas.setFocusable(true);
            this.pack();
            this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
            this.setVisible(true);
            new Thread(new Runnable(){

                @Override
                public void run() {
                    while (true) {
                        if (!((MinecraftFrame)MinecraftFrame.this).minecraft.running) {
                            MinecraftFrame.this.minecraft.shutdown();
                            MinecraftFrame.this.dispose();
                        }
                        try {
                            Thread.sleep(1L);
                            continue;
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }).start();
            boolean pass = false;
            while (!pass) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.minecraft.running) continue;
                pass = true;
            }
        }

        private class LoaderApplet2
        extends MinecraftApplet {
            private Map<String, String> parameters = new HashMap<String, String>();

            @Override
            public URL getDocumentBase() {
                try {
                    return new URL("http://minecraft.net:80/play.jsp");
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public URL getCodeBase() {
                try {
                    return new URL("http://minecraft.net:80/");
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getParameter(String name) {
                return this.parameters.get(name);
            }
        }

        private class MinecraftCanvas
        extends Canvas {
            private static final long serialVersionUID = 1L;
            private Minecraft minecraft;
            private Thread thread;

            @Override
            public synchronized void addNotify() {
                super.addNotify();
                this.startThread();
            }

            @Override
            public synchronized void removeNotify() {
                this.stopThread();
                super.removeNotify();
            }

            public void setMinecraft(Minecraft minecraft) {
                this.minecraft = minecraft;
            }

            private synchronized void startThread() {
                if (this.thread == null) {
                    this.thread = new Thread((Runnable)this.minecraft, "Client");
                    this.thread.start();
                }
            }

            private synchronized void stopThread() {
                if (this.thread != null) {
                    this.minecraft.running = false;
                    try {
                        this.thread.join();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        this.minecraft.shutdown();
                    }
                    this.thread = null;
                }
            }
        }
    }
}

