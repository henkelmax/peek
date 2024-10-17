package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(method = "renderItemModelRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.AFTER))
    private void apply(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, boolean bl2, float f, CallbackInfo ci) {
        //TODO Create a common method to detect custom rendered items
        if (!ShulkerRenderEvents.isShulkerRenderStack(itemStack) && !DecoratedPotRenderEvents.isPotRenderStack(itemStack)) {
            return;
        }
        poseStack.popPose();
        poseStack.pushPose();
        bakedModel.getTransforms().getTransform(ItemDisplayContext.FIXED).apply(bl, poseStack);
    }

}
