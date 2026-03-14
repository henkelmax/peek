package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.utils.EnderChestUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Inject(method = "handleContainerInput", at = @At("TAIL"))
    public void handleContainerInput(int containerId, int slotNum, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        if (EnderChestUtils.isScreenEnderChest(minecraft.screen)) {
            DataStore.enderChestInventory = player.containerMenu.getItems();
        }
    }
}