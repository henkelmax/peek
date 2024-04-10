package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {

    public CompassItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);

        if (!Peek.CONFIG.peekCompasses.get()) {
            return;
        }
        @Nullable Level level = Minecraft.getInstance().level;

        LodestoneTracker lodestoneTracker = itemStack.get(DataComponents.LODESTONE_TRACKER);
        if (lodestoneTracker != null && lodestoneTracker.target().isPresent()) {
            addLodeStoneHoverText(tooltipContext, list, lodestoneTracker.target().get(), level);
            return;
        }

        if (level != null) {
            GlobalPos spawnPosition = CompassItem.getSpawnPosition(level);
            if (spawnPosition != null) {
                addSpawnHoverText(list, spawnPosition);
            }
        }
    }

    @Unique
    private void addLodeStoneHoverText(TooltipContext tooltipContext, List<Component> list, GlobalPos lodestonePosition, @Nullable Level level) {
        ResourceLocation location = lodestonePosition.dimension().location();

        if (level != null && location.equals(level.dimension().location())) {
            list.add(Component.translatable("tooltip.peek.compass.lodestone_position",
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

        list.add(Component.translatable("tooltip.peek.compass.lodestone_position.other_dimension",
                Component.literal(String.valueOf(lodestonePosition.pos().getX())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(lodestonePosition.pos().getY())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(lodestonePosition.pos().getZ())).withStyle(ChatFormatting.WHITE),
                dimensionName.withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }

    @Unique
    private void addSpawnHoverText(List<Component> list, GlobalPos spawnPosition) {
        list.add(Component.translatable("tooltip.peek.compass.spawn_position",
                Component.literal(String.valueOf(spawnPosition.pos().getX())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(spawnPosition.pos().getY())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(spawnPosition.pos().getZ())).withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }

}
