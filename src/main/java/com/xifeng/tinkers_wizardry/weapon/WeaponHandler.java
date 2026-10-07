package com.xifeng.tinkers_wizardry.weapon;

import com.xifeng.tinkers_wizardry.Registry;
import com.xifeng.tinkers_wizardry.Tags;
import com.xifeng.tinkers_wizardry.TinkersWizardry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;

public class WeaponHandler {
    public static SpellBlade spellBlade;

    public static ToolPart magicFocus;

    public static void initWeapon(RegistryEvent.Register<Item> event) {
        magicFocus = new ToolPart(288);
        magicFocus.setTranslationKey("magic_focus").setRegistryName(Tags.MOD_ID, "magic_focus");
        event.getRegistry().register(magicFocus);

        TinkerRegistry.registerToolPart(magicFocus);
        TinkersWizardry.proxy.registerToolPartModel(magicFocus);
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), magicFocus));

        spellBlade = new SpellBlade();
        Registry.initTool(spellBlade, event);
    }
}
