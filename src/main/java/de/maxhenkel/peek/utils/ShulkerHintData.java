package de.maxhenkel.peek.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class ShulkerHintData {

    @Nullable
    private Component label;
    @Nullable
    private Item displayItem;

    public ShulkerHintData(@Nullable Component label, @Nullable Item displayItem) {
        this.label = label;
        this.displayItem = displayItem;
    }

    public ShulkerHintData() {

    }

    @Nullable
    public Component getLabel() {
        return label;
    }

    @Nullable
    public Item getDisplayItem() {
        return displayItem;
    }

    public void setLabel(@Nullable Component label) {
        this.label = label;
    }

    public void setDisplayItem(@Nullable Item displayItem) {
        this.displayItem = displayItem;
    }
}
