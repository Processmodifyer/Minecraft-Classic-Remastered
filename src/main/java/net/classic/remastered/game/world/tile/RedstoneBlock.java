package net.classic.remastered.game.world.tile;

public class RedstoneBlock extends Block {

	public RedstoneBlock(final int var1, final int var2) {
		super(var1, var2);
        this.textureId = 112;
	}
	
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture == 1) ? (this.textureId - 16) : ((texture == 0) ? (this.textureId + 16) : this.textureId);
    }

}
