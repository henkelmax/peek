package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin extends BaseEntityBlock {

    protected BeehiveBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);

        if (!Peek.CONFIG.peekBeehives.get()) {
            return;
        }

        int beeCount = 0;
        List<BeehiveBlockEntity.Occupant> occupants = itemStack.get(DataComponents.BEES);
        if (occupants != null) {
            beeCount = occupants.size();
        }
        list.add(Component.translatable("tooltip.peek.beehive.bee_count", Component.literal(beeCount + "/3").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));

        BlockItemStateProperties props = itemStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        Integer honeyLevel = props.get(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel == null) {
            honeyLevel = 0;
        }
        list.add(Component.translatable("tooltip.peek.beehive.honey_level", Component.literal(honeyLevel + "/5").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY));
    }

}
