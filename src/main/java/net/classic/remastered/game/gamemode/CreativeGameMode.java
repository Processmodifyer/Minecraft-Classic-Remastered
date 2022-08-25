package net.classic.remastered.game.gamemode;

import net.classic.remastered.client.gui.BlockSelectScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.player.inventory.CreativeInventoryStorage;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class CreativeGameMode extends GameMode
{
	public CreativeGameMode(Minecraft minecraft)
	{
		super(minecraft);

		instantBreak = true;
	}

	@Override
	public void apply(World world)
	{
		super.apply(world);

		world.removeAllNonCreativeModeEntities();

		world.creativeMode = true;
		world.growTrees = true;
	}

	@Override
	public void openInventory()
	{
		BlockSelectScreen  blockSelectScreen = new BlockSelectScreen ();

		minecraft.setCurrentScreen(blockSelectScreen);
	}

	@Override
	public boolean isSurvival()
	{
		return false;
	}

	@Override
	public void apply(Player player)
	{
		for(int slot = 0; slot < 9; slot++)
		{
			player.inventory.count[slot] = 1;

			if(player.inventory.slots[slot] <= 0)
			{
				player.inventory.slots[slot] = ((Block) CreativeInventoryStorage.allowedBlocks.get(slot)).id;
			}
		}

	}
}