package net.classic.remastered.client.gui;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.mainnemu.GenerateLevelScreenMainNemu;
import net.classic.remastered.client.gui.mainnemu.OptionsMainNemu;
import net.classic.remastered.client.settings.GameSettings;


public final class MainNemuScreen
extends GuiScreen {
	private GuiScreen activegui;
    public MainNemuScreen(GuiScreen var1) {
        this.activegui = var1;
    }
	@Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 50, "Play the game!"));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 75, "Multiplayer (WIP)"));
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 125, "Options"));        
	}

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 1) {
            this.minecraft.setCurrentScreen(new GenerateLevelScreenMainNemu(activegui));
        }
        if (var1.id == 4) {
            this.minecraft.setCurrentScreen(new OptionsMainNemu(activegui, this.minecraft.settings));
        }        
    }

    @Override
    public final void render(int var1, int var2) {
        MainNemuScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        MainNemuScreen.drawCenteredString(this.fontRenderer, "Minecraft: Classic Remastered", this.width / 2, 10, 0xFFFFFF);
        super.render(var1, var2);
        final net.classic.remastered.client.render.Tessellator instance = net.classic.remastered.client.render.Tessellator.instance;
        GL11.glBindTexture(3553, this.minecraft.textureManager.load("/gui/logo.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        instance.setColorOpaque_I(16777215);
        this.drawImage((this.width - 256) / 2, 30, 0, 0, 256, 49);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float n;
        GL11.glScalef(n = (n = 1.8f - net.dungland.util.MathHelper.abs(net.dungland.util.MathHelper.sin(System.currentTimeMillis() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f)) * 100.0f / (this.fontRenderer.getWidth("splash") + 32), n, n);
        GL11.glPopMatrix();
        final String s = "License Apache 2.0.";
        GuiScreen.drawString(this.fontRenderer, s, this.width - this.fontRenderer.getWidth(s) - 2, this.height - 10, 16777215);
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final String string = new StringBuilder().append("Free memory: ").append((maxMemory - Runtime.getRuntime().freeMemory()) * 100L / maxMemory).append("% of ").append(maxMemory / 1024L / 1024L).append("MB").toString();
        GuiScreen.drawString(this.fontRenderer, string, this.width - this.fontRenderer.getWidth(string) - 2, 2, 8421504);
        final String string2 = new StringBuilder().append("Allocated memory: ").append(totalMemory * 100L / maxMemory).append("% (").append(totalMemory / 1024L / 1024L).append("MB)").toString();
        GuiScreen.drawString(this.fontRenderer, string2, this.width - this.fontRenderer.getWidth(string2) - 2, 12, 8421504);
    }
}

