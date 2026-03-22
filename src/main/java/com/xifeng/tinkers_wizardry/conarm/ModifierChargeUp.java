package com.xifeng.tinkers_wizardry.conarm;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.Multimap;
import com.windanesz.wizardryutils.server.Attributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ModifierChargeUp extends ArmorModifierTrait {
    private static final UUID[] uuids = {UUID.nameUUIDFromBytes("chargeup".getBytes()), UUID.nameUUIDFromBytes("chargeup1".getBytes()), UUID.nameUUIDFromBytes("chargeup2".getBytes()), UUID.nameUUIDFromBytes("chargeup3".getBytes())};
    public ModifierChargeUp() {
        super("magic_chargeup", 0xffe57f);
    }
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(Attributes.COOLDOWN.getName(), new AttributeModifier(uuids[slot.getIndex()], "magic_chargeup", -0.10, 1));
        }
    }
}
