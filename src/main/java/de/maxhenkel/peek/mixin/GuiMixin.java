package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.events.HudEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 999)
public class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderEffects", at = @At(value = "HEAD"))
    private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        HudEvents.onRenderHud(guiGraphics, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
    }

}
