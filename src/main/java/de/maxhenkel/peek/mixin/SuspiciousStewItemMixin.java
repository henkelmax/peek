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
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(SuspiciousStewItem.class)
public abstract class SuspiciousStewItemMixin extends Item {

    public SuspiciousStewItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        if (!Peek.CONFIG.peekSuspiciousStews.get()) {
            return;
        }

        SuspiciousStewEffects effects = itemStack.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
        for (SuspiciousStewEffects.Entry entry : effects.effects()) {
            addPotionTooltip(level, entry.createEffectInstance(), list);
        }
    }

    @Unique
    private static void addPotionTooltip(@Nullable Level level, MobEffectInstance effect, List<Component> list) {
        MutableComponent tooltip = Component.translatable(effect.getDescriptionId());

        if (effect.getAmplifier() > 0) {
            tooltip = Component.translatable("potion.withAmplifier", tooltip, Component.translatable("potion.potency." + effect.getAmplifier()));
        }

        if (effect.getDuration() > 20) {
            tooltip = Component.translatable("potion.withDuration", tooltip, MobEffectUtil.formatDuration(effect, 1F, level == null ? 20F : level.tickRateManager().tickrate()));
        }

        Holder<MobEffect> effectHolder = effect.getEffect();

        if (effectHolder.isBound()) {
            list.add(tooltip.withStyle(effectHolder.value().getCategory().getTooltipFormatting()));
        } else {
            list.add(tooltip);
        }
    }

}
