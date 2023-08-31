package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.utils.EnderChestUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
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
    @Inject(method = "handleInventoryMouseClick", at = @At("TAIL"))
    public void onHandleInventoryMouseClick(int i, int j, int k, ClickType clickType, Player player, CallbackInfo ci) {
        if (EnderChestUtils.isScreenEnderChest(minecraft.screen)) {
            DataStore.enderChestInventory = player.containerMenu.getItems();
        }
    }
}