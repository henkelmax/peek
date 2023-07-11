package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;

public class RenderEvents {

    private static final Minecraft mc = Minecraft.getInstance();
    public static final CompoundTag RENDER_ITEM_TAG = new CompoundTag();

    public static void renderShulkerBoxItemLabel(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        NonNullList<ItemStack> items = ShulkerBoxUtils.getItems(stack);
        if (items == null) {
            return;
        }
        renderShulkerBoxLabel(items, 0F, poseStack, multiBufferSource, light, overlay, null);
    }

    public static void renderShulkerBoxLabel(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        renderShulkerBoxLabel(shulkerBoxBlockEntity.getItems(), partialTicks, poseStack, multiBufferSource, light, overlay, shulkerBoxBlockEntity);
    }

    private static void renderShulkerBoxLabel(NonNullList<ItemStack> items, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, @Nullable ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
        Item renderItem = null;
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) {
                continue;
            }
            if (renderItem == null) {
                renderItem = itemStack.getItem();
            } else {
                if (!renderItem.equals(itemStack.getItem())) {
                    return;
                }
            }
        }

        if (renderItem == null) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.5D, 1D, 0.5D);

        if (shulkerBoxBlockEntity != null) {
            poseStack.translate(0F, shulkerBoxBlockEntity.getProgress(partialTicks) * 0.5F, 0F);
            poseStack.rotateAround(Axis.YP.rotationDegrees(-270F * shulkerBoxBlockEntity.getProgress(partialTicks)), 0F, 1F, 0F);
        }

        poseStack.rotateAround(Axis.XP.rotationDegrees(-90F), 1F, 0F, 0F);

        ItemStack renderItemStack = new ItemStack(renderItem);
        renderItemStack.setTag(RENDER_ITEM_TAG);

        mc.getItemRenderer().renderStatic(renderItemStack, ItemDisplayContext.GUI, light, overlay, poseStack, multiBufferSource, mc.level, 0);
        poseStack.popPose();
    }

}
