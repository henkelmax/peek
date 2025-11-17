package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekShulkerBoxRenderState;
import de.maxhenkel.peek.utils.ItemRenderUtils;
import de.maxhenkel.peek.utils.ShulkerHintData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ShulkerRenderEvents {

    public static final ResourceLocation SHULKER_ITEM_CONDITION = ResourceLocation.fromNamespaceAndPath(Peek.MODID, "shulker_item");

    public static void submitShulkerBoxLabel(ShulkerBoxRenderState shulkerBoxRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!(shulkerBoxRenderState instanceof PeekShulkerBoxRenderState peekState)) {
            throw new IllegalStateException("ShulkerBoxRenderState is not a PeekShulkerBoxRenderState");
        }
        submitShulkerBoxLabel(shulkerBoxRenderState.direction, shulkerBoxRenderState.progress, peekState.peek$getDisplayItem(), peekState.peek$getLabel(), shulkerBoxRenderState.lightCoords, poseStack, submitNodeCollector);
    }

    public static void submitShulkerBoxItemLabel(ShulkerHintData shulkerHintData, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light) {
        ItemStackRenderState itemStackRenderState = ItemRenderUtils.fromStack(Minecraft.getInstance().level, null, createShulkerRenderStack(shulkerHintData.getDisplayItem()));
        submitShulkerBoxLabel(Direction.UP, 0F, itemStackRenderState, shulkerHintData.getLabelFormatted(), light, poseStack, submitNodeCollector);
    }

    private static void submitShulkerBoxLabel(Direction direction, float progress, @Nullable ItemStackRenderState displayItem, @Nullable FormattedCharSequence label, int lightCoords, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        Minecraft mc = Minecraft.getInstance();

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(direction.getRotation());
        poseStack.translate(0D, 0.5D, 0D);
        poseStack.translate(0F, progress * 0.5F, 0F);
        poseStack.rotateAround(Axis.YP.rotationDegrees(-270F * progress), 0F, 1F, 0F);

        poseStack.rotateAround(Axis.XP.rotationDegrees(-90F), 1F, 0F, 0F);

        if (displayItem != null) {
            poseStack.pushPose();
            poseStack.rotateAround(Axis.YP.rotationDegrees(180F), 0F, 1F, 0F);
            if (label != null) {
                poseStack.translate(0F, 2F / 16F, 0F);
                poseStack.scale(12F / 16F, 12F / 16F, 12F / 16F);
            }
            displayItem.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        if (label != null) {
            poseStack.pushPose();
            if (displayItem != null) {
                poseStack.translate(0F, -6F / 16F, 0F);
            }
            int width = mc.font.width(label);
            float textScale = Math.min(0.8F / width, 0.02F);
            poseStack.scale(textScale, textScale, textScale);
            poseStack.translate(0F, mc.font.lineHeight / 2F, 0F);
            poseStack.rotateAround(Axis.XP.rotationDegrees(180F), 1F, 0F, 0F);
            submitNodeCollector.submitText(poseStack, -width / 2F, 0F, label, false, Font.DisplayMode.POLYGON_OFFSET, lightCoords, 0xFFFFFFFF, 0x00000000, 0xFF000000);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    private static final DataComponentType<Boolean> SHULKER_ITEM_TYPE = DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build();

    public static boolean isShulkerRenderStack(ItemStack stack) {
        return stack.getComponents().getOrDefault(SHULKER_ITEM_TYPE, false);
    }

    public static ItemStack createShulkerRenderStack(@Nullable ItemStack source) {
        if (source == null) {
            return null;
        }
        ItemStack stack = source.copy();
        stack.applyComponents(DataComponentPatch.builder().set(SHULKER_ITEM_TYPE, true).build());
        return stack;
    }
}
