package net.classic.remastered.game.world.item;

public class SignItem extends Item {

	public SignItem(int id, int textureid) {
		super(66, 43);
	}
	
	public void use() {
	  this.minecraft.level.addEntity(null);
	}

}
