package net.classic.remastered.game.world.tile;

import net.classic.remastered.*;
import net.classic.remastered.client.particle.*;
import net.classic.remastered.game.entity.other.*;
import net.classic.remastered.game.world.*;

public final class TNTBlock extends Block
{
    private World level;
    
    public TNTBlock(final int var1, final int var2) {
        super(46, 8);
    }
    
    @Override
    protected final int getTextureId(final int texture) {
        return (texture == 0) ? (this.textureId + 2) : ((texture == 1) ? (this.textureId + 1) : this.textureId);
    }
    
    @Override
    public final int getDropCount() {
        return 0;
    }
    
    @Override
    public final void explode(final World var1, final int var2, final int var3, final int var4) {
        final PrimedTnt var5;
        final PrimedTnt primedTnt = var5 = new PrimedTnt(var1, var2 + 0.5f, var3 + 0.5f, var4 + 0.5f);
        primedTnt.life = TNTBlock.random.nextInt(var5.life / 4) + var5.life / 8;
        var1.addEntity(var5);
    }
    
    @Override
    public final void spawnBreakParticles(final World level, final int x, final int y, final int z, final ParticleManager particleManager) {
        level.addEntity(new PrimedTnt(level, x + 0.5f, y + 0.5f, z + 0.5f));
        super.spawnBreakParticles(level, x, y, z, particleManager);
    }
}
