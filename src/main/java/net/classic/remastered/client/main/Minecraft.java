package net.classic.remastered.client.main;

import org.lwjgl.util.glu.*;


import java.lang.reflect.*;
import javax.swing.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.*;
import org.lwjgl.*;

import javax.sound.sampled.*;

import java.awt.*;
import java.awt.Cursor;
import java.nio.*;
import java.awt.color.*;
import java.util.*;
import java.util.List;
import java.text.*;
import javax.imageio.*;

import net.classic.remastered.client.chat.ChatLine;
import net.classic.remastered.client.gui.*;
import net.classic.remastered.client.model.*;
import net.classic.remastered.client.particle.*;
import net.classic.remastered.client.render.*;
import net.classic.remastered.client.render.Renderer;
import net.classic.remastered.client.render.texture.*;
import net.classic.remastered.client.settings.GameSettings;
import net.classic.remastered.client.sound.*;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.*;
import net.classic.remastered.game.entity.other.*;
import net.classic.remastered.game.gamemode.*;
import net.classic.remastered.game.phys.*;
import net.classic.remastered.game.player.*;
import net.classic.remastered.game.player.inventory.CreativeInventoryStorage;
import net.classic.remastered.game.world.*;
import net.classic.remastered.game.world.generator.*;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.liquid.*;
import net.classic.remastered.game.world.tile.*;
import net.classic.remastered.network.*;
import net.classic.remastered.client.render.texture.TextureFireFX;
import net.dungland.util.*;

import java.awt.image.*;
import java.io.*;

public final class Minecraft implements Runnable
{
    public GameMode gamemode;
    private boolean fullscreen;
    public int width;
    public int height;
    private Timer timer;
    public World level;
    public LevelRenderer levelRenderer;
    public Player player;
    public ParticleManager particleManager;
    public CreativeInventoryStorage session;
    public String host;
    public Canvas canvas;
    public boolean levelLoaded;
    public volatile boolean waiting;
    private org.lwjgl.input.Cursor cursor;
    public TextureManager textureManager;
    public FontRenderer fontRenderer;
    public GuiScreen currentScreen;
    public ProgressBarDisplay progressBar;
    public Renderer renderer;
    public WorldIO levelIo;
    public SoundManager sound;
    private ResourceDownloadThread resourceThread;
    private int ticks;
    private int blockHitTime;
    public String levelName;
    public int levelId;
    public Robot robot;
    public HUDScreen hud;
    public boolean online;
    public NetworkManager networkManager;
    public SoundPlayer soundPlayer;
    public MovingObjectPosition selected;
    public GameSettings settings;
    private MinecraftApplet applet;
    public String server;
    public int port;
    public volatile boolean running;
    public String debug;
    public boolean hasMouse;
    private int lastClick;
    public boolean raining;
    private Object heldBlock;
    private Object packetHandler;
    private boolean isSinglePlayer;
    float delta;
    public boolean canRenderGUI;
	public boolean isMainnemu;
	private Random random;
	public MonitoringThread monitoringThread;
    public static File mcDir;
    
    public static File getMinecraftDirectory() {
        if (Minecraft.mcDir != null) {
            return Minecraft.mcDir;
        }
        final String folder = "mclassic/remastered";
        final String home = System.getProperty("user.home");
        final File minecraftFolder = NewOS.detect().getMinecraftFolder(home, folder);
        if (!minecraftFolder.exists() && !minecraftFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + minecraftFolder);
        }
        return minecraftFolder;
    }
    
    public Minecraft(final Canvas var1, final MinecraftApplet var2, final int var3, final int var4, final boolean var5) {
        this.gamemode = new SurvivalGameMode(this);
        this.fullscreen = false;
        this.timer = new Timer(20.0f);
        this.session = null;
        this.levelLoaded = false;
        this.waiting = false;
        this.currentScreen = null;
        this.progressBar = new ProgressBarDisplay(this);
        this.renderer = new Renderer(this);
        this.delta = this.timer.delta;
        this.levelIo = new WorldIO(this.progressBar);
        this.sound = new SoundManager();
        this.ticks = 0;
        this.blockHitTime = 0;
        this.levelName = null;
        this.levelId = 0;
        this.online = false;
        new HumanoidModel(0.0f);
        this.selected = null;
        this.server = null;
        this.port = 0;
        this.running = false;
        this.debug = "";
        this.hasMouse = false;
        this.lastClick = 0;
        this.raining = false;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
        this.applet = var2;
        new SleepForeverThread(this);
        this.canvas = var1;
        this.width = var3;
        this.height = var4;
        this.fullscreen = var5;
        if (var1 != null) {
            try {
                this.robot = new Robot();
            }
            catch (AWTException var7) {
                var7.printStackTrace();
            }
        }
    }
    
    public final void setCurrentScreen(GuiScreen var1) {
        if (!(this.currentScreen instanceof ErrorScreen)) {
            if (this.currentScreen != null) {
                this.currentScreen.onClose();
            }
            if (var1 == null && this.player.health <= 0) {
                var1 = new GameOverScreen();
            }
            if ((this.currentScreen = var1) != null) {
                if (this.hasMouse) {
                    this.player.releaseAllKeys();
                    this.hasMouse = false;
                    if (this.levelLoaded) {
                        try {
                            Mouse.setNativeCursor(null);
                        }
                        catch (LWJGLException var2) {
                            var2.printStackTrace();
                        }
                    }
                    else {
                        Mouse.setGrabbed(false);
                    }
                }
                final int var3 = this.width * 240 / this.height;
                final int var4 = this.height * 240 / this.height;
                var1.open(this, var3, var4);
                this.online = false;
            }
            else {
                this.grabMouse();
            }
        }
    }
    
    private static void checkGLError(final String var0) {
        final int var;
        if ((var = GL11.glGetError()) != 0) {
            final String var2 = GLU.gluErrorString(var);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + var0);
            System.out.println(String.valueOf(var) + ": " + var2);
            System.exit(0);
        }
    }
    
    private boolean isSystemShuttingDown() {
        try {
            final Field running = Class.forName("java.lang.Shutdown").getDeclaredField("RUNNING");
            final Field state = Class.forName("java.lang.Shutdown").getDeclaredField("state");
            running.setAccessible(true);
            state.setAccessible(true);
            return state.getInt(null) > running.getInt(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public final void shutdown() {
        try {
            if (this.soundPlayer != null) {
                final SoundPlayer var1 = this.soundPlayer;
                this.soundPlayer.running = false;
            }
            if (this.resourceThread != null) {
                final ResourceDownloadThread var2 = this.resourceThread;
                this.resourceThread.running = true;
            }
        }
        catch (Exception ex) {}
        final Minecraft var3 = this;
        if (!this.levelLoaded) {
            try {
                if (var3.level.creativeMode) {
                    WorldIO.save(var3.level, new FileOutputStream(new File(Minecraft.mcDir, "levelc.dat")));
                }
                else {
                    WorldIO.save(var3.level, new FileOutputStream(new File(Minecraft.mcDir, "levels.dat")));
                    if (var3.level.adventureMode) {
                        WorldIO.save(var3.level, new FileOutputStream(new File(Minecraft.mcDir, "levela.dat")));
                    }
                }
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        Mouse.destroy();
        Keyboard.destroy();
        if (!this.isSystemShuttingDown()) {
            Display.destroy();
        }
    }
    
    @Override
    public final void run() {
        this.running = true;
        final String folder = "mclassic/remastered";
        final String home = System.getProperty("user.home", ".");
        final String osName = System.getProperty("os.name").toLowerCase();
        File minecraftFolder = null;
        monitoringThread = new MonitoringThread(1000); // 1s refresh

        switch (OperatingSystemLookup.lookup[(osName.contains("win") ? MinecraftOS.windows : (osName.contains("mac") ? MinecraftOS.macos : (osName.contains("solaris") ? MinecraftOS.solaris : (osName.contains("sunos") ? MinecraftOS.solaris : (osName.contains("linux") ? MinecraftOS.linux : (osName.contains("unix") ? MinecraftOS.linux : MinecraftOS.unknown)))))).ordinal()]) {
            case 1: {
                System.out.println("UNKNOWN OS!!!");
                return;
            }
            case 2: {
                minecraftFolder = new File(home, String.valueOf('.') + folder + '/');
                break;
            }
            case 3: {
                final String appData = System.getenv("APPDATA");
                if (appData != null) {
                    minecraftFolder = new File(appData, "." + folder + '/');
                    break;
                }
                minecraftFolder = new File(home, String.valueOf('.') + folder + '/');
                break;
            }
            case 4: {
                minecraftFolder = new File(home, "Library/Application Support/" + folder);
                break;
            }
            default: {
                minecraftFolder = new File(home, String.valueOf(folder) + '/');
                break;
            }
        }
        if (!minecraftFolder.exists() && !minecraftFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + minecraftFolder);
        }
        Minecraft.mcDir = minecraftFolder;
        try {
            final Minecraft var1 = this;
            if (!minecraftFolder.exists()) {
                JOptionPane.showMessageDialog(null, "Welcome to the Minecraft Classic Remastered!\nPleave give Remastered some time to download required files.\nTHIS CAN TAKE A LONG TIME DEPENDING ON YOUR INTERNET SPEED.", "Welcome to Remastered Client", 1);
            }
            (var1.resourceThread = new ResourceDownloadThread(Minecraft.mcDir, var1)).run();
            System.setProperty("org.lwjgl.librarypath", Minecraft.mcDir + "/native/windows");
            System.setProperty("net.java.games.input.librarypath", Minecraft.mcDir + "/native/windows");
            if (this.canvas != null) {
                Display.setParent(this.canvas);
            }
            else if (this.fullscreen) {
                Display.setFullscreen(true);
                this.width = Display.getDisplayMode().getWidth();
                this.height = Display.getDisplayMode().getHeight();
            }
            else {
                Display.setDisplayMode(new DisplayMode(this.width, this.height));
            }
            Display.setTitle("Minecraft 0.30");
            try {
                Display.create();
            }
            catch (LWJGLException var2) {
                var2.printStackTrace();
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {}
                Display.create();
            }
            Keyboard.create();
            Mouse.create();
            try {
                Controllers.create();
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
            checkGLError("Pre startup");
            GL11.glEnable(3553);
            GL11.glShadeModel(7425);
            GL11.glClearDepth(1.0);
            GL11.glEnable(2929);
            GL11.glDepthFunc(515);
            GL11.glEnable(3008);
            GL11.glAlphaFunc(516, 0.0f);
            GL11.glCullFace(1029);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            checkGLError("Startup");
            this.settings = new GameSettings(this, minecraftFolder);
            this.gamemode = (this.settings.creativeMode ? new CreativeGameMode(this) : new SurvivalGameMode(this));
            (this.textureManager = new TextureManager(this.settings)).registerAnimation(new TextureLavaFX());
            this.textureManager.registerAnimation(new TextureWaterFX());
            this.textureManager.registerAnimation(new TextureFireFX(0));
            this.textureManager.registerAnimation(new TextureFireFX(1));
            this.fontRenderer = new FontRenderer(this.settings, "/default.png", this.textureManager);
            final IntBuffer var4;
            (var4 = BufferUtils.createIntBuffer(256)).clear().limit(256);
            this.levelRenderer = new LevelRenderer(this, this.textureManager);
            NonLivingEntity.initModels();
            LivingEntity.modelCache = new ModelManager();
            GL11.glViewport(0, 0, this.width, this.height);
            if (this.server != null && this.session != null) {
                final World var5;
                (var5 = new World()).setData(8, 8, 8, new byte[512]);
                this.setLevel(var5);
            }
            else {
                final boolean var6 = false;
                try {
                    if (var1.levelName != null) {
                        var1.loadOnlineLevel(var1.levelName, var1.levelId);
                    }
                    else if (!var1.levelLoaded) {
                        World var7 = null;
                        if (this.gamemode instanceof CreativeGameMode) {
                            if ((var7 = var1.levelIo.load(new FileInputStream(new File(Minecraft.mcDir, "levelc.dat")))) != null) {
                                var1.setLevel(var7);
                            }
                        }
                    }
                }
                catch (Exception var8) {
                    var8.printStackTrace();
                }
                if (this.level == null) {
                    this.setCurrentScreen(new MainNemuScreen(currentScreen));
                }
                
            }
            this.particleManager = new ParticleManager(this.level, this.textureManager);
            if (this.levelLoaded) {
            }
            try {
                var1.soundPlayer = new SoundPlayer(var1.settings);
                final SoundPlayer var10 = var1.soundPlayer;
                try {
                    final AudioFormat var11 = new AudioFormat(44100.0f, 16, 2, true, true);
                    (var10.dataLine = AudioSystem.getSourceDataLine(var11)).open(var11, 4410);
                    var10.dataLine.start();
                    var10.running = true;
                    final Thread var12;
                    (var12 = new Thread(var10)).setDaemon(true);
                    var12.setPriority(10);
                    var12.start();
                }
                catch (Exception var13) {
                    var13.printStackTrace();
                    var10.running = false;
                }
            }
            catch (Exception ex2) {}
            checkGLError("Post startup");
            this.hud = new HUDScreen(this, this.width, this.height);
            new SkinDownloadThread(this).start();
            if (this.server != null && this.session != null) {
                this.networkManager = new NetworkManager(this, this.server, this.port, this.session.username, this.session.mppass);
            }
        }
        catch (Exception var14) {
            var14.printStackTrace();
            JOptionPane.showMessageDialog(null, var14.toString(), "Failed to start Minecraft", 0);
            return;
        }
        long var15 = System.currentTimeMillis();
        int var16 = 0;
        try {
            while (this.running) {
                if (this.waiting) {
                    Thread.sleep(100L);
                }
                else {
                    if (this.canvas == null && Display.isCloseRequested()) {
                        this.running = false;
                    }
                    if (!Display.isFullscreen() && (this.canvas.getWidth() != Display.getDisplayMode().getWidth() || this.canvas.getHeight() != Display.getDisplayMode().getHeight())) {
                        final DisplayMode displayMode = new DisplayMode(this.canvas.getWidth(), this.canvas.getHeight());
                        try {
                            Display.setDisplayMode(displayMode);
                        }
                        catch (LWJGLException e) {
                            e.printStackTrace();
                        }
                        this.resize();
                    }
                    try {
                        final Timer var17 = this.timer;
                        final long var19;
                        final long var18 = (var19 = System.currentTimeMillis()) - var17.lastSysClock;
                        final long var20 = System.nanoTime() / 1000000L;
                        if (var18 > 1000L) {
                            final long var21 = var20 - var17.lastHRClock;
                            final double var22 = var18 / (double)var21;
                            final Timer timer = var17;
                            timer.adjustment += (var22 - var17.adjustment) * 0.20000000298023224;
                            var17.lastSysClock = var19;
                            var17.lastHRClock = var20;
                        }
                        if (var18 < 0L) {
                            var17.lastSysClock = var19;
                            var17.lastHRClock = var20;
                        }
                        final double var23;
                        double var22 = ((var23 = var20 / 1000.0) - var17.lastHR) * var17.adjustment;
                        var17.lastHR = var23;
                        if (var22 < 0.0) {
                            var22 = 0.0;
                        }
                        if (var22 > 1.0) {
                            var22 = 1.0;
                        }
                        var17.elapsedDelta += (float)(var22 * var17.speed * var17.tps);
                        var17.elapsedTicks = (int)var17.elapsedDelta;
                        if (var17.elapsedTicks > 100) {
                            var17.elapsedTicks = 100;
                        }
                        final Timer timer2 = var17;
                        timer2.elapsedDelta -= var17.elapsedTicks;
                        var17.delta = var17.elapsedDelta;
                        for (int var24 = 0; var24 < this.timer.elapsedTicks; ++var24) {
                            ++this.ticks;
                            this.tick();
                        }
                        checkGLError("Pre render");
                        GL11.glEnable(3553);
                        if (!this.online) {
                            this.gamemode.applyCracks(this.timer.delta);
                            final float var25 = this.timer.delta;
                            final Renderer var26 = this.renderer;
                            if (this.renderer.displayActive && !Display.isActive()) {
                                var26.minecraft.pause();
                            }
                            var26.displayActive = Display.isActive();
                            if (var26.minecraft.hasMouse) {
                                int var27 = 0;
                                int var28 = 0;
                                if (var26.minecraft.levelLoaded) {
                                    if (var26.minecraft.canvas != null) {
                                        final Point var30;
                                        final int var29 = (var30 = var26.minecraft.canvas.getLocationOnScreen()).x + var26.minecraft.width / 2;
                                        final int var31 = var30.y + var26.minecraft.height / 2;
                                        final Point var32;
                                        var27 = (var32 = MouseInfo.getPointerInfo().getLocation()).x - var29;
                                        var28 = -(var32.y - var31);
                                        var26.minecraft.robot.mouseMove(var29, var31);
                                    }
                                    else {
                                        Mouse.setCursorPosition(var26.minecraft.width / 2, var26.minecraft.height / 2);
                                    }
                                }
                                else {
                                    var27 = Mouse.getDX();
                                    var28 = Mouse.getDY();
                                }
                                byte var33 = 1;
                                if (var26.minecraft.settings.invertMouse) {
                                    var33 = -1;
                                }
                                var26.minecraft.player.turn((float)var27, (float)(var28 * var33));
                            }
                            if (!var26.minecraft.online) {
                                int var27 = var26.minecraft.width * 240 / var26.minecraft.height;
                                int var28 = var26.minecraft.height * 240 / var26.minecraft.height;
                                final int var34 = Mouse.getX() * var27 / var26.minecraft.width;
                                final int var29 = var28 - Mouse.getY() * var28 / var26.minecraft.height - 1;
                                Label_7903: {
                                    if (var26.minecraft.level != null) {
                                        final float var35 = var25;
                                        final Renderer var36 = var26;
                                        Renderer var37 = var26;
                                        Player var39;
                                        float var38 = (var39 = var26.minecraft.player).xRotO + (var39.xRot - var39.xRotO) * var25;
                                        float var40 = var39.yRotO + (var39.yRot - var39.yRotO) * var25;
                                        Vec3D var41 = var26.getPlayerVector(var25);
                                        float var42 = MathHelper.cos(-var40 * 0.017453292f - 3.1415927f);
                                        float var43 = MathHelper.sin(-var40 * 0.017453292f - 3.1415927f);
                                        float var44 = MathHelper.cos(-var38 * 0.017453292f);
                                        float var45 = MathHelper.sin(-var38 * 0.017453292f);
                                        float var46 = var43 * var44;
                                        float var47 = var42 * var44;
                                        float var48 = var26.minecraft.gamemode.getReachDistance();
                                        Vec3D var49 = var41.add(var46 * var48, var45 * var48, var47 * var48);
                                        var26.minecraft.selected = var26.minecraft.level.clip(var41, var49);
                                        var44 = var48;
                                        if (var26.minecraft.selected != null) {
                                            var44 = var26.minecraft.selected.vec.distance(var26.getPlayerVector(var25));
                                        }
                                        var41 = var26.getPlayerVector(var25);
                                        if (var26.minecraft.gamemode instanceof CreativeGameMode) {
                                            var48 = 32.0f;
                                        }
                                        else {
                                            var48 = var44;
                                        }
                                        var49 = var41.add(var46 * var48, var45 * var48, var47 * var48);
                                        var26.entity = null;
                                        final List var50 = var26.minecraft.level.blockMap.getEntities(var39, var39.bb.expand(var46 * var48, var45 * var48, var47 * var48));
                                        float var51 = 0.0f;
                                        for (var27 = 0; var27 < var50.size(); ++var27) {
                                            final Entity var52;
                                            if ((var52 = (Entity) var50.get(var27)).isPickable()) {
                                                var44 = 0.1f;
                                                final MovingObjectPosition var53;
                                                if ((var53 = var52.bb.grow(var44, var44, var44).clip(var41, var49)) != null && ((var44 = var41.distance(var53.vec)) < var51 || var51 == 0.0f)) {
                                                    var37.entity = var52;
                                                    var51 = var44;
                                                }
                                            }
                                        }
                                        if (var37.entity != null && !(var37.minecraft.gamemode instanceof CreativeGameMode)) {
                                            var37.minecraft.selected = new MovingObjectPosition(var37.entity);
                                        }
                                        while (true) {
                                            for (int var54 = 0; var54 < 2; ++var54) {
                                                if (var36.minecraft.settings.anaglyph) {
                                                    if (var54 == 0) {
                                                        GL11.glColorMask(false, true, true, false);
                                                    }
                                                    else {
                                                        GL11.glColorMask(true, false, false, false);
                                                    }
                                                }
                                                final Player var55 = var36.minecraft.player;
                                                final World var56 = var36.minecraft.level;
                                                final LevelRenderer var57 = var36.minecraft.levelRenderer;
                                                final ParticleManager var58 = var36.minecraft.particleManager;
                                                GL11.glViewport(0, 0, var36.minecraft.width, var36.minecraft.height);
                                                final World var59 = var36.minecraft.level;
                                                var39 = var36.minecraft.player;
                                                var38 = 1.0f / (4 - var36.minecraft.settings.viewDistance);
                                                var38 = 1.0f - (float)Math.pow(var38, 0.25);
                                                var40 = (var59.skyColor >> 16 & 0xFF) / 255.0f;
                                                float var60 = (var59.skyColor >> 8 & 0xFF) / 255.0f;
                                                var42 = (var59.skyColor & 0xFF) / 255.0f;
                                                var36.fogRed = (var59.fogColor >> 16 & 0xFF) / 255.0f;
                                                var36.fogBlue = (var59.fogColor >> 8 & 0xFF) / 255.0f;
                                                var36.fogGreen = (var59.fogColor & 0xFF) / 255.0f;
                                                final Renderer renderer = var36;
                                                renderer.fogRed += (var40 - var36.fogRed) * var38;
                                                final Renderer renderer2 = var36;
                                                renderer2.fogBlue += (var60 - var36.fogBlue) * var38;
                                                final Renderer renderer3 = var36;
                                                renderer3.fogGreen += (var42 - var36.fogGreen) * var38;
                                                final Renderer renderer4 = var36;
                                                renderer4.fogRed *= var36.fogColorMultiplier;
                                                final Renderer renderer5 = var36;
                                                renderer5.fogBlue *= var36.fogColorMultiplier;
                                                final Renderer renderer6 = var36;
                                                renderer6.fogGreen *= var36.fogColorMultiplier;
                                                Block var61;
                                                if ((var61 = Block.blocks[var59.getTile((int)var39.x, (int)(var39.y + 0.12f), (int)var39.z)]) != null && var61.getLiquidType() != LiquidType.NOT_LIQUID) {
                                                    final LiquidType var62;
                                                    if ((var62 = var61.getLiquidType()) == LiquidType.WATER) {
                                                        var36.fogRed = 0.02f;
                                                        var36.fogBlue = 0.02f;
                                                        var36.fogGreen = 0.2f;
                                                    }
                                                    else if (var62 == LiquidType.LAVA) {
                                                        var36.fogRed = 0.6f;
                                                        var36.fogBlue = 0.1f;
                                                        var36.fogGreen = 0.0f;
                                                    }
                                                }
                                                if (var36.minecraft.settings.anaglyph) {
                                                    var44 = (var36.fogRed * 30.0f + var36.fogBlue * 59.0f + var36.fogGreen * 11.0f) / 100.0f;
                                                    var45 = (var36.fogRed * 30.0f + var36.fogBlue * 70.0f) / 100.0f;
                                                    var46 = (var36.fogRed * 30.0f + var36.fogGreen * 70.0f) / 100.0f;
                                                    var36.fogRed = var44;
                                                    var36.fogBlue = var45;
                                                    var36.fogGreen = var46;
                                                }
                                                GL11.glClearColor(var36.fogRed, var36.fogBlue, var36.fogGreen, 0.0f);
                                                GL11.glClear(16640);
                                                var36.fogColorMultiplier = 1.0f;
                                                GL11.glEnable(2884);
                                                var36.fogEnd = (float)(512 >> (var36.minecraft.settings.viewDistance << 1));
                                                GL11.glMatrixMode(5889);
                                                GL11.glLoadIdentity();
                                                var38 = 0.07f;
                                                if (var36.minecraft.settings.anaglyph) {
                                                    GL11.glTranslatef(-((var54 << 1) - 1) * var38, 0.0f, 0.0f);
                                                }
                                                Player var63 = var36.minecraft.player;
                                                var43 = 70.0f;
                                                if (var63.health <= 0) {
                                                    var44 = var63.deathTime + var35;
                                                    var43 /= (1.0f - 500.0f / (var44 + 500.0f)) * 2.0f + 1.0f;
                                                }
                                                GLU.gluPerspective(var43, var36.minecraft.width / (float)var36.minecraft.height, 0.05f, var36.fogEnd);
                                                GL11.glMatrixMode(5888);
                                                GL11.glLoadIdentity();
                                                if (var36.minecraft.settings.anaglyph) {
                                                    GL11.glTranslatef(((var54 << 1) - 1) * 0.1f, 0.0f, 0.0f);
                                                }
                                                var36.hurtEffect(var35);
                                                if (var36.minecraft.settings.viewBobbing) {
                                                    var36.applyBobbing(var35);
                                                }
                                                var63 = var36.minecraft.player;
                                                GL11.glTranslatef(0.0f, 0.0f, -0.1f);
                                                GL11.glRotatef(var63.xRotO + (var63.xRot - var63.xRotO) * var35, 1.0f, 0.0f, 0.0f);
                                                GL11.glRotatef(var63.yRotO + (var63.yRot - var63.yRotO) * var35, 0.0f, 1.0f, 0.0f);
                                                var43 = var63.xo + (var63.x - var63.xo) * var35;
                                                var44 = var63.yo + (var63.y - var63.yo) * var35;
                                                var45 = var63.zo + (var63.z - var63.zo) * var35;
                                                GL11.glTranslatef(-var43, -var44, -var45);
                                                final Frustrum var65;
                                                final Frustrum var64 = var65 = FrustrumImpl.update();
                                                LevelRenderer var66 = var36.minecraft.levelRenderer;
                                                for (int var67 = 0; var67 < var66.chunkCache.length; ++var67) {
                                                    var66.chunkCache[var67].clip(var65);
                                                }
                                                var66 = var36.minecraft.levelRenderer;
                                                Collections.sort((List<Object>)var36.minecraft.levelRenderer.chunks, new ChunkDirtyDistanceComparator(var55));
                                                int var67 = var66.chunks.size() - 1;
                                                int var68;
                                                if ((var68 = var66.chunks.size()) > 3) {
                                                    var68 = 3;
                                                }
                                                for (int var69 = 0; var69 < var68; ++var69) {
                                                    final Chunk var70;
                                                    (var70 = (Chunk) var66.chunks.remove(var67 - var69)).update();
                                                    var70.loaded = false;
                                                }
                                                var36.updateFog();
                                                GL11.glEnable(2912);
                                                var57.sortChunks(var55, 0);
                                                if (var56.isSolid(var55.x, var55.y, var55.z, 0.1f)) {
                                                    final int var71 = (int)var55.x;
                                                    final int var72 = (int)var55.y;
                                                    final int var73 = (int)var55.z;
                                                    for (int var74 = var71 - 1; var74 <= var71 + 1; ++var74) {
                                                        for (int var75 = var72 - 1; var75 <= var72 + 1; ++var75) {
                                                            for (int var76 = var73 - 1; var76 <= var73 + 1; ++var76) {
                                                                var68 = var76;
                                                                var67 = var75;
                                                                final int var77 = var74;
                                                                final int var69;
                                                                if ((var69 = var57.level.getTile(var74, var75, var76)) != 0 && Block.blocks[var69].isSolid()) {
                                                                    GL11.glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
                                                                    GL11.glDepthFunc(513);
                                                                    final ShapeRenderer var78 = ShapeRenderer.instance;
                                                                    ShapeRenderer.instance.begin();
                                                                    for (int var79 = 0; var79 < 6; ++var79) {
                                                                        Block.blocks[var69].renderInside(var78, var77, var67, var68, var79);
                                                                    }
                                                                    var78.end();
                                                                    GL11.glCullFace(1028);
                                                                    var78.begin();
                                                                    for (int var79 = 0; var79 < 6; ++var79) {
                                                                        Block.blocks[var69].renderInside(var78, var77, var67, var68, var79);
                                                                    }
                                                                    var78.end();
                                                                    GL11.glCullFace(1029);
                                                                    GL11.glDepthFunc(515);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                var36.setLighting(true);
                                                final Vec3D var80 = var36.getPlayerVector(var35);
                                                var57.level.blockMap.render(var80, var64, var57.textureManager, var35);
                                                var36.setLighting(false);
                                                var36.updateFog();
                                                float var81 = var35;
                                                final ParticleManager var82 = var58;
                                                var38 = -MathHelper.cos(var55.yRot * 3.1415927f / 180.0f);
                                                var60 = -(var40 = -MathHelper.sin(var55.yRot * 3.1415927f / 180.0f)) * MathHelper.sin(var55.xRot * 3.1415927f / 180.0f);
                                                var42 = var38 * MathHelper.sin(var55.xRot * 3.1415927f / 180.0f);
                                                var43 = MathHelper.cos(var55.xRot * 3.1415927f / 180.0f);
                                                for (int var72 = 0; var72 < 2; ++var72) {
                                                    if (var82.particles[var72].size() != 0) {
                                                        int var73 = 0;
                                                        if (var72 == 0) {
                                                            var73 = var82.textureManager.load("/particles.png");
                                                        }
                                                        if (var72 == 1) {
                                                            var73 = var82.textureManager.load("/terrain.png");
                                                        }
                                                        GL11.glBindTexture(3553, var73);
                                                        final ShapeRenderer var83 = ShapeRenderer.instance;
                                                        ShapeRenderer.instance.begin();
                                                        for (int var71 = 0; var71 < var82.particles[var72].size(); ++var71) {
                                                            ((Particle) var82.particles[var72].get(var71)).render(var83, var81, var38, var43, var40, var60, var42);
                                                        }
                                                        var83.end();
                                                    }
                                                }
                                                GL11.glBindTexture(3553, var57.textureManager.load("/rock.png"));
                                                GL11.glEnable(3553);
                                                GL11.glCallList(var57.listId);
                                                var36.updateFog();
                                                var66 = var57;
                                                
                                                final Tessellator instance;
                                                (instance = Tessellator.instance).startDrawingQuads();
                                                GL11.glBindTexture(3553, var57.textureManager.load("/terrain/sun.png"));
                                                instance.startDrawingQuads();
                                                instance.addVertexWithUV(-30.0f, 100.0f, -30.0f, 0.0f, 0.0f);
                                                instance.addVertexWithUV(30.0f, 100.0f, -30.0f, 1.0f, 0.0f);
                                                instance.addVertexWithUV(30.0f, 100.0f, 30.0f, 1.0f, 1.0f);
                                                instance.addVertexWithUV(-30.0f, 100.0f, 30.0f, 0.0f, 1.0f);
                                                instance.draw();
                                                
                                                GL11.glBindTexture(3553, var57.textureManager.load("/cloudsnight.png"));
                                                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                                var81 = (var57.level.cloudColor >> 16 & 0xFF) / 255.0f;
                                                var38 = (var57.level.cloudColor >> 8 & 0xFF) / 255.0f;
                                                var40 = (var57.level.cloudColor & 0xFF) / 255.0f;
                                                if (var57.minecraft.settings.anaglyph) {
                                                    var60 = (var81 * 30.0f + var38 * 59.0f + var40 * 11.0f) / 100.0f;
                                                    var42 = (var81 * 30.0f + var38 * 70.0f) / 100.0f;
                                                    var43 = (var81 * 30.0f + var40 * 70.0f) / 100.0f;
                                                    var81 = var60;
                                                    var38 = var42;
                                                    var40 = var43;
                                                }
     
                                                final ShapeRenderer var78 = ShapeRenderer.instance;
                                                var44 = 0.0f;
                                                var45 = 4.8828125E-4f;
                                                var44 = (float)(var57.level.depth + 2);
                                                var46 = (var57.ticks + var35) * var45 * 0.03f;
                                                var51 = 0.0f;
                                                var78.begin();
                                                var78.color(var81, var38, var40);
                                                for (var28 = -2048; var28 < var66.level.width + 2048; var28 += 512) {
                                                    for (int var75 = -2048; var75 < var66.level.height + 2048; var75 += 512) {
                                                        var78.vertexUV((float)var28, var44, (float)(var75 + 512), var28 * var45 + var46, (var75 + 512) * var45);
                                                        var78.vertexUV((float)(var28 + 512), var44, (float)(var75 + 512), (var28 + 512) * var45 + var46, (var75 + 512) * var45);
                                                        var78.vertexUV((float)(var28 + 512), var44, (float)var75, (var28 + 512) * var45 + var46, var75 * var45);
                                                        var78.vertexUV((float)var28, var44, (float)var75, var28 * var45 + var46, var75 * var45);
                                                        var78.vertexUV((float)var28, var44, (float)var75, var28 * var45 + var46, var75 * var45);
                                                        var78.vertexUV((float)(var28 + 512), var44, (float)var75, (var28 + 512) * var45 + var46, var75 * var45);
                                                        var78.vertexUV((float)(var28 + 512), var44, (float)(var75 + 512), (var28 + 512) * var45 + var46, (var75 + 512) * var45);
                                                        var78.vertexUV((float)var28, var44, (float)(var75 + 512), var28 * var45 + var46, (var75 + 512) * var45);
                                                    }
                                                }
                                                var78.end();
                                                GL11.glDisable(3553);
                                                var78.begin();
                                                var46 = (var66.level.skyColor >> 16 & 0xFF) / 255.0f;
                                                var51 = (var66.level.skyColor >> 8 & 0xFF) / 255.0f;
                                                var47 = (var66.level.skyColor & 0xFF) / 255.0f;
                                                if (var66.minecraft.settings.anaglyph) {
                                                    var48 = (var46 * 30.0f + var51 * 59.0f + var47 * 11.0f) / 100.0f;
                                                    var43 = (var46 * 30.0f + var51 * 70.0f) / 100.0f;
                                                    var44 = (var46 * 30.0f + var47 * 70.0f) / 100.0f;
                                                    var46 = var48;
                                                    var51 = var43;
                                                    var47 = var44;
                                                }
                                                var78.color(var46, var51, var47);
                                                var44 = (float)(var66.level.depth + 10);
                                                for (int var75 = -2048; var75 < var66.level.width + 2048; var75 += 512) {
                                                    for (int var31 = -2048; var31 < var66.level.height + 2048; var31 += 512) {
                                                        var78.vertex((float)var75, var44, (float)var31);
                                                        var78.vertex((float)(var75 + 512), var44, (float)var31);
                                                        var78.vertex((float)(var75 + 512), var44, (float)(var31 + 512));
                                                        var78.vertex((float)var75, var44, (float)(var31 + 512));
                                                    }
                                                }
                                                var78.end();
                                                GL11.glEnable(3553);
                                                var36.updateFog();
                                                if (var36.minecraft.selected != null) {
                                                    GL11.glDisable(3008);
                                                    MovingObjectPosition var84 = var36.minecraft.selected;
                                                    var68 = var55.inventory.getSelected();
                                                    boolean var85 = false;
                                                    MovingObjectPosition var86 = var84;
                                                    var66 = var57;
                                                    final ShapeRenderer var87 = ShapeRenderer.instance;
                                                    GL11.glEnable(3042);
                                                    GL11.glEnable(3008);
                                                    GL11.glBlendFunc(770, 1);
                                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, (MathHelper.sin(System.currentTimeMillis() / 100.0f) * 0.2f + 0.4f) * 0.5f);
                                                    if (var57.cracks > 0.0f) {
                                                        GL11.glBlendFunc(774, 768);
                                                        final int var88 = var57.textureManager.load("/terrain.png");
                                                        GL11.glBindTexture(3553, var88);
                                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                                                        GL11.glPushMatrix();
                                                        final int var79;
                                                        final Block var89 = var61 = (((var79 = var57.level.getTile(var86.x, var86.y, var86.z)) > 0) ? Block.blocks[var79] : null);
                                                        var44 = (var89.x1 + var61.x2) / 2.0f;
                                                        var45 = (var61.y1 + var61.y2) / 2.0f;
                                                        var46 = (var61.z1 + var61.z2) / 2.0f;
                                                        GL11.glTranslatef(var86.x + var44, var86.y + var45, var86.z + var46);
                                                        var51 = 1.01f;
                                                        GL11.glScalef(1.01f, var51, var51);
                                                        GL11.glTranslatef(-(var86.x + var44), -(var86.y + var45), -(var86.z + var46));
                                                        var87.begin();
                                                        var87.noColor();
                                                        GL11.glDepthMask(false);
                                                        if (var61 == null) {
                                                            var61 = Block.STONE;
                                                        }
                                                        for (var28 = 0; var28 < 6; ++var28) {
                                                            var61.renderSide(var87, var86.x, var86.y, var86.z, var28, 240 + (int)(var66.cracks * 10.0f));
                                                        }
                                                        var87.end();
                                                        GL11.glDepthMask(true);
                                                        GL11.glPopMatrix();
                                                    }
                                                    GL11.glDisable(3042);
                                                    GL11.glDisable(3008);
                                                    var84 = var36.minecraft.selected;
                                                    var55.inventory.getSelected();
                                                    var85 = false;
                                                    var86 = var84;
                                                    GL11.glEnable(3042);
                                                    GL11.glBlendFunc(770, 771);
                                                    GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
                                                    GL11.glLineWidth(2.0f);
                                                    GL11.glDisable(3553);
                                                    GL11.glDepthMask(false);
                                                    var38 = 0.002f;
                                                    final int var69;
                                                    if ((var69 = var57.level.getTile(var86.x, var86.y, var86.z)) > 0) {
                                                        final AABB var90 = Block.blocks[var69].getSelectionBox(var86.x, var86.y, var86.z).grow(var38, var38, var38);
                                                        GL11.glBegin(3);
                                                        GL11.glVertex3f(var90.x0, var90.y0, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y0, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y0, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y0, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y0, var90.z0);
                                                        GL11.glEnd();
                                                        GL11.glBegin(3);
                                                        GL11.glVertex3f(var90.x0, var90.y1, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y1, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y1, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y1, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y1, var90.z0);
                                                        GL11.glEnd();
                                                        GL11.glBegin(1);
                                                        GL11.glVertex3f(var90.x0, var90.y0, var90.z0);
                                                        GL11.glVertex3f(var90.x0, var90.y1, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y0, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y1, var90.z0);
                                                        GL11.glVertex3f(var90.x1, var90.y0, var90.z1);
                                                        GL11.glVertex3f(var90.x1, var90.y1, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y0, var90.z1);
                                                        GL11.glVertex3f(var90.x0, var90.y1, var90.z1);
                                                        GL11.glEnd();
                                                    }
                                                    GL11.glDepthMask(true);
                                                    GL11.glEnable(3553);
                                                    GL11.glDisable(3042);
                                                    GL11.glEnable(3008);
                                                }
                                                GL11.glBlendFunc(770, 771);
                                                var36.updateFog();
                                                GL11.glEnable(3553);
                                                GL11.glEnable(3042);
                                                GL11.glBindTexture(3553, var57.textureManager.load("/water.png"));
                                                GL11.glCallList(var57.listId + 1);
                                                GL11.glDisable(3042);
                                                GL11.glEnable(3042);
                                                GL11.glColorMask(false, false, false, false);
                                                int var71 = var57.sortChunks(var55, 1);
                                                GL11.glColorMask(true, true, true, true);
                                                if (var36.minecraft.settings.anaglyph) {
                                                    if (var54 == 0) {
                                                        GL11.glColorMask(false, true, true, false);
                                                    }
                                                    else {
                                                        GL11.glColorMask(true, false, false, false);
                                                    }
                                                }
                                                if (var71 > 0) {
                                                    GL11.glBindTexture(3553, var57.textureManager.load("/terrain.png"));
                                                    GL11.glCallLists(var57.buffer);
                                                }
                                                GL11.glDepthMask(true);
                                                GL11.glDisable(3042);
                                                GL11.glDisable(2912);
                                                if (var36.minecraft.raining) {
                                                    final float var91 = var35;
                                                    var37 = var36;
                                                    var39 = var36.minecraft.player;
                                                    final World var92 = var36.minecraft.level;
                                                    final int var69 = (int)var39.x;
                                                    final int var88 = (int)var39.y;
                                                    final int var79 = (int)var39.z;
                                                    final ShapeRenderer var93 = ShapeRenderer.instance;
                                                    GL11.glDisable(2884);
                                                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                                                    GL11.glEnable(3042);
                                                    GL11.glBlendFunc(770, 771);
                                                    GL11.glBindTexture(3553, var36.minecraft.textureManager.load("/rain.png"));
                                                    for (int var73 = var69 - 5; var73 <= var69 + 5; ++var73) {
                                                        for (int var74 = var79 - 5; var74 <= var79 + 5; ++var74) {
                                                            var71 = var92.getHighestTile(var73, var74);
                                                            var28 = var88 - 5;
                                                            int var75 = var88 + 5;
                                                            if (var28 < var71) {
                                                                var28 = var71;
                                                            }
                                                            if (var75 < var71) {
                                                                var75 = var71;
                                                            }
                                                            if (var28 != var75) {
                                                                var44 = ((var37.levelTicks + var73 * 3121 + var74 * 418711) % 32 + var91) / 32.0f;
                                                                final float var94 = var73 + 0.5f - var39.x;
                                                                var51 = var74 + 0.5f - var39.z;
                                                                final float var95 = MathHelper.sqrt(var94 * var94 + var51 * var51) / 5.0f;
                                                                GL11.glColor4f(1.0f, 1.0f, 1.0f, (1.0f - var95 * var95) * 0.7f);
                                                                var93.begin();
                                                                var93.vertexUV((float)var73, (float)var28, (float)var74, 0.0f, var28 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)(var73 + 1), (float)var28, (float)(var74 + 1), 2.0f, var28 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)(var73 + 1), (float)var75, (float)(var74 + 1), 2.0f, var75 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)var73, (float)var75, (float)var74, 0.0f, var75 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)var73, (float)var28, (float)(var74 + 1), 0.0f, var28 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)(var73 + 1), (float)var28, (float)var74, 2.0f, var28 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)(var73 + 1), (float)var75, (float)var74, 2.0f, var75 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.vertexUV((float)var73, (float)var75, (float)(var74 + 1), 0.0f, var75 * 2.0f / 8.0f + var44 * 2.0f);
                                                                var93.end();
                                                            }
                                                        }
                                                    }
                                                    GL11.glEnable(2884);
                                                    GL11.glDisable(3042);
                                                }
                                                if (var36.entity != null) {
                                                    var36.entity.renderHover(var36.minecraft.textureManager, var35);
                                                }
                                                GL11.glClear(256);
                                                GL11.glLoadIdentity();
                                                if (var36.minecraft.settings.anaglyph) {
                                                    GL11.glTranslatef(((var54 << 1) - 1) * 0.1f, 0.0f, 0.0f);
                                                }
                                                var36.hurtEffect(var35);
                                                if (var36.minecraft.settings.viewBobbing) {
                                                    var36.applyBobbing(var35);
                                                }
                                                final HeldBlock var96 = var36.heldBlock;
                                                var60 = var36.heldBlock.lastPos + (var96.pos - var96.lastPos) * var35;
                                                var63 = var96.minecraft.player;
                                                GL11.glPushMatrix();
                                                GL11.glRotatef(var63.xRotO + (var63.xRot - var63.xRotO) * var35, 1.0f, 0.0f, 0.0f);
                                                GL11.glRotatef(var63.yRotO + (var63.yRot - var63.yRotO) * var35, 0.0f, 1.0f, 0.0f);
                                                var96.minecraft.renderer.setLighting(true);
                                                GL11.glPopMatrix();
                                                GL11.glPushMatrix();
                                                var43 = 0.8f;
                                                if (var96.moving) {
                                                    var45 = MathHelper.sin((var44 = (var96.offset + var35) / 7.0f) * 3.1415927f);
                                                    GL11.glTranslatef(-MathHelper.sin(MathHelper.sqrt(var44) * 3.1415927f) * 0.4f, MathHelper.sin(MathHelper.sqrt(var44) * 3.1415927f * 2.0f) * 0.2f, -var45 * 0.2f);
                                                }
                                                GL11.glTranslatef(0.7f * var43, -0.65f * var43 - (1.0f - var60) * 0.6f, -0.9f * var43);
                                                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                                                GL11.glEnable(2977);
                                                if (var96.moving) {
                                                    var45 = MathHelper.sin((var44 = (var96.offset + var35) / 7.0f) * var44 * 3.1415927f);
                                                    GL11.glRotatef(MathHelper.sin(MathHelper.sqrt(var44) * 3.1415927f) * 80.0f, 0.0f, 1.0f, 0.0f);
                                                    GL11.glRotatef(-var45 * 20.0f, 1.0f, 0.0f, 0.0f);
                                                }
                                                GL11.glColor4f(var44 = var96.minecraft.level.getBrightness((int)var63.x, (int)var63.y, (int)var63.z), var44, var44, 1.0f);
                                                final ShapeRenderer var97 = ShapeRenderer.instance;
                                                if (var96.block != null) {
                                                    var46 = 0.4f;
                                                    GL11.glScalef(0.4f, var46, var46);
                                                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                                                    GL11.glBindTexture(3553, var96.minecraft.textureManager.load("/terrain.png"));
                                                    var96.block.renderPreview(var97);
                                                }
                                                else {
                                                    var63.bindTexture(var96.minecraft.textureManager);
                                                    GL11.glScalef(1.0f, -1.0f, -1.0f);
                                                    GL11.glTranslatef(0.0f, 0.2f, 0.0f);
                                                    GL11.glRotatef(-120.0f, 0.0f, 0.0f, 1.0f);
                                                    GL11.glScalef(1.0f, 1.0f, 1.0f);
                                                    var46 = 0.0625f;
                                                    final ModelPart var98;
                                                    if (!(var98 = var96.minecraft.player.getModel().leftArm).hasList) {
                                                        var98.generateList(var46);
                                                    }
                                                    GL11.glCallList(var98.list);
                                                }
                                                GL11.glDisable(2977);
                                                GL11.glPopMatrix();
                                                var96.minecraft.renderer.setLighting(false);
                                                if (!var36.minecraft.settings.anaglyph) {
                                                    var26.minecraft.hud.render(var25, var26.minecraft.currentScreen != null, var34, var29);
                                                    break Label_7903;
                                                }
                                            }
                                            GL11.glColorMask(true, true, true, false);
                                            continue;
                                        }
                                    }
                                    GL11.glViewport(0, 0, var26.minecraft.width, var26.minecraft.height);
                                    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                    GL11.glClear(16640);
                                    GL11.glMatrixMode(5889);
                                    GL11.glLoadIdentity();
                                    GL11.glMatrixMode(5888);
                                    GL11.glLoadIdentity();
                                    var26.enableGuiMode();
                                }
                                if (var26.minecraft.currentScreen != null) {
                                    var26.minecraft.currentScreen.render(var34, var29);
                                }
                                Thread.yield();
                                Display.update();
                            }
                        }
                        if (this.settings.limitFramerate) {
                            Thread.sleep(5L);
                        }
                        checkGLError("Post render");
                        ++var16;
                    }
                    catch (Exception var99) {
                        this.setCurrentScreen(new ErrorScreen("Client error", "The game crashed! due an bug or mod is breaking game!"));
                        var99.printStackTrace();
                    }
                    while (System.currentTimeMillis() >= var15 + 1000L) {
                        this.debug = String.valueOf(var16) + " fps, " + Chunk.chunkUpdates + " chunk updates";
                        Chunk.chunkUpdates = 0;
                        var15 += 1000L;
                        var16 = 0;
                    }
                }
            }
        }
        catch (StopGameException ex3) {}
        catch (Exception var100) {
            var100.printStackTrace();
        }
        finally {
            this.shutdown();
        }
    }
    
    public final void grabMouse() {
        if (!this.hasMouse) {
            this.hasMouse = true;
            if (this.levelLoaded) {
                try {
                    Mouse.setNativeCursor(this.cursor);
                    Mouse.setCursorPosition(this.width / 2, this.height / 2);
                }
                catch (LWJGLException var2) {
                    var2.printStackTrace();
                }
                if (this.canvas == null) {
                    this.canvas.requestFocus();
                }
            }
            else {
                Mouse.setGrabbed(true);
            }
            this.setCurrentScreen(null);
            this.lastClick = this.ticks + 10000;
        }

    }
    
    public final void pause() {
        if (this.currentScreen == null) {
            this.setCurrentScreen(new PauseScreen());
        }
    }
    
    private void onMouseClick(final int mouseclick) {
        if (mouseclick != 0 || this.blockHitTime <= 0) {
            if (mouseclick == 0) {
                final HeldBlock var2 = this.renderer.heldBlock;
                this.renderer.heldBlock.offset = -1;
                var2.moving = true;
            }
            int var3;
            if (mouseclick == 1 && (var3 = this.player.inventory.getSelected()) > 0 && this.gamemode.useItem(this.player, var3)) {
                final HeldBlock var2 = this.renderer.heldBlock;
                this.renderer.heldBlock.pos = 0.0f;
            }
            else if (this.selected == null) {
                if (mouseclick == 0 && !(this.gamemode instanceof CreativeGameMode)) {
                    this.blockHitTime = 10;
                }
            }

            else if (this.selected.entityPos == 1) {
                if (mouseclick == 0) {
                    this.selected.entity.hurt(this.player, 4);
                }
            }
            else if (this.selected.entityPos == 0) {
                var3 = this.selected.x;
                int var4 = this.selected.y;
                int var5 = this.selected.z;
                if (mouseclick != 0) {
                    if (this.selected.face == 0) {
                        --var4;
                    }
                    if (this.selected.face == 1) {
                        ++var4;
                    }
                    if (this.selected.face == 2) {
                        --var5;
                    }
                    if (this.selected.face == 3) {
                        ++var5;
                    }
                    if (this.selected.face == 4) {
                        --var3;
                    }
                    if (this.selected.face == 5) {
                        ++var3;
                    }
                }
                final Block var6 = Block.blocks[this.level.getTile(var3, var4, var5)];
                if (mouseclick == 0) {
                    if (var6 != Block.BEDROCK || this.player.userType >= 100) {
                        this.gamemode.hitBlock(var3, var4, var5);
                    }
                }
                else {
                    final int var7;
                    if ((var7 = this.player.inventory.getSelected()) <= 0) {
                        return;
                    }
                    final Block var8;
                    if ((var8 = Block.blocks[this.level.getTile(var3, var4, var5)]) == null || var8 == Block.WATER || var8 == Block.STATIONARY_WATER || var8 == Block.LAVA || var8 == Block.STATIONARY_LAVA) {
                        final AABB var9;
                        if ((var9 = Block.blocks[var7].getCollisionBox(var3, var4, var5)) != null) {
                            if (this.player.bb.intersects(var9)) {
                                return;
                            }
                            if (!this.level.isFree(var9)) {
                                return;
                            }
                        }
                        if (!this.gamemode.canPlace(var7)) {
                            return;
                        }
                        if (this.isOnline()) {
                            this.networkManager.sendBlockChange(var3, var4, var5, mouseclick, var7);
                        }
                        this.level.netSetTile(var3, var4, var5, var7);
                        final HeldBlock var2 = this.renderer.heldBlock;
                        this.renderer.heldBlock.pos = 0.0f;
                        Block.blocks[var7].onPlace(this.level, var3, var4, var5);
                    }
                }
            }
        }
    }
    
    public void takeAndSaveScreenshot(int width, int height) {
        try {

            int size = width * height * 3;

            int packAlignment = GL11.glGetInteger(GL11.GL_PACK_ALIGNMENT);
            int unpackAlignment = GL11.glGetInteger(GL11.GL_UNPACK_ALIGNMENT);
            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1); // Byte alignment.
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

            GL11.glReadBuffer(GL11.GL_FRONT);
            ByteBuffer buffer = ByteBuffer.allocateDirect(size);
            GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, packAlignment);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, unpackAlignment);

            byte[] pixels = new byte[size];
            buffer.get(pixels);
            pixels = flipPixels(pixels, width, height);

            ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
            int[] bitsPerPixel = {8, 8, 8};
            int[] colOffsets = {0, 1, 2};

            ComponentColorModel colorComp = new ComponentColorModel(colorSpace, bitsPerPixel,
                    false, false, 3, DataBuffer.TYPE_BYTE);

            WritableRaster raster = Raster.createInterleavedRaster(new DataBufferByte(pixels,
                    pixels.length), width, height, width * 3, 3, colOffsets, null);

            BufferedImage image = new BufferedImage(colorComp, raster, false, null);

            Calendar cal = Calendar.getInstance();
            String str = String.format("screenshot_%1$tY%1$tm%1$td%1$tH%1$tM%1$tS.png", cal);

            String month = new SimpleDateFormat("MMM").format(cal.getTime());
            String serverName = ProgressBarDisplay.title.toLowerCase().contains("connecting..") ? ""
                    : ProgressBarDisplay.title;
            if (isSinglePlayer) {
                serverName = "Singleplayer";
            }
            serverName = FontRenderer.stripColor(serverName);
            serverName = serverName.replaceAll("[^A-Za-z0-9\\._-]+", "_");
            File logDir = new File(Minecraft.getMinecraftDirectory(), "/Screenshots/");
            File serverDir = new File(logDir, serverName);
            File monthDir = new File(serverDir, "/" + month + "/");
            monthDir.mkdirs();
            if (ImageIO.write(image, "png", new File(monthDir, str))) {
                hud.addChat("&2Screenshot saved into the Screenshots folder");
            }
        } catch (Exception ex) {
            LogUtil.logError("Error taking a screenshot.", ex);
        }
    }
    
    private void tick() {
        if (this.soundPlayer != null) {
            final SoundPlayer var1 = this.soundPlayer;
            final SoundManager var2 = this.sound;

        }
        this.gamemode.spawnMob();
        final HUDScreen var3 = this.hud;
        final HUDScreen hud = this.hud;
        ++hud.ticks;
        for (int var4 = 0; var4 < var3.chat.size(); ++var4) {
            final ChatLine chatLine = (ChatLine) var3.chat.get(var4);
            ++chatLine.time;
        }
        GL11.glBindTexture(3553, this.textureManager.load("/terrain.png"));
        final TextureManager var5 = this.textureManager;
        for (int var4 = 0; var4 < var5.animations.size(); ++var4) {
            final TextureFX var6;
            (var6 = var5.animations.get(var4)).anaglyph = var5.settings.anaglyph;
            var6.animate();
            var5.textureBuffer.clear();
            var5.textureBuffer.put(var6.textureData);
            var5.textureBuffer.position(0).limit(var6.textureData.length);
            GL11.glTexSubImage2D(3553, 0, var6.textureId % 16 << 4, var6.textureId / 16 << 4, 16, 16, 6408, 5121, var5.textureBuffer);
        }
        if (this.networkManager != null && !(this.currentScreen instanceof ErrorScreen)) {
            if (!this.networkManager.isConnected()) {
                this.progressBar.setTitle("Connecting..");
                this.progressBar.setProgress(0);
            }
            else {
                NetworkManager var7 = this.networkManager;
                if (this.networkManager.successful) {
                    final NetworkHandler var8 = var7.netHandler;
                    if (var7.netHandler.connected) {
                        try {
                            final NetworkHandler var9 = var7.netHandler;
                            var7.netHandler.channel.read(var9.in);
                            int var10 = 0;
                            while (var9.in.position() > 0 && var10++ != 100) {
                                var9.in.flip();
                                byte var11 = var9.in.get(0);
                                final PacketType var12;
                                if ((var12 = PacketType.packets[var11]) == null) {
                                    throw new IOException("Bad command: " + var11);
                                }
                                if (var9.in.remaining() < var12.length + 1) {
                                    var9.in.compact();
                                    break;
                                }
                                var9.in.get();
                                final Object[] var13 = new Object[var12.params.length];
                                for (int var14 = 0; var14 < var13.length; ++var14) {
                                    var13[var14] = var9.readObject(var12.params[var14]);
                                }
                                final NetworkManager var15 = var9.netManager;
                                if (var9.netManager.successful) {
                                    if (var12 == PacketType.IDENTIFICATION) {
                                        var15.minecraft.progressBar.setTitle(var13[1].toString());
                                        var15.minecraft.progressBar.setText(var13[2].toString());
                                        var15.minecraft.player.userType = (byte)var13[3];
                                    }
                                    else if (var12 == PacketType.LEVEL_INIT) {
                                        var15.minecraft.setLevel(null);
                                        var15.levelData = new ByteArrayOutputStream();
                                    }
                                    else if (var12 == PacketType.LEVEL_DATA) {
                                        final short var16 = (short)var13[0];
                                        final byte[] var17 = (byte[])var13[1];
                                        final byte var18 = (byte)var13[2];
                                        var15.minecraft.progressBar.setProgress(var18);
                                        var15.levelData.write(var17, 0, var16);
                                    }
                                    else if (var12 == PacketType.LEVEL_FINALIZE) {
                                        try {
                                            var15.levelData.close();
                                        }
                                        catch (IOException var19) {
                                            var19.printStackTrace();
                                        }
                                        final byte[] var20 = WorldIO.decompress(new ByteArrayInputStream(var15.levelData.toByteArray()));
                                        var15.levelData = null;
                                        final short var21 = (short)var13[0];
                                        final short var22 = (short)var13[1];
                                        final short var23 = (short)var13[2];
                                        final World var24;
                                        (var24 = new World()).setNetworkMode(true);
                                        var24.setData(var21, var22, var23, var20);
                                        var15.minecraft.setLevel(var24);
                                        var15.minecraft.online = false;
                                        var15.levelLoaded = true;
                                    }
                                    else if (var12 == PacketType.BLOCK_CHANGE) {
                                        if (var15.minecraft.level != null) {
                                            var15.minecraft.level.netSetTile((short)var13[0], (short)var13[1], (short)var13[2], (byte)var13[3]);
                                        }
                                    }
                                    else if (var12 == PacketType.SPAWN_PLAYER) {
                                        final byte var25 = (byte)var13[0];
                                        final String var26 = (String)var13[1];
                                        final short var27 = (short)var13[2];
                                        final short var28 = (short)var13[3];
                                        final short var29 = (short)var13[4];
                                        final byte var30 = (byte)var13[5];
                                        final byte var31 = (byte)var13[6];
                                        byte var32 = var30;
                                        final short var33 = var29;

                                        short var34 = var28;
                                        final short var35 = var27;
                                        final String var36 = var26;
                                        var11 = var25;
                                        if (var11 >= 0) {
                                            var32 += 128;
                                            var34 -= 22;
                                            final NetworkPlayer var37 = new NetworkPlayer(var15.minecraft, var11, var36, var35, var34, var33, var32 * 360 / 256.0f, var31 * 360 / 256.0f);
                                            var15.players.put(var11, var37);
                                            var15.minecraft.level.addEntity(var37);
                                        }
                                        else {
                                            var15.minecraft.level.setSpawnPos(var35 / 32, var34 / 32, var33 / 32, (float)(var32 * 320 / 256));
                                            var15.minecraft.player.moveTo(var35 / 32.0f, var34 / 32.0f, var33 / 32.0f, var32 * 360 / 256.0f, var31 * 360 / 256.0f);
                                        }
                                    }
                                    else if (var12 == PacketType.POSITION_ROTATION) {
                                        final byte var25 = (byte)var13[0];
                                        final short var38 = (short)var13[1];
                                        final short var27 = (short)var13[2];
                                        final short var28 = (short)var13[3];
                                        final byte var39 = (byte)var13[4];
                                        final byte var32 = (byte)var13[5];
                                        byte var40 = var39;
                                        final short var34 = var28;
                                        short var35 = var27;
                                        final short var41 = var38;
                                        var11 = var25;
                                        if (var11 < 0) {
                                            var15.minecraft.player.moveTo(var41 / 32.0f, var35 / 32.0f, var34 / 32.0f, var40 * 360 / 256.0f, var32 * 360 / 256.0f);
                                        }
                                        else {
                                            var40 += 128;
                                            var35 -= 22;
                                            final NetworkPlayer var42;
                                            if ((var42 = var15.players.get(var11)) != null) {
                                                var42.teleport(var41, var35, var34, var40 * 360 / 256.0f, var32 * 360 / 256.0f);
                                            }
                                        }
                                    }
                                    else if (var12 == PacketType.POSITION_ROTATION_UPDATE) {
                                        final byte var25 = (byte)var13[0];
                                        final byte var43 = (byte)var13[1];
                                        final byte var44 = (byte)var13[2];
                                        final byte var45 = (byte)var13[3];
                                        final byte var39 = (byte)var13[4];
                                        final byte var32 = (byte)var13[5];
                                        byte var40 = var39;
                                        final byte var46 = var45;
                                        final byte var47 = var44;
                                        final byte var48 = var43;
                                        var11 = var25;
                                        if (var11 >= 0) {
                                            var40 += 128;
                                            final NetworkPlayer var42;
                                            if ((var42 = var15.players.get(var11)) != null) {
                                                var42.queue(var48, var47, var46, var40 * 360 / 256.0f, var32 * 360 / 256.0f);
                                            }
                                        }
                                    }
                                    else if (var12 == PacketType.ROTATION_UPDATE) {
                                        final byte var25 = (byte)var13[0];
                                        final byte var43 = (byte)var13[1];
                                        final byte var47 = (byte)var13[2];
                                        byte var48 = var43;
                                        var11 = var25;
                                        if (var11 >= 0) {
                                            var48 += 128;
                                            final NetworkPlayer var49;
                                            if ((var49 = var15.players.get(var11)) != null) {
                                                var49.queue(var48 * 360 / 256.0f, var47 * 360 / 256.0f);
                                            }
                                        }
                                    }
                                    else if (var12 == PacketType.POSITION_UPDATE) {
                                        final byte var25 = (byte)var13[0];
                                        final byte var43 = (byte)var13[1];
                                        final byte var44 = (byte)var13[2];
                                        final byte var46 = (byte)var13[3];
                                        final byte var47 = var44;
                                        final byte var48 = var43;
                                        var11 = var25;
                                        final NetworkPlayer var50;
                                        if (var11 >= 0 && (var50 = var15.players.get(var11)) != null) {
                                            var50.queue(var48, var47, var46);
                                        }
                                    }
                                    else if (var12 == PacketType.DESPAWN_PLAYER) {
                                        var11 = (byte)var13[0];
                                        final NetworkPlayer var37;
                                        if (var11 >= 0 && (var37 = var15.players.remove(var11)) != null) {
                                            var37.clear();
                                            var15.minecraft.level.removeEntity(var37);
                                        }
                                    }
                                    else if (var12 == PacketType.CHAT_MESSAGE) {
                                        final byte var25 = (byte)var13[0];
                                        final String var36 = (String)var13[1];
                                        var11 = var25;
                                        if (var11 < 0) {
                                            var15.minecraft.hud.addChat("&e" + var36);
                                        }
                                        else {
                                            var15.players.get(var11);
                                            var15.minecraft.hud.addChat(var36);
                                        }
                                    }
                                    else if (var12 == PacketType.DISCONNECT) {
                                        var15.netHandler.close();
                                        var15.minecraft.setCurrentScreen(new ErrorScreen("Connection lost", (String)var13[0]));
                                    }
                                    else if (var12 == PacketType.UPDATE_PLAYER_TYPE) {
                                        var15.minecraft.player.userType = (byte)var13[0];
                                    }
                                }
                                if (!var9.connected) {
                                    break;
                                }
                                var9.in.compact();
                            }
                            if (var9.out.position() > 0) {
                                var9.out.flip();
                                var9.channel.write(var9.out);
                                var9.out.compact();
                            }
                        }
                        catch (Exception var51) {
                            var7.minecraft.setCurrentScreen(new ErrorScreen("Disconnected!", "You've lost connection to the server"));
                            var7.minecraft.online = false;
                            var51.printStackTrace();
                            var7.netHandler.close();
                            var7.minecraft.networkManager = null;
                        }
                    }
                }
                final Player var52 = this.player;
                var7 = this.networkManager;
                if (this.networkManager.levelLoaded) {
                    final int var53 = (int)(var52.x * 32.0f);
                    final int var10 = (int)(var52.y * 32.0f);
                    final int var54 = (int)(var52.z * 32.0f);
                    final int var55 = (int)(var52.yRot * 256.0f / 360.0f) & 0xFF;
                    final int var56 = (int)(var52.xRot * 256.0f / 360.0f) & 0xFF;
                    var7.netHandler.send(PacketType.POSITION_ROTATION, -1, var53, var10, var54, var55, var56);
                }
            }
        }
        if (this.currentScreen == null && this.player != null && this.player.health <= 0) {
            this.setCurrentScreen(null);
        }
        Label_4948: {
            if (this.currentScreen != null) {
                if (!this.currentScreen.grabsMouse) {
                    break Label_4948;
                }
            }
            while (Mouse.next()) {
                final int var57;
                if ((var57 = Mouse.getEventDWheel()) != 0) {
                    this.player.inventory.swapPaint(var57);
                }
                if (this.currentScreen == null) {
                    if (!this.hasMouse && Mouse.getEventButtonState()) {
                        this.grabMouse();
                    }
                    else {
                        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                            this.onMouseClick(0);
                            this.lastClick = this.ticks;
                        }
                        if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                            this.onMouseClick(1);
                            this.lastClick = this.ticks;
                        }
                        if (Mouse.getEventButton() == 2 && Mouse.getEventButtonState() && this.selected != null) {
                            int var4;
                            if ((var4 = this.level.getTile(this.selected.x, this.selected.y, this.selected.z)) == Block.GRASS.id) {
                                var4 = Block.DIRT.id;
                            }
                            if (var4 == Block.DOUBLE_SLAB.id) {
                                var4 = Block.SLAB.id;
                            }
                            if (var4 == Block.BEDROCK.id) {
                                var4 = Block.STONE.id;
                            }
                            this.player.inventory.grabTexture(var4, this.gamemode instanceof CreativeGameMode);
                        }
                    }
                }
                if (this.currentScreen != null) {
                    this.currentScreen.mouseEvent();
                }
            }
            if (this.blockHitTime > 0) {
                --this.blockHitTime;
            }
            while (Keyboard.next()) {
                this.player.setKey(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    if (this.currentScreen != null) {
                        this.currentScreen.keyboardEvent();
                    }
                    if (this.currentScreen == null) {
                        if (Keyboard.getEventKey() == 1) {
                            this.pause();
                        }
                        if (this.gamemode instanceof CreativeGameMode) {
                            if (Keyboard.getEventKey() == this.settings.loadLocationKey.key) {
                                this.player.resetPos();
                            }
                            if (Keyboard.getEventKey() == this.settings.saveLocationKey.key) {
                                this.level.setSpawnPos((int)this.player.x, (int)this.player.y, (int)this.player.z, this.player.yRot);
                                this.player.resetPos();
                            }
                        }
                        if (Keyboard.getEventKey() == 15 && this.gamemode instanceof SurvivalGameMode && this.player.arrows > 0) {
                            this.level.addEntity(new NewArrow(this.level, this.player, this.player.x, this.player.y, this.player.z, this.player.yRot, this.player.xRot, 1.2f));
                            final Player player = this.player;
                            --player.arrows;
                        }
                        Keyboard.getEventKey();
                        if (Keyboard.getEventKey() == 63) {
                            this.raining = !this.raining;
                        }
                        if (Keyboard.getEventKey() == 63) {
                            this.takeAndSaveScreenshot(width, height);
                        }
                        Keyboard.getEventKey();
                        if (Keyboard.getEventKey() == 64) {
                            this.settings.thirdPersonMode = this.settings.thirdPersonMode.next();
                        }
                        if (Keyboard.getEventKey() == this.settings.inventoryKey.key) {
                            this.gamemode.openInventory();
                        }
                        if (Keyboard.getEventKey() == this.settings.craftingkey.key && !this.level.creativeMode) {
                            if (this.player.inventory.slots[this.player.inventory.selected] == Block.LOG.id && this.player.inventory.count[this.player.inventory.selected] > 0) {
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.COBBLESTONE.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.COBBLESTONE.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.COBBLESTONE.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.COBBLESTONE.id));
                                final int[] count = this.player.inventory.count;
                                final int selected = this.player.inventory.selected;
                                --count[selected];
                                if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                    this.player.inventory.slots[this.player.inventory.selected] = -1;
                                }
                            }
                            else if (this.player.inventory.slots[this.player.inventory.selected] == Block.COBBLESTONE.id && this.player.inventory.count[this.player.inventory.selected] > 3) {
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.STONE.id));
                                final int[] count2 = this.player.inventory.count;
                                final int selected2 = this.player.inventory.selected;
                                count2[selected2] -= 4;
                                if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                    this.player.inventory.slots[this.player.inventory.selected] = -1;
                                }
                            }
                        }
                        if (Keyboard.getEventKey() == this.settings.craftingkey.key && !this.level.creativeMode) {
                            if (this.player.inventory.slots[this.player.inventory.selected] == Block.WHITE_WOOL.id && this.player.inventory.count[this.player.inventory.selected] > 8) {
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Item.FEATHER.id));
                                final int[] count3 = this.player.inventory.count;
                                final int selected3 = this.player.inventory.selected;
                                --count3[selected3];
                                if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                    this.player.inventory.slots[this.player.inventory.selected] = -1;
                                }
                            }
                            else if (this.player.inventory.slots[this.player.inventory.selected] == Block.DIRT.id && this.player.inventory.count[this.player.inventory.selected] > 3) {
                                this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.GRASS.id));
                                final int[] count4 = this.player.inventory.count;
                                final int selected4 = this.player.inventory.selected;
                                count4[selected4] -= 4;
                                if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                    this.player.inventory.slots[this.player.inventory.selected] = -1;
                                }
                            }
                        }
                        if (Keyboard.getEventKey() == this.settings.craftingkey.key && !this.level.creativeMode && this.player.inventory.slots[this.player.inventory.selected] == Item.FEATHER.id && this.player.inventory.count[this.player.inventory.selected] > 3) {
                            this.level.addEntity(new NonLivingEntity(this.level, this.player.x, this.player.y, this.player.z, Block.WHITE_WOOL.id));
                            final int[] count5 = this.player.inventory.count;
                            final int selected5 = this.player.inventory.selected;
                            count5[selected5] -= 4;
                            if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                this.player.inventory.slots[this.player.inventory.selected] = -1;
                            }
                            if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                this.player.inventory.slots[this.player.inventory.selected] = -1;
                            }
                           
                        }

                             if (this.player.inventory.slots[this.player.inventory.selected] == Block.FIREBLOCK.id && this.player.inventory.count[this.player.inventory.selected] > 1) {
                                final int[] count4 = this.player.inventory.count;
                                final int selected4 = this.player.inventory.selected;
                                if (this.player.inventory.count[this.player.inventory.selected] == 0) {
                                }
                            }
                        }
                        if (Keyboard.getEventKey() == this.settings.chatKey.key) {
                            this.player.releaseAllKeys();
                            this.setCurrentScreen(new ChatInputScreen());
                        }
                    
                    for (int var57 = 0; var57 < 9; ++var57) {
                        if (Keyboard.getEventKey() == var57 + 2) {
                            this.player.inventory.selected = var57;
                        }
                    }
                    if (Keyboard.getEventKey() != this.settings.toggleFogKey.key) {
                        continue;
                    }
                    this.settings.toggleSetting(4, (!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) ? 1 : -1);
                }
            }
            if (this.currentScreen == null) {
                if (Mouse.isButtonDown(0) && this.ticks - this.lastClick >= this.timer.tps / 4.0f && this.hasMouse) {
                    this.onMouseClick(0);
                    this.lastClick = this.ticks;
                }
                if (Mouse.isButtonDown(1) && this.ticks - this.lastClick >= this.timer.tps / 4.0f && this.hasMouse) {
                    this.onMouseClick(1);
                    this.lastClick = this.ticks;
                }
            }
            final boolean var58 = this.currentScreen == null && Mouse.isButtonDown(0) && this.hasMouse;
            final boolean var59 = false;
            if (!this.gamemode.instantBreak && this.blockHitTime <= 0) {
                if (var58 && this.selected != null && this.selected.entityPos == 0) {
                    final int var10 = this.selected.x;
                    final int var54 = this.selected.y;
                    final int var55 = this.selected.z;
                    this.gamemode.hitBlock(var10, var54, var55, this.selected.face);
                }
                else {
                    this.gamemode.resetHits();
                }
            }
        }
        if (this.currentScreen != null) {
            this.lastClick = this.ticks + 10000;
        }
        if (this.currentScreen != null) {
            this.currentScreen.doInput();
            if (this.currentScreen != null) {
                this.currentScreen.tick();
            }
        }
        switch (Keyboard.getEventKey()) {
        case Keyboard.KEY_F2:
            takeAndSaveScreenshot(width, height);
            break;
            
        }
        if (this.level != null) {
            final Renderer var60 = this.renderer;
            final Renderer renderer = this.renderer;
            ++renderer.levelTicks;
            final HeldBlock var61 = var60.heldBlock;
            var60.heldBlock.lastPos = var61.pos;
            if (var61.moving) {
                final HeldBlock heldBlock = var61;
                ++heldBlock.offset;
                if (var61.offset == 7) {
                    var61.offset = 0;
                    var61.moving = false;
                }
            }
            Player var62 = var61.minecraft.player;
            final int var10 = var61.minecraft.player.inventory.getSelected();
            Block var63 = null;
            if (var10 > 0) {
                var63 = Block.blocks[var10];
            }
            final float var64 = 0.4f;
            float var65;
            if ((var65 = ((var63 == var61.block) ? 1.0f : 0.0f) - var61.pos) < -var64) {
                var65 = -var64;
            }
            if (var65 > var64) {
                var65 = var64;
            }
            final HeldBlock heldBlock2 = var61;
            heldBlock2.pos += var65;
            if (var61.pos < 0.1f) {
                var61.block = var63;
            }
            if (var60.minecraft.raining) {
                final Renderer var66 = var60;
                var62 = var60.minecraft.player;
                final World var67 = var60.minecraft.level;
                final int var54 = (int)var62.x;
                final int var55 = (int)var62.y;
                final int var56 = (int)var62.z;
                for (int var14 = 0; var14 < 50; ++var14) {
                    final int var68 = var54 + var66.random.nextInt(9) - 4;
                    final int var69 = var56 + var66.random.nextInt(9) - 4;
                    final int var70;
                    if ((var70 = var67.getHighestTile(var68, var69)) <= var55 + 4 && var70 >= var55 - 4) {
                        final float var71 = var66.random.nextFloat();
                        final float var72 = var66.random.nextFloat();
                        var66.minecraft.particleManager.spawnParticle(new WaterDropParticle(var67, var68 + var71, var70 + 0.1f, var69 + var72));
                    }
                }
            }
            final LevelRenderer var73 = this.levelRenderer;
            final LevelRenderer levelRenderer = this.levelRenderer;
            ++levelRenderer.ticks;
            this.level.tickEntities();
            if (!this.isOnline()) {
                this.level.tick();
            }
            this.particleManager.tick();
        }
        
    }
    
    public final boolean isOnline() {
        return this.networkManager != null;
    }
    
    public final void generateLevel(final int var1) {
        final String var2 = (this.session != null) ? this.session.username : "anonymous";
        final World var3 = new WorldGenerator(this.progressBar).generate(var2, 128 << var1, 128 << var1, 64);
        this.gamemode.prepareLevel(var3);
        this.setLevel(var3);
    }

    public final boolean loadOnlineLevel(final String var1, final int var2) {
        final World var3;
        if ((var3 = this.levelIo.loadOnline(this.host, var1, var2)) == null) {
            return false;
        }
        this.setLevel(var3);
        return true;
    }
    
    public final void setLevel(World var1) {
        if (this.applet == null || (!this.applet.getDocumentBase().getHost().equalsIgnoreCase("minecraft.net") && !this.applet.getDocumentBase().getHost().equalsIgnoreCase("www.minecraft.net")) || (!this.applet.getCodeBase().getHost().equalsIgnoreCase("minecraft.net") && !this.applet.getCodeBase().getHost().equalsIgnoreCase("www.minecraft.net"))) {
            var1 = null;
        }
        if ((this.level = var1) != null) {
            var1.initTransient();
            this.gamemode.apply(var1);
            var1.font = this.fontRenderer;
            var1.rendererContext$5cd64a7f = this;
            if (!this.isOnline()) {
                this.player = (Player)var1.findSubclassOf(Player.class);
            }
            else if (this.player != null) {
                this.player.resetPos();
                this.gamemode.preparePlayer(this.player);
                if (var1 != null) {
                    var1.player = this.player;
                    var1.addEntity(this.player);
                }
            }
        }
        if (this.player == null) {
            (this.player = new Player(var1)).resetPos();
            this.gamemode.preparePlayer(this.player);
            if (var1 != null) {
                var1.player = this.player;
            }
        }
        if (this.player != null) {
            this.player.input = new InputHandlerImpl(this.settings);
            this.gamemode.apply(this.player);
        }
        if (this.levelRenderer != null) {
            final LevelRenderer var2 = this.levelRenderer;
            if (this.levelRenderer.level != null) {
                var2.level.removeListener(var2);
            }
            if ((var2.level = var1) != null) {
                var1.addListener(var2);
                var2.refresh();
            }
        }
        if (this.particleManager != null) {
            final ParticleManager var3 = this.particleManager;
            if (var1 != null) {
                var1.particleEngine = var3;
            }
            for (int var4 = 0; var4 < 2; ++var4) {
                var3.particles[var4].clear();
            }
        }
        System.gc();
    }
    
    public void resize() {
        this.width = Display.getDisplayMode().getWidth();
        this.height = Display.getDisplayMode().getHeight();
        if (this.hud != null) {
            this.hud.width = this.width * 240 / this.height;
            this.hud.height = this.height * 240 / this.height;
        }
        if (this.currentScreen != null) {
            this.currentScreen.width = this.width * 240 / this.height;
            this.currentScreen.height = this.height * 240 / this.height;
            this.currentScreen.onOpen();
        }
    }


    public byte[] flipPixels(final byte[] originalBuffer, final int width, final int height) {
        byte[] flippedBuffer = null;
        final int stride = width * 3;
        if (originalBuffer != null) {
            flippedBuffer = new byte[originalBuffer.length];
            for (int y = 0; y < height; ++y) {
                System.arraycopy(originalBuffer, y * stride, flippedBuffer, (height - y - 1) * stride, stride);
            }
        }
        return flippedBuffer;
    }
}
