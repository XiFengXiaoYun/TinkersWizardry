package com.xifeng.tinkers_wizardry.modifiers;

import com.xifeng.tinkers_wizardry.aspect.SpecialCategory;
import com.xifeng.tinkers_wizardry.materials.MagicNBT;
import com.xifeng.tinkers_wizardry.utils.TWTagUtil;
import electroblob.wizardry.constants.Constants;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModifierMagicUpgrade extends ToolModifier {
    public static final ModifierMagicUpgrade INSTANCE = new ModifierMagicUpgrade();

    public ModifierMagicUpgrade() {
        this("magic_upgrade", 0xcb3ff7);
    }
    public ModifierMagicUpgrade(String identifier, int color) {
        super(identifier, color);
        this.addAspects(SpecialCategory.aspect, new ModifierAspect.LevelAspect(this, 3), new ModifierAspect.DataAspect(this));
    }

    @Override
    public void applyEffect(NBTTagCompound root, NBTTagCompound mod) {
        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(mod);
        int level = data.level;
        MagicNBT nbt = TWTagUtil.getOriginalMagicStats(root);
        int maxMana = nbt.maxMana;
        maxMana += (int) (level * Constants.STORAGE_INCREASE_PER_LEVEL * maxMana);
        NBTTagCompound tag = TagUtil.getToolTag(root);
        maxMana -= nbt.maxMana;
        maxMana += tag.getInteger("maxMana");
        tag.setInteger("maxMana", maxMana);
    }
}
