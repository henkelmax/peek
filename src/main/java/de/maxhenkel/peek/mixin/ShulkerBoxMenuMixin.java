package de.maxhenkel.peek.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.data.DataStore;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ShulkerBoxMenu.class)
public class ShulkerBoxMenuMixin {

    @ModifyVariable(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static Container container(Container container, @Local(argsOnly = true) int containerId) {
        if (!Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            return container;
        }

        if (!(container instanceof SimpleContainer)) {
            return container;
        }
        if (DataStore.lastOpenedShulkerBox == null) {
            return container;
        }
        if (DataStore.lastOpenedShulkerBoxContainerId != containerId) {
            return container;
        }
        DataStore.lastOpenedShulkerBox.clearContent();
        return DataStore.lastOpenedShulkerBox;
    }

}
