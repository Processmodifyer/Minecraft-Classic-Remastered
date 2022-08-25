/*
 * 
 */
package net.classic.remastered.game.player.inventory;

import java.io.Serializable;

import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public class Inventory
implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int POP_TIME_DURATION = 5;
    public int[] slots = new int[9];
    public int[] count = new int[9];
    public int[] popTime = new int[9];
    public int selected = 0;
	private Item[] Item;
    
    public Inventory() {
        for (int var1 = 0; var1 < 9; ++var1) {
            this.slots[var1] = -1;
            this.count[var1] = 0;
        }
    }

    public int getSelected() {
        return this.slots[this.selected];
    }
    
    public Item getItemStack() {
        return Item[this.selected];
    }

    public int getSlot(int var1) {
        for (int var2 = 0; var2 < this.slots.length; ++var2) {
            if (var1 != this.slots[var2]) continue;
            return var2;
        }
        return -1;
    }

    public void grabTexture(int var1, boolean var2) {
        int var3 = this.getSlot(var1);
        if (var3 >= 0) {
            this.selected = var3;
        } else if (var2 && var1 > 0 && CreativeInventoryStorage.allowedBlocks.contains(Block.blocks[var1])) {
            this.replaceSlot(Block.blocks[var1]);
        }
    }

    public void swapPaint(int var1) {
        if (var1 > 0) {
            var1 = 1;
        }
        if (var1 < 0) {
            var1 = -1;
        }
        this.selected -= var1;
        while (this.selected < 0) {
            this.selected += this.slots.length;
        }
        while (this.selected >= this.slots.length) {
            this.selected -= this.slots.length;
        }
    }

    public void replaceSlot(int var1) {
        if (var1 >= 0) {
            this.replaceSlot((Block)CreativeInventoryStorage.allowedBlocks.get(var1));
        }
    }

    public void replaceSlot(Block var1) {
        if (var1 != null) {
            int var2 = this.getSlot(var1.id);
            if (var2 >= 0) {
                this.slots[var2] = this.slots[this.selected];
            }
            this.slots[this.selected] = var1.id;
        }
    }

    public boolean addResource(int var1) {
        int var2 = this.getSlot(var1);
        if (var2 < 0) {
            var2 = this.getSlot(-1);
        }
        if (var2 < 0) {
            return false;
        }
        if (this.count[var2] >= 64) {
            return false;
        }
        this.slots[var2] = var1;
        int n = var2;
        this.count[n] = this.count[n] + 1;
        this.popTime[var2] = 5;
        return true;
    }

    public void tick() {
        for (int var1 = 0; var1 < this.popTime.length; ++var1) {
            if (this.popTime[var1] <= 0) continue;
            int n = var1;
            this.popTime[n] = this.popTime[n] - 1;
        }
    }

    public boolean removeResource(int var1) {
        if ((var1 = this.getSlot(var1)) < 0) {
            return false;
        }
        int n = var1;
        this.count[n] = this.count[n] - 1;
        if (this.count[n] <= 0) {
            this.slots[var1] = -1;
        }
        return true;
    }

    public boolean DonotRemoveResource(int item) {
        if ((item = this.getSlot(item)) < 0) {
            return false;
        }
        int n = item;
        if (this.count[n] <= 0) {
        }
        return false;    
     }
}

