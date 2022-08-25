package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.game.world.*;

public final class SaplingBlock extends FlowerBlock
{
    protected SaplingBlock(final int var1, final int var2) {
        super(6, 15);
        final float var3 = 0.4f;
        this.setBounds(0.5f - var3, 0.0f, 0.5f - var3, var3 + 0.5f, var3 * 2.0f, var3 + 0.5f);
    }
    
    @Override
    public final void update(final World level, final int x, final int y, final int z, final Random rand) {
        final int var6 = level.getTile(x, y - 1, z);
        if (level.isLit(x, y, z) && (var6 == SaplingBlock.DIRT.id || var6 == SaplingBlock.GRASS.id)) {
            if (rand.nextInt(5) == 0) {
                level.setTileNoUpdate(x, y, z, 0);
                if (!level.maybeGrowTree(x, y, z)) {
                    level.setTileNoUpdate(x, y, z, this.id);
                }
            }
        }
        else {
            level.setTile(x, y, z, 0);
        }
    }
}
