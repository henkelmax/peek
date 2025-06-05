package de.maxhenkel.peek;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.peek.config.PeekConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Peek implements ModInitializer {

    public static final String MODID = "peek";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static PeekConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = ConfigBuilder.builder(PeekConfig::new).path(FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("peek.properties")).build();
    }
}
