package net.classic.remastered.game.world.tile;

public final class WorkBenchBlock extends Block
{
    public WorkBenchBlock(final int var1, final int var2) {
        super(54, 26);
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture == 0) ? (this.textureId + 2) : ((texture == 1) ? (this.textureId + 1) : this.textureId);
    }
    
    @Override
    public final int getDropCount() {
        return 1;
    }
    
    @Override
    public final int getDrop() {
        return Block.WOOD.getDrop();
    }
}
