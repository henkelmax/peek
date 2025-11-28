package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(LodestoneTracker.class)
public class LodestoneTrackerMixin implements TooltipProvider {
    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (!Peek.CONFIG.peekCompasses.get()) {
            return;
        }
        @Nullable Level level = Minecraft.getInstance().level;

        LodestoneTracker lodestoneTracker = (LodestoneTracker) (Object) this;
        if (lodestoneTracker.target().isPresent()) {
            addLodeStoneHoverText(context, consumer, lodestoneTracker.target().get(), level);
        }
    }

    @Unique
    private void addLodeStoneHoverText(Item.TooltipContext tooltipContext, Consumer<Component> consumer, GlobalPos lodestonePosition, @Nullable Level level) {
        Identifier location = lodestonePosition.dimension().identifier();

        if (level != null && location.equals(level.dimension().identifier())) {
            consumer.accept(Component.translatable("tooltip.peek.compass.lodestone_position",
                    Component.literal(String.valueOf(lodestonePosition.pos().getX())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(lodestonePosition.pos().getY())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(lodestonePosition.pos().getZ())).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
            return;
        }

        MutableComponent dimensionName = Component.translatable(location.toString());

        if (location.getNamespace().equals("minecraft")) {
            dimensionName = Component.translatable("tooltip.peek.dimension." + location.getPath());
        }

        consumer.accept(Component.translatable("tooltip.peek.compass.lodestone_position.other_dimension",
                Component.literal(String.valueOf(lodestonePosition.pos().getX())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(lodestonePosition.pos().getY())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(lodestonePosition.pos().getZ())).withStyle(ChatFormatting.WHITE),
                dimensionName.withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }

}
