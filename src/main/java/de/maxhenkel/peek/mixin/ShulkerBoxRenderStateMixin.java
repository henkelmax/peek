package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.interfaces.PeekShulkerBoxRenderState;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxRenderState.class)
public class ShulkerBoxRenderStateMixin implements PeekShulkerBoxRenderState {

    @Unique
    @Nullable
    private FormattedCharSequence label;
    @Unique
    @Nullable
    private ItemStackRenderState displayItem;

    @Nullable
    @Override
    public FormattedCharSequence peek$getLabel() {
        return label;
    }

    @Override
    public void peek$setLabel(@Nullable FormattedCharSequence label) {
        this.label = label;
    }

    @Nullable
    @Override
    public ItemStackRenderState peek$getDisplayItem() {
        return displayItem;
    }

    @Override
    public void peek$setDisplayItem(@Nullable ItemStackRenderState displayItem) {
        this.displayItem = displayItem;
    }
}
