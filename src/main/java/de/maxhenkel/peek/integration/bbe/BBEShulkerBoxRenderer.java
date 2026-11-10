package de.maxhenkel.peek.integration.bbe;

import betterblockentities.render.AltRenderer;
import betterblockentities.render.AltRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;

public class BBEShulkerBoxRenderer implements AltRenderer<ShulkerBoxBlockEntity, ShulkerBoxRenderState> {

    public BBEShulkerBoxRenderer(AltRendererProvider.Context context) {

    }

    @Override
    public boolean shouldRender(ShulkerBoxBlockEntity blockEntity, Vec3 cameraPosition) {
        return AltRenderer.super.shouldRender(blockEntity, cameraPosition) && ShulkerRenderEvents.shouldRenderShulkerBox(blockEntity);
    }

    @Override
    public ShulkerBoxRenderState createRenderState() {
        return new ShulkerBoxRenderState();
    }

    @Override
    public void extractRenderState(ShulkerBoxBlockEntity blockEntity, ShulkerBoxRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        AltRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.direction = blockEntity.getBlockState().getValueOrElse(ShulkerBoxBlock.FACING, Direction.UP);
        state.color = blockEntity.getColor();
        state.progress = blockEntity.getProgress(partialTicks);
        ShulkerRenderEvents.onExtractShulkerBoxRenderState(blockEntity, state);
    }

    @Override
    public void submit(ShulkerBoxRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        ShulkerRenderEvents.onSubmitShulkerBoxLabel(state, poseStack, submitNodeCollector, camera);
    }
}
