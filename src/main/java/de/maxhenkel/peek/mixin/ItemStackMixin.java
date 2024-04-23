package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekItemStack;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements PeekItemStack, DataComponentHolder {

    @Inject(method = "getHoverName", at = @At(value = "RETURN"), cancellable = true)
    private void getHoverName(CallbackInfoReturnable<Component> cir) {
        if (!ShulkerBoxUtils.isShulkerBox((ItemStack) (Object) this)) {
            return;
        }
        if (!Peek.CONFIG.useShulkerBoxDataStrings.get()) {
            return;
        }

        if (!Minecraft.getInstance().isSameThread()) {
            return;
        }

        cir.setReturnValue(ShulkerBoxUtils.cleanName(cir.getReturnValue()));
    }

    @Override
    public Component peek$getRealHoverName() {
        Component component = get(DataComponents.CUSTOM_NAME);
        return component != null ? component : this.getItem().getName(((ItemStack) (Object) this));
    }

    @Shadow
    public abstract Item getItem();

}
