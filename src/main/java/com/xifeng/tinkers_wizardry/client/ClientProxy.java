package com.xifeng.tinkers_wizardry.client;

import com.xifeng.tinkers_wizardry.client.book.BookTransformerModifiers;
import com.xifeng.tinkers_wizardry.client.book.BookTransformerWeapons;
import com.xifeng.tinkers_wizardry.common.CommonProxy;
import com.xifeng.tinkers_wizardry.weapon.WeaponHandler;
import net.minecraft.item.Item;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;

public class ClientProxy extends CommonProxy {
    @Override
    public void initToolGuis() {
        if (WeaponHandler.spellBlade != null) {
            ToolBuildGuiInfo info = new ToolBuildGuiInfo(WeaponHandler.spellBlade);
            info.addSlotPosition(12, 62);
            info.addSlotPosition(48, 26);
            info.addSlotPosition(30, 44);
            TinkerRegistryClient.addToolBuilding(info);
        }
    }

    @Override
    public void registerToolModel(ToolCore toolCore) {
        ModelRegisterUtil.registerToolModel(toolCore);
    }

    @Override
    public void postInit() {
        TinkerBook.INSTANCE.addTransformer(new BookTransformerWeapons(new FileRepository("tconstruct:book")));
        TinkerBook.INSTANCE.addTransformer(new BookTransformerModifiers(new FileRepository("tconstruct:book")));
    }

    @Override
    public <T extends Item & IToolPart> void registerToolPartModel(T part) {
        ModelRegisterUtil.registerPartModel(part);
    }
}
