package com.xifeng.tinkers_wizardry.materials;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class MagicMaterials {

    public static void initMagicMaterials() {
        Material.UNKNOWN.addStats(new MagicMaterialStats(1.0f, 100));
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood, new MagicMaterialStats(1.5f, 80));
        TinkerRegistry.addMaterialStats(TinkerMaterials.manyullyn, new MagicMaterialStats(25.0f, 1000));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone, new MagicMaterialStats(2.0f, 300));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blaze, new MagicMaterialStats(5.0f, 400));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ardite, new MagicMaterialStats(15.0f, 850));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cobalt, new MagicMaterialStats(12.0f, 720));
        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine, new MagicMaterialStats(10.0f, 400));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum, new MagicMaterialStats(18.0f, 400));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bloodbone, new MagicMaterialStats(10.0f, 500));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver, new MagicMaterialStats(17.0f, 600));
        TinkerRegistry.addMaterialStats(TinkerMaterials.alumite, new MagicMaterialStats(15.0f, 550));
        TinkerRegistry.addMaterialStats(TinkerMaterials.xu,  new MagicMaterialStats(20.0f, 900));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel, new MagicMaterialStats(15.0f, 750));
    }
}
