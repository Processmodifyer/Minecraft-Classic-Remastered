package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.game.world.*;

public final class GrassBlock extends Block
{
    protected GrassBlock(final int var1) {
        super(2);
        this.textureId = 3;
        this.setPhysics(true);
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture == 1) ? 0 : ((texture == 0) ? 2 : 3);
    }
    
    @Override
    public final void update(final World level, final int x, final int y, final int z, final Random rand) {
        if (rand.nextInt(4) == 0) {
            if (!level.isLit(x, y, z)) {
                level.setTile(x, y, z, GrassBlock.DIRT.id);
            }
            else {
                for (int var9 = 0; var9 < 4; ++var9) {
                    final int var10 = x + rand.nextInt(3) - 1;
                    final int var11 = y + rand.nextInt(5) - 3;
                    final int var12 = z + rand.nextInt(3) - 1;
                    if (level.getTile(var10, var11, var12) == GrassBlock.DIRT.id && level.isLit(var10, var11, var12)) {
                        level.setTile(var10, var11, var12, GrassBlock.GRASS.id);
                    }
                }
            }
        }
    }
    
    @Override
    public final int getDrop() {
        return GrassBlock.DIRT.getDrop();
    }
}
