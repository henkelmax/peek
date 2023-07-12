package de.maxhenkel.peek.utils;

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

    private static final Pattern DATA_PATTERN = Pattern.compile("\\{([^{}]+)}");

    public static ShulkerHintData fromShulkerBox(NonNullList<ItemStack> contents, @Nullable Component name) {
        ShulkerHintData data = ShulkerHintData.fromDataString(ShulkerBoxUtils.getStringFromComponent(name));
        if (data != null) {
            return data;
        }
        return fromContents(contents, name);
    }

    public static ShulkerHintData fromContents(NonNullList<ItemStack> contents, @Nullable Component name) {
        ShulkerHintData data = new ShulkerHintData();
        data.setDisplayItem(ShulkerBoxUtils.getBulkItem(contents));
        data.setLabel(name);
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
        if (map.containsKey("label")) {
            data.setLabel(Component.literal(map.get("label")));
        }
        if (map.containsKey("item")) {
            Item item = ShulkerBoxUtils.byId(map.get("item"));
            if (item != null) {
                data.setDisplayItem(item);
            }
        }
        return data;
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
