package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import de.maxhenkel.peek.utils.ShulkerHintData;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxSpecialRenderer.class)
public abstract class ShulkerBoxSpecialRendererMixin implements SpecialModelRenderer<ShulkerHintData> {

    @Shadow
    public abstract void submit(ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl);

    @Nullable
    @Override
    public ShulkerHintData extractArgument(ItemStack stack) {
        return ShulkerHintData.fromShulkerBox(ShulkerBoxUtils.getItems(stack), ShulkerBoxUtils.getCustomName(stack));
    }

    @Override
    public void submit(@Nullable ShulkerHintData data, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl) {
        submit(itemDisplayContext, poseStack, submitNodeCollector, i, j, bl);
        if (!Peek.CONFIG.showShulkerBoxItemHint.get()) {
            return;
        }
        if (data != null) {
            ShulkerRenderEvents.submitShulkerBoxItemLabel(data, poseStack, submitNodeCollector);
        }
    }

}
