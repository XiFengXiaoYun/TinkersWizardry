package com.xifeng.tinkers_wizardry;

import com.xifeng.tinkers_wizardry.weapon.WeaponHandler;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class Registry {
    //register tools here
    @SubscribeEvent
    public static void registerTools(RegistryEvent.Register<Item> event) {
        WeaponHandler.initWeapon(event);
    }

    public static void initForgeTool(ToolCore core, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(core);
        TinkerRegistry.registerToolForgeCrafting(core);
        TinkersWizardry.proxy.registerToolModel(core);
    }

    public static void initTool(ToolCore core, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(core);
        TinkerRegistry.registerToolCrafting(core);
        TinkersWizardry.proxy.registerToolModel(core);
    }
}
