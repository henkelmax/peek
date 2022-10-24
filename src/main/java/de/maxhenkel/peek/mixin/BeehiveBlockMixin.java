package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin extends BaseEntityBlock {

    private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
    private static final String BLOCK_STATE_TAG = "BlockStateTag";
    private static final String HONEY_LEVEL = "honey_level";

    protected BeehiveBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);

        if (!Peek.CLIENT_CONFIG.peekBeehives.get()) {
            return;
        }

        int beeCount = 0;
        CompoundTag blockEntityTag = itemStack.getTagElement(BLOCK_ENTITY_TAG);
        if (blockEntityTag != null) {
            if (blockEntityTag.contains(BeehiveBlockEntity.BEES, NbtType.LIST)) {
                beeCount = blockEntityTag.getList(BeehiveBlockEntity.BEES, NbtType.COMPOUND).size();
            }
        }
        list.add(Component.translatable("tooltip.peek.beehive.bee_count", Component.literal(beeCount + "/3").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));

        int honeyLevel = 0;
        CompoundTag blockStateTag = itemStack.getTagElement(BLOCK_STATE_TAG);
        if (blockStateTag != null) {
            if (blockStateTag.contains(HONEY_LEVEL, NbtType.INT)) {
                honeyLevel = blockStateTag.getInt(HONEY_LEVEL);
            }
        }
        list.add(Component.translatable("tooltip.peek.beehive.honey_level", Component.literal(honeyLevel + "/5").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
    }
}
