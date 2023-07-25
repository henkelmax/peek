package de.maxhenkel.peek;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.peek.config.PeekConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class Peek implements ClientModInitializer {

    public static final String MODID = "peek";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static PeekConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CONFIG = ConfigBuilder.build(FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("peek.properties"), PeekConfig::new);
    }
}
