package com.xifeng.tinkers_wizardry.mixins;

import com.xifeng.tinkers_wizardry.utils.SpellBladeHelper;
import com.xifeng.tinkers_wizardry.weapon.SpellBlade;
import electroblob.wizardry.constants.Element;
import electroblob.wizardry.tileentity.TileEntityImbuementAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = TileEntityImbuementAltar.class, remap = false)
public abstract class MixinTileEntityImbuementAltar {
    @Inject(method = "getImbuementResult", at = @At("TAIL"), cancellable = true)
    private static void getResult(ItemStack input, Element[] receptacleElements, boolean fullLootGen, World world, EntityPlayer lastUser, CallbackInfoReturnable<ItemStack> cir) {
        if(input.getItem() instanceof SpellBlade) {
            if(!(SpellBladeHelper.getElement(input).equals(Element.MAGIC.name()))) return;
            ItemStack output = input.copy();
            if(Arrays.stream(receptacleElements).distinct().count() == 1L && receptacleElements[0] != null) {
                Element element = receptacleElements[0];
                SpellBladeHelper.setElement(output, element.name());
                cir.setReturnValue(output);
            }
        }
    }
}
