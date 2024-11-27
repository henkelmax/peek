package de.maxhenkel.peek.mixin;

import com.mojang.serialization.MapCodec;
import de.maxhenkel.peek.events.DecoratedPotRenderEvents;
import de.maxhenkel.peek.events.ShulkerRenderEvents;
import de.maxhenkel.peek.itemproperties.DecoratedPotItemConditionalItemModelProperty;
import de.maxhenkel.peek.itemproperties.ShulkerItemConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConditionalItemModelProperties.class)
public class ConditionalItemModelPropertiesMixin {

    @Shadow
    @Final
    private static ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends ConditionalItemModelProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        ID_MAPPER.put(ShulkerRenderEvents.SHULKER_ITEM_CONDITION, ShulkerItemConditionalItemModelProperty.MAP_CODEC);
        ID_MAPPER.put(DecoratedPotRenderEvents.DECORATED_POT_ITEM_CONDITION, DecoratedPotItemConditionalItemModelProperty.MAP_CODEC);
    }

}
