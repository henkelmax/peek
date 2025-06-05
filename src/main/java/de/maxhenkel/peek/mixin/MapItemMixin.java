package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.MapDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(MapItem.class)
public abstract class MapItemMixin extends Item {

    public MapItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (!Peek.CONFIG.peekExplorationMaps.get()) {
            return;
        }

        MapDecorations mapDecorations = itemStack.get(DataComponents.MAP_DECORATIONS);

        if (mapDecorations == null) {
            return;
        }

        for (Map.Entry<String, MapDecorations.Entry> entry : mapDecorations.decorations().entrySet()) {
            if (!entry.getKey().equals("+")) {
                continue;
            }
            double posX = entry.getValue().x();
            double posZ = entry.getValue().z();
            list.add(Component.translatable("tooltip.peek.map.marker_position",
                    Component.literal(String.valueOf(posX)).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(posZ)).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
