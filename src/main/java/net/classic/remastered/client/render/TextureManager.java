/*
 * 
 */
package net.classic.remastered.client.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.render.texture.TextureFX;
import net.classic.remastered.client.settings.GameSettings;

public class TextureManager {
    public HashMap<String, Integer> textures = new HashMap();
    public HashMap<Integer, BufferedImage> textureImages = new HashMap();
    public IntBuffer idBuffer = BufferUtils.createIntBuffer(1);
    public ByteBuffer textureBuffer = BufferUtils.createByteBuffer(262144);
    public List<TextureFX> animations = new ArrayList<TextureFX>();
    public GameSettings settings;
    public HashMap<String, Integer> externalTexturePacks = new HashMap();
    public File minecraftFolder;
    public File texturesFolder;
    public int previousMipmapMode;

    public TextureManager(GameSettings settings) {
        this.settings = settings;
        this.minecraftFolder = Minecraft.mcDir;
        this.texturesFolder = new File(this.minecraftFolder, "texturepacks");
        if (!this.texturesFolder.exists()) {
            this.texturesFolder.mkdir();
        }
    }

    public int load(String file) {
        if (this.textures.get(file) != null) {
            return this.textures.get(file);
        }
        if (this.externalTexturePacks.get(file) != null) {
            return this.externalTexturePacks.get(file);
        }
        try {
            this.idBuffer.clear();
            GL11.glGenTextures(this.idBuffer);
            int textureID = this.idBuffer.get(0);
            if (file.endsWith(".png")) {
                if (file.startsWith("##")) {
                    this.load(TextureManager.load1(ImageIO.read(TextureManager.class.getResourceAsStream(file.substring(2)))), textureID);
                } else {
                    this.load(ImageIO.read(TextureManager.class.getResourceAsStream(file)), textureID);
                }
                this.textures.put(file, textureID);
            } else if (file.endsWith(".zip")) {
                String terrainPNG;
                ZipFile zip = new ZipFile(new File(this.minecraftFolder, "texturepacks/" + file));
                if (zip.getEntry((terrainPNG = "terrain.png").startsWith("/") ? terrainPNG.substring(1, terrainPNG.length()) : terrainPNG) != null) {
                    this.load(ImageIO.read(zip.getInputStream(zip.getEntry(terrainPNG.startsWith("/") ? terrainPNG.substring(1, terrainPNG.length()) : terrainPNG))), textureID);
                } else {
                    this.load(ImageIO.read(TextureManager.class.getResourceAsStream(terrainPNG)), textureID);
                }
                zip.close();
                this.externalTexturePacks.put(file, textureID);
            }
            return textureID;
        }
        catch (IOException e) {
            throw new RuntimeException("!!", e);
        }
    }

    public static BufferedImage load1(BufferedImage image) {
        int charWidth = image.getWidth() / 16;
        BufferedImage image1 = new BufferedImage(16, image.getHeight() * charWidth, 2);
        Graphics graphics = image1.getGraphics();
        for (int i = 0; i < charWidth; ++i) {
            graphics.drawImage(image, -i << 4, i * image.getHeight(), null);
        }
        graphics.dispose();
        return image1;
    }

    public int load(BufferedImage image) {
        this.idBuffer.clear();
        GL11.glGenTextures(this.idBuffer);
        int textureID = this.idBuffer.get(0);
        this.load(image, textureID);
        this.textureImages.put(textureID, image);
        return textureID;
    }

    public void load(BufferedImage image, int textureID) {
        GL11.glBindTexture(3553, textureID);
        if (this.settings.smoothing > 0) {
            GL11.glTexParameteri(3553, 10241, 9986);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 33084, 0);
            GL11.glTexParameteri(3553, 33085, 4);
            if (this.settings.anisotropic > 0) {
                GL11.glTexParameteri(3553, 34046, 16);
            }
        } else {
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        byte[] color = new byte[width * height << 2];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for (int pixel = 0; pixel < pixels.length; ++pixel) {
            int alpha = pixels[pixel] >>> 24;
            int red = pixels[pixel] >> 16 & 0xFF;
            int green = pixels[pixel] >> 8 & 0xFF;
            int blue = pixels[pixel] & 0xFF;
            if (this.settings.anaglyph) {
                int rgba3D = (red * 30 + green * 59 + blue * 11) / 100;
                green = (red * 30 + green * 70) / 100;
                blue = (red * 30 + blue * 70) / 100;
                red = rgba3D;
            }
            color[pixel << 2] = (byte)red;
            color[(pixel << 2) + 1] = (byte)green;
            color[(pixel << 2) + 2] = (byte)blue;
            color[(pixel << 2) + 3] = (byte)alpha;
        }
        this.textureBuffer.clear();
        this.textureBuffer.put(color);
        this.textureBuffer.position(0).limit(color.length);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, this.textureBuffer);
        if (this.settings.smoothing > 0) {
            if (this.settings.smoothing == 1) {
                ContextCapabilities capabilities = GLContext.getCapabilities();
                if (capabilities.OpenGL30) {
                    if (this.previousMipmapMode != this.settings.smoothing) {
                        System.out.println("Using OpenGL 3.0 for mipmap generation.");
                    }
                    GL30.glGenerateMipmap(3553);
                } else if (capabilities.GL_EXT_framebuffer_object) {
                    if (this.previousMipmapMode != this.settings.smoothing) {
                        System.out.println("Using GL_EXT_framebuffer_object extension for mipmap generation.");
                    }
                    EXTFramebufferObject.glGenerateMipmapEXT(3553);
                } else if (capabilities.OpenGL14) {
                    if (this.previousMipmapMode != this.settings.smoothing) {
                        System.out.println("Using OpenGL 1.4 for mipmap generation.");
                    }
                    GL11.glTexParameteri(3553, 33169, 1);
                }
            } else if (this.settings.smoothing == 2) {
                if (this.previousMipmapMode != this.settings.smoothing) {
                    System.out.println("Using custom system for mipmap generation.");
                }
                this.generateMipMaps(this.textureBuffer, width, height, false);
            }
        }
        this.previousMipmapMode = this.settings.smoothing;
    }

    public void generateMipMaps(ByteBuffer data, int width, int height, boolean test) {
        ByteBuffer mipData = data;
        for (int level = test ? 0 : 1; level <= 4; ++level) {
            int parWidth = width >> level - 1;
            int mipWidth = width >> level;
            int mipHeight = height >> level;
            if (mipWidth <= 0 || mipHeight <= 0) break;
            ByteBuffer mipData1 = BufferUtils.createByteBuffer(data.capacity());
            mipData1.clear();
            for (int mipX = 0; mipX < mipWidth; ++mipX) {
                for (int mipY = 0; mipY < mipHeight; ++mipY) {
                    int p1 = mipData.getInt((mipX * 2 + 0 + (mipY * 2 + 0) * parWidth) * 4);
                    int p2 = mipData.getInt((mipX * 2 + 1 + (mipY * 2 + 0) * parWidth) * 4);
                    int p3 = mipData.getInt((mipX * 2 + 1 + (mipY * 2 + 1) * parWidth) * 4);
                    int p4 = mipData.getInt((mipX * 2 + 0 + (mipY * 2 + 1) * parWidth) * 4);
                    int pixel = this.b(this.b(p1, p2), this.b(p3, p4));
                    mipData1.putInt((mipX + mipY * mipWidth) * 4, pixel);
                }
            }
            GL11.glTexImage2D(3553, level, 6408, mipWidth, mipHeight, 0, 6408, 5121, mipData1);
            GL11.glAlphaFunc(518, 0.1f * (float)level);
            mipData = mipData1;
        }
    }

    private int b(int c1, int c2) {
        int a1 = (c1 & 0xFF000000) >> 24 & 0xFF;
        int a2 = (c2 & 0xFF000000) >> 24 & 0xFF;
        int ax = (a1 + a2) / 2;
        if (ax > 255) {
            ax = 255;
        }
        if (a1 + a2 <= 0) {
            a1 = 1;
            a2 = 1;
            ax = 0;
        }
        int r1 = (c1 >> 16 & 0xFF) * a1;
        int g1 = (c1 >> 8 & 0xFF) * a1;
        int b1 = (c1 & 0xFF) * a1;
        int r2 = (c2 >> 16 & 0xFF) * a2;
        int g2 = (c2 >> 8 & 0xFF) * a2;
        int b2 = (c2 & 0xFF) * a2;
        int rx = (r1 + r2) / (a1 + a2);
        int gx = (g1 + g2) / (a1 + a2);
        int bx = (b1 + b2) / (a1 + a2);
        return ax << 24 | rx << 16 | gx << 8 | bx;
    }

    public void registerAnimation(TextureFX FX) {
        this.animations.add(FX);
        FX.animate();
    }
}

