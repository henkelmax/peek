package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotRenderer.class)
public class DecoratedPotRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void render(DecoratedPotBlockEntity pot, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo ci) {
        if (!Peek.CONFIG.showDecoratedPotHint.get()) {
            return;
        }

        DecoratedPotRenderEvents.renderDecoratedPotLabel(pot, partialTicks, poseStack, multiBufferSource, light, overlay);
    }

}
