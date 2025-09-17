package de.maxhenkel.peek.interfaces;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.FormattedCharSequence;

import javax.annotation.Nullable;

public interface PeekShulkerBoxRenderState {

    @Nullable
    FormattedCharSequence peek$getLabel();

    void peek$setLabel(@Nullable FormattedCharSequence label);

    @Nullable
    ItemStackRenderState peek$getDisplayItem();

    void peek$setDisplayItem(@Nullable ItemStackRenderState displayItem);

}
