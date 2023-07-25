package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.ConfigEntry;

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

    public final ConfigEntry<Boolean> showShulkerBoxItemHint;
    public final ConfigEntry<Boolean> showShulkerBoxBlockHint;
    public final ConfigEntry<Boolean> useShulkerBoxDataStrings;
    public final ConfigEntry<Boolean> useShulkerBoxItemNames;
    public final ConfigEntry<Boolean> hideShulkerBoxDataStrings;
    public final ConfigEntry<Integer> shulkerBoxItemHintCustomModelData;
    public final ConfigEntry<Boolean> sendShulkerBoxDataToClient;

    public PeekConfig(ConfigBuilder builder) {
        showEmptyContainers = builder.booleanEntry("show_empty_containers", false);

        peekShulkerBoxes = builder.booleanEntry("peek_shulker_boxes", true);
        peekChests = builder.booleanEntry("peek_chests", true);
        peekBarrels = builder.booleanEntry("peek_barrels", true);
        peekDispensers = builder.booleanEntry("peek_dispensers", true);
        peekHoppers = builder.booleanEntry("peek_hoppers", true);
        peekBeehives = builder.booleanEntry("peek_beehives", true);
        peekExplorationMaps = builder.booleanEntry("peek_exploration_maps", true);
        peekCompasses = builder.booleanEntry("peek_compasses", true);
        peekRecoveryCompasses = builder.booleanEntry("peek_recovery_compasses", true);
        peekSuspiciousStews = builder.booleanEntry("peek_suspicious_stews", true);

        showShulkerBoxItemHint = builder.booleanEntry("show_shulker_box_item_hint", true);
        showShulkerBoxBlockHint = builder.booleanEntry("show_shulker_box_block_hint", true);
        useShulkerBoxDataStrings = builder.booleanEntry("use_shulker_box_data_strings", false);
        useShulkerBoxItemNames = builder.booleanEntry("use_shulker_box_item_names", false);
        hideShulkerBoxDataStrings = builder.booleanEntry("hide_shulker_box_data_strings", true);
        shulkerBoxItemHintCustomModelData = builder.integerEntry("shulker_box_item_hint_custom_model_data", 1702, 0, Integer.MAX_VALUE);
        sendShulkerBoxDataToClient = builder.booleanEntry("send_shulker_box_data_to_client", true);
    }

}
