package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientBundleTooltip.class)
public class ClientBundleTooltipMixin {

    @ModifyConstant(method = {"slotCount", "renderBundleWithItemsTooltip"}, constant = @Constant(intValue = 12))
    private int modifyMaxBundleSlots(int original) {
        return Peek.CONFIG.getBundleItemCount();
    }

    @ModifyConstant(method = {"getWidth", "getContentXOffset", "renderBundleWithItemsTooltip", "drawProgressbar", "drawEmptyBundleDescriptionText", "getEmptyBundleDescriptionTextHeight"}, constant = @Constant(intValue = 96))
    private static int scaleGridWidth(int original) {
        return Peek.CONFIG.bundleColumns.get() * 24;
    }

    @ModifyConstant(method = "gridSizeY", constant = @Constant(intValue = 4))
    private int modifyGridColumns(int original) {
        return Peek.CONFIG.bundleColumns.get();
    }

    @ModifyConstant(method = "renderBundleWithItemsTooltip", constant = @Constant(intValue = 4, ordinal = 0))
    private int modifyColumnLoopLimit(int original) {
        return Peek.CONFIG.bundleColumns.get();
    }

    @ModifyConstant(method = "getProgressBarFill", constant = @Constant(intValue = 94))
    private static int modifyProgressBarFillWidth(int original) {
        return Peek.CONFIG.bundleColumns.get() * 24 - 2;
    }

    @ModifyConstant(method = "drawProgressbar", constant = @Constant(intValue = 48))
    private static int modifyProgressBarTextCenter(int original) {
        return (Peek.CONFIG.bundleColumns.get() * 24) / 2;
    }

}
