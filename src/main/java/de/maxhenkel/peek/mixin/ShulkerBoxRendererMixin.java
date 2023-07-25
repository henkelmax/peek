package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.RenderEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxRenderer.class)
public class ShulkerBoxRendererMixin {

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void render(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {
        if (!Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            return;
        }

        RenderEvents.renderShulkerBoxLabel(shulkerBoxBlockEntity, partialTicks, poseStack, multiBufferSource, light, overlay);
    }

}
