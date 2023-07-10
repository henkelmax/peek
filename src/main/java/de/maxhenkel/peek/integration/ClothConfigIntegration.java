package de.maxhenkel.peek.integration;

import de.maxhenkel.configbuilder.ConfigEntry;
import de.maxhenkel.peek.Peek;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder
                .create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("cloth_config.peek.settings"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("cloth_config.peek.category.general"));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_empty_containers"),
                Component.translatable("cloth_config.peek.show_empty_containers.description"),
                Peek.CLIENT_CONFIG.showEmptyContainers
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_shulker_box_item_hint"),
                Component.translatable("cloth_config.peek.show_shulker_box_item_hint.description"),
                Peek.CLIENT_CONFIG.showShulkerBoxItemHint
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_shulker_boxes"),
                Component.translatable("cloth_config.peek.peek_shulker_boxes.description"),
                Peek.CLIENT_CONFIG.peekShulkerBoxes
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_beehives"),
                Component.translatable("cloth_config.peek.peek_beehives.description"),
                Peek.CLIENT_CONFIG.peekBeehives
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_exploration_maps"),
                Component.translatable("cloth_config.peek.peek_exploration_maps.description"),
                Peek.CLIENT_CONFIG.peekExplorationMaps
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_compasses"),
                Component.translatable("cloth_config.peek.peek_compasses.description"),
                Peek.CLIENT_CONFIG.peekCompasses
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_recovery_compasses"),
                Component.translatable("cloth_config.peek.peek_recovery_compasses.description"),
                Peek.CLIENT_CONFIG.peekRecoveryCompasses
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_suspicious_stews"),
                Component.translatable("cloth_config.peek.peek_suspicious_stews.description"),
                Peek.CLIENT_CONFIG.peekSuspiciousStews
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_chests"),
                Component.translatable("cloth_config.peek.peek_chests.description"),
                Peek.CLIENT_CONFIG.peekChests
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_barrels"),
                Component.translatable("cloth_config.peek.peek_barrels.description"),
                Peek.CLIENT_CONFIG.peekBarrels
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_dispensers"),
                Component.translatable("cloth_config.peek.peek_dispensers.description"),
                Peek.CLIENT_CONFIG.peekDispensers
        ));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_hoppers"),
                Component.translatable("cloth_config.peek.peek_hoppers.description"),
                Peek.CLIENT_CONFIG.peekHoppers
        ));

        return builder.build();
    }

    private static <T> AbstractConfigListEntry<T> fromConfigEntry(ConfigEntryBuilder entryBuilder, Component name, Component description, ConfigEntry<T> entry) {
        if (entry instanceof de.maxhenkel.configbuilder.ConfigBuilder.DoubleConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startDoubleField(name, e.get())
                    .setTooltip(description)
                    .setMin(e.getMin())
                    .setMax(e.getMax())
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof de.maxhenkel.configbuilder.ConfigBuilder.IntegerConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startIntField(name, e.get())
                    .setTooltip(description)
                    .setMin(e.getMin())
                    .setMax(e.getMax())
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof de.maxhenkel.configbuilder.ConfigBuilder.BooleanConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startBooleanToggle(name, e.get())
                    .setTooltip(description)
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof de.maxhenkel.configbuilder.ConfigBuilder.StringConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startStrField(name, e.get())
                    .setTooltip(description)
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        }

        return null;
    }
}
