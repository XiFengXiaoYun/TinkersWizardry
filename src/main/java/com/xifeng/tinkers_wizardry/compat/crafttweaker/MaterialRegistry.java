package com.xifeng.tinkers_wizardry.compat.crafttweaker;

import com.xifeng.tinkers_wizardry.materials.MagicMaterialStats;
import com.xifeng.tinkers_wizardry.materials.MagicMaterials;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.mc1120.CraftTweaker;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tinkerswizardry.Materials")
@ZenRegister
public class MaterialRegistry {
    @ZenMethod
    public static void addMagicStats(String mat, int mana, float spellPotency) {
        CraftTweakerAPI.logInfo("Adding material " + mat + " with stats: " + " mana:" + mana + " spell potency:" + spellPotency);
        MagicMaterialStats stats = new MagicMaterialStats(spellPotency, mana);
        MagicMaterials.crtMat.putIfAbsent(mat, stats);
    }
}
