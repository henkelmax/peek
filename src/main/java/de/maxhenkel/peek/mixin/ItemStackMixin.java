package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekItemStack;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements PeekItemStack {

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
        CompoundTag tag = getTagElement(ItemStack.TAG_DISPLAY);
        if (tag != null && tag.contains(ItemStack.TAG_DISPLAY_NAME, Tag.TAG_STRING)) {
            try {
                MutableComponent hoverName = Component.Serializer.fromJson(tag.getString(ItemStack.TAG_DISPLAY_NAME));
                if (hoverName != null) {
                    return hoverName;
                }
                tag.remove(ItemStack.TAG_DISPLAY_NAME);
            } catch (Exception e) {
                tag.remove(ItemStack.TAG_DISPLAY_NAME);
            }
        }
        return getItem().getName((ItemStack) (Object) this);
    }

    @Shadow
    public abstract Item getItem();

    @Shadow
    @Nullable
    public abstract CompoundTag getTagElement(String string);

}
