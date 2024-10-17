package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class SuspiciousStewItemMixin {

    @Inject(method = "appendHoverText", at = @At("RETURN"))
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (!itemStack.has(DataComponents.SUSPICIOUS_STEW_EFFECTS)) {
            return;
        }
        if (!Peek.CONFIG.peekSuspiciousStews.get()) {
            return;
        }

        SuspiciousStewEffects effects = itemStack.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
        for (SuspiciousStewEffects.Entry entry : effects.effects()) {
            addPotionTooltip(tooltipContext, entry.createEffectInstance(), list);
        }
    }

    @Unique
    private static void addPotionTooltip(Item.TooltipContext tooltipContext, MobEffectInstance effect, List<Component> list) {
        MutableComponent tooltip = Component.translatable(effect.getDescriptionId());

        if (effect.getAmplifier() > 0) {
            tooltip = Component.translatable("potion.withAmplifier", tooltip, Component.translatable("potion.potency." + effect.getAmplifier()));
        }

        if (effect.getDuration() > 20) {
            tooltip = Component.translatable("potion.withDuration", tooltip, MobEffectUtil.formatDuration(effect, 1F, tooltipContext.tickRate()));
        }

        Holder<MobEffect> effectHolder = effect.getEffect();

        if (effectHolder.isBound()) {
            list.add(tooltip.withStyle(effectHolder.value().getCategory().getTooltipFormatting()));
        } else {
            list.add(tooltip);
        }
    }

}
