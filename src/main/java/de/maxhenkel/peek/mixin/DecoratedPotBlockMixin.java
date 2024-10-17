package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;setChanged()V"))
    private void setTheItem(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (!Peek.CONFIG.sendDecoratedPotDataToClient.get()) {
            return;
        }
        BlockEntity be = level.getBlockEntity(blockPos);
        if (!(be instanceof DecoratedPotBlockEntity pot)) {
            return;
        }
        Packet<ClientGamePacketListener> packet = pot.getUpdatePacket();
        if (packet == null) {
            return;
        }
        //TODO Move this to utility class and use it for shulker boxes as well
        PlayerLookup.tracking(pot).forEach(p -> p.connection.send(packet));
    }

}
