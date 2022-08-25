/*
 * 
 */
package net.classic.remastered.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.main.StopGameException;

public final class LevelNameScreen
extends GuiScreen {
    private GuiScreen parent;
    private String title = "Enter level name:";
    private int id;
    private String name;
    private int counter = 0;

    public LevelNameScreen(GuiScreen var1, String var2, int var3) {
        this.parent = var1;
        this.id = var3;
        this.name = var2;
        if (this.name.equals("-")) {
            this.name = "";
        }
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4 + 120, "Save"));
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 144, "Cancel"));
        ((Button)this.buttons.get((int)0)).active = this.name.trim().length() > 1;
    }

    @Override
    public final void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public final void tick() {
        ++this.counter;
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.active) {
            if (var1.id == 0 && this.name.trim().length() > 1) {
                Minecraft var10000 = this.minecraft;
                int var10001 = this.id;
                String var2 = this.name.trim();
                int var3 = var10001;
                Minecraft var4 = var10000;
                try {
					var10000.levelIo.saveOnline(var4.level, var4.host, var4.session.username, var4.session.sessionId, var2, var3);
				} catch (StopGameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                this.minecraft.setCurrentScreen(null);
                this.minecraft.grabMouse();
            }
            if (var1.id == 1) {
                this.minecraft.setCurrentScreen(this.parent);
            }
        }
    }

    @Override
    protected final void onKeyPress(char var1, int var2) {
        if (var2 == 14 && this.name.length() > 0) {
            this.name = this.name.substring(0, this.name.length() - 1);
        }
        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_'*!\"#%/()=+?[]{}<>".indexOf(var1) >= 0 && this.name.length() < 64) {
            this.name = String.valueOf(this.name) + var1;
        }
        ((Button)this.buttons.get((int)0)).active = this.name.trim().length() > 1;
    }

    @Override
    public final void render(int var1, int var2) {
        LevelNameScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        LevelNameScreen.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        int var3 = this.width / 2 - 100;
        int var4 = this.height / 2 - 10;
        LevelNameScreen.drawBox(var3 - 1, var4 - 1, var3 + 200 + 1, var4 + 20 + 1, -6250336);
        LevelNameScreen.drawBox(var3, var4, var3 + 200, var4 + 20, -16777216);
        LevelNameScreen.drawString(this.fontRenderer, String.valueOf(this.name) + (this.counter / 6 % 2 == 0 ? "_" : ""), var3 + 4, var4 + 6, 0xE0E0E0);
        super.render(var1, var2);
    }
}

