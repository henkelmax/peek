package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.events.TooltipImageEvents;
import de.maxhenkel.peek.utils.ShulkerBoxUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

    @Shadow
    @Final
    private Block block;

    public BlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        Optional<TooltipComponent> tooltipImage = TooltipImageEvents.getTooltipImage(stack, block);
        if (tooltipImage == null) {
            return super.getTooltipImage(stack);
        }
        return tooltipImage;
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/gameevent/GameEvent$Context;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void place(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir, BlockPlaceContext blockPlaceContext2) {
        if (!Peek.CLIENT_CONFIG.showShulkerBoxBlockHint.get()) {
            return;
        }
        Level level = blockPlaceContext2.getLevel();
        if (!level.isClientSide) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(blockPlaceContext2.getClickedPos());
        if (!(blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity)) {
            return;
        }

        ItemStack item = blockPlaceContext2.getItemInHand();
        if (!ShulkerBoxUtils.isShulkerBox(item)) {
            return;
        }

        shulkerBoxBlockEntity.setItems(ShulkerBoxUtils.getItems(item));

        Component customName = ShulkerBoxUtils.getCustomName(item);
        if (customName != null) {
            shulkerBoxBlockEntity.setCustomName(customName);
        }
    }

}
