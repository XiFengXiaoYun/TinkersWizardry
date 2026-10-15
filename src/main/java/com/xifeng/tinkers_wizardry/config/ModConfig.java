package com.xifeng.tinkers_wizardry.config;

import com.xifeng.tinkers_wizardry.Tags;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID)
public final class ModConfig {
    @Config.Comment("Settings about all the modifiers")
    public static Modifier modifier;
    @Config.Comment("SpellBlade mana capacity increase per level")
    public static double manaCapacityIncrease = 0.15;
    @Config.Comment("SpellBlade spell potency increase per level")
    public static double spellPotencyIncrease = 0.15;
    @Config.Comment("Melee magic damage increase per level of melee upgrade")
    public static double meleeMagicDamageIncrease = 0.125;
    @Config.Comment("Can spellblade trigger certain traits when target is hurt by spells, it's kind of op")
    public static boolean spellTriggerCertainTraits = false;
    @Config.Comment("Does spellblade require the consumption of stored mana to infuse melee attacks with magic damage, the mana consumption depends on magic damage dealt")
    public static boolean magicDmgCostMana = true;

    public static class Modifier {
        @Config.Comment("Settings about upgrade modifiers, which can give potion effect when all the armors(helmet,chestplate,leggings,boots) has the same type of upgrade")
        public static Upgrade upgrade;
        @Config.Comment("Potency increase in percent per magic crystal for magic enhance modifier")
        @Config.RangeDouble(min = 0, max = 1)
        public static double potencyIncrease = 0.01;
        @Config.Comment("Spell cost discount for magic affinity modifier")
        @Config.RangeDouble(min = -1, max = 0)
        public static double magicAffinity = -0.1;
        @Config.Comment("Spell cooldown reduction for magic chargeup modifier")
        @Config.RangeDouble(min = -1, max = 0)
        public static double cooldown = -0.1;

        public static class Upgrade {
            @Config.Comment("Spell cost discount for each upgrade modifier, order is potency, cooldown, cost upgrade")
            @Config.RangeDouble(min = -1, max = 0)
            public static double[] costReduction = new double[] {
                    -0.05,
                    -0.1,
                    -0.15
            };
            @Config.Comment("Spell potency bonus for potency upgrade, first value is max potency bonus, second is base bonus")
            @Config.RangeDouble(min = -0, max = 100)
            public static double[] potencyBonus = new double[] {
                    25.0,
                    7.5
            };
            @Config.Comment("Spell cooldown reduction for cooldown upgrade, first value is max cooldown reduction, second is base cooldown reduction")
            @Config.RangeDouble(min = -1, max = 0)
            public static double[] cooldown = new double[] {
                    -0.15,
                    -0.05
            };
            @Config.Comment("Mana condensing for cost upgrade, first value is max condensing value, second is base condensing value")
            @Config.RangeDouble(min = 0, max = 3)
            public static double[] condenser = new double[] {
                    1,
                    0.25
            };

            @Config.Comment("Potion effect to give when player's armors has the same upgrade modifier, order is potency, cooldown, cost upgrade")
            public static String[] potionBonus = new String[] {
                    "ebwizardry:ward",
                    "minecraft:speed",
                    "ebwizardry:muffle"
            };
        }
    }
}
