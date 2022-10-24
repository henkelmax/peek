package de.maxhenkel.peek.tooltips;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxTooltip implements TooltipComponent {

    private final NonNullList<ItemStack> items;

    public ShulkerBoxTooltip(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
}
