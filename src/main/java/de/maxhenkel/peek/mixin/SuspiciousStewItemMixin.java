package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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

        if (!Peek.CLIENT_CONFIG.peekSuspiciousStews.get()) {
            return;
        }

        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null && compoundTag.contains(SuspiciousStewItem.EFFECTS_TAG, NbtType.LIST)) {
            ListTag listTag = compoundTag.getList(SuspiciousStewItem.EFFECTS_TAG, NbtType.COMPOUND);

            for (int i = 0; i < listTag.size(); i++) {
                int duration = 160;
                CompoundTag effectTag = listTag.getCompound(i);
                if (effectTag.contains(SuspiciousStewItem.EFFECT_DURATION_TAG, NbtType.INT)) {
                    duration = effectTag.getInt(SuspiciousStewItem.EFFECT_DURATION_TAG);
                }
                MobEffect mobEffect = MobEffect.byId(effectTag.getInt(SuspiciousStewItem.EFFECT_ID_TAG));
                if (mobEffect != null) {
                    addPotionTooltip(new MobEffectInstance(mobEffect, duration), list);
                }
            }
        }
    }

    private static void addPotionTooltip(MobEffectInstance effect, List<Component> list) {
        MutableComponent tooltip = Component.translatable(effect.getDescriptionId());

        if (effect.getAmplifier() > 0) {
            tooltip = Component.translatable("potion.withAmplifier", tooltip, Component.translatable("potion.potency." + effect.getAmplifier()));
        }

        if (effect.getDuration() > 20) {
            tooltip = Component.translatable("potion.withDuration", tooltip, MobEffectUtil.formatDuration(effect, 1F));
        }

        list.add(tooltip.withStyle(effect.getEffect().getCategory().getTooltipFormatting()));
    }

}
