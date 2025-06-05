package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin extends RandomizableContainerBlockEntity implements WorldlyContainer {

    @Unique
    @Nullable
    private CompoundTag lastData;

    @Shadow
    private NonNullList<ItemStack> itemStacks;

    protected ShulkerBoxBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        if (!Peek.CONFIG.sendShulkerBoxDataToClient.get()) {
            return super.getUpdatePacket();
        }
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        if (!Peek.CONFIG.sendShulkerBoxDataToClient.get()) {
            return super.getUpdateTag(provider);
        }
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(problemPath(), Peek.LOGGER)) {
            TagValueOutput tag = TagValueOutput.createWithContext(scopedCollector, provider);
            saveAdditional(tag);
            if (tag.isEmpty()) {
                // If the tag is empty, save container items again with an empty items list,so that the update packet is actually processed
                // Empty tags are replaced with null in the ClientboundBlockEntityDataPacket and thus not processed on the client
                ContainerHelper.saveAllItems(tag, itemStacks, true);
            }

            return tag.buildResult();
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!Peek.CONFIG.sendShulkerBoxDataToClient.get()) {
            return;
        }
        if (level == null || level.isClientSide()) {
            return;
        }
        Packet<ClientGamePacketListener> packet = getUpdatePacket();
        if (!(packet instanceof ClientboundBlockEntityDataPacket dataPacket)) {
            return;
        }
        CompoundTag tag = dataPacket.getTag();
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
    protected abstract void saveAdditional(ValueOutput valueOutput);
}
