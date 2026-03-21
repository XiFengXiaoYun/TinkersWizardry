package com.xifeng.tinkers_wizardry.modifiers;

import electroblob.wizardry.registry.WizardryItems;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

import java.util.HashSet;
import java.util.Set;

public class ModifierRegister {
    public static final Set<ModifierTrait> modifierTraits = new HashSet<>();
    private static final ModifierMagicEnhance magicEnhance =  new ModifierMagicEnhance();

    public static void initModifiers() {
        magicEnhance.addItem(WizardryItems.magic_crystal);
        TinkerRegistry.addTrait(magicEnhance);
        modifierTraits.add(magicEnhance);
    }
}
