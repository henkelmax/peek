package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;
import net.minecraft.network.chat.Component;

import java.math.BigInteger;

public class PeekConfig {

    public final ConfigEntry<Boolean> showEmptyContainers;

    public final ConfigEntry<Boolean> peekShulkerBoxes;
    public final ConfigEntry<Boolean> peekChests;
    public final ConfigEntry<Boolean> peekBarrels;
    public final ConfigEntry<Boolean> peekDispensers;
    public final ConfigEntry<Boolean> peekHoppers;
    public final ConfigEntry<Boolean> peekBeehives;
    public final ConfigEntry<Boolean> peekExplorationMaps;
    public final ConfigEntry<Boolean> peekCompasses;
    public final ConfigEntry<Boolean> peekRecoveryCompasses;
    public final ConfigEntry<Boolean> peekSuspiciousStews;
    public final ConfigEntry<Boolean> peekEnderChests;

    public final ConfigEntry<Boolean> showShulkerBoxItemHint;
    public final ConfigEntry<Boolean> showShulkerBoxBlockHint;
    public final ConfigEntry<Boolean> showShulkerBoxLabels;
    public final ConfigEntry<Boolean> useShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> useShulkerBoxItemNames;
    public final ConfigEntry<ShulkerItemDisplayType> shulkerBoxItemDisplayType;
    public final ConfigEntry<Boolean> hideShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> sendShulkerBoxDataToClient;

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
        peekChests = builder.booleanEntry(
                "peek_chests",
                true,
                "Displays the items inside pick-blocked chests and trapped chests"
        );
        peekBarrels = builder.booleanEntry(
                "peek_barrels",
                true,
                "Displays the items inside pick-blocked barrels"
        );
        peekDispensers = builder.booleanEntry(
                "peek_dispensers",
                true,
                "Displays the items inside pick-blocked dispensers and droppers"
        );
        peekHoppers = builder.booleanEntry(
                "peek_hoppers",
                true,
                "Displays the items inside pick-blocked hoppers"
        );
        peekBeehives = builder.booleanEntry(
                "peek_beehives",
                true,
                "Displays the amount of bees and the honey level"
        );
        peekExplorationMaps = builder.booleanEntry(
                "peek_exploration_maps",
                true,
                "Displays the marker coordinates of exploration maps"
        );
        peekCompasses = builder.booleanEntry(
                "peek_compasses",
                true,
                "Displays the destinations of compasses"
        );
        peekRecoveryCompasses = builder.booleanEntry(
                "peek_recovery_compasses",
                true,
                "Displays your death location on recovery compasses"
        );
        peekSuspiciousStews = builder.booleanEntry(
                "peek_suspicious_stews",
                true,
                "Displays the effect of suspicious stews"
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
        sendShulkerBoxDataToClient = builder.booleanEntry(
                "send_shulker_box_data_to_client",
                true,
                "If this is enabled, the mod will send the contents of placed down shulker boxes to the client",
                "This allows the mod to display hints on shulker boxes that are placed down without needing to open them first",
                "If you want to use this feature on a server, the server needs the mod installed and this setting enabled"
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
