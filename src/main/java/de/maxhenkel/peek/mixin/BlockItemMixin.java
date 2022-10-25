package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.tooltips.ContainerTooltip;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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
        if (!(block instanceof ShulkerBoxBlock)) {
            return super.getTooltipImage(stack);
        }

        if (!Peek.CLIENT_CONFIG.peekShulkerBoxes.get()) {
            return super.getTooltipImage(stack);
        }

        // TODO cache contents
        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData != null) {
            if (blockEntityData.contains(ShulkerBoxBlockEntity.LOOT_TABLE_TAG, NbtType.STRING)) {
                // TODO add string about loot table
            }
            if (blockEntityData.contains(ShulkerBoxBlockEntity.ITEMS_TAG, NbtType.LIST)) {
                ContainerHelper.loadAllItems(blockEntityData, items);
            }
        }

        return Optional.of(new ContainerTooltip(9, 3, items));
    }
}
