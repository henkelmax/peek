package de.maxhenkel.peek.utils;

import de.maxhenkel.peek.Peek;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShulkerHintData {

    public static final Pattern DATA_PATTERN = Pattern.compile("\\{([^{}]+)}");

    @Nullable
    private Component label;
    @Nullable
    private Item displayItem;

    public ShulkerHintData(@Nullable Component label, @Nullable Item displayItem) {
        this.label = label;
        this.displayItem = displayItem;
    }

    public ShulkerHintData() {

    }

    @Nullable
    public Component getLabel() {
        return label;
    }

    @Nullable
    public Item getDisplayItem() {
        return displayItem;
    }

    public void setLabel(@Nullable Component label) {
        this.label = label;
    }

    public void setDisplayItem(@Nullable Item displayItem) {
        this.displayItem = displayItem;
    }

    public static ShulkerHintData fromShulkerBox(NonNullList<ItemStack> contents, @Nullable Component name) {
        if (Peek.CONFIG.useShulkerBoxDataStrings.get()) {
            ShulkerHintData data = ShulkerHintData.fromDataString(ShulkerBoxUtils.getStringFromComponent(name));
            if (data != null) {
                return data;
            }
        }
        if (name != null && Peek.CONFIG.useShulkerBoxItemNames.get()) {
            ShulkerHintData data = ShulkerHintData.fromItemName(name);
            if (data != null) {
                return data;
            }
        }
        return fromContents(contents, name);
    }

    public static ShulkerHintData fromContents(NonNullList<ItemStack> contents, @Nullable Component name) {
        ShulkerHintData data = new ShulkerHintData();
        if (Peek.CONFIG.showShulkerBoxItems.get()) {
            data.setDisplayItem(ShulkerBoxUtils.getBulkItem(contents));
        }
        if (Peek.CONFIG.showShulkerBoxLabels.get()) {
            data.setLabel(name);
        }
        return data;
    }

    @Nullable
    public static ShulkerHintData fromDataString(@Nullable String s) {
        if (s == null) {
            return null;
        }
        Matcher matcher = DATA_PATTERN.matcher(s);
        if (!matcher.find()) {
            return null;
        }
        ShulkerHintData data = new ShulkerHintData();
        String str = matcher.group(1);
        Map<String, String> map = parseData(str);
        String label = getFromMap(map, "label", "l");
        if (label != null) {
            data.setLabel(Component.literal(label));
        }
        String itemId = getFromMap(map, "item", "i");
        if (itemId != null) {
            Item item = ShulkerBoxUtils.byId(itemId);
            if (item != null) {
                data.setDisplayItem(item);
            }
        }
        return data;
    }

    @Nullable
    public static ShulkerHintData fromItemName(Component label) {
        Item item = ItemNameCache.byIdName(label.getString());
        if (item != null) {
            return new ShulkerHintData(null, item);
        }
        item = ItemNameCache.byName(label.getString());
        if (item != null) {
            return new ShulkerHintData(null, item);
        }
        return null;
    }

    @Nullable
    private static String getFromMap(Map<String, String> map, String... keys) {
        for (String key : keys) {
            if (map.containsKey(key)) {
                return map.get(key);
            }
        }
        return null;
    }

    private static Map<String, String> parseData(String s) {
        Map<String, String> data = new HashMap<>();
        String[] split = s.split(";");
        for (String str : split) {
            String[] split2 = str.split("=", 2);
            if (split2.length != 2) {
                continue;
            }
            data.put(split2[0], split2[1]);
        }
        return data;
    }

}
