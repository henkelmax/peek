package de.maxhenkel.peek.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.data.DataStore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {

    @Inject(method = "appendHoverText", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BaseEntityBlock;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/BlockGetter;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", shift = At.Shift.AFTER), cancellable = true)
    private void appendHoverText(CallbackInfo ci) {
        if (Peek.CONFIG.peekShulkerBoxes.get()) {
            ci.cancel();
        }
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;"))
    private OptionalInt use(Player instance, MenuProvider menu, Operation<OptionalInt> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) Player player) {
        OptionalInt result = original.call(instance, menu);
        if (!Peek.CONFIG.showShulkerBoxBlockHint.get()) {
            return result;
        }

        if (!level.isClientSide) {
            return result;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            DataStore.lastOpenedShulkerBox = shulkerBoxBlockEntity;
            DataStore.lastOpenedShulkerBoxContainerId = result.orElse(-1);
        }
        return result;
    }

}
