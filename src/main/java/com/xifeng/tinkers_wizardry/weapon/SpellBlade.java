package com.xifeng.tinkers_wizardry.weapon;

import com.google.common.collect.Multimap;
import com.windanesz.wizardryutils.server.Attributes;
import com.xifeng.tinkers_wizardry.aspect.SpecialCategory;
import com.xifeng.tinkers_wizardry.config.ModConfig;
import com.xifeng.tinkers_wizardry.materials.MagicMaterialStats;
import com.xifeng.tinkers_wizardry.materials.MagicNBT;
import com.xifeng.tinkers_wizardry.modifiers.ModifierMagic;
import com.xifeng.tinkers_wizardry.part.MagicMaterialType;
import com.xifeng.tinkers_wizardry.utils.SpellBladeHelper;
import com.xifeng.tinkers_wizardry.utils.WizardryUtil;
import electroblob.wizardry.client.DrawingUtils;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.item.IWorkbenchItem;
import electroblob.wizardry.registry.WizardryItems;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.ParticleBuilder;
import electroblob.wizardry.util.SpellModifiers;
import electroblob.wizardry.util.WandHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.UUID;
public class SpellBlade extends SwordCore implements IWorkbenchItem, ISpellCastingItem, IManaStoringItem {
    private final WizardryCore wizardryCore;
    private static final UUID uuid = UUID.fromString("46b3ce0a-c968-dcd3-1d84-7045ff7d6580");

    public SpellBlade() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.knifeBlade),
                MagicMaterialType.magicFocus(WeaponHandler.magicFocus));
        this.addCategory(Category.WEAPON, SpecialCategory.Wizardry);
        setTranslationKey("spellblade").setRegistryName("spellblade");
        this.wizardryCore = new WizardryCore();
    }
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        if(this.isInCreativeTab(tab)) {
            addDefaultSubItems(subItems);
            addInfiTool(subItems, "InfiSpellBlade");
        }
    }

    public ItemStack getDemo() {
        return this.getInfiTool("InfiSpellBlade");
    }

    @Override
    protected MagicNBT buildTagData(List<Material> list) {
        if(list.isEmpty()) {
            throw new RuntimeException("The list is empty! Report this issue to the mod author!");
        }
        HandleMaterialStats handle = list.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
        HeadMaterialStats head = list.get(1).getStatsOrUnknown(MaterialTypes.HEAD);
        MagicMaterialStats magic = list.get(2).getStatsOrUnknown(MagicMaterialType.MAGICFOCUS);

        MagicNBT data = new MagicNBT();
        data.head(head);
        data.handle(handle);
        data.magic(magic);

        data.attack += 1;
        data.durability *= 0.8;
        return data;
    }

    @Override
    public float damagePotential() {
        return 0.5f;
    }

    @Override
    public double attackSpeed() {
        return 1.8;
    }

    //TODO: 播放对应元素的魔法粒子效果
    @Override
    public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
        boolean hit =  super.dealDamage(stack, player, entity, damage);
        if(player instanceof EntityPlayer && entity instanceof EntityLivingBase) {
            MagicNBT nbt = MagicNBT.from(stack);
            //近战升级也能提高附加伤害
            int level = WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade);
            float mod = (float) (level * ModConfig.meleMagicDamageIncrease + 1);
            double baseDamage = nbt.attack * 0.5 + 1;
            float potency = WizardryUtil.getSpellPotency(stack) / 100.0f;
            float bonusMagicDmg = (float) (baseDamage * potency) * mod;
            entity.hurtResistantTime = 0;
            ((EntityLivingBase) entity).lastDamage = 0;
            DamageSource source = DamageSource.causePlayerDamage((EntityPlayer) player);
            source.setMagicDamage();
            entity.attackEntityFrom(source, bonusMagicDmg);
            if(!SpellBladeHelper.getElement(stack).equals("MAGIC")) {
                spawnParticle(player, stack, entity);
            }
        }
        return hit;
    }

    //copy from wizardry
    private static void spawnParticle(EntityLivingBase player, ItemStack stack, Entity target) {
        if(!player.world.isRemote) return;
        Random rand = player.world.rand;
        Element element = Element.valueOf(SpellBladeHelper.getElement(stack));
        Vec3d origin = player.getPositionEyes(1.0f);
        Vec3d hit = origin.add(player.getLookVec().scale(player.getDistance(target)));
        Vec3d vec1 = player.getLookVec().rotatePitch(90);
        Vec3d vec2 = player.getLookVec().crossProduct(vec1);
        float r=0, g=0, b=0, fr=0, fg=0, fb=0;

        switch (element) {
            case FIRE:        r=1.0F; g=0.6F; b=0.0F; fr=0.8F; fg=0.1F; fb=0.0F; break;
            case ICE:         r=0.9F; g=0.95F;b=1.0F; fr=0.4F; fg=0.7F; fb=1.0F; break;
            case EARTH:       r=0.4F; g=1.0F; b=0.2F; fr=0.0F; fg=0.6F; fb=0.1F; break;
            case NECROMANCY:  r=0.6F; g=0.3F; b=0.8F; fr=0.2F; fg=0.0F; fb=0.3F; break;
            case HEALING:     r=1.0F; g=1.0F; b=1.0F; fr=1.0F; fg=0.7F; fb=0.2F; break;
            case LIGHTNING:   r=0.9F; g=0.9F; b=1.0F; fr=0.2F; fg=0.4F; fb=1.0F; break;
            case SORCERY:     r=0.8F; g=0.4F; b=1.0F; fr=0.4F; fg=0.0F; fb=0.6F; break;
            default: break;
        }
        for(int i = 0; i < 8; i++){
            Vec3d velocity = vec1.scale(rand.nextFloat() * 0.3f - 0.15f).add(vec2.scale(rand.nextFloat() * 0.3f - 0.15f));
            ParticleBuilder.create(ParticleBuilder.Type.SPARKLE)
                    .pos(hit)
                    .vel(velocity)
                    .clr(r, g, b)
                    .fade(fr, fg, fb)
                    .time(8 + rand.nextInt(4))
                    .spawn(player.world);
        }
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        double amount = calcSpellPotency(stack);

        if (slot == EntityEquipmentSlot.MAINHAND && !ToolHelper.isBroken(stack)) {
            multimap.put(Attributes.POTENCY.getName(), new AttributeModifier(uuid, "Magic_Potency", amount, 1));
        }

        return multimap;
    }

    private static double calcSpellPotency(ItemStack stack) {
        return WizardryUtil.getSpellPotency(stack)/100.0;
    }

    @Override
    public void addMaterialTraits(NBTTagCompound root, List<Material> materials) {
        super.addMaterialTraits(root, materials);
        ModifierMagic.INSTANCE.apply(root);
    }

    //Magic part!
    @Nonnull
    public Spell getCurrentSpell(ItemStack stack){
        return wizardryCore.getCurrentSpell(stack);
    }

    @MethodsReturnNonnullByDefault
    @Override
    public Spell getNextSpell(ItemStack stack){
        return wizardryCore.getNextSpell(stack);
    }

    @MethodsReturnNonnullByDefault
    @Override
    public Spell getPreviousSpell(ItemStack stack){
        return wizardryCore.getPreviousSpell(stack);
    }

    @Override
    public Spell[] getSpells(ItemStack stack){
        return wizardryCore.getSpells(stack);
    }

    @Override
    public void selectNextSpell(ItemStack stack){
        wizardryCore.selectNextSpell(stack);
    }

    @Override
    public void selectPreviousSpell(ItemStack stack){
        wizardryCore.selectPreviousSpell(stack);
    }

    @Override
    public boolean selectSpell(ItemStack stack, int index){
        return wizardryCore.selectSpell(stack, index);
    }

    @Override
    public int getCurrentCooldown(ItemStack stack){
        return wizardryCore.getCurrentCooldown(stack);
    }

    @Override
    public int getCurrentMaxCooldown(ItemStack stack){
        return wizardryCore.getCurrentMaxCooldown(stack);
    }

    @Override
    public boolean showSpellHUD(EntityPlayer player, ItemStack stack){
        return true;
    }

    @Override
    public boolean showTooltip(ItemStack stack){
        return true;
    }

    @Override
    public void setMana(ItemStack stack, int mana){
        WizardryUtil.setMana(stack, mana);
    }

    @Override
    public int getMana(ItemStack stack){
        return WizardryUtil.getMana(stack);
    }

    @Override
    public int getManaCapacity(ItemStack stack){
        return WizardryUtil.getMaxMana(stack);
    }

    @Override
    public void onUpdate(@ParametersAreNonnullByDefault ItemStack stack, @ParametersAreNonnullByDefault World world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean isHeldInMainhand){
        super.onUpdate(stack, world, entity, slot, isHeldInMainhand);
        wizardryCore.onUpdate(stack, world, entity, isHeldInMainhand);
    }

    @Override
    public boolean canContinueUsing(@ParametersAreNonnullByDefault ItemStack oldStack, @ParametersAreNonnullByDefault ItemStack newStack){
        return wizardryCore.canContinueUsing(oldStack, newStack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, @ParametersAreNonnullByDefault ItemStack newStack, boolean slotChanged){
        return wizardryCore.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @MethodsReturnNonnullByDefault
    @Override
    public EnumAction getItemUseAction(@ParametersAreNonnullByDefault ItemStack itemstack){
        return wizardryCore.getItemUseAction(itemstack);
    }

    @Override
    public int getMaxItemUseDuration(@ParametersAreNonnullByDefault ItemStack stack){
        return 72000;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@ParametersAreNonnullByDefault ItemStack stack, World world, @ParametersAreNonnullByDefault List<String> text, @ParametersAreNonnullByDefault net.minecraft.client.util.ITooltipFlag advanced){
        super.addInformation(stack, world, text, advanced);
        if(!Util.isCtrlKeyDown() && !Util.isShiftKeyDown()) {
            wizardryCore.addInformation(stack, text, advanced);
        }
    }

    @Override
    public int getRGBDurabilityForDisplay(@ParametersAreNonnullByDefault ItemStack stack){
        Element element = wizardryCore.getElement(stack);
        float percentage = (float) getDurabilityForDisplay(stack);
        switch (element){
            case MAGIC:
                return getColor(0xff8bfe, 0x8e2ee4, percentage);
            case ICE:
                return getColor(0x8bdeff, 0x2ea4e4,  percentage);
            case FIRE:
                return getColor(0xfb6374, 0xdb161f, percentage);
            case EARTH:
                return getColor(0x4aff83, 0x27b920, percentage);
            case HEALING:
                return getColor(0xf5fe6e, 0xdfeb1e, percentage);
            case SORCERY:
                return getColor(0x37fda4, 0x06c971, percentage);
            case LIGHTNING:
                return getColor(0x18a1d8, 0x056d97, percentage);
            case NECROMANCY:
                return getColor(0xc103de, 0x731d80, percentage);
        }
        return getColor(0xff8bfe, 0x8e2ee4, percentage);
    }

    private static int getColor(int color1, int color2, float percentage) {
        return DrawingUtils.mix(color1, color2, percentage);
    }

    @MethodsReturnNonnullByDefault
    @Override
    public ActionResult<ItemStack> onItemRightClick(@ParametersAreNonnullByDefault World world, @ParametersAreNonnullByDefault EntityPlayer player, @ParametersAreNonnullByDefault EnumHand hand){
        return wizardryCore.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUsingTick(@ParametersAreNonnullByDefault ItemStack stack, @ParametersAreNonnullByDefault EntityLivingBase user, int count){
        wizardryCore.onUsingTick(stack, user, count);
    }

    @Override
    public boolean canCast(ItemStack stack, Spell spell, EntityPlayer caster, EnumHand hand, int castingTick, SpellModifiers modifiers){
        if(ToolHelper.isBroken(stack)){
            return false;
        }
        return wizardryCore.canCast(stack, spell, caster, hand, castingTick, modifiers);
    }

    @Override
    public boolean cast(ItemStack stack, Spell spell, EntityPlayer caster, EnumHand hand, int castingTick, SpellModifiers modifiers){
        if(ToolHelper.isBroken(stack)){
            return false;
        }
        return wizardryCore.cast(stack, spell, caster, hand, castingTick, modifiers);
    }

    @Override
    public void onPlayerStoppedUsing(@ParametersAreNonnullByDefault ItemStack stack, @ParametersAreNonnullByDefault World world, @ParametersAreNonnullByDefault EntityLivingBase user, int timeLeft){
        wizardryCore.onPlayerStoppedUsing(stack, world, user, timeLeft);
    }

    @Override
    public boolean itemInteractionForEntity(@ParametersAreNonnullByDefault ItemStack stack, @ParametersAreNonnullByDefault EntityPlayer player, @ParametersAreNonnullByDefault EntityLivingBase entity, @ParametersAreNonnullByDefault EnumHand hand){
        return wizardryCore.itemInteractionForEntity(player, entity);
    }
    // Workbench stuff

    @Override
    public int getSpellSlotCount(ItemStack stack){
        return wizardryCore.getSpellSlotCount(stack);
    }

    @Override
    public ItemStack applyUpgrade(@Nullable EntityPlayer player, ItemStack wand, ItemStack upgrade){
        return wizardryCore.applyUpgrade(player, wand, upgrade);
    }

    @Override
    public boolean onApplyButtonPressed(EntityPlayer player, Slot centre, Slot crystals, Slot upgrade, Slot[] spellBooks){
        return wizardryCore.onApplyButtonPressed(player, centre, crystals, upgrade, spellBooks);
    }

    @Override
    public void onClearButtonPressed(EntityPlayer player, Slot centre, Slot crystals, Slot upgrade, Slot[] spellBooks){
        wizardryCore.onClearButtonPressed(centre);
    }

}
