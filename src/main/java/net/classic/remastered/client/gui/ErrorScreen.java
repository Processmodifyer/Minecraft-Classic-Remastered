/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.GuiScreen;

public final class ErrorScreen
extends GuiScreen {
    private String title;
    private String text;

    public ErrorScreen(String var1, String var2) {
        this.title = var1;
        this.text = var2;
    }

    @Override
    public final void onOpen() {
    }

    @Override
    public final void render(int var1, int var2) {
        ErrorScreen.drawFadingBox(0, 0, this.width, this.height, -12574688, -11530224);
        ErrorScreen.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 90, 0xFFFFFF);
        ErrorScreen.drawCenteredString(this.fontRenderer, this.text, this.width / 2, 110, 0xFFFFFF);
        super.render(var1, var2);
    }

    @Override
    protected final void onKeyPress(char var1, int var2) {
    }
}

