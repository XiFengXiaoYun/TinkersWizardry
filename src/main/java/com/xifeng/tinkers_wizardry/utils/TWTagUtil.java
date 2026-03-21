package com.xifeng.tinkers_wizardry.utils;

import com.xifeng.tinkers_wizardry.materials.MagicNBT;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.TagUtil;

public class TWTagUtil {
    public static MagicNBT getMagicStats(NBTTagCompound root) {
        return new MagicNBT(TagUtil.getToolTag(root));
    }

    public static MagicNBT getOriginalMagicStats(NBTTagCompound root) {
        return new MagicNBT(TagUtil.getTagSafe(root, "StatsOriginal"));
    }
}
