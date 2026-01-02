package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.interfaces.PeekDecoratedPot;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockEntityMixin extends BlockEntity implements PeekDecoratedPot {

    @Unique
    @Nullable
    private CompoundTag lastData;
    @Shadow
    private ItemStack item;

    public DecoratedPotBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public ItemStack peek$getContainedItem() {
        return item;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!Peek.CONFIG.sendDecoratedPotDataToClient.get()) {
            return;
        }
        if (level == null || level.isClientSide()) {
            return;
        }
        ClientboundBlockEntityDataPacket packet = getUpdatePacket();
        if (packet == null) {
            return;
        }
        CompoundTag tag = packet.getTag();
        if (tag == null) {
            tag = new CompoundTag();
        }
        if (lastData != null && lastData.equals(tag)) {
            return;
        }

        PlayerLookup.tracking(this).forEach(p -> p.connection.send(packet));
        lastData = tag;
    }
    
    @Shadow
    public abstract ClientboundBlockEntityDataPacket getUpdatePacket();

}
