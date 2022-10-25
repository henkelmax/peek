package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.ConfigEntry;

public class ClientConfig {

    public final ConfigEntry<Boolean> peekShulkerBoxes;
    public final ConfigEntry<Boolean> peekChests;
    public final ConfigEntry<Boolean> peekBarrels;
    public final ConfigEntry<Boolean> peekDispensers;
    public final ConfigEntry<Boolean> peekHoppers;
    public final ConfigEntry<Boolean> peekBeehives;
    public final ConfigEntry<Boolean> peekExplorationMaps;
    public final ConfigEntry<Boolean> peekCompasses;
    public final ConfigEntry<Boolean> peekRecoveryCompasses;

    public ClientConfig(ConfigBuilder builder) {
        peekShulkerBoxes = builder.booleanEntry("peek_shulker_boxes", true);
        peekChests = builder.booleanEntry("peek_chests", true);
        peekBarrels = builder.booleanEntry("peek_barrels", true);
        peekDispensers = builder.booleanEntry("peek_dispensers", true);
        peekHoppers = builder.booleanEntry("peek_hoppers", true);
        peekBeehives = builder.booleanEntry("peek_beehives", true);
        peekExplorationMaps = builder.booleanEntry("peek_exploration_maps", true);
        peekCompasses = builder.booleanEntry("peek_compasses", true);
        peekRecoveryCompasses = builder.booleanEntry("peek_recovery_compasses", true);
    }

}
