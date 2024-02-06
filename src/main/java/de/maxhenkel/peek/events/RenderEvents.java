package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import de.maxhenkel.peek.utils.ShulkerHintData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;

public class RenderEvents {

    public static final ResourceLocation SHULKER_ITEM_PREDICATE = new ResourceLocation(Peek.MODID, "shulker_item");

    private static final Minecraft mc = Minecraft.getInstance();
    private static final String SHULKER_ITEM_TAG = "ShulkerBoxItem";

    public static void renderShulkerBoxItemLabel(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        ShulkerHintData data = ShulkerHintData.fromShulkerBox(ShulkerBoxUtils.getItems(stack), ShulkerBoxUtils.getCustomName(stack));
        renderShulkerBoxLabel(0F, poseStack, multiBufferSource, light, overlay, null, data);
    }

    public static void renderShulkerBoxLabel(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        ShulkerHintData data = ShulkerHintData.fromShulkerBox(shulkerBoxBlockEntity.getItems(), shulkerBoxBlockEntity.getCustomName());
        renderShulkerBoxLabel(partialTicks, poseStack, multiBufferSource, light, overlay, shulkerBoxBlockEntity, data);
    }

    private static void renderShulkerBoxLabel(float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, @Nullable ShulkerBoxBlockEntity shulkerBoxBlockEntity, ShulkerHintData data) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        if (shulkerBoxBlockEntity != null) {
            Direction direction = shulkerBoxBlockEntity.getBlockState().getValue(ShulkerBoxBlock.FACING);
            poseStack.mulPose(direction.getRotation());
        }
        poseStack.translate(0D, 0.5D, 0D);

        if (shulkerBoxBlockEntity != null) {
            poseStack.translate(0F, shulkerBoxBlockEntity.getProgress(partialTicks) * 0.5F, 0F);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-270F * shulkerBoxBlockEntity.getProgress(partialTicks)));
        }

        poseStack.mulPose(Vector3f.XP.rotationDegrees(-90F));

        ItemStack displayItem = data.getDisplayItem();
        Component label = data.getLabel();

        if (displayItem != null) {
            poseStack.pushPose();
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180F));
            if (label != null) {
                poseStack.translate(0F, 2F / 16F, 0F);
                poseStack.scale(12F / 16F, 12F / 16F, 12F / 16F);
            }
            ItemStack renderItemStack = createShulkerRenderStack(displayItem);
            mc.getItemRenderer().renderStatic(renderItemStack, ItemTransforms.TransformType.GUI, light, overlay, poseStack, multiBufferSource, 0);
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
            poseStack.mulPose(Vector3f.XP.rotationDegrees(180F));
            mc.font.drawInBatch8xOutline(label.getVisualOrderText(), -width / 2F, 0F, 0xFFFFFF, 0x00, poseStack.last().pose(), multiBufferSource, light);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    public static boolean isShulkerRenderStack(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(SHULKER_ITEM_TAG, Tag.TAG_BYTE);
    }

    public static ItemStack createShulkerRenderStack(ItemStack source) {
        ItemStack stack = source.copy();
        stack.getOrCreateTag().putBoolean(SHULKER_ITEM_TAG, true);
        return stack;
    }

}
