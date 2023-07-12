package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekItemStack;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @Shadow
    private EditBox name;

    @Inject(method = "slotChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setValue(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack stack, CallbackInfo ci) {
        if (!ShulkerBoxUtils.isShulkerBox(stack)) {
            return;
        }
        if (!Peek.CLIENT_CONFIG.hideShulkerBoxDataStrings.get()) {
            return;
        }
        if (stack.isEmpty()) {
            return;
        }
        name.setValue(((PeekItemStack) (Object) stack).peek$getRealHoverName().getString());
    }

}
