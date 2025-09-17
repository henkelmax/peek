package de.maxhenkel.peek.utils;

import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ItemRenderUtils {

    @Nullable
    public static ItemStackRenderState fromStack(Level level, @Nullable BlockPos blockPos, @Nullable ItemStack stack) {
        ItemStackRenderState itemStackRenderState = null;
        if (stack != null) {
            itemStackRenderState = new ItemStackRenderState();
            Minecraft.getInstance().getItemModelResolver().updateForTopItem(itemStackRenderState, stack, ItemDisplayContext.FIXED, level, null, blockPos == null ? 0 : HashCommon.long2int(blockPos.asLong()));
        }
        return itemStackRenderState;
    }

}
