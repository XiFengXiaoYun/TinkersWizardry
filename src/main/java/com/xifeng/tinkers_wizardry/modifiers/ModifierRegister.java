package com.xifeng.tinkers_wizardry.modifiers;

import electroblob.wizardry.registry.WizardryItems;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ModifierRegister {
    private static final ModifierMagicEnhance magicEnhance =  new ModifierMagicEnhance();

    public static void initModifiers() {
        magicEnhance.addItem(WizardryItems.magic_crystal);
        TinkerRegistry.addTrait(magicEnhance);
    }
}
