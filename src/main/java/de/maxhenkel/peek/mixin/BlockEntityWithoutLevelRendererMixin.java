package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {

    @Inject(method = "renderByItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;renderItem(Lnet/minecraft/world/level/block/entity/BlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)Z", shift = At.Shift.AFTER))
    public void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {
        if (!ShulkerBoxUtils.isShulkerBox(itemStack)) {
            return;
        }
        if (!Peek.CONFIG.showShulkerBoxItemHint.get()) {
            return;
        }

        ShulkerRenderEvents.renderShulkerBoxItemLabel(itemStack, transformType, poseStack, multiBufferSource, light, overlay);
    }

}
