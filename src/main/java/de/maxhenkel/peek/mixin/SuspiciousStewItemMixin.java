package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

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

        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null && compoundTag.contains(SuspiciousStewItem.EFFECTS_TAG, Tag.TAG_LIST)) {
            SuspiciousEffectHolder.EffectEntry.LIST_CODEC.parse(NbtOps.INSTANCE, compoundTag.getList(SuspiciousStewItem.EFFECTS_TAG, Tag.TAG_COMPOUND)).result().ifPresent(effectEntries -> {
                for (SuspiciousEffectHolder.EffectEntry effectEntry : effectEntries) {
                    addPotionTooltip(level, effectEntry.createEffectInstance(), list);
                }
            });
        }
    }

    private static void addPotionTooltip(@Nullable Level level, MobEffectInstance effect, List<Component> list) {
        MutableComponent tooltip = Component.translatable(effect.getDescriptionId());

        if (effect.getAmplifier() > 0) {
            tooltip = Component.translatable("potion.withAmplifier", tooltip, Component.translatable("potion.potency." + effect.getAmplifier()));
        }

        if (effect.getDuration() > 20) {
            tooltip = Component.translatable("potion.withDuration", tooltip, MobEffectUtil.formatDuration(effect, 1F, level == null ? 20F : level.tickRateManager().tickrate()));
        }

        list.add(tooltip.withStyle(effect.getEffect().getCategory().getTooltipFormatting()));
    }

}
