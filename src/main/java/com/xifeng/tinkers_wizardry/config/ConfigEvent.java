package com.xifeng.tinkers_wizardry.config;

import com.xifeng.tinkers_wizardry.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class ConfigEvent {
    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
