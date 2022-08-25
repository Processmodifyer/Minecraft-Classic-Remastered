package net.classic.remastered.game.world.item;

import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.tile.Block;

public class FeatherItem extends Item
{
	public FeatherItem(final int id, final int textureID) {
        super(61, 51);
    }
	public boolean useItem(Player player, int type)
	{
		Block block = Block.blocks[type];
		if(block == Item.FEATHER && minecraft.player.inventory.removeResource(type))
		{
        player.heal(10);
		} else {
			return false;
		}
		return true;
	}
}
