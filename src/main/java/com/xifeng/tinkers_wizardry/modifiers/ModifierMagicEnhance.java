package com.xifeng.tinkers_wizardry.modifiers;

import com.xifeng.tinkers_wizardry.aspect.SpecialCategory;
import com.xifeng.tinkers_wizardry.materials.MagicNBT;
import com.xifeng.tinkers_wizardry.utils.TWTagUtil;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ModifierMagicEnhance extends ModifierTrait {
    public ModifierMagicEnhance() {
        super("magic_enhance", 0xe4ea60, 3, 20);
        this.addAspects(SpecialCategory.aspect);
    }


    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ModifierNBT.IntegerNBT nbt = ModifierNBT.readInteger(modifierTag);
        int current = nbt.current;
        MagicNBT data = TWTagUtil.getMagicStats(rootCompound);
        MagicNBT oldData = TWTagUtil.getOriginalMagicStats(rootCompound);
        data.spellPotency += (float) (oldData.spellPotency * current * 0.01);
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
