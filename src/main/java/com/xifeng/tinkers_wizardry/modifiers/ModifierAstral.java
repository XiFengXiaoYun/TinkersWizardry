package com.xifeng.tinkers_wizardry.modifiers;

import com.xifeng.tinkers_wizardry.Tags;
import com.xifeng.tinkers_wizardry.aspect.SpecialCategory;
import electroblob.wizardry.event.SpellCastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

//@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class ModifierAstral extends ModifierTrait {

    public ModifierAstral() {
        super("astral", 0x1122334);
        this.addAspects(SpecialCategory.aspect);
    }

    @SubscribeEvent
    public static void onCast(SpellCastEvent.Tick event) {
        //System.out.println(event.getSource().name());
    }
}
