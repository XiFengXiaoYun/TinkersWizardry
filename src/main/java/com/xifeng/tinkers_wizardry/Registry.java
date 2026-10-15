package com.xifeng.tinkers_wizardry;

import com.xifeng.tinkers_wizardry.modifiers.ModifierRegister;
import com.xifeng.tinkers_wizardry.weapon.WeaponHandler;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tools.TinkerModifiers;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class Registry {
    //register tools here
    @SubscribeEvent
    public static void registerTools(RegistryEvent.Register<Item> event) {
        WeaponHandler.initWeapon(event);
    }

    @SubscribeEvent
    public static void registerModifierTexture(RegistryEvent.Register<Item> event) {
        for(IModifier mod : new IModifier[]{ModifierRegister.magicEnhance, TinkerModifiers.modBaneOfArthopods, TinkerModifiers.modBeheading, TinkerModifiers.modDiamond, TinkerModifiers.modEmerald, TinkerModifiers.modGlowing, TinkerModifiers.modHaste, TinkerModifiers.modFiery, TinkerModifiers.modKnockback, TinkerModifiers.modLuck, TinkerModifiers.modMendingMoss, TinkerModifiers.modNecrotic, TinkerModifiers.modReinforced, TinkerModifiers.modSharpness, TinkerModifiers.modShulking, TinkerModifiers.modSilktouch, TinkerModifiers.modSmite, TinkerModifiers.modSoulbound, TinkerModifiers.modWebbed}) {
            TinkersWizardry.proxy.registerModifierModel(mod, new ResourceLocation(Tags.MOD_ID, "models/item/modifiers/" + mod.getIdentifier()));
        }
    }

    public static void initTool(ToolCore core, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(core);
        TinkerRegistry.registerToolCrafting(core);
        TinkersWizardry.proxy.registerToolModel(core);
    }
}
