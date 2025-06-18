package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxSpecialRenderer.class)
public abstract class ShulkerBoxSpecialRendererMixin implements SpecialModelRenderer<ItemStack> {

    @Shadow
    public abstract void render(ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType);

    @Nullable
    @Override
    public ItemStack extractArgument(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    public void render(@Nullable ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        render(displayContext, poseStack, bufferSource, packedLight, packedOverlay, hasFoilType);
        if (itemStack == null) {
            return;
        }
        if (!ShulkerBoxUtils.isShulkerBox(itemStack)) {
            return;
        }
        if (!Peek.CONFIG.showShulkerBoxItemHint.get()) {
            return;
        }
        ShulkerRenderEvents.renderShulkerBoxItemLabel(itemStack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
    }

}
