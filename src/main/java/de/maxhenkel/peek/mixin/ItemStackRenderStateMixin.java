package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.interfaces.NoTransformAccessor;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin implements NoTransformAccessor {

    @Unique
    private boolean transform;

    @Override
    public boolean peek$shouldTransform() {
        return transform;
    }

    public void peek$setShouldTransform(boolean transform) {
        this.transform = transform;
    }

}
