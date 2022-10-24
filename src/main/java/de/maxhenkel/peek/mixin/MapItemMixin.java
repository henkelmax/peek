package de.maxhenkel.peek.mixin;

import de.maxhenkel.peek.Peek;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MapItem.class)
public abstract class MapItemMixin extends Item {

    private static final String DECORATIONS = "Decorations";
    private static final String ID = "id";
    private static final String X = "x";
    private static final String Z = "z";

    public MapItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (!Peek.CLIENT_CONFIG.peekExplorationMaps.get()) {
            return;
        }

        CompoundTag tag = itemStack.getTag();

        if (tag == null || !tag.contains(DECORATIONS, NbtType.LIST)) {
            return;
        }

        ListTag decorations = tag.getList(DECORATIONS, NbtType.COMPOUND);

        for (int i = 0; i < decorations.size(); i++) {
            CompoundTag decoration = decorations.getCompound(i);
            String id = decoration.getString(ID);
            if (!id.equals("+")) {
                continue;
            }
            int posX = decoration.getInt(X);
            int posZ = decoration.getInt(Z);
            list.add(Component.translatable("tooltip.peek.map.marker_position",
                    Component.literal(String.valueOf(posX)).withStyle(ChatFormatting.WHITE),
                    Component.literal(String.valueOf(posZ)).withStyle(ChatFormatting.WHITE)
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
