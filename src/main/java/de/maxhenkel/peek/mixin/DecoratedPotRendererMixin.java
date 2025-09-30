package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import de.maxhenkel.peek.interfaces.PeekDecoratedPot;
import de.maxhenkel.peek.interfaces.PeekDecoratedPotRenderState;
import de.maxhenkel.peek.utils.ItemRenderUtils;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotRenderer.class)
public class DecoratedPotRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("HEAD"))
    private void extractRenderState(DecoratedPotBlockEntity decoratedPotBlockEntity, DecoratedPotRenderState decoratedPotRenderState, float f, Vec3 vec3, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, CallbackInfo ci) {
        if (!(decoratedPotRenderState instanceof PeekDecoratedPotRenderState peekDecoratedPotRenderState)) {
            throw new IllegalStateException("DecoratedPotRenderState is not a PeekDecoratedPotRenderState");
        }
        if (!Peek.CONFIG.showDecoratedPotHint.get()) {
            peekDecoratedPotRenderState.peek$setLabel(null);
            peekDecoratedPotRenderState.peek$setDisplayItem(null);
            return;
        }
        ItemStack containedItem = ((PeekDecoratedPot) decoratedPotBlockEntity).peek$getContainedItem();
        if (containedItem == null || containedItem.isEmpty()) {
            return;
        }

        MutableComponent text = Component.translatable("label.peek.decorated_pot_count", containedItem.getCount());
        peekDecoratedPotRenderState.peek$setLabel(text.getVisualOrderText());

        ItemStack displayItem = DecoratedPotRenderEvents.createPotRenderStack(containedItem);
        ItemStackRenderState itemStackRenderState = ItemRenderUtils.fromStack(decoratedPotBlockEntity.getLevel(), decoratedPotBlockEntity.getBlockPos(), displayItem);
        peekDecoratedPotRenderState.peek$setDisplayItem(itemStackRenderState);
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/DecoratedPotRenderer;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IILnet/minecraft/world/level/block/entity/PotDecorations;I)V"))
    private void submit(DecoratedPotRenderState decoratedPotRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, CallbackInfo ci) {
        DecoratedPotRenderEvents.submitDecoratedPotLabel(decoratedPotRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

}
