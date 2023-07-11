package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.events.TooltipImageEvents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
        Optional<TooltipComponent> tooltipImage = TooltipImageEvents.getTooltipImage(stack, block);
        if (tooltipImage == null) {
            return super.getTooltipImage(stack);
        }
        return tooltipImage;
    }
}
