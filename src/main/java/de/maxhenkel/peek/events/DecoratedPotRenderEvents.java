package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekDecoratedPot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

public class DecoratedPotRenderEvents {

    public static final ResourceLocation DECORATED_POT_ITEM_PREDICATE = new ResourceLocation(Peek.MODID, "decorated_pot_item");

    private static final Minecraft mc = Minecraft.getInstance();
    private static final String DECORATED_POT_ITEM_TAG = "DecoratedPotItem";

    public static void renderDecoratedPotLabel(DecoratedPotBlockEntity pot, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (pot == null) {
            return;
        }
        ItemStack containedItem = ((PeekDecoratedPot) pot).peek$getContainedItem();
        if (containedItem == null || containedItem.isEmpty()) {
            return;
        }
        ItemStack renderItemStack = createPotRenderStack(containedItem);
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.rotateAround(Axis.YP.rotationDegrees(180F), 0F, 1F, 0F);

        poseStack.translate(0D, 0D, -7.001D / 16D);
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(0D, 4D / 16D, 0D);
        mc.getItemRenderer().renderStatic(renderItemStack, ItemDisplayContext.GUI, light, overlay, poseStack, multiBufferSource, mc.level, 0);
        poseStack.popPose();
        poseStack.translate(0D, -5D / 16D, 0D);
        MutableComponent text = Component.translatable("label.peek.decorated_pot_count", containedItem.getCount());
        int width = mc.font.width(text);
        float textScale = 0.02F;
        poseStack.scale(textScale, textScale, textScale);
        poseStack.translate(0F, mc.font.lineHeight / 2F, 0F);
        poseStack.rotateAround(Axis.ZP.rotationDegrees(180F), 0F, 0F, 1F);
        mc.font.drawInBatch8xOutline(text.getVisualOrderText(), -width / 2F, 0F, 0xFFFFFF, 0x00, poseStack.last().pose(), multiBufferSource, light);
        poseStack.popPose();
    }

    public static boolean isPotRenderStack(ItemStack stack) {
        CompoundTag tag = stack.getComponents().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
        return tag.contains(DECORATED_POT_ITEM_TAG, Tag.TAG_BYTE);
    }

    public static ItemStack createPotRenderStack(ItemStack source) {
        ItemStack stack = source.copy();
        CompoundTag tag = new CompoundTag();
        tag.putBoolean(DECORATED_POT_ITEM_TAG, true);
        stack.applyComponents(DataComponentPatch.builder().set(DataComponents.CUSTOM_DATA, CustomData.of(tag)).build());
        return stack;
    }

}
