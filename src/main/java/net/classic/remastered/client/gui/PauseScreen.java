/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.CheatMenu;
import net.classic.remastered.client.gui.GenerateLevelScreen;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.LoadLevelScreen;
import net.classic.remastered.client.gui.OptionsScreen;
import net.classic.remastered.client.gui.SaveLevelScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.dungland.util.LogUtil;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public final class PauseScreen
extends GuiScreen {
    private static final String VersionString = "0.1";
	private PauseScreen pausa;
    private float xloc;
    private float yloc;
    private float zloc;
    int greenColor = 8454016;
    int orangeColor = 16750160;
    int redColor = 16737380;

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Options..."));
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Generate new level..."));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Save level.."));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 72, "Load level.."));
        this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 4 + 120, "Go to the Spawn Point"));
        this.buttons.add(new Button(6, this.width / 2 - 100, this.height / 4 + 140, "Debug Nemu"));
        
        
        if (this.minecraft.session == null) {
            ((Button)this.buttons.get((int)2)).active = false;
            ((Button)this.buttons.get((int)3)).active = false;
        if (this.minecraft.networkManager != null) {
            ((Button)this.buttons.get((int)1)).active = false;
            ((Button)this.buttons.get((int)2)).active = false;
            ((Button)this.buttons.get((int)3)).active = false;
        }
        }
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 0) {
            this.minecraft.setCurrentScreen(new OptionsScreen(this, this.minecraft.settings));
        }
        if (var1.id == 1) {
            this.minecraft.setCurrentScreen(new GenerateLevelScreen(this));
        }
        if (this.minecraft.session != null) {
            if (var1.id == 2) {
                this.minecraft.setCurrentScreen(new SaveLevelScreen(this));
            }
            if (var1.id == 3) {
                this.minecraft.setCurrentScreen(new LoadLevelScreen(this));
            }
        }
        if (var1.id == 4) {
            this.minecraft.setCurrentScreen(null);
            this.minecraft.grabMouse();
        }
        if (var1.id == 5) {
            this.minecraft.player.health = 20;
            this.minecraft.setCurrentScreen(this.pausa);
            this.minecraft.player.resetPos();
            for (int i = 0; i <= 8; ++i) {
                for (int varacable = this.minecraft.player.inventory.count[i]; varacable > 0; --varacable) {
                    this.minecraft.level.addEntity(new NonLivingEntity(this.minecraft.level, this.xloc, this.yloc, this.zloc, this.minecraft.player.inventory.slots[i]));
                }
                this.minecraft.player.inventory.slots[i] = -1;
                this.minecraft.player.inventory.count[i] = 0;
            }
            this.minecraft.gamemode.preparePlayer(this.minecraft.player);
        }
        if (var1.id == 6) {
            this.minecraft.setCurrentScreen(new DebugNemu(this));
        }
        if (var1.id == 7) {
            File file = new File(Minecraft.getMinecraftDirectory(), "/Screenshots/");
            file.mkdirs();
            try {
                Desktop.getDesktop().open(file);
            }
            catch (IOException ex) {
                LogUtil.logError("Error opening screenshots folder.", ex);
            }
        }
    }

    @Override
    public final void render(int var1, int var2) {
        String titlePrint = net.classic.remastered.game.world.ProgressBarDisplay.title;
        if (minecraft.session == null) {
            titlePrint = "Singleplayer";
        }

        drawFadingBox(0, 0, width, height, 1610941696, -1607454624);
        drawCenteredString(fontRenderer, "Game menu", width / 2, 40, 16777215);
        drawString(fontRenderer, titlePrint, width - fontRenderer.getWidth(titlePrint) - 15, 2,
                16777215);
        drawString(fontRenderer, "Remastered Client " + VersionString,
                width - fontRenderer.getWidth("ClassiCube " + VersionString) - 15, 13, 14474460);

        double cpuUsage = minecraft.monitoringThread.getAverageUsagePerCPU();
        double roundedCpuUsage = Math.round(cpuUsage * 100.0) / 100.0;

        int colorToUse = greenColor;
        if (cpuUsage >= 21) {
            colorToUse = orangeColor;
        } else if (cpuUsage >= 32) {
            colorToUse = redColor;
        } else if (cpuUsage <= 20) {
            colorToUse = greenColor;
        }

        String s = "Average CPU: " + roundedCpuUsage + "%";
        drawString(fontRenderer, s, width - fontRenderer.getWidth(s) - 15, 24, colorToUse);

        long dMem = minecraft.monitoringThread.totalMemory - minecraft.monitoringThread.freeMemory;
        float percent = dMem * 100L / minecraft.monitoringThread.maxMemory;
        if (percent >= 75) {
            colorToUse = redColor;
        } else if (percent >= 50) {
            colorToUse = orangeColor;
        } else {
            colorToUse = greenColor;
        }

        String Usage = "Used memory: " + percent + "% (" + dMem / 1024L / 1024L + "MB)";
        drawString(fontRenderer, Usage, width - fontRenderer.getWidth(Usage) - 15, 35, colorToUse);
        String max = "Allocated memory: " + minecraft.monitoringThread.maxMemory / 1024L / 1024L
                + "MB";
        drawString(fontRenderer, max, width - fontRenderer.getWidth(max) - 15, 46, 15132260);
        super.render(var1, var2);
    }
}

