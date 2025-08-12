package de.maxhenkel.peek.data;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;

public class DataStore {
    @Nullable
    public static ShulkerBoxBlockEntity lastOpenedShulkerBox;
    public static int lastOpenedShulkerBoxContainerId = -1;
    @Nullable
    public static NonNullList<ItemStack> enderChestInventory;
}
