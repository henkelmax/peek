package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

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
    public final ConfigEntry<Boolean> showShulkerBoxItems;
    public final ConfigEntry<Boolean> showShulkerBoxLabels;
    public final ConfigEntry<Boolean> useShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> useShulkerBoxItemNames;
    public final ConfigEntry<Boolean> hideShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> sendShulkerBoxDataToClient;

    public final ConfigEntry<Boolean> showDecoratedPotHint;
    public final ConfigEntry<Boolean> sendDecoratedPotDataToClient;

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
        showShulkerBoxItems = builder.booleanEntry(
                "show_shulker_box_items",
                true,
                "If this is enabled, the mod will show the item on the shulker box lid if it only contains one type of item"
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
        showDecoratedPotHint = builder.booleanEntry(
                "show_decorated_pot_hint",
                false,
                "If this is enabled, decorated pots will show the contained item and amount",
                "If you are playing on a player, you need to have the mod installed on the server with the config option 'send_decorated_pot_data_to_client' enabled",
                "If you are on singleplayer, the config option 'send_decorated_pot_data_to_client' must be enabled"
        );
        sendDecoratedPotDataToClient = builder.booleanEntry(
                "send_decorated_pot_data_to_client",
                true,
                "If this is enabled, the mod will send the contents of decorated pots to the client",
                "This allows the mod to display hints on decorated pots",
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

}
