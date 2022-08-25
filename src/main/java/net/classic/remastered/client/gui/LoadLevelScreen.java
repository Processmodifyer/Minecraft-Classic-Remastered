/*
 * 
 */
package net.classic.remastered.client.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.LevelDialog;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.world.World;

public class LoadLevelScreen
extends GuiScreen
implements Runnable {
    protected GuiScreen parent;
    private boolean finished = false;
    private boolean loaded = false;
    private String[] levels = null;
    private String status = "";
    protected String title = "Load level";
    boolean frozen = false;
    JFileChooser chooser;
    protected boolean saving = false;
    protected File selectedFile;

    public LoadLevelScreen(GuiScreen var1) {
        this.parent = var1;
    }

    @Override
    public void run() {
        try {
            if (this.frozen) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            }
            this.status = "Getting level list..";
            URL var1 = new URL("http://" + this.minecraft.host + "/listmaps.jsp?user=" + this.minecraft.session.username);
            BufferedReader var4 = new BufferedReader(new InputStreamReader(var1.openConnection().getInputStream()));
            this.levels = var4.readLine().split(";");
            if (this.levels.length >= 5) {
                this.setLevels(this.levels);
                this.loaded = true;
                return;
            }
            this.status = this.levels[0];
            this.finished = true;
        }
        catch (Exception var3) {
            var3.printStackTrace();
            this.status = "Failed to load levels";
            this.finished = true;
        }
    }

    protected void setLevels(String[] var1) {
        for (int var2 = 0; var2 < 5; ++var2) {
            ((Button)this.buttons.get((int)var2)).active = !var1[var2].equals("-");
            ((Button)this.buttons.get((int)var2)).text = var1[var2];
            ((Button)this.buttons.get((int)var2)).visible = true;
        }
    }

    @Override
    public void onOpen() {
        new Thread(this).start();
        for (int var1 = 0; var1 < 5; ++var1) {
            this.buttons.add(new Button(var1, this.width / 2 - 100, this.height / 6 + var1 * 24, "---"));
            ((Button)this.buttons.get((int)var1)).visible = false;
            ((Button)this.buttons.get((int)var1)).active = false;
        }
        this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 6 + 120 + 12, "Load file..."));
        this.buttons.add(new Button(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (!this.frozen && var1.active) {
            if (this.loaded && var1.id < 5) {
                this.openLevel(var1.id);
            }
            if (this.finished || this.loaded && var1.id == 5) {
                this.frozen = true;
                LevelDialog var2 = new LevelDialog(this);
                var2.setDaemon(true);
                SwingUtilities.invokeLater(var2);
            }
            if (this.finished || this.loaded && var1.id == 6) {
                this.minecraft.setCurrentScreen(this.parent);
            }
        }
    }

    protected void openLevel(File var1) {
        File var2 = var1;
        Minecraft var4 = this.minecraft;
        World var5 = this.minecraft.levelIo.load(var2);
        if (var5 == null) {
            boolean var10000 = false;
        } else {
            var4.setLevel(var5);
            boolean var10000 = true;
        }
        this.minecraft.setCurrentScreen(this.parent);
    }

    protected void openLevel(int var1) {
        this.minecraft.loadOnlineLevel(this.minecraft.session.username, var1);
        this.minecraft.setCurrentScreen(null);
        this.minecraft.grabMouse();
    }

    @Override
    public void render(int var1, int var2) {
        LoadLevelScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        LoadLevelScreen.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        if (this.frozen) {
            LoadLevelScreen.drawCenteredString(this.fontRenderer, "Selecting file..", this.width / 2, this.height / 2 - 4, 0xFFFFFF);
            try {
                Thread.sleep(20L);
            }
            catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        } else {
            if (!this.loaded) {
                LoadLevelScreen.drawCenteredString(this.fontRenderer, this.status, this.width / 2, this.height / 2 - 4, 0xFFFFFF);
            }
            super.render(var1, var2);
        }
    }

    @Override
    public final void onClose() {
        super.onClose();
        if (this.chooser != null) {
            this.chooser.cancelSelection();
        }
    }

    @Override
    public final void tick() {
        super.tick();
        if (this.selectedFile != null) {
            this.openLevel(this.selectedFile);
            this.selectedFile = null;
        }
    }
}

