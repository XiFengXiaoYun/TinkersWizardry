package com.xifeng.tinkers_wizardry.config;

import com.xifeng.tinkers_wizardry.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public final class ModConfig {
    @Config.Comment("Wizardry compact")
    public static boolean enableWizardryCompact = true;
    @Config.Comment("SpellBlade mana capacity increase per level")
    public static double manaCapacityIncrease = 0.15;
    @Config.Comment("SpellBlade spell potency increase per level")
    public static double spellPotencyIncrease = 0.15;
    @Config.Comment("Can spellblade trigger certain traits when target is hurt by spells, it's kind of op")
    public static boolean spellTriggerCertainTraits = false;
}
