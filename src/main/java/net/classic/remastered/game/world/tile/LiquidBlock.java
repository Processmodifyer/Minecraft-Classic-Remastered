package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.client.render.*;
import net.classic.remastered.game.phys.*;
import net.classic.remastered.game.world.*;
import net.classic.remastered.game.world.liquid.*;

public class LiquidBlock extends Block
{
    protected LiquidType type;
    protected int stillId;
    protected int movingId;
    
    protected LiquidBlock(final int var1, final LiquidType var2) {
        super(var1);
        this.type = var2;
        this.textureId = 14;
        if (var2 == LiquidType.LAVA) {
            this.textureId = 30;
        }
        Block.liquid[var1] = true;
        this.movingId = var1;
        this.stillId = var1 + 1;
        final float var3 = 0.01f;
        final float var4 = 0.1f;
        this.setBounds(var3 + 0.0f, 0.0f - var4 + var3, var3 + 0.0f, var3 + 1.0f, 1.0f - var4 + var3, var3 + 1.0f);
        this.setPhysics(true);
        if (var2 == LiquidType.LAVA) {
            this.setTickDelay(16);
        }
    }
    
    @Override
    public final boolean isCube() {
        return false;
    }
    
    @Override
    public final void onPlace(final World level, final int x, final int y, final int z) {
        level.addToTickNextTick(x, y, z, this.movingId);
    }
    
    @Override
    public void update(World level, int x, int y, int z, final Random rand) {
        final boolean var7 = false;
        z = z;
        y = y;
        x = x;
        level = level;
        boolean var8 = false;
        boolean var9;
        do {
            --y;
            if (level.getTile(x, y, z) != 0) {
                break;
            }
            if (!this.canFlow(level, x, y, z)) {
                break;
            }
            if (!(var9 = level.setTile(x, y, z, this.movingId))) {
                continue;
            }
            var8 = true;
        } while (var9 && this.type != LiquidType.LAVA);
        ++y;
        if (this.type == LiquidType.WATER || !var8) {
            var8 = (var8 | this.flow(level, x - 1, y, z) | this.flow(level, x + 1, y, z) | this.flow(level, x, y, z - 1) | this.flow(level, x, y, z + 1));
        }
        if (!var8) {
            level.setTileNoUpdate(x, y, z, this.stillId);
        }
        else {
            level.addToTickNextTick(x, y, z, this.movingId);
        }
    }
    
    private boolean canFlow(final World var1, final int var2, final int var3, final int var4) {
        if (this.type == LiquidType.WATER) {
            for (int var5 = var2 - 2; var5 <= var2 + 2; ++var5) {
                for (int var6 = var3 - 2; var6 <= var3 + 2; ++var6) {
                    for (int var7 = var4 - 2; var7 <= var4 + 2; ++var7) {
                        if (var1.getTile(var5, var6, var7) == Block.SPONGE.id) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean flow(final World var1, final int var2, final int var3, final int var4) {
        if (var1.getTile(var2, var3, var4) == 0) {
            if (!this.canFlow(var1, var2, var3, var4)) {
                return false;
            }
            if (var1.setTile(var2, var3, var4, this.movingId)) {
                var1.addToTickNextTick(var2, var3, var4, this.movingId);
            }
        }
        return false;
    }
    
    @Override
    protected final float getBrightness(final World level, final int x, final int y, final int z) {
        return (this.type == LiquidType.LAVA) ? 100.0f : level.getBrightness(x, y, z);
    }
    
    @Override
    public final boolean canRenderSide(final World level, final int x, final int y, final int z, final int side) {
        final int var6;
        return x >= 0 && y >= 0 && z >= 0 && x < level.width && z < level.height && (var6 = level.getTile(x, y, z)) != this.movingId && var6 != this.stillId && ((side == 1 && (level.getTile(x - 1, y, z) == 0 || level.getTile(x + 1, y, z) == 0 || level.getTile(x, y, z - 1) == 0 || level.getTile(x, y, z + 1) == 0)) || super.canRenderSide(level, x, y, z, side));
    }
    
    @Override
    public final void renderInside(final ShapeRenderer shapeRenderer, final int x, final int y, final int z, final int side) {
        super.renderInside(shapeRenderer, x, y, z, side);
        super.renderSide(shapeRenderer, x, y, z, side);
    }
    
    @Override
    public final boolean isOpaque() {
        return true;
    }
    
    @Override
    public final boolean isSolid() {
        return false;
    }
    
    @Override
    public final LiquidType getLiquidType() {
        return this.type;
    }
    
    @Override
    public void onNeighborChange(final World var1, final int var2, final int var3, final int var4, final int var5) {
        if (var5 != 0) {
            final LiquidType var6 = Block.blocks[var5].getLiquidType();
            if ((this.type == LiquidType.WATER && var6 == LiquidType.LAVA) || (var6 == LiquidType.WATER && this.type == LiquidType.LAVA)) {
                var1.setTile(var2, var3, var4, Block.STONE.id);
                return;
            }
        }
        var1.addToTickNextTick(var2, var3, var4, var5);
    }
    
    @Override
    public final int getTickDelay() {
        return (this.type == LiquidType.LAVA) ? 1 : 0;
    }
    
    @Override
    public final void dropItems(final World var1, final int var2, final int var3, final int var4, final float var5) {
    }
    
    @Override
    public final void onBreak(final World var1, final int var2, final int var3, final int var4) {
    }
    
    @Override
    public final int getDropCount() {
        return 1;
    }
    
    @Override
    public final int getRenderPass() {
        return (this.type == LiquidType.WATER) ? 1 : 0;
    }
    
    @Override
    public AABB getCollisionBox(final int x, final int y, final int z) {
        return null;
    }
}
