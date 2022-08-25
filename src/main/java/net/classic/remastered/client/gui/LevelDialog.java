/*
 * 
 */
package net.classic.remastered.client.gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.classic.remastered.client.gui.LoadLevelScreen;

final class LevelDialog
extends Thread {
    private LoadLevelScreen screen;

    LevelDialog(LoadLevelScreen var1) {
        this.screen = var1;
    }

    @Override
    public final void run() {
        LoadLevelScreen var2;
        JFileChooser var1;
        try {
            int var7;
            LoadLevelScreen var10000 = this.screen;
            var10000.chooser = var1 = new JFileChooser();
            FileNameExtensionFilter var3 = new FileNameExtensionFilter("Minecraft levels", "mine");
            var2 = this.screen;
            this.screen.chooser.setFileFilter(var3);
            var2 = this.screen;
            this.screen.chooser.setMultiSelectionEnabled(false);
            if (this.screen.saving) {
                var2 = this.screen;
                var7 = this.screen.chooser.showSaveDialog(this.screen.minecraft.canvas);
            } else {
                var2 = this.screen;
                var7 = this.screen.chooser.showOpenDialog(this.screen.minecraft.canvas);
            }
            if (var7 == 0) {
                var2 = this.screen;
                this.screen.selectedFile = this.screen.chooser.getSelectedFile();
            }
        }
        finally {
            boolean var6 = false;
            var2 = this.screen;
            this.screen.frozen = false;
            var1 = null;
            var2 = this.screen;
            this.screen.chooser = var1;
        }
    }
}

