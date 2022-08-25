package net.classic.remastered.game.world.tile;

import net.classic.remastered.*;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.entity.other.*;
import net.classic.remastered.game.world.*;
import net.classic.remastered.game.world.liquid.*;

public final class SandBlock extends Block
{
    public Minecraft minecraft;
    
    public SandBlock(final int var1, final int var2) {
        super(var1, var2);
    }
    
    @Override
    public final void onPlace(final World level, final int x, final int y, final int z) {
        this.fall(level, x, y, z);
    }
    
    @Override
    public final void onNeighborChange(final World var1, final int var2, final int var3, final int var4, final int var5) {
        this.fall(var1, var2, var3, var4);
    }
    
    @Override
    public final void onAdded(final World level, final int x, final int y, final int z) {
        for (int var7 = x - 1; var7 <= x + 1; ++var7) {
            for (int var8 = z - 1; var8 <= z + 1; ++var8) {
                if (level.isLit(var7, y, var8) && this == Block.SAND) {
                    level.setTile(x, y, z, Block.SAND.id);
                }
            }
        }
    }
    
    private void fall(final World var1, final int var2, final int var3, final int var4) {
        final int var5 = var2;
        int var6 = var3;
        final int var7 = var4;
        while (true) {
            final int var8 = var6 - 1;
            final int var9;
            final LiquidType var10;
            if ((var9 = var1.getTile(var5, var8, var7)) != 0 && (var10 = Block.blocks[var9].getLiquidType()) != LiquidType.WATER && var10 != LiquidType.LAVA) {
                break;
            }
            if (var6 <= 0) {
                break;
            }
            --var6;
        }
        if (var6 != var3) {
            final int var11;
            if ((var11 = var1.getTile(var5, var6, var7)) > 0 && Block.blocks[var11].getLiquidType() != LiquidType.NOT_LIQUID) {
                var1.setTile(var5, var6, var7, 0);
            }
            else {
                var1.addEntity(new SandFall(var1, var2 + 0.5f, var3 + 0.5f, var4 + 0.5f, this.id));
                var1.setTile(var2, var3, var4, 0);
            }
        }
    }
}
