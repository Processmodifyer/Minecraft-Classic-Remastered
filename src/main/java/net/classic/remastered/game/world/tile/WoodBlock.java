package net.classic.remastered.game.world.tile;

public final class WoodBlock extends Block
{
    protected WoodBlock(final int var1) {
        super(17);
        this.textureId = 20;
    }
    
    @Override
    public final int getDropCount() {
        return WoodBlock.random.nextInt(3) + 3;
    }
    
    @Override
    public final int getDrop() {
        return WoodBlock.WOOD.id;
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture == 1) ? 21 : ((texture == 0) ? 21 : 20);
    }
}
