package com.xifeng.tinkers_wizardry.conarm.upgrade;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.Multimap;
import com.windanesz.wizardryutils.server.Attributes;
import com.xifeng.tinkers_wizardry.config.ModConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class ModifierArmorUpgrade extends ArmorModifierTrait {
    private static final UUID[] uuids = new UUID[]{
            UUID.fromString("a051a345-a94d-646f-1e92-f9b637c5c27a"),
            UUID.fromString("136ef64b-6056-f4d6-e98c-183e1792e45c"),
            UUID.fromString("0d27927e-0897-e62b-56b6-0942c9bf0574"),
            UUID.fromString("cf7c049c-6e68-fbc9-ff58-703d099af1f5")
    };
    private static final UUID[] uuid2 = new UUID[] {
            UUID.fromString("55936a2e-089c-af74-2d62-b704bef7c6c4"),
            UUID.fromString("60d8cba2-5c0f-2bbe-eb71-bdd58eb33f82"),
            UUID.fromString("88824d10-242f-5796-d8ee-4430183054a5"),
            UUID.fromString("162ae674-883d-df5b-9da1-1b620e93d85b")
    };
    private static final String attributePrefix = "wizardryutils.";
    private static final String potencySuffix = "SpellPotency";
    private static final String cooldownSuffix = "SpellCooldown";
    private static final String costSuffix = "SpellCost";
    public UpgradeType type;
    public double costDiscount = 0;
    public ModifierArmorUpgrade(UpgradeType type) {
        super(type.getIdentifier(), 0xffe57f);
        this.type = type;
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !(otherModifier instanceof ModifierArmorUpgrade);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(ToolHelper.isBroken(stack)) return;
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            ElementNBT nbt = ElementNBT.get(stack, type);
            if(nbt == null) return;
            int index = nbt.hasElement() ? 0 : 1;
            applyModifier(attributeMap, slot, index, nbt.element);
            if(nbt.hasElement()) {
                attributeMap.put(getElementAttribute(UpgradeType.COST, nbt.element), new AttributeModifier(uuid2[slot.getIndex()], "CostDiscount", costDiscount, 2));
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        if(player.world.isRemote) return;
        if(player.ticksExisted % 20 != 0) return;
        //四件套才有套装效果
        for(ItemStack stack : player.getArmorInventoryList()) {
            if(!TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), identifier)) {
                return;
            }
        }
        applyEffect(player);
    }

    protected void applyModifier(Multimap<String, AttributeModifier> attributeMap, EntityEquipmentSlot slot, int index, String element) {

    }

    protected Potion getPotion() {
        return null;
    }

    private void applyEffect(EntityPlayer player) {
        Potion potion = getPotion();
        if(potion != null) {
            player.addPotionEffect(new PotionEffect(potion, 120, 0, false, false));
        }
    }

    private static String getElementAttribute(UpgradeType type, String element) {
        if(element == null || element.isEmpty()) return "";
        String suffix = "";
        switch (type) {
            case POTENCY:
                suffix = potencySuffix;
                break;
            case COOLDOWN:
                suffix = cooldownSuffix;
                break;
            case COST:
                suffix = costSuffix;
                break;
        }
        if(element.equals(ElementNBT.NONE)) return attributePrefix + suffix;
        return attributePrefix + element.charAt(0) + element.substring(1).toLowerCase() + suffix;
    }

    public static class Potency extends ModifierArmorUpgrade {
        public Potency() {
            super(UpgradeType.POTENCY);
            this.costDiscount = ModConfig.Modifier.Upgrade.costReduction[0];
        }
        protected void applyModifier(Multimap<String, AttributeModifier> attributeMap, EntityEquipmentSlot slot, int index, String element) {
            double amount = ModConfig.Modifier.Upgrade.potencyBonus[index];
            attributeMap.put(ModifierArmorUpgrade.getElementAttribute(type, element), new AttributeModifier(uuids[slot.getIndex()], identifier, amount, 0));
        }

        protected Potion getPotion() {
            return Potion.getPotionFromResourceLocation(ModConfig.Modifier.Upgrade.potionBonus[0]);
        }
    }

    public static class CoolDown extends ModifierArmorUpgrade {
        public CoolDown() {
            super(UpgradeType.COOLDOWN);
            this.costDiscount = ModConfig.Modifier.Upgrade.costReduction[1];
        }
        protected void applyModifier(Multimap<String, AttributeModifier> attributeMap, EntityEquipmentSlot slot, int index, String element) {
            double amount = ModConfig.Modifier.Upgrade.cooldown[index];
            attributeMap.put(ModifierArmorUpgrade.getElementAttribute(type, element), new AttributeModifier(uuids[slot.getIndex()], identifier, amount, 2));
        }
        protected Potion getPotion() {
            return Potion.getPotionFromResourceLocation(ModConfig.Modifier.Upgrade.potionBonus[1]);
        }

    }

    public static class Cost extends ModifierArmorUpgrade {
        public Cost() {
            super(UpgradeType.COST);
            this.costDiscount = ModConfig.Modifier.Upgrade.costReduction[2];
        }
        protected void applyModifier(Multimap<String, AttributeModifier> attributeMap, EntityEquipmentSlot slot, int index, String element) {
            double amount = ModConfig.Modifier.Upgrade.condenser[index];
            attributeMap.put(Attributes.CONDENSING.getName(), new AttributeModifier(uuids[slot.getIndex()], identifier, amount, 0));
        }
        protected Potion getPotion() {
            return Potion.getPotionFromResourceLocation(ModConfig.Modifier.Upgrade.potionBonus[2]);
        }
    }
}
