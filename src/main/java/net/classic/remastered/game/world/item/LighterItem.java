package net.classic.remastered.game.world.item;

import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class LighterItem extends Item {

	public LighterItem(int id, int textureid) {
        super(64, 54);
	}
    public void onAdded(final World level, final int x, final int y, final int z) {
        level.setTile(x, y, z, Block.FIREBLOCK.id);
      
            
    }

}
