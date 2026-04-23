package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;
import net.minecraft.network.chat.Component;

import java.math.BigInteger;

public class PeekConfig {

    public final ConfigEntry<Boolean> showEmptyContainers;

    public final ConfigEntry<Boolean> peekShulkerBoxes;

    public final ConfigEntry<Boolean> peekEnderChests;

    public final ConfigEntry<Boolean> showShulkerBoxItemHint;
    public final ConfigEntry<Boolean> showShulkerBoxBlockHint;
    public final ConfigEntry<Boolean> showShulkerBoxLabels;
    public final ConfigEntry<Boolean> useShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> useShulkerBoxItemNames;
    public final ConfigEntry<ShulkerItemDisplayType> shulkerBoxItemDisplayType;
    public final ConfigEntry<Boolean> hideShulkerBoxDataStrings;

    public final ConfigEntry<Boolean> showDecoratedPotHint;

    public final ConfigEntry<Boolean> showHud;
    public final ConfigEntry<String> hudBackgroundColor;
    public final ConfigEntry<String> hudTextColor;

    public final int hudBackgroundColorValue;
    public final int hudTextColorValue;

    public PeekConfig(ConfigBuilder builder) {
        showEmptyContainers = builder.booleanEntry(
                "show_empty_containers",
                false,
                "If empty containers should show the item slots in the tooltip"
        );

        peekShulkerBoxes = builder.booleanEntry(
                "peek_shulker_boxes",
                true,
                "Displays the items inside shulker boxes"
        );

        peekEnderChests = builder.booleanEntry(
                "peek_ender_chests",
                false,
                "Displays the cached contents of ender chests"
        );

        showShulkerBoxItemHint = builder.booleanEntry(
                "show_shulker_box_item_hint",
                true,
                "If this is enabled, the mod will show additional information about the items in shulker boxes when in item form"
        );
        showShulkerBoxBlockHint = builder.booleanEntry(
                "show_shulker_box_block_hint",
                true,
                "If this is enabled, the mod will show additional information about the shulker box block on the lid of the shulker box"
        );
        showShulkerBoxLabels = builder.booleanEntry(
                "show_shulker_box_labels",
                true,
                "If this is enabled, the mod will show the custom name of the shulker box on the lid of the shulker box"
        );
        useShulkerBoxDataStrings = builder.booleanEntry(
                "use_shulker_box_data_strings",
                false,
                "If this is enabled, the mod will use the data strings to determine what to show on the shulker box lid",
                "Note that this setting is experimental and subject to change"
        );
        useShulkerBoxItemNames = builder.booleanEntry(
                "use_shulker_box_item_names",
                false,
                "If this is enabled, the mod will use item names to determine what to show on the shulker box lid",
                "Note that this setting is experimental and subject to change"
        );
        shulkerBoxItemDisplayType = builder.enumEntry(
                "shulker_box_item_display_type",
                ShulkerItemDisplayType.SINGLE_TYPE,
                "How the mod should determine which item to show on the shulker box lid",
                "This gets overridden if you are using data strings or item names",
                "Possible values:",
                "NONE: Don't show any item",
                "SINGLE_TYPE: If the shulker box only contains one type of item, show that item",
                "BULK: Show the item thats most common in the shulker box",
                "FIRST_ITEM: Show the first item in the shulker box"
        );
        hideShulkerBoxDataStrings = builder.booleanEntry(
                "hide_shulker_box_data_strings",
                true,
                "If this is enabled, the mod will hide the data strings on tooltips and GUIs"
        );
        showDecoratedPotHint = builder.booleanEntry(
                "show_decorated_pot_hint",
                false,
                "If this is enabled, decorated pots will show the contained item and amount",
                "If you are playing on a player, you need to have the mod installed on the server with the config option 'send_decorated_pot_data_to_client' enabled",
                "If you are on singleplayer, the config option 'send_decorated_pot_data_to_client' must be enabled"
        );
        
        showHud = builder.booleanEntry(
                "show_hud",
                false,
                "If the HUD should be shown",
                "NOTE: This option is experimental"
        );
        hudBackgroundColor = builder.stringEntry(
                "hud_background_color",
                "AA000000",
                "The background color of the HUD in ARGB hex"
        );
        hudTextColor = builder.stringEntry(
                "hud_text_color",
                "FFFFFFFF",
                "The color of the HUD text in ARGB hex"
        );

        hudBackgroundColorValue = new BigInteger(hudBackgroundColor.get(), 16).intValue();
        hudTextColorValue = new BigInteger(hudTextColor.get(), 16).intValue();
    }

    public static enum ShulkerItemDisplayType {
        NONE("none"),
        SINGLE_TYPE("single_type"),
        BULK("bulk"),
        FIRST_ITEM("first_item");

        private final Component name;
        private final Component description;

        ShulkerItemDisplayType(String name) {
            String key = "cloth_config.peek.shulker_box_item_display_type.%s".formatted(name);
            this.name = Component.translatable(key);
            this.description = Component.translatable("%s.description".formatted(key));
        }

        public Component getName() {
            return name;
        }

        public Component getDescription() {
            return description;
        }
    }

}
