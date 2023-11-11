package de.maxhenkel.peek.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.maxhenkel.peek.Peek;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (isClothConfigLoaded()) {
            return ClothConfigIntegration::createConfigScreen;
        }
        return parent -> null;
    }

    private static boolean isClothConfigLoaded() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                Peek.LOGGER.info("Using Cloth Config GUI");
                return true;
            } catch (Exception e) {
                Peek.LOGGER.warn("Failed to load Cloth Config", e);
            }
        }
        return false;
    }

}