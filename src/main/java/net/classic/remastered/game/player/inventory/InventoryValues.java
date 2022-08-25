/*
 * 
 */
package net.classic.remastered.game.player.inventory;

import java.util.ArrayList;
import java.util.List;

import net.classic.remastered.game.world.tile.Block;

public final class InventoryValues {
    public static List<Block> allowedBlocks = new ArrayList<Block>();
    public static List<Integer> inventoryCount = new ArrayList<Integer>();

    public InventoryValues(String var1, String var2) {
    }

    public static void resetlist() {
        allowedBlocks.clear();
        inventoryCount.clear();
    }

    public static String getList() {
        String combined = "";
        for (int i = 0; i < allowedBlocks.size(); ++i) {
            combined = i != 0 ? String.valueOf(String.valueOf(combined)) + ";" + InventoryValues.allowedBlocks.get((int)i).id + "-" + inventoryCount.get(i) : String.valueOf(String.valueOf(combined)) + InventoryValues.allowedBlocks.get((int)i).id + "-" + inventoryCount.get(i);
        }
        return combined;
    }

    public static void setList(String splitting) {
        allowedBlocks.clear();
        inventoryCount.clear();
        String[] blocks = splitting.split(";");
        for (int i = 0; i < blocks.length; ++i) {
            String[] last = blocks[i].split("-");
            try {
                allowedBlocks.add(Block.blocks[Integer.parseInt(last[0])]);
                inventoryCount.add(Integer.parseInt(last[1]));
                continue;
            }
            catch (Exception e) {
                InventoryValues.resetlist();
            }
        }
    }

    public static void addItem(Block blk, int count) {
        if (allowedBlocks.indexOf(blk) != -1) {
            int where = allowedBlocks.indexOf(blk);
            inventoryCount.set(where, count + inventoryCount.get(where));
        } else {
            allowedBlocks.add(blk);
            inventoryCount.add(count);
        }
    }
}

