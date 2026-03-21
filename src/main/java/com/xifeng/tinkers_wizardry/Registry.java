package com.xifeng.tinkers_wizardry;

import com.google.common.collect.ImmutableSet;
import com.xifeng.tinkers_wizardry.weapon.WeaponHandler;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class Registry {
    private static final Set<ToolCore> tools = new HashSet<>();
    //register tools here
    @SubscribeEvent
    public static void registerTools(RegistryEvent.Register<Item> event) {
        WeaponHandler.initWeapon(event, tools);
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

    public static Set<ToolCore> getTools() {
        return ImmutableSet.copyOf(tools);
    }
}
