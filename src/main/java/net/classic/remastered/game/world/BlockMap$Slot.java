/*
 * 
 */
package net.classic.remastered.game.world;

import java.io.Serializable;

import net.classic.remastered.game.entity.Entity;

class BlockMap$Slot
implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int xSlot;
    private int ySlot;
    private int zSlot;
    final BlockMap blockMap;

    private BlockMap$Slot(BlockMap var1) {
        this.blockMap = var1;
    }

    public BlockMap$Slot init(float var1, float var2, float var3) {
        this.xSlot = (int)(var1 / 16.0f);
        this.ySlot = (int)(var2 / 16.0f);
        this.zSlot = (int)(var3 / 16.0f);
        if (this.xSlot < 0) {
            this.xSlot = 0;
        }
        if (this.ySlot < 0) {
            this.ySlot = 0;
        }
        if (this.zSlot < 0) {
            this.zSlot = 0;
        }
        if (this.xSlot >= BlockMap.getWidth(this.blockMap)) {
            this.xSlot = BlockMap.getWidth(this.blockMap) - 1;
        }
        if (this.ySlot >= BlockMap.getDepth(this.blockMap)) {
            this.ySlot = BlockMap.getDepth(this.blockMap) - 1;
        }
        if (this.zSlot >= BlockMap.getHeight(this.blockMap)) {
            this.zSlot = BlockMap.getHeight(this.blockMap) - 1;
        }
        return this;
    }

    @SuppressWarnings("unchecked")
	public void add(Entity var1) {
        if (this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
            this.blockMap.entityGrid[(this.zSlot * BlockMap.getDepth(this.blockMap) + this.ySlot) * BlockMap.getWidth(this.blockMap) + this.xSlot].add(var1);
        }
    }

    public void remove(Entity var1) {
        if (this.xSlot >= 0 && this.ySlot >= 0 && this.zSlot >= 0) {
            this.blockMap.entityGrid[(this.zSlot * BlockMap.getDepth(this.blockMap) + this.ySlot) * BlockMap.getWidth(this.blockMap) + this.xSlot].remove(var1);
        }
    }

    BlockMap$Slot(BlockMap var1, SyntheticClass var2) {
        this(var1);
    }

    static int getXSlot(BlockMap$Slot var0) {
        return var0.xSlot;
    }

    static int getYSlot(BlockMap$Slot var0) {
        return var0.ySlot;
    }

    static int getZSlot(BlockMap$Slot var0) {
        return var0.zSlot;
    }
}

