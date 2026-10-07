package com.xifeng.tinkers_wizardry.compat.jei;

import com.xifeng.tinkers_wizardry.weapon.WeaponHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEIDesc implements IModPlugin {

    public JEIDesc() {

    }

    public void register(@Nonnull IModRegistry registry) {
        if(WeaponHandler.spellBlade == null) return;
        ItemStack stack = WeaponHandler.spellBlade.getDemo();
        registry.addIngredientInfo(stack, VanillaTypes.ITEM, "item.spellblade.jei_desc");
    }
}
