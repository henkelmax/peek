package de.maxhenkel.peek.tooltips;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ContainerTooltip implements TooltipComponent {

    private final int width;
    private final int height;
    private final NonNullList<ItemStack> items;

    public ContainerTooltip(int width, int height, ItemContainerContents contents, int size) {
        this.width = width;
        this.height = height;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
        contents.copyInto(items);
    }

    public ContainerTooltip(int width, int height, NonNullList<ItemStack> items) {
        this.width = width;
        this.height = height;
        this.items = items;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
}
