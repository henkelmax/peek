package de.maxhenkel.peek.events;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.tooltips.ContainerTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;
import java.util.Optional;

public class TooltipImageEvents {

    private static final String ITEMS = "Items";

    @Nullable
    public static Optional<TooltipComponent> getTooltipImage(ItemStack stack, Block block) {
        if (block instanceof ShulkerBoxBlock) {
            return getShulkerBoxTooltipImage(stack);
        } else if (block instanceof ChestBlock) {
            return getChestTooltipImage(stack);
        } else if (block instanceof BarrelBlock) {
            return getBarrelTooltipImage(stack);
        } else if (block instanceof DispenserBlock) {
            return getDispenserTooltipImage(stack);
        } else if (block instanceof HopperBlock) {
            return getHopperTooltipImage(stack);
        }

        return null;
    }

    private static Optional<TooltipComponent> getShulkerBoxTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekShulkerBoxes.get()) {
            return null;
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return null;
        }

        if (!blockEntityData.contains(ShulkerBoxBlockEntity.ITEMS_TAG, Tag.TAG_LIST)) {
            return null;
        }

        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return null;
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }

    private static Optional<TooltipComponent> getChestTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekChests.get()) {
            return null;
        }
        return getDefaultChestSizeTooltipImage(stack);
    }

    private static Optional<TooltipComponent> getBarrelTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekBarrels.get()) {
            return null;
        }
        return getDefaultChestSizeTooltipImage(stack);
    }

    private static Optional<TooltipComponent> getDispenserTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekDispensers.get()) {
            return null;
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return null;
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return null;
        }

        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return null;
        }

        return Optional.of(new ContainerTooltip(3, 3, items));
    }

    private static Optional<TooltipComponent> getHopperTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekHoppers.get()) {
            return null;
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return null;
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return null;
        }

        NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return null;
        }

        return Optional.of(new ContainerTooltip(5, 1, items));
    }

    private static Optional<TooltipComponent> getDefaultChestSizeTooltipImage(ItemStack stack) {
        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return null;
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return null;
        }

        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return null;
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }

}
