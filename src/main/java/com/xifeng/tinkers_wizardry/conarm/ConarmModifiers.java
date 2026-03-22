package com.xifeng.tinkers_wizardry.conarm;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.utils.RecipeMatchHolder;
import electroblob.wizardry.registry.WizardryBlocks;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;

import java.util.HashSet;
import java.util.Set;

public class ConarmModifiers {
    public static final Set<ArmorModifierTrait> modifiers = new HashSet<>();
    public static ModifierMagicAffinity modifierMagicAffinity;
    public static ModifierChargeUp modifierChargeUp;

    public static void initModifiers(){
        modifierMagicAffinity = new ModifierMagicAffinity();
        RecipeMatchHolder.addRecipeMatch(modifierMagicAffinity, new RecipeMatch.ItemCombination(1, new ItemStack(WizardryItems.siphon_upgrade), new ItemStack(WizardryBlocks.crystal_block)));
        modifiers.add(modifierMagicAffinity);
        modifierChargeUp = new ModifierChargeUp();
        RecipeMatchHolder.addRecipeMatch(modifierChargeUp, new RecipeMatch.ItemCombination(1, new ItemStack(WizardryItems.cooldown_upgrade), new ItemStack(WizardryBlocks.crystal_block)));
        modifiers.add(modifierChargeUp);
    }
}
