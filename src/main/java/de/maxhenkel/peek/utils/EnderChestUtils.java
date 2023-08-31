package de.maxhenkel.peek.utils;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;

public class EnderChestUtils {
    public static boolean isScreenEnderChest(Screen screen) {
        return screen instanceof ContainerScreen && screen.getTitle().equals(Component.translatable("container.enderchest"));
    }
}
