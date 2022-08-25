package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.game.world.*;
import net.classic.remastered.game.world.liquid.*;

public final class StillLiquidBlock extends LiquidBlock
{
    protected StillLiquidBlock(final int var1, final LiquidType var2) {
        super(var1, var2);
        this.movingId = var1 - 1;
        this.stillId = var1;
        this.setPhysics(false);
    }
    
    @Override
    public final void update(final World level, final int x, final int y, final int z, final Random rand) {
    }
    
    @Override
    public final void onNeighborChange(final World var1, final int var2, final int var3, final int var4, final int var5) {
        boolean var6 = false;
        if (var1.getTile(var2 - 1, var3, var4) == 0) {
            var6 = true;
        }
        if (var1.getTile(var2 + 1, var3, var4) == 0) {
            var6 = true;
        }
        if (var1.getTile(var2, var3, var4 - 1) == 0) {
            var6 = true;
        }
        if (var1.getTile(var2, var3, var4 + 1) == 0) {
            var6 = true;
        }
        if (var1.getTile(var2, var3 - 1, var4) == 0) {
            var6 = true;
        }
        if (var5 != 0) {
            final LiquidType var7 = Block.blocks[var5].getLiquidType();
            if ((this.type == LiquidType.WATER && var7 == LiquidType.LAVA) || (var7 == LiquidType.WATER && this.type == LiquidType.LAVA)) {
                var1.setTile(var2, var3, var4, Block.STONE.id);
                return;
            }
        }
        if (var6) {
            var1.setTileNoUpdate(var2, var3, var4, this.movingId);
            var1.addToTickNextTick(var2, var3, var4, this.movingId);
        }
    }
}
