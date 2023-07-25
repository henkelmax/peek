package de.maxhenkel.peek;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.peek.config.PeekConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Peek implements ModInitializer {

    public static final String MODID = "peek";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static PeekConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = ConfigBuilder.build(FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("peek.properties"), true, PeekConfig::new);
    }
}
