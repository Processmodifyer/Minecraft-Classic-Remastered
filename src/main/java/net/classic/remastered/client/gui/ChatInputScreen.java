/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.chat.ChatClickData;
import net.classic.remastered.client.gui.ChatScreenData;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.network.PacketType;
import net.dungland.util.LogUtil;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ChatInputScreen
extends GuiScreen {
    public static List<String> history = new ArrayList<String>();
    public static int ChatRGB = new Color(0, 0, 0, 130).getRGB();
    public String inputLine = "";
    public int caretPos = 0;
    int j;
    private int tickCount = 0;
    private int historyPos = 0;
    private LivingEntity Mob;

    private String getClipboard() {
        Transferable clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        try {
            if (clipboard != null && clipboard.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)clipboard.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (UnsupportedFlavorException | IOException exception) {
            // empty catch block
        }
        return null;
    }

    private void setClipboard(String paramString) {
        StringSelection localStringSelection = new StringSelection(paramString);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(localStringSelection, null);
    }

    private void insertTextAtCaret(String paramString) {
        int i = this.minecraft.session != null ? 64 - this.minecraft.session.username.length() - 2 : 64;
        int j = paramString.length();
        this.inputLine = String.valueOf(this.inputLine.substring(0, this.caretPos)) + paramString + this.inputLine.substring(this.caretPos);
        this.caretPos += j;
        if (this.inputLine.length() > i) {
            this.inputLine = this.inputLine.substring(0, i);
        }
        if (this.caretPos > this.inputLine.length()) {
            this.caretPos = this.inputLine.length();
        }
    }

    public String joinToString(String[] Names) throws Exception {
        String buildable = "";
        if (Names == null) {
            throw new Exception("Names cannot be null");
        }
        if (Names.length == 0) {
            return buildable;
        }
        for (int i = 0; i < Names.length; ++i) {
            buildable = String.valueOf(buildable) + Names[i];
            if (i == Names.length) continue;
            buildable = String.valueOf(buildable) + ", ";
        }
        return buildable;
    }

    @Override
    public final void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected final void onKeyPress(char paramChar, int paramInt) {
        boolean j;
        if (paramInt == 1) {
            this.minecraft.setCurrentScreen(null);
            return;
        }
        if (paramInt == 60) {
            this.minecraft.setCurrentScreen(null);
            this.minecraft.takeAndSaveScreenshot(this.minecraft.width, this.minecraft.height);
            this.minecraft.setCurrentScreen(this);
        }
        if (Keyboard.isKeyDown(15)) {
            return;
        }
        if (paramInt == 60) {
            this.minecraft.setCurrentScreen(null);
            this.minecraft.takeAndSaveScreenshot(this.minecraft.width, this.minecraft.height);
            this.minecraft.setCurrentScreen(this);
        }
        if (paramInt == 28) {
            String message = this.inputLine.trim();
            if (message.toLowerCase().startsWith("/client")) {
                if (message.equalsIgnoreCase("/client debug")) {
                    this.minecraft.settings.showDebug = !this.minecraft.settings.showDebug;
                    this.minecraft.hud.addChat("&eDebug: &a" + (!this.minecraft.settings.showDebug ? "On" : "Off") + " -> " + (this.minecraft.settings.showDebug ? "On" : "Off"));
                } else if (message.equalsIgnoreCase("/client gui")) {
                    this.minecraft.canRenderGUI = !this.minecraft.canRenderGUI;
                    this.minecraft.hud.addChat("&eGUI: &a" + (!this.minecraft.canRenderGUI ? "On" : "Off") + " -> " + (this.minecraft.canRenderGUI ? "On" : "Off"));
                } else if (message.equalsIgnoreCase("/client spawnmob")) {
                    this.minecraft.hud.addChat("&eSorry theres is A MOB");
                } else if (message.equalsIgnoreCase("/client hacks")) {
                    this.minecraft.hud.addChat("&eSpeedHack: &a");
                } else if (message.equalsIgnoreCase("/client help")) {
                    this.minecraft.hud.addChat("&a/Client GUI &e- Toggles the GUI");
                    this.minecraft.hud.addChat("&a/Client Debug &e- Toggles the showing of the debug information");
                    this.minecraft.hud.addChat("&a/Client Hacks &e- Toggles being able to use hacks");
                    this.minecraft.hud.addChat("&a/Client SpeedHack &e- Switches between normal and advanced speedhack");
                    this.minecraft.hud.addChat("&a/Client Status &e- Lists the settings and their current state");
                    this.minecraft.hud.addChat("&a/Client Help &e- Displays this current page");
                    this.minecraft.hud.addChat("&eTell us what you want as a command!");
                } else if (message.equalsIgnoreCase("/client status")) {
                    this.minecraft.hud.addChat("&eCurrent client command settings:");
                    this.minecraft.hud.addChat("  &eGUI: &a" + (this.minecraft.canRenderGUI ? "On" : "Off"));
                    this.minecraft.hud.addChat("  &eDebug: &a" + (this.minecraft.settings.showDebug ? "On" : "Off"));
                } else {
                    this.minecraft.hud.addChat("&eTo see a list of client commands type in &a/Client Help");
                }
            } else if (this.minecraft.session == null) {
                this.minecraft.hud.addChat("&f" + message);
            } else if (message.length() > 0 && (message = message.trim()).length() > 0) {
                this.minecraft.networkManager.send(PacketType.CHAT_MESSAGE, -1, message);
            }
            history.add(message);
            this.minecraft.setCurrentScreen(null);
            return;
        }
        int i = this.inputLine.length();
        if (paramInt == 14 && i > 0 && this.caretPos > 0) {
            this.inputLine = String.valueOf(this.inputLine.substring(0, this.caretPos - 1)) + this.inputLine.substring(this.caretPos);
            --this.caretPos;
        }
        if (paramInt == 203 && this.caretPos > 0) {
            --this.caretPos;
        }
        if (paramInt == 205 && this.caretPos < i) {
            ++this.caretPos;
        }
        if (paramInt == 199) {
            this.caretPos = 0;
        }
        if (paramInt == 207) {
            this.caretPos = i;
        }
        if (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) || Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
            if (paramInt == 47) {
                paramChar = '\u0000';
                String clipboardText = this.getClipboard();
                if (clipboardText != null) {
                    this.insertTextAtCaret(clipboardText);
                }
            } else if (paramInt == 46) {
                paramChar = '\u0000';
                this.setClipboard(this.inputLine);
            }
        }
        if (paramInt == 200) {
            this.j = history.size();
            if (this.historyPos < this.j) {
                ++this.historyPos;
                this.inputLine = history.get(this.j - this.historyPos);
                this.caretPos = this.inputLine.length();
            }
        }
        if (paramInt == 208) {
            this.j = history.size();
            if (this.historyPos > 0) {
                --this.historyPos;
                this.inputLine = this.historyPos > 0 ? history.get(this.j - this.historyPos) : "";
                this.caretPos = this.inputLine.length();
            }
        }
        boolean bl = j = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_'*!\\\"#%/()=+?[]{}<>@|$;~`^".indexOf(paramChar) >= 0;
        if (j) {
            this.insertTextAtCaret(String.valueOf(paramChar));
        }
    }

    @Override
    protected final void onMouseClick(int x, int y, int clickType) {
        if (clickType == 0 && this.minecraft.hud.hoveredPlayer != null) {
            this.insertTextAtCaret(String.valueOf(this.minecraft.hud.hoveredPlayer) + " ");
        }
        if (clickType == 0) {
            for (ChatScreenData chatLine : this.minecraft.hud.chatsOnScreen) {
                if (!((float)x >= chatLine.bounds.maxX) || !((float)x <= chatLine.bounds.minX) || !((float)y >= chatLine.bounds.maxY) || !((float)y <= chatLine.bounds.minY)) continue;
                ChatClickData chatClickData = new ChatClickData(chatLine.string);
                for (ChatClickData.LinkData link : chatClickData.getClickedUrls(this.fontRenderer)) {
                    if (x < 4 + link.x0 || x > 4 + link.x1) continue;
                    this.openWebPage(link.url);
                }
            }
        }
    }

    @Override
    public final void onOpen() {
        Keyboard.enableRepeatEvents(true);
    }

    public void openWebPage(URI uri) {
        Desktop desktop;
        Desktop desktop2 = desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            }
            catch (Exception ex) {
                LogUtil.logError("Error opening a chat link: " + uri, ex);
            }
        }
    }

    @Override
    public void render(int paramInt1, int paramInt2) {
        String lineWithCaret = "> " + this.inputLine.substring(0, this.caretPos) + "_";
        if (this.inputLine.length() > this.caretPos) {
            lineWithCaret = String.valueOf(lineWithCaret) + this.inputLine.substring(this.caretPos + 1);
        }
        boolean makeCaret = this.tickCount / 6 % 2 == 0;
        String line = makeCaret ? lineWithCaret : "> " + this.inputLine;
        int x1 = 2;
        int x2 = x1 + this.fontRenderer.getWidth(lineWithCaret) + 4;
        int y1 = this.height - 14;
        int y2 = y1 + 12;
        GuiScreen.drawBox(x1, y1, x2, y2, ChatRGB);
        ChatInputScreen.drawString(this.fontRenderer, line, 4, this.height - 12, 0xE0E0E0);
        int chatSpacing = (int)Math.ceil(9.0f * this.minecraft.settings.scale);
        int x = Mouse.getEventX() * this.width / this.minecraft.width;
        int y = this.height - Mouse.getEventY() * this.height / this.minecraft.height - 1;
        for (ChatScreenData chatLine : this.minecraft.hud.chatsOnScreen) {
            if (!((float)x > chatLine.bounds.maxX) || !((float)x < chatLine.bounds.minX) || !((float)y > chatLine.bounds.maxY) || !((float)y < chatLine.bounds.minY)) continue;
            ChatClickData chatClickData = new ChatClickData(chatLine.string);
            for (ChatClickData.LinkData link : chatClickData.getClickedUrls(this.fontRenderer)) {
                if (x <= 3 + link.x0 || x >= 3 + link.x1) continue;
                GuiScreen.drawBox(3 + link.x0, chatLine.y, 4 + link.x1, chatLine.y + (float)chatSpacing, Integer.MIN_VALUE);
            }
        }
    }

    @Override
    public final void tick() {
        ++this.tickCount;
    }
}

