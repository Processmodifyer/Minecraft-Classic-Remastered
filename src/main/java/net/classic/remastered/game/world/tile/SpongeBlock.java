package net.classic.remastered.game.world.tile;

import net.classic.remastered.game.world.*;

public final class SpongeBlock extends Block
{
    protected SpongeBlock(final int var1) {
        super(19);
        this.textureId = 48;
    }
    
    @Override
    public final void onAdded(final World level, final int x, final int y, final int z) {
        for (int var7 = x - 2; var7 <= x + 2; ++var7) {
            for (int var8 = y - 2; var8 <= y + 2; ++var8) {
                for (int var9 = z - 2; var9 <= z + 2; ++var9) {
                    if (level.isWater(var7, var8, var9)) {
                        level.setTileNoNeighborChange(var7, var8, var9, 0);
                    }
                }
            }
        }
    }
    
    @Override
    public final void onRemoved(final World var1, final int var2, final int var3, final int var4) {
        for (int var5 = var2 - 2; var5 <= var2 + 2; ++var5) {
            for (int var6 = var3 - 2; var6 <= var3 + 2; ++var6) {
                for (int var7 = var4 - 2; var7 <= var4 + 2; ++var7) {
                    var1.updateNeighborsAt(var5, var6, var7, var1.getTile(var5, var6, var7));
                }
            }
        }
    }
}
