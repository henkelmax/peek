package de.maxhenkel.peek.integration.bbe;

import net.fabricmc.loader.api.FabricLoader;

public class BBEIntegration {

    private static final boolean LOADED = FabricLoader.getInstance().isModLoaded("betterblockentities");

    public static boolean isLoaded() {
        return LOADED;
    }

}
