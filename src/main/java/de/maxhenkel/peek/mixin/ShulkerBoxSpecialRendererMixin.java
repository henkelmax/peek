package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxSpecialRenderer.class)
public abstract class ShulkerBoxSpecialRendererMixin implements NoDataSpecialModelRenderer {

    @Unique
    @Nullable
    private ItemStack itemStack;

    @Nullable
    @Override
    public Void extractArgument(ItemStack itemStack) {
        this.itemStack = itemStack;
        return NoDataSpecialModelRenderer.super.extractArgument(itemStack);
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    public void render(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, boolean bl, CallbackInfo ci) {
        if (itemStack == null) {
            return;
        }
        if (!ShulkerBoxUtils.isShulkerBox(itemStack)) {
            return;
        }
        if (!Peek.CONFIG.showShulkerBoxItemHint.get()) {
            return;
        }
        ShulkerRenderEvents.renderShulkerBoxItemLabel(itemStack, itemDisplayContext, poseStack, multiBufferSource, light, overlay);
    }

}
