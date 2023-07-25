package de.maxhenkel.peek;

import de.maxhenkel.peek.interfaces.PeekPackRepository;
import de.maxhenkel.peek.resourcepacks.PeekResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class PeekClient implements ClientModInitializer {

    public static PeekResourcePack ORTHOGONAL_SHULKER_ICONS;

    @Override
    public void onInitializeClient() {
        ORTHOGONAL_SHULKER_ICONS = new PeekResourcePack("flat_shulker_icons");
        PeekPackRepository repository = (PeekPackRepository) Minecraft.getInstance().getResourcePackRepository();
        repository.peek$addSource((consumer) -> consumer.accept(ORTHOGONAL_SHULKER_ICONS.toPack(Component.translatable("resourcepack.peek.flat_shulker_icons"))));
    }

}
