package de.maxhenkel.peek.itemproperties;

import com.mojang.serialization.MapCodec;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ShulkerItemConditionalItemModelProperty implements ConditionalItemModelProperty {

    public static final MapCodec<ShulkerItemConditionalItemModelProperty> MAP_CODEC = MapCodec.unit(new ShulkerItemConditionalItemModelProperty());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        return ShulkerRenderEvents.isShulkerRenderStack(itemStack);
    }

    @Override
    public MapCodec<ShulkerItemConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
