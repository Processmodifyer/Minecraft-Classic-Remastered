package net.classic.remastered.client.settings;

import org.lwjgl.input.*;
import java.awt.image.*;

import javax.imageio.*;
import java.util.*;
import java.io.*;
import org.lwjgl.opengl.*;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.render.*;

public final class GameSettings
{
    private static final String[] renderDistances;
    public boolean music;
    public boolean sound;
    public boolean invertMouse;
    public boolean showFrameRate;
    public int viewDistance;
    public static final int FIRST_PERSON = 0;
    public static final int THIRD_PERSON_BACK = 1;
    public static final int THIRD_PERSON_FRONT = 2;
    private static final int VIEWDISTANCE_MAX = 0;
    private static final int SMOOTHING_UNIVERSAL = 0;
    private static final int SMOOTHING_OFF = 0;
    private static final String FRAMERATE_LIMITS;
    private static final int MAX_SUPPORTED_FRAMERATE = 0;
    public ThirdPersonMode thirdPersonMode;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean limitFramerate;
    public KeyBinding forwardKey;
    public KeyBinding leftKey;
    public KeyBinding backKey;
    public KeyBinding rightKey;
    public KeyBinding jumpKey;
    public KeyBinding inventoryKey;
    public KeyBinding chatKey;
    public KeyBinding toggleFogKey;
    public KeyBinding saveLocationKey;
    public KeyBinding loadLocationKey;
    public KeyBinding craftingkey;
    public KeyBinding[] bindings;
    private Minecraft minecraft;
    private File settingsFile;
    public int settingCount;
    public int smoothing;
    public String[] smoothingOptions;
    public int anisotropic;
    public String[] anisotropicOptions;
    public KeyBinding runKey;
    private int framerateLimit;
    private byte anisotropy;
    private boolean canServerChangeTextures;
    public boolean showDebug;
    public float scale;
    public boolean creativeMode;
    
    static {
        renderDistances = new String[] { "FAR", "NORMAL", "SHORT", "TINY" };
        FRAMERATE_LIMITS = null;
    }
    
    public GameSettings(final Minecraft minecraft, final File minecraftFolder) {
        this.music = true;
        this.sound = true;
        this.invertMouse = false;
        this.showFrameRate = false;
        this.viewDistance = 0;
        this.thirdPersonMode = ThirdPersonMode.BACK_FACING;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.limitFramerate = false;
        this.forwardKey = new KeyBinding("Forward", 17);
        this.leftKey = new KeyBinding("Left", 30);
        this.backKey = new KeyBinding("Back", 31);
        this.rightKey = new KeyBinding("Right", 32);
        this.jumpKey = new KeyBinding("Jump", 57);
        this.inventoryKey = new KeyBinding("Inventory", 18);
        this.chatKey = new KeyBinding("Chat", 20);
        this.toggleFogKey = new KeyBinding("Toggle fog", 33);
        this.saveLocationKey = new KeyBinding("Save location", 28);
        this.loadLocationKey = new KeyBinding("Load location", 19);
        this.craftingkey = new KeyBinding("Preliminary Craft", 22);
        this.smoothing = 0;
        this.smoothingOptions = new String[] { "OFF", "Automatic", "Universal" };
        this.anisotropic = 0;
        this.anisotropicOptions = new String[] { "OFF", "ON" };
        this.runKey = new KeyBinding("Run", 42);
        this.scale = 1.0f;
        this.creativeMode = false;
        this.bindings = new KeyBinding[] { this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.inventoryKey, this.chatKey, this.toggleFogKey, this.saveLocationKey, this.loadLocationKey, this.runKey, this.craftingkey };
        this.settingCount = 10;
        this.minecraft = minecraft;
        this.settingsFile = new File(minecraftFolder, "options.txt");
        this.load();
    }
    
    public String getBinding(final int key) {
        return String.valueOf(this.bindings[key].name) + ": " + Keyboard.getKeyName(this.bindings[key].key);
    }
    
    public void setBinding(final int key, final int keyID) {
        this.bindings[key].key = keyID;
        this.save();
    }
    
    public void toggleSetting(final int setting, final int fogValue) {
        if (setting == 0) {
            this.music = !this.music;
        }
        if (setting == 1) {
            this.sound = !this.sound;
        }
        if (setting == 2) {
            this.creativeMode = !this.creativeMode;
        }
        if (setting == 3) {
            this.showFrameRate = !this.showFrameRate;
        }
        if (setting == 4) {
            this.viewDistance = (this.viewDistance + fogValue & 0x3);
        }
        if (setting == 5) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (setting == 6) {
            this.anaglyph = !this.anaglyph;
            final TextureManager textureManager = this.minecraft.textureManager;
            for (final int i : this.minecraft.textureManager.textureImages.keySet()) {
                final BufferedImage image = textureManager.textureImages.get(i);
                textureManager.load(image, i);
            }
            for (final String s : textureManager.textures.keySet()) {
                try {
                    BufferedImage image;
                    if (s.startsWith("##")) {
                        image = TextureManager.load1(ImageIO.read(TextureManager.class.getResourceAsStream(s.substring(2))));
                    }
                    else {
                        image = ImageIO.read(TextureManager.class.getResourceAsStream(s));
                    }
                    final int i = textureManager.textures.get(s);
                    textureManager.load(image, i);
                }
                catch (IOException var6) {
                    var6.printStackTrace();
                }
            }
        }
        if (setting == 7) {
            this.limitFramerate = !this.limitFramerate;
        }
        if (setting == 8) {
            if (this.smoothing == this.smoothingOptions.length - 1) {
                this.smoothing = 0;
            }
            else {
                ++this.smoothing;
            }
            this.minecraft.textureManager.textures.clear();
            this.minecraft.textureManager.textureImages.clear();
            this.minecraft.levelRenderer.refresh();
        }
        if (setting == 9) {
            if (this.anisotropic == this.anisotropicOptions.length - 1) {
                this.anisotropic = 0;
            }
            else {
                ++this.anisotropic;
            }
            this.minecraft.textureManager.textures.clear();
            this.minecraft.textureManager.textureImages.clear();
            this.minecraft.levelRenderer.refresh();
        }
        this.save();
    }
    
    public String getSetting(final int id) {
        return (id == 0) ? ("Music: " + (this.music ? "ON" : "OFF")) : ((id == 1) ? ("Sound: " + (this.sound ? "ON" : "OFF")) : ((id == 2) ? ("Mode: " + (this.creativeMode ? "CREATIVE" : "SURVIVAL")) : ((id == 3) ? ("Show FPS: " + (this.showFrameRate ? "ON" : "OFF")) : ((id == 4) ? ("Render distance: " + GameSettings.renderDistances[this.viewDistance]) : ((id == 5) ? ("View bobbing: " + (this.viewBobbing ? "ON" : "OFF")) : ((id == 6) ? ("3d anaglyph: " + (this.anaglyph ? "ON" : "OFF")) : ((id == 7) ? ("Limit framerate: " + (this.limitFramerate ? "ON" : "OFF")) : ((id == 8) ? ("Smoothing: " + this.smoothingOptions[this.smoothing]) : ((id == 9) ? ("Anisotropic: " + this.anisotropicOptions[this.anisotropic]) : "")))))))));
    }
    
    private void load() {
        try {
            if (this.settingsFile.exists()) {
                final FileReader fileReader = new FileReader(this.settingsFile);
                final BufferedReader reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    final String[] setting = line.split(":");
                    if (setting[0].equals("music")) {
                        this.music = setting[1].equals("true");
                    }
                    if (setting[0].equals("sound")) {
                        this.sound = setting[1].equals("true");
                    }
                    if (setting[0].equals("invertYMouse")) {
                        this.invertMouse = setting[1].equals("true");
                    }
                    if (setting[0].equals("showFrameRate")) {
                        this.showFrameRate = setting[1].equals("true");
                    }
                    if (setting[0].equals("viewDistance")) {
                        this.viewDistance = Integer.parseInt(setting[1]);
                    }
                    if (setting[0].equals("bobView")) {
                        this.viewBobbing = setting[1].equals("true");
                    }
                    if (setting[0].equals("anaglyph3d")) {
                        this.anaglyph = setting[1].equals("true");
                    }
                    if (setting[0].equals("limitFramerate")) {
                        this.limitFramerate = setting[1].equals("true");
                    }
                    if (setting[0].equals("smoothing")) {
                        this.smoothing = Integer.parseInt(setting[1]);
                    }
                    if (setting[0].equals("anisotropic")) {
                        this.anisotropic = Integer.parseInt(setting[1]);
                    }
                    if (setting[0].equals("creativeMode")) {
                        this.creativeMode = Boolean.parseBoolean(setting[1]);
                    }
                    for (int index = 0; index < this.bindings.length; ++index) {
                        if (setting[0].equals("key_" + this.bindings[index].name)) {
                            this.bindings[index].key = Integer.parseInt(setting[1]);
                        }
                    }
                }
                reader.close();
            }
        }
        catch (Exception e) {
            System.out.println("Failed to load options");
            e.printStackTrace();
        }
    }
    
    private void save() {
        try {
            final FileWriter fileWriter = new FileWriter(this.settingsFile);
            final PrintWriter writer = new PrintWriter(fileWriter);
            writer.println("music:" + this.music);
            writer.println("sound:" + this.sound);
            writer.println("invertYMouse:" + this.invertMouse);
            writer.println("showFrameRate:" + this.showFrameRate);
            writer.println("viewDistance:" + this.viewDistance);
            writer.println("bobView:" + this.viewBobbing);
            writer.println("anaglyph3d:" + this.anaglyph);
            writer.println("limitFramerate:" + this.limitFramerate);
            writer.println("smoothing:" + this.smoothing);
            writer.println("anisotropic:" + this.anisotropic);
            writer.println("creativeMode:" + this.creativeMode);
            for (int binding = 0; binding < this.bindings.length; ++binding) {
                writer.println("key_" + this.bindings[binding].name + ":" + this.bindings[binding].key);
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Failed to save options");
            e.printStackTrace();
        }
    }
    
    private void parseOneSetting(final String key, final String value) {
        final boolean isTrue = "true".equalsIgnoreCase(value) || "1".equals(value);
        switch (key) {
            case "smoothing": {
                this.smoothing = Math.min(Math.max(Byte.parseByte(value), 0), 0);
                return;
            }
            case "showdebug": {
                return;
            }
            case "anisotropic": {
                this.anisotropy = Byte.parseByte(value);
                return;
            }
            case "canserverchangetextures": {
                this.canServerChangeTextures = isTrue;
                return;
            }
            case "frameratelimit": {
                this.framerateLimit = Integer.parseInt(value);
                if (this.framerateLimit != 0) {
                    this.framerateLimit = Math.min(this.framerateLimit, 0);
                    this.framerateLimit = this.closestTo(GameSettings.FRAMERATE_LIMITS, this.framerateLimit);
                }
                if (Display.isCreated()) {
                    Display.setVSyncEnabled(this.framerateLimit != 0);
                }
                return;
            }
            case "viewdistance": {
                this.viewDistance = Math.min(Math.max(Byte.parseByte(value), this.viewDistance), 0);
                return;
            }
            case "bobview": {
                this.viewBobbing = isTrue;
                return;
            }
            case "music": {
                this.music = isTrue;
                return;
            }
            case "sound": {
                this.sound = isTrue;
                return;
            }
            case "limitframerate": {
                if (isTrue) {
                    this.framerateLimit = 60;
                    return;
                }
                this.framerateLimit = 0;
                return;
            }
            case "invertymouse": {
                this.invertMouse = isTrue;
                return;
            }
            default:
                break;
        }
        KeyBinding[] bindings;
        for (int length = (bindings = this.bindings).length, i = 0; i < length; ++i) {
            final KeyBinding binding = bindings[i];
            if (("key_" + binding.name.toLowerCase()).equals(key)) {
                binding.key = Integer.parseInt(value);
                break;
            }
        }
    }
    
    private int closestTo(final String framerateLimits, final int framerateLimit2) {
        return 0;
    }

	public static int getSmoothingOff() {
		return SMOOTHING_OFF;
	}
}
