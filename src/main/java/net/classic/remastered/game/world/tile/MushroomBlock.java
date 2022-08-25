package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.game.world.*;

public final class MushroomBlock extends FlowerBlock
{
    protected MushroomBlock(final int var1, final int var2) {
        super(var1, var2);
        final float var3 = 0.2f;
        this.setBounds(0.5f - var3, 0.0f, 0.5f - var3, var3 + 0.5f, var3 * 2.0f, var3 + 0.5f);
    }
    
    @Override
    public final void update(final World level, final int x, final int y, final int z, final Random rand) {
        final int var6 = level.getTile(x, y - 1, z);
        if (level.isLit(x, y, z) || (var6 != MushroomBlock.STONE.id && var6 != MushroomBlock.GRAVEL.id && var6 != MushroomBlock.COBBLESTONE.id)) {
            level.setTile(x, y, z, 0);
        }
    }
}
