package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.ConfigEntry;

public class ClientConfig {

    public final ConfigEntry<Boolean> peekShulkerBoxes;
    public final ConfigEntry<Boolean> peekBeehives;

    public ClientConfig(ConfigBuilder builder) {
        peekShulkerBoxes = builder.booleanEntry("peek_shulker_boxes", true);
        peekBeehives = builder.booleanEntry("peek_beehives", true);
    }

}
