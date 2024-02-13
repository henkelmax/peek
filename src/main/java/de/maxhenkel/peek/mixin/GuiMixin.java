package de.maxhenkel.peek.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.events.HudEvents;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 999)
public class GuiMixin {

    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private void render(PoseStack poseStack, float f, CallbackInfo ci) {
        HudEvents.onRenderHud(poseStack, screenWidth, screenHeight);
    }

}
