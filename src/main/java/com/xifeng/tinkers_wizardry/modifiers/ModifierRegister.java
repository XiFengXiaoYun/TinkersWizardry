package com.xifeng.tinkers_wizardry.modifiers;

import electroblob.wizardry.registry.WizardryItems;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ModifierRegister {
    public static final ModifierMagicEnhance magicEnhance =  new ModifierMagicEnhance();
    //private static final ModifierAstral astral = new ModifierAstral();

    public static void initModifiers() {
        magicEnhance.addItem(WizardryItems.magic_crystal);
        TinkerRegistry.addTrait(magicEnhance);
        //astral.addItem(WizardryItems.astral_diamond);
        //TinkerRegistry.addTrait(astral);
    }
}
