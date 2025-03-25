package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.TooltipEvents;
import de.maxhenkel.peek.interfaces.PeekItemStack;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements PeekItemStack, DataComponentHolder {

    @Inject(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", shift = At.Shift.AFTER, ordinal = 0))
    public void addDetailsToTooltip(Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Player player, TooltipFlag tooltipFlag, Consumer<Component> consumer, CallbackInfo ci) {
        TooltipEvents.getTooltip((ItemStack) (Object) this, tooltipContext, tooltipFlag, consumer);
        addToTooltip(DataComponents.LODESTONE_TRACKER, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        addToTooltip(DataComponents.MAP_DECORATIONS, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }

    @Inject(method = "addToTooltip", at = @At(value = "HEAD"), cancellable = true)
    public void addToTooltip(DataComponentType<?> dataComponentType, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (dataComponentType != DataComponents.CONTAINER) {
            return;
        }
        // Always hide ALL container tooltips no matter if options are enabled or disabled in the config
        // TODO Maybe put this behind a config option
        ci.cancel();
    }

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
        return component != null ? component : getItem().getName(((ItemStack) (Object) this));
    }

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract void addToTooltip(DataComponentType<?> dataComponentType, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag);

}
