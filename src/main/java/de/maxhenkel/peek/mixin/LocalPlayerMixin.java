package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.utils.EnderChestUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Inject(method = "clientSideCloseContainer", at = @At("HEAD"))
    public void onClientSideCloseContainer(CallbackInfo ci) {
        if (EnderChestUtils.isScreenEnderChest(minecraft.screen)) {
            DataStore.enderChestInventory = minecraft.player.containerMenu.getItems();
        }
    }

}
