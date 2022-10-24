package de.maxhenkel.peek.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.ConfigEntry;

public class ClientConfig {

    public final ConfigEntry<Boolean> peekShulkerBoxes;

    public ClientConfig(ConfigBuilder builder) {
        peekShulkerBoxes = builder.booleanEntry("peek_shulker_boxes", true);
    }

}
