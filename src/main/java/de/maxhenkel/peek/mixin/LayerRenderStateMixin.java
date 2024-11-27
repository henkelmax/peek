package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.interfaces.NoTransformAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin {

    @Shadow
    @Nullable
    BakedModel model;

    @Unique
    private ItemStackRenderState self;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(ItemStackRenderState state, CallbackInfo ci) {
        self = state;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.AFTER))
    private void apply(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        if (model == null) {
            return;
        }
        if (((NoTransformAccessor) self).peek$shouldTransform()) {
            return;
        }
        poseStack.popPose();
        poseStack.pushPose();
        model.getTransforms().getTransform(ItemDisplayContext.FIXED).apply(self.isLeftHand, poseStack);
    }

}
