package com.xifeng.tinkers_wizardry.modifiers;

import com.xifeng.tinkers_wizardry.aspect.SpecialCategory;
import com.xifeng.tinkers_wizardry.config.ModConfig;
import com.xifeng.tinkers_wizardry.materials.MagicNBT;
import com.xifeng.tinkers_wizardry.utils.TWTagUtil;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModifierMagic extends ToolModifier {
    public static ModifierMagic INSTANCE = new ModifierMagic();

    public ModifierMagic() {
        this("magic_data", 0xcb3ff7);
    }

    public ModifierMagic(String identifier, int color) {
        super(identifier, color);
        this.addAspects(SpecialCategory.aspect, new ModifierAspect.LevelAspect(this, 4), new ModifierAspect.DataAspect(this));
    }

    @Override
    public void applyEffect(NBTTagCompound root, NBTTagCompound modifierTag) {
        int level = ModifierNBT.readInteger(modifierTag).level - 1;
        if(level > 0){
            MagicNBT nbt = TWTagUtil.getMagicStats(root);
            MagicNBT original = TWTagUtil.getOriginalMagicStats(root);
            int maxMana = original.maxMana;
            float potency = original.spellPotency;
            nbt.maxMana += (int) (maxMana * (ModConfig.manaCapacityIncrease * level));
            nbt.spellPotency += (float) (potency * ModConfig.spellPotencyIncrease * level);
            TagUtil.setToolTag(root, nbt.get());
        }
    }
}
