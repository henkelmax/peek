package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.function.Consumer;

@Mixin(MapDecorations.class)
public abstract class MapDecorationsMixin implements TooltipProvider {

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (!Peek.CONFIG.peekExplorationMaps.get()) {
            return;
        }

        MapDecorations mapDecorations = (MapDecorations) ((Object) this);

        for (Map.Entry<String, MapDecorations.Entry> entry : mapDecorations.decorations().entrySet()) {
            if (!entry.getKey().equals("+")) {
                continue;
            }
            double posX = entry.getValue().x();
            double posZ = entry.getValue().z();
            consumer.accept(Component.translatable("tooltip.peek.map.marker_position",
                    Component.literal(String.valueOf(posX)).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(posZ)).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
