package de.maxhenkel.peek.itemproperties;

import com.mojang.serialization.MapCodec;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DecoratedPotItemConditionalItemModelProperty implements ConditionalItemModelProperty {

    public static final MapCodec<DecoratedPotItemConditionalItemModelProperty> MAP_CODEC = MapCodec.unit(new DecoratedPotItemConditionalItemModelProperty());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        return DecoratedPotRenderEvents.isPotRenderStack(itemStack);
    }

    @Override
    public MapCodec<DecoratedPotItemConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
