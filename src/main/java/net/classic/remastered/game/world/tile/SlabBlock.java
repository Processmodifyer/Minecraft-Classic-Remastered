package net.classic.remastered.game.world.tile;

import net.classic.remastered.game.world.*;

public final class SlabBlock extends Block
{
    private boolean doubleSlab;
    
    public SlabBlock(final int var1, final boolean var2) {
        super(var1, 6);
        if (!(this.doubleSlab = var2)) {
            this.setBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture <= 1) ? 6 : 5;
    }
    
    @Override
    public final boolean isSolid() {
        return this.doubleSlab;
    }
    
    @Override
    public final void onNeighborChange(final World var1, final int var2, final int var3, final int var4, final int var5) {
        if (this == SlabBlock.SLAB) {}
    }
    
    @Override
    public final void onAdded(final World level, final int x, final int y, final int z) {
        if (this != SlabBlock.SLAB) {
            super.onAdded(level, x, y, z);
        }
        if (level.getTile(x, y - 1, z) == SlabBlock.SLAB.id) {
            level.setTile(x, y, z, 0);
            level.setTile(x, y - 1, z, SlabBlock.DOUBLE_SLAB.id);
        }
    }
    
    @Override
    public final int getDrop() {
        return SlabBlock.SLAB.id;
    }
    
    @Override
    public final boolean isCube() {
        return this.doubleSlab;
    }
    
    @Override
    public final boolean canRenderSide(final World level, final int x, final int y, final int z, final int side) {
        if (this != SlabBlock.SLAB) {
            super.canRenderSide(level, x, y, z, side);
        }
        return side == 1 || (super.canRenderSide(level, x, y, z, side) && (side == 0 || level.getTile(x, y, z) != this.id));
    }
}
