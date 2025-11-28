package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekDecoratedPotRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class DecoratedPotRenderEvents {

    public static final Identifier DECORATED_POT_ITEM_CONDITION = Identifier.fromNamespaceAndPath(Peek.MODID, "decorated_pot_item");

    public static void submitDecoratedPotLabel(DecoratedPotRenderState decoratedPotRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!(decoratedPotRenderState instanceof PeekDecoratedPotRenderState peekDecoratedPotRenderState)) {
            throw new IllegalStateException("DecoratedPotRenderState is not a PeekDecoratedPotRenderState");
        }
        ItemStackRenderState itemStackRenderState = peekDecoratedPotRenderState.peek$getDisplayItem();
        FormattedCharSequence label = peekDecoratedPotRenderState.peek$getLabel();

        if (itemStackRenderState == null || label == null) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.rotateAround(Axis.YP.rotationDegrees(180F), 0F, 1F, 0F);

        poseStack.translate(0D, 0D, -7.001D / 16D);

        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(0D, 4D / 16D, 0D);
        itemStackRenderState.submit(poseStack, submitNodeCollector, decoratedPotRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();

        poseStack.translate(0D, -5D / 16D, 0D);
        int width = mc.font.width(label);
        float textScale = 0.02F;
        poseStack.scale(textScale, textScale, textScale);
        poseStack.translate(0F, mc.font.lineHeight / 2F, 0F);
        poseStack.rotateAround(Axis.ZP.rotationDegrees(180F), 0F, 0F, 1F);
        submitNodeCollector.submitText(poseStack, -width / 2F, 0F, label, false, Font.DisplayMode.POLYGON_OFFSET, decoratedPotRenderState.lightCoords, 0xFFFFFFFF, 0x00000000, 0xFF000000);
        poseStack.popPose();
    }

    private static final DataComponentType<Boolean> DECORATED_POT_ITEM_TYPE = DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build();

    public static boolean isPotRenderStack(ItemStack stack) {
        return stack.getComponents().getOrDefault(DECORATED_POT_ITEM_TYPE, false);
    }

    public static ItemStack createPotRenderStack(@Nullable ItemStack source) {
        if (source == null) {
            return null;
        }
        ItemStack stack = source.copy();
        stack.applyComponents(DataComponentPatch.builder().set(DECORATED_POT_ITEM_TYPE, true).build());
        return stack;
    }

}
