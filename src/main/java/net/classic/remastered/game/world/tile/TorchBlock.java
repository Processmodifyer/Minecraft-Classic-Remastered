package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.client.render.*;
import net.classic.remastered.game.phys.*;
import net.classic.remastered.game.world.*;
import net.dungland.util.*;

public class TorchBlock extends Block
{
    protected TorchBlock(final int var1, final int var2) {
        super(var1);
        this.textureId = var2;
        this.setPhysics(true);
        final float var3 = 0.2f;
        this.setBounds(0.5f - var3, 0.0f, 0.5f - var3, var3 + 0.5f, var3 * 3.0f, var3 + 0.5f);
    }
    
    @Override
    public void update(final World level, final int x, final int y, final int z, final Random rand) {
        if (level.getTile(x, y, z) == Block.GRASS.id) {
            level.setTile(x, y, z, 0);
        }
        super.update(level, x, y, z, rand);
    }
    
    private void render(final ShapeRenderer var1, final float var2, final float var3, final float var4) {
        final int var6;
        final int var5 = (var6 = this.getTextureId(15)) % 16 << 4;
        final int var7 = var6 / 16 << 4;
        final float var8 = var5 / 256.0f;
        final float var9 = (var5 + 15.99f) / 256.0f;
        final float var10 = var7 / 256.0f;
        final float var11 = (var7 + 15.99f) / 256.0f;
        for (int var12 = 0; var12 < 2; ++var12) {
            float var13 = (float)(MathHelper.sin(var12 * 3.1415927f / 2.0f + 0.7853982f) * 0.5);
            float var14 = (float)(MathHelper.cos(var12 * 3.1415927f / 2.0f + 0.7853982f) * 0.5);
            final float var15 = var2 + 0.5f - var13;
            var13 += var2 + 0.5f;
            final float var16 = var3 + 1.0f;
            final float var17 = var4 + 0.5f - var14;
            var14 += var4 + 0.5f;
            var1.vertexUV(var15, var16, var17, var9, var10);
            var1.vertexUV(var13, var16, var14, var8, var10);
            var1.vertexUV(var13, var3, var14, var8, var11);
            var1.vertexUV(var15, var3, var17, var9, var11);
            var1.vertexUV(var13, var16, var14, var9, var10);
            var1.vertexUV(var15, var16, var17, var8, var10);
            var1.vertexUV(var15, var3, var17, var8, var11);
            var1.vertexUV(var13, var3, var14, var9, var11);
        }
    }
    
    @Override
    public final boolean isOpaque() {
        return false;
    }
    
    @Override
    public final boolean isSolid() {
        return false;
    }
    
    @Override
    public final void renderPreview(final ShapeRenderer var1) {
        var1.normal(0.0f, 1.0f, 0.0f);
        var1.begin();
        this.render(var1, 0.0f, 0.4f, -0.3f);
        var1.end();
    }
    
    @Override
    public final boolean isCube() {
        return false;
    }
    
    @Override
    public final boolean render(final World var1, final int var2, final int var3, final int var4, final ShapeRenderer var5) {
        final float var6 = var1.getBrightness(var2, var3, var4);
        var5.color(var6, var6, var6);
        this.render(var5, (float)var2, (float)var3, (float)var4);
        return true;
    }
    
    @Override
    public final void renderFullbright(final ShapeRenderer shapeRenderer) {
        shapeRenderer.color(6.0f, 6.0f, 6.0f);
        this.render(shapeRenderer, -2.0f, 0.0f, 0.0f);
    }
    
    @Override
    public AABB getCollisionBox(final int x, final int y, final int z) {
        return null;
    }
    
    @Override
    public final int getDrop() {
        return TorchBlock.TORCH.id;
    }
}
