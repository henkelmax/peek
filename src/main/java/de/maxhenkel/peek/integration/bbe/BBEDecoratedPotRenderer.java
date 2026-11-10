package de.maxhenkel.peek.integration.bbe;

import betterblockentities.render.AltRenderer;
import betterblockentities.render.AltRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.phys.Vec3;

public class BBEDecoratedPotRenderer implements AltRenderer<DecoratedPotBlockEntity, DecoratedPotRenderState> {

    public BBEDecoratedPotRenderer(AltRendererProvider.Context context) {

    }

    @Override
    public boolean shouldRender(DecoratedPotBlockEntity blockEntity, Vec3 cameraPosition) {
        return Peek.CONFIG.showDecoratedPotHint.get() && AltRenderer.super.shouldRender(blockEntity, cameraPosition);
    }

    @Override
    public DecoratedPotRenderState createRenderState() {
        return new DecoratedPotRenderState();
    }

    @Override
    public void extractRenderState(DecoratedPotBlockEntity blockEntity, DecoratedPotRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        AltRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.decorations = blockEntity.getDecorations();
        state.direction = blockEntity.getDirection();
        DecoratedPotBlockEntity.WobbleStyle wobbleStyle = blockEntity.lastWobbleStyle;
        if (wobbleStyle != null && blockEntity.getLevel() != null) {
            state.wobbleProgress = ((float) (blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + partialTicks) / (float) wobbleStyle.duration;
        } else {
            state.wobbleProgress = 0F;
        }
        DecoratedPotRenderEvents.onExtractDecoratedPotRenderState(blockEntity, state);
    }

    @Override
    public void submit(DecoratedPotRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        DecoratedPotRenderEvents.onSubmitDecoratedPotLabel(state, poseStack, submitNodeCollector, camera);
    }
}
