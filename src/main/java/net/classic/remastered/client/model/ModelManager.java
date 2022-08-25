/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.AnimalModel;
import net.classic.remastered.client.model.ChickenModel;
import net.classic.remastered.client.model.CowModel;
import net.classic.remastered.client.model.CreeperModel;
import net.classic.remastered.client.model.CrocodileModel;
import net.classic.remastered.client.model.EnderKingModel;
import net.classic.remastered.client.model.EndermanModel;
import net.classic.remastered.client.model.GiantCreeperModel;
import net.classic.remastered.client.model.HumanoidModel;
import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.PigModel;
import net.classic.remastered.client.model.PrinterModel;
import net.classic.remastered.client.model.SheepFurModel;
import net.classic.remastered.client.model.SheepModel;
import net.classic.remastered.client.model.SkeletonModel;
import net.classic.remastered.client.model.SpiderModel;
import net.classic.remastered.client.model.TntSpeerModel;
import net.classic.remastered.client.model.ZombieModel;

public final class ModelManager {
    private HumanoidModel human = new HumanoidModel(0.0f);
    private HumanoidModel armoredHuman = new HumanoidModel(1.0f);
    private CreeperModel creeper = new CreeperModel();
    private SkeletonModel skeleton = new SkeletonModel();
    private ZombieModel zombie = new ZombieModel();
    private AnimalModel pig = new PigModel();
    private AnimalModel sheep = new SheepModel();
    private SpiderModel spider = new SpiderModel();
    private SheepFurModel sheepFur = new SheepFurModel();
    private CrocodileModel crocodille = new CrocodileModel();
    private ChickenModel chicken = new ChickenModel();
    private PrinterModel printer = new PrinterModel();
    private CowModel cow = new CowModel();
    private TntSpeerModel speer = new TntSpeerModel(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    private EndermanModel endermen = new EndermanModel();
    private GiantCreeperModel creeperboss = new GiantCreeperModel();
    private EnderKingModel hingno = new EnderKingModel();

    public final Model getModel(String var1) {
        return var1.equals("humanoid") ? this.human : (var1.equals("humanoid.armor") ? this.armoredHuman : (var1.equals("creeper") ? this.creeper : (var1.equals("speer") ? this.speer : (var1.equals("cow") ? this.cow : (var1.equals("endermen") ? this.endermen : (var1.equals("creeperboss") ? this.creeperboss : (var1.equals("hingno") ? this.hingno : (var1.equals("printer") ? this.printer : (var1.equals("chicken") ? this.chicken : (var1.equals("skeleton") ? this.skeleton : (var1.equals("zombie") ? this.zombie : (var1.equals("pig") ? this.pig : (var1.equals("crocodile") ? this.crocodille : (var1.equals("sheep") ? this.sheep : (var1.equals("spider") ? this.spider : (var1.equals("sheep.fur") ? this.sheepFur : null))))))))))))))));
    }
}

