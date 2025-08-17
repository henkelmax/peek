package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SuspiciousStewEffects.class)
public class SuspiciousStewEffectsMixin {

    @Redirect(method = "addToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/TooltipFlag;isCreative()Z"))
    private boolean isCreative(TooltipFlag instance) {
        if (Peek.CONFIG.peekSuspiciousStews.get()) {
            return true;
        }
        return instance.isCreative();
    }

}
