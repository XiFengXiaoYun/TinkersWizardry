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

public class ModifierMagicAffinity extends ArmorModifierTrait {
    private static final UUID[] uuids = {UUID.nameUUIDFromBytes("affinity".getBytes()), UUID.nameUUIDFromBytes("affinity1".getBytes()), UUID.nameUUIDFromBytes("affinity2".getBytes()), UUID.nameUUIDFromBytes("affinity3".getBytes())};
    public ModifierMagicAffinity() {
        super("magic_affinity", 0x66b2ff);
    }
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(Attributes.COST.getName(), new AttributeModifier(uuids[slot.getIndex()], "magic_affinity", -0.125, 1));
        }
    }
}
