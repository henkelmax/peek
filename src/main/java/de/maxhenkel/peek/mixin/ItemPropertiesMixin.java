package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.events.ShulkerRenderEvents;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemProperties.class)
public abstract class ItemPropertiesMixin {

    @Shadow
    private static ClampedItemPropertyFunction registerGeneric(ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        throw new IllegalStateException();
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        registerGeneric(ShulkerRenderEvents.SHULKER_ITEM_PREDICATE, (stack, lvl, e, i) -> {
            return ShulkerRenderEvents.isShulkerRenderStack(stack) ? 1F : 0F;
        });
    }

}
