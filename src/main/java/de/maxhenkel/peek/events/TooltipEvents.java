package de.maxhenkel.peek.events;

import de.maxhenkel.peek.Peek;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class TooltipEvents {

    public static void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipType, Consumer<Component> consumer) {
        if (stack.getItem() == Items.COMPASS) {
            getCompassTooltip(stack, tooltipContext, tooltipType, consumer);
        } else if (stack.getItem() == Items.RECOVERY_COMPASS) {
            getRecoveryCompassTooltip(stack, tooltipContext, tooltipType, consumer);
        }
    }

    public static void getCompassTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipType, Consumer<Component> consumer) {
        if (!Peek.CONFIG.peekCompasses.get()) {
            return;
        }

        LodestoneTracker lodestoneTracker = stack.get(DataComponents.LODESTONE_TRACKER);
        if (lodestoneTracker != null && lodestoneTracker.target().isPresent()) {
            return;
        }

        @Nullable Level level = Minecraft.getInstance().level;

        if (level != null && level.dimensionType().natural()) {
            GlobalPos spawnPosition = GlobalPos.of(level.dimension(), level.getSharedSpawnPos());
            consumer.accept(Component.translatable("tooltip.peek.compass.spawn_position",
                    Component.literal(String.valueOf(spawnPosition.pos().getX())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(spawnPosition.pos().getY())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(spawnPosition.pos().getZ())).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
        }
    }

    public static void getRecoveryCompassTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipType, Consumer<Component> consumer) {
        if (!Peek.CONFIG.peekRecoveryCompasses.get()) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        GlobalPos deathLocation = player.getLastDeathLocation().orElse(null);
        if (deathLocation == null) {
            return;
        }

        ResourceLocation location = deathLocation.dimension().location();

        Level level = Minecraft.getInstance().level;

        if (level != null && location.equals(level.dimension().location())) {
            consumer.accept(Component.translatable("tooltip.peek.recovery_compass.death_location",
                    Component.literal(String.valueOf(deathLocation.pos().getX())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(deathLocation.pos().getY())).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(deathLocation.pos().getZ())).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
            return;
        }

        MutableComponent dimensionName = Component.translatable(location.toString());

        if (location.getNamespace().equals("minecraft")) {
            dimensionName = Component.translatable("tooltip.peek.dimension." + location.getPath());
        }

        consumer.accept(Component.translatable("tooltip.peek.recovery_compass.death_location.other_dimension",
                Component.literal(String.valueOf(deathLocation.pos().getX())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(deathLocation.pos().getY())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(deathLocation.pos().getZ())).withStyle(ChatFormatting.WHITE),
                dimensionName.withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }

}
