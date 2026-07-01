package com.xifeng.tinkers_wizardry.conarm;

import c4.conarm.lib.modifiers.ArmorModifier;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public abstract class ModifierArmorUpgrade extends ArmorModifierTrait {
    public ModifierArmorUpgrade(String type) {
        super("upgrade_" + type, 0xffe57f);
    }

    public static class UpgradeNBT extends ModifierNBT {
        public String element;
        public UpgradeNBT(ArmorModifier modifier, String element) {
            super(modifier);
            this.element = element;
        }

        public void read(NBTTagCompound tag) {
            super.read(tag);
            element = tag.getString("element");
        }

        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setString("element", element);
        }
    }
}
