package net.classic.remastered.game.world.tile;

import net.classic.remastered.game.world.*;

public class LeavesBaseBlock extends Block
{
    private boolean showNeighborSides;
    
    protected LeavesBaseBlock(final int var1, final int var2, final boolean var3) {
        super(var1, var2);
        this.showNeighborSides = true;
    }
    
    @Override
    public final boolean isSolid() {
        return false;
    }
    
    @Override
    public final boolean canRenderSide(final World level, final int x, final int y, final int z, final int side) {
        final int var6 = level.getTile(x, y, z);
        return (this.showNeighborSides || var6 != this.id) && super.canRenderSide(level, x, y, z, side);
    }
    
    @Override
    public final boolean isOpaque() {
        return false;
    }
}
