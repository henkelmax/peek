package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.tooltips.ContainerTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

    private static final String ITEMS = "Items";

    @Shadow
    @Final
    private Block block;

    public BlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
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

        return super.getTooltipImage(stack);
    }

    private Optional<TooltipComponent> getShulkerBoxTooltipImage(ItemStack stack) {
        if (!Peek.CLIENT_CONFIG.peekShulkerBoxes.get()) {
            return super.getTooltipImage(stack);
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return super.getTooltipImage(stack);
        }

        if (!blockEntityData.contains(ShulkerBoxBlockEntity.ITEMS_TAG, Tag.TAG_LIST)) {
            return super.getTooltipImage(stack);
        }

        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CLIENT_CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return super.getTooltipImage(stack);
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }

    private Optional<TooltipComponent> getChestTooltipImage(ItemStack stack) {
        if (!Peek.CLIENT_CONFIG.peekChests.get()) {
            return super.getTooltipImage(stack);
        }
        return getDefaultChestSizeTooltipImage(stack);
    }

    private Optional<TooltipComponent> getBarrelTooltipImage(ItemStack stack) {
        if (!Peek.CLIENT_CONFIG.peekBarrels.get()) {
            return super.getTooltipImage(stack);
        }
        return getDefaultChestSizeTooltipImage(stack);
    }

    private Optional<TooltipComponent> getDispenserTooltipImage(ItemStack stack) {
        if (!Peek.CLIENT_CONFIG.peekDispensers.get()) {
            return super.getTooltipImage(stack);
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return super.getTooltipImage(stack);
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return super.getTooltipImage(stack);
        }

        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CLIENT_CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return super.getTooltipImage(stack);
        }

        return Optional.of(new ContainerTooltip(3, 3, items));
    }

    private Optional<TooltipComponent> getHopperTooltipImage(ItemStack stack) {
        if (!Peek.CLIENT_CONFIG.peekHoppers.get()) {
            return super.getTooltipImage(stack);
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return super.getTooltipImage(stack);
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return super.getTooltipImage(stack);
        }

        NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CLIENT_CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return super.getTooltipImage(stack);
        }

        return Optional.of(new ContainerTooltip(5, 1, items));
    }

    private Optional<TooltipComponent> getDefaultChestSizeTooltipImage(ItemStack stack) {
        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return super.getTooltipImage(stack);
        }
        if (!blockEntityData.contains(ITEMS, Tag.TAG_LIST)) {
            return super.getTooltipImage(stack);
        }

        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(blockEntityData, items);

        if (!Peek.CLIENT_CONFIG.showEmptyContainers.get() && items.stream().allMatch(ItemStack::isEmpty)) {
            return super.getTooltipImage(stack);
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }

}
