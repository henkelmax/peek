package de.maxhenkel.peek.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.maxhenkel.peek.Peek;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientBundleTooltip.class)
public class ClientBundleTooltipMixin {

    @ModifyExpressionValue(method = {"slotCount", "renderBundleWithItemsTooltip"}, at = @At(value = "CONSTANT", args = "intValue=12"))
    private int modifyMaxBundleSlots(int original) {
        return Peek.CONFIG.getBundleItemCount();
    }

    @ModifyExpressionValue(method = {"getWidth", "getContentXOffset", "renderBundleWithItemsTooltip", "drawProgressbar", "drawEmptyBundleDescriptionText", "getEmptyBundleDescriptionTextHeight"}, at = @At(value = "CONSTANT", args = "intValue=96"))
    private static int scaleGridWidth(int original) {
        return Peek.CONFIG.bundleColumns.get() * 24;
    }

    @ModifyExpressionValue(method = "gridSizeY", at = @At(value = "CONSTANT", args = "intValue=4"))
    private int modifyGridColumns(int original) {
        return Peek.CONFIG.bundleColumns.get();
    }

    @ModifyExpressionValue(method = "renderBundleWithItemsTooltip", at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 0))
    private int modifyColumnLoopLimit(int original) {
        return Peek.CONFIG.bundleColumns.get();
    }

    @ModifyExpressionValue(method = "getProgressBarFill", at = @At(value = "CONSTANT", args = "intValue=94"))
    private static int modifyProgressBarFillWidth(int original) {
        return Peek.CONFIG.bundleColumns.get() * 24 - 2;
    }

    @ModifyExpressionValue(method = "drawProgressbar", at = @At(value = "CONSTANT", args = "intValue=48"))
    private static int modifyProgressBarTextCenter(int original) {
        return (Peek.CONFIG.bundleColumns.get() * 24) / 2;
    }

}
