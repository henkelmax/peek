package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.utils.ItemNameCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public ClientLevel level;

    @Inject(method = "setLevel", at = @At("RETURN"))
    public void setLevel(ClientLevel clientLevel, CallbackInfo ci) {
        if (clientLevel != null) {
            ItemNameCache.load(clientLevel);
        }
    }

    @Inject(method = "reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    public void reloadResourcePacks(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (level != null) {
            ItemNameCache.load(level);
        }
    }

}
