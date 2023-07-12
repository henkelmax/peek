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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShulkerBoxUtils {

    private static final Pattern ITEM_ID_PATTERN = Pattern.compile("\\{((?:[a-z0-9_-]+:)?[a-z0-9_-]+)}");

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
    private static Item getItemFromName(@Nullable String name) {
        if (name == null) {
            return null;
        }
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        Matcher matcher = ITEM_ID_PATTERN.matcher(name);
        if (!matcher.find()) {
            return null;
        }
        String group = matcher.group(1);
        ResourceLocation id = new ResourceLocation(group);
        return level.registryAccess().registryOrThrow(Registries.ITEM).get(id);
    }

    public static ShulkerHintData getHint(NonNullList<ItemStack> contents, @Nullable Component name) {
        ShulkerHintData data = new ShulkerHintData();

        String text = getStringFromComponent(name);

        Item itemFromName = getItemFromName(text);
        if (itemFromName != null) {
            if (itemFromName != Items.AIR) {
                data.setDisplayItem(itemFromName);
            }
        } else {
            data.setDisplayItem(getBulkItem(contents));
        }

        if (text != null) {
            Matcher matcher = ITEM_ID_PATTERN.matcher(text);
            if (matcher.find()) {
                String replaced = matcher.replaceAll("").trim();
                data.setLabel(replaced.isEmpty() ? null : Component.literal(replaced));
            } else {
                data.setLabel(name);
            }
        }

        return data;
    }

}
