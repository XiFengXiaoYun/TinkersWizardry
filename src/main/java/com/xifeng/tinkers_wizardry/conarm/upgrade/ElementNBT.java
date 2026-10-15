package com.xifeng.tinkers_wizardry.conarm.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ElementNBT extends ModifierNBT {
    public static final String NONE = "NONE";
    public String element = NONE;
    public ElementNBT(NBTTagCompound modTag) {
        this.read(modTag);
    }

    public void read(NBTTagCompound tag) {
        super.read(tag);
        element = tag.hasKey("element") ? tag.getString("element") : NONE;
    }

    public void write(NBTTagCompound tag) {
        super.write(tag);
        tag.setString("element", element);
    }
    public static ElementNBT get(ItemStack stack, UpgradeType type) {
        NBTTagCompound modTag = TinkerUtil.getModifierTag(stack, type.getTrueIdentifier());
        if(modTag.isEmpty()) {
            return null;
        }
        return get(modTag);
    }

    public static ElementNBT get(NBTTagCompound modTag) {
        return new ElementNBT(modTag);
    }

    public boolean hasElement() {
        return !element.equals(NONE);
    }
}
