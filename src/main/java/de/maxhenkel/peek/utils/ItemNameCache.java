package de.maxhenkel.peek.utils;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemNameCache {

    private static Map<String, Item> cache = new HashMap<>();

    public static void load(ClientLevel clientLevel) {
        cache.clear();
        Registry<Item> itemRegistry = clientLevel.registryAccess().registryOrThrow(Registries.ITEM);
        for (Item item : itemRegistry) {
            Component name = Component.translatable(item.getDescriptionId());
            cache.put(name.getString().toLowerCase(), item);
        }
    }

    @Nullable
    public static Item byName(String translatedName) {
        return cache.get(translatedName.toLowerCase().trim());
    }

}
