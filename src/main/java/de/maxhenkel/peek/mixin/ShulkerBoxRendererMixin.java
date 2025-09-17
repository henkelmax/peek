package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.interfaces.PeekShulkerBoxRenderState;
import de.maxhenkel.peek.utils.ItemRenderUtils;
import de.maxhenkel.peek.utils.ShulkerHintData;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxRenderer.class)
public abstract class ShulkerBoxRendererMixin implements BlockEntityRenderer<ShulkerBoxBlockEntity, ShulkerBoxRenderState> {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/ShulkerBoxBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("HEAD"))
    private void extractRenderState(ShulkerBoxBlockEntity shulkerBoxBlockEntity, ShulkerBoxRenderState shulkerBoxRenderState, float f, Vec3 vec3, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, CallbackInfo ci) {
        if (!(shulkerBoxRenderState instanceof PeekShulkerBoxRenderState peekShulkerBoxRenderState)) {
            throw new IllegalStateException("ShulkerBoxRenderState is not a PeekShulkerBoxRenderState");
        }
        if (!Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            peekShulkerBoxRenderState.peek$setLabel(null);
            peekShulkerBoxRenderState.peek$setDisplayItem(null);
            return;
        }
        ShulkerHintData shulkerHintData = ShulkerHintData.fromShulkerBox(shulkerBoxBlockEntity.getItems(), shulkerBoxBlockEntity.getCustomName());
        peekShulkerBoxRenderState.peek$setLabel(shulkerHintData.getLabelFormatted());

        ItemStack displayItem = ShulkerRenderEvents.createShulkerRenderStack(shulkerHintData.getDisplayItem());
        ItemStackRenderState itemStackRenderState = ItemRenderUtils.fromStack(shulkerBoxBlockEntity.getLevel(), shulkerBoxBlockEntity.getBlockPos(), displayItem);
        peekShulkerBoxRenderState.peek$setDisplayItem(itemStackRenderState);
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At("RETURN"))
    private void submit(ShulkerBoxRenderState shulkerBoxRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, CallbackInfo ci) {
        ShulkerRenderEvents.submitShulkerBoxLabel(shulkerBoxRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
