package com.xifeng.tinkers_wizardry.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import electroblob.wizardry.registry.WizardryBlocks;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;

public class ConarmModifiers {
    public static ModifierMagicAffinity modifierMagicAffinity;
    public static ModifierChargeUp modifierChargeUp;

    public static void initModifiers(){
        modifierMagicAffinity = new ModifierMagicAffinity();
        RecipeMatchHolder.addRecipeMatch(modifierMagicAffinity, new RecipeMatch.ItemCombination(1, new ItemStack(WizardryItems.siphon_upgrade), new ItemStack(WizardryBlocks.crystal_block)));
        modifierChargeUp = new ModifierChargeUp();
        RecipeMatchHolder.addRecipeMatch(modifierChargeUp, new RecipeMatch.ItemCombination(1, new ItemStack(WizardryItems.cooldown_upgrade), new ItemStack(WizardryBlocks.crystal_block)));
    }
}
