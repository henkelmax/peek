package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.data.DataStore;
import de.maxhenkel.peek.utils.EnderChestUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientGamePacketListener {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleContainerContent", at = @At("TAIL"))
    private void onHandleContainerContent(ClientboundContainerSetContentPacket packet, CallbackInfo ci) {
        // Intercept packet on opening Ender Chest to set contents
        // Could ignore all but the first, but this is potentially more reliable
        if (EnderChestUtils.isScreenEnderChest(minecraft.screen)) {
            if (DataStore.enderChestInventory == null) {
                DataStore.enderChestInventory = NonNullList.withSize(27, ItemStack.EMPTY);
            }
            for (int i = 0; i < Math.min(packet.getItems().size() - 36, 27); i++) {
                DataStore.enderChestInventory.set(i, packet.getItems().get(i));
            }
        }
    }
}