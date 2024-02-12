package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Object o = this;
        if (o != Items.RECOVERY_COMPASS) {
            return;
        }

        if (!Peek.CONFIG.peekRecoveryCompasses.get()) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        GlobalPos lastDeathLocation = player.getLastDeathLocation().orElse(null);
        if (lastDeathLocation == null) {
            return;
        }

        addDeathLocationText(level, list, lastDeathLocation);
    }

    @Unique
    private void addDeathLocationText(@Nullable Level level, List<Component> list, GlobalPos deathLocation) {
        ResourceLocation location = deathLocation.dimension().location();

        if (level != null && location.equals(level.dimension().location())) {
            list.add(Component.translatable("tooltip.peek.recovery_compass.death_location",
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

        list.add(Component.translatable("tooltip.peek.recovery_compass.death_location.other_dimension",
                Component.literal(String.valueOf(deathLocation.pos().getX())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(deathLocation.pos().getY())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(deathLocation.pos().getZ())).withStyle(ChatFormatting.WHITE),
                dimensionName.withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GRAY));
    }

}
