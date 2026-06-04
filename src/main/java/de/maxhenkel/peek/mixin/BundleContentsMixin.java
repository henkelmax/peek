package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.class)
public abstract class BundleContentsMixin {

    @Shadow
    public abstract int size();

    @Inject(method = "getNumberOfItemsToShow", at = @At("HEAD"), cancellable = true)
    private void overrideGetNumberOfItemsToShow(CallbackInfoReturnable<Integer> cir) {
        int maxItems = Peek.CONFIG.getBundleItemCount();
        int columns = Peek.CONFIG.bundleColumns.get();
        int size = size();
        int availableItemsToShow = size > maxItems ? maxItems - 1 : maxItems;
        int itemsOnNonFullRow = size % columns;
        int emptySpaceOnNonFullRow = itemsOnNonFullRow == 0 ? 0 : columns - itemsOnNonFullRow;
        int itemsToShow = availableItemsToShow - emptySpaceOnNonFullRow;

        if (itemsToShow <= 0) {
            itemsToShow = availableItemsToShow;
        }

        cir.setReturnValue(Math.min(size, itemsToShow));
    }

}
