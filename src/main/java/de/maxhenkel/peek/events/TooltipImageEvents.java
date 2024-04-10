package de.maxhenkel.peek.events;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.tooltips.ContainerTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.*;

import java.util.Optional;

public class TooltipImageEvents {

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
        } else if (block instanceof EnderChestBlock) {
            return getEnderChestTooltipImage();
        }

        return Optional.empty();
    }

    private static Optional<TooltipComponent> getEnderChestTooltipImage() {
        if (!Peek.CONFIG.peekEnderChests.get()) {
            return Optional.empty();
        }
        if (DataStore.enderChestInventory == null) {
            return Optional.empty();
        }
        return Optional.of(new ContainerTooltip(9, 3, DataStore.enderChestInventory));
    }

    private static Optional<TooltipComponent> getShulkerBoxTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekShulkerBoxes.get()) {
            return Optional.empty();
        }
        return sizedContainerTooltipImage(stack, 9, 3);
    }

    private static Optional<TooltipComponent> getChestTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekChests.get()) {
            return Optional.empty();
        }
        return sizedContainerTooltipImage(stack, 9, 3);
    }

    private static Optional<TooltipComponent> getBarrelTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekBarrels.get()) {
            return Optional.empty();
        }
        return sizedContainerTooltipImage(stack, 9, 3);
    }

    private static Optional<TooltipComponent> getDispenserTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekDispensers.get()) {
            return Optional.empty();
        }
        return sizedContainerTooltipImage(stack, 3, 3);
    }

    private static Optional<TooltipComponent> getHopperTooltipImage(ItemStack stack) {
        if (!Peek.CONFIG.peekHoppers.get()) {
            return Optional.empty();
        }
        return sizedContainerTooltipImage(stack, 5, 1);
    }

    private static Optional<TooltipComponent> sizedContainerTooltipImage(ItemStack stack, int width, int height) {
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        if (!Peek.CONFIG.showEmptyContainers.get() && contents.stream().allMatch(ItemStack::isEmpty)) {
            return Optional.empty();
        }

        NonNullList<ItemStack> items = NonNullList.withSize(width * height, ItemStack.EMPTY);
        contents.copyInto(items);

        return Optional.of(new ContainerTooltip(width, height, items));
    }

}
