package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.interfaces.NoTransformAccessor;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {

    @Inject(method = "updateForTopItem", at = @At("RETURN"))
    private void resolve(ItemStackRenderState itemStackRenderState, ItemStack stack, ItemDisplayContext itemDisplayContext, boolean bl, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        //TODO Create a common method to detect custom rendered items
        ((NoTransformAccessor) itemStackRenderState).peek$setShouldTransform(!ShulkerRenderEvents.isShulkerRenderStack(stack) && !DecoratedPotRenderEvents.isPotRenderStack(stack));
    }

}
