package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.interfaces.PeekDecoratedPotRenderState;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(DecoratedPotRenderState.class)
public class DecoratedPotRenderStateMixin implements PeekDecoratedPotRenderState {
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
