package com.xifeng.tinkers_wizardry.mixins;

import c4.conarm.lib.armor.ArmorCore;
import com.xifeng.tinkers_wizardry.conarm.upgrade.ElementNBT;
import com.xifeng.tinkers_wizardry.conarm.upgrade.UpgradeType;
import com.xifeng.tinkers_wizardry.utils.SpellBladeHelper;
import com.xifeng.tinkers_wizardry.weapon.SpellBlade;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.tileentity.TileEntityImbuementAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.Arrays;

@Mixin(value = TileEntityImbuementAltar.class, remap = false)
public abstract class MixinTileEntityImbuementAltar {
    @Inject(method = "getImbuementResult", at = @At("TAIL"), cancellable = true)
    private static void getResult(ItemStack input, Element[] receptacleElements, boolean fullLootGen, World world, EntityPlayer lastUser, CallbackInfoReturnable<ItemStack> cir) {
        if(input.getItem() instanceof SpellBlade) {
            if(!(SpellBladeHelper.getElement(input).equals(Element.MAGIC.name()))) return;
            if(Arrays.stream(receptacleElements).distinct().count() == 1L && receptacleElements[0] != null) {
                ItemStack output = input.copy();
                Element element = receptacleElements[0];
                SpellBladeHelper.setElement(output, element.name());
                cir.setReturnValue(output);
            }
        } else if (input.getItem() instanceof ArmorCore) {
            ElementNBT nbt = null;
            UpgradeType type = null;
            for(UpgradeType types : UpgradeType.values()) {
                ElementNBT nbt2 = ElementNBT.get(input, types);
                if(nbt2 != null) {
                    nbt = nbt2;
                    type =types;
                    break;
                }
            }
            if(nbt == null) return;
            if(nbt.hasElement()) return;
            if(Arrays.stream(receptacleElements).distinct().count() != 1L || receptacleElements[0] == null) return;
            ItemStack out = input.copy();
            Element element = receptacleElements[0];
            nbt.element = element.name();
            NBTTagList tagList = TagUtil.getModifiersTagList(out);
            int index = TinkerUtil.getIndexInCompoundList(tagList, type.getTrueIdentifier());
            NBTTagCompound modTag = tagList.getCompoundTagAt(index);
            nbt.write(modTag);
            TagUtil.setModifiersTagList(out, tagList);
            cir.setReturnValue(out);
        }
    }
}
