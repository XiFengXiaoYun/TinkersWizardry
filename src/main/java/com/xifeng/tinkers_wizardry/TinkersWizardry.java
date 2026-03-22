package com.xifeng.tinkers_wizardry;

import com.xifeng.tinkers_wizardry.common.CommonProxy;
import com.xifeng.tinkers_wizardry.conarm.ConarmModifiers;
import com.xifeng.tinkers_wizardry.materials.MagicMaterials;
import com.xifeng.tinkers_wizardry.modifiers.ModifierRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.VERSION,
        dependencies = "required-after:tconstruct;required-after:conarm;required-after:ebwizardry;required-after:wizardryutils"
)
public class TinkersWizardry {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @SidedProxy(serverSide = "com.xifeng.tinkers_wizardry.common.CommonProxy", clientSide = "com.xifeng.tinkers_wizardry.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
        MagicMaterials.initMagicMaterials();
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initToolGuis();
        ModifierRegister.initModifiers();
        ConarmModifiers.initModifiers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.initToolGuis();
        proxy.postInit();
    }

}
