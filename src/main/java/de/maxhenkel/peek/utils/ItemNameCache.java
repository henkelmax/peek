package de.maxhenkel.peek.utils;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemNameCache {

    private static Map<String, Item> translatedItemNamesCache = new HashMap<>();
    private static Map<String, Item> translatedItemIdNamesCache = new HashMap<>();

    public static void loadTranslatedNames(ClientLevel clientLevel) {
        translatedItemNamesCache.clear();
        Registry<Item> itemRegistry = clientLevel.registryAccess().lookupOrThrow(Registries.ITEM);
        for (Item item : itemRegistry) {
            Component name = Component.translatable(item.getDescriptionId());
            translatedItemNamesCache.put(name.getString().toLowerCase(), item);
        }
    }

    public static void loadIdNames(ClientLevel clientLevel) {
        translatedItemIdNamesCache.clear();
        Registry<Item> itemRegistry = clientLevel.registryAccess().lookupOrThrow(Registries.ITEM);
        for (Map.Entry<ResourceKey<Item>, Item> item : itemRegistry.entrySet()) {
            ResourceLocation location = item.getKey().location();
            if (location.getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)) {
                translatedItemIdNamesCache.put(getResourceLocationName(location.getPath()), item.getValue());
            } else {
                translatedItemIdNamesCache.put("%s %s".formatted(getResourceLocationName(location.getNamespace()), getResourceLocationName(location.getPath())), item.getValue());
            }
        }
    }

    private static String getResourceLocationName(String str) {
        return str.replace("_", " ").replace("/", " ").toLowerCase().trim();
    }

    @Nullable
    public static Item byName(String translatedName) {
        return translatedItemNamesCache.get(translatedName.toLowerCase().trim());
    }

    @Nullable
    public static Item byIdName(String translatedName) {
        return translatedItemIdNamesCache.get(translatedName.toLowerCase().trim());
    }

}
