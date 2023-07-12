package de.maxhenkel.peek.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;

public class ShulkerBoxUtils {

    public static NonNullList<ItemStack> getItems(ItemStack stack) {
        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);
        if (blockEntityData == null) {
            return items;
        }

        if (!blockEntityData.contains(ShulkerBoxBlockEntity.ITEMS_TAG, Tag.TAG_LIST)) {
            return items;
        }

        ContainerHelper.loadAllItems(blockEntityData, items);

        return items;
    }

    @Nullable
    public static Component getCustomName(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            return stack.getHoverName();
        }

        CompoundTag blockEntityData = BlockItem.getBlockEntityData(stack);

        if (blockEntityData == null) {
            return null;
        }

        String customNameJson = blockEntityData.getString("CustomName");
        try {
            MutableComponent component = Component.Serializer.fromJson(customNameJson);
            if (component != null) {
                return component;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Item getBulkItem(NonNullList<ItemStack> contents) {
        Item renderItem = null;
        for (ItemStack itemStack : contents) {
            if (itemStack.isEmpty()) {
                continue;
            }
            if (renderItem == null) {
                renderItem = itemStack.getItem();
            } else {
                if (!renderItem.equals(itemStack.getItem())) {
                    return null;
                }
            }
        }
        return renderItem;
    }

    public static String getStringFromComponent(@Nullable Component component) {
        if (component == null) {
            return null;
        }
        return component.getString();
    }

    @Nullable
    public static Item byId(String id) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        return level.registryAccess().registryOrThrow(Registries.ITEM).get(new ResourceLocation(id));
    }

}
