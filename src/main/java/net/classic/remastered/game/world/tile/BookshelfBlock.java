package net.classic.remastered.game.world.tile;

public final class BookshelfBlock extends Block
{
    public BookshelfBlock(final int var1, final int var2) {
        super(47, 35);
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture <= 1) ? 4 : this.textureId;
    }
    
    @Override
    public final int getDropCount() {
        return 0;
    }
}
