package de.maxhenkel.peek.integration;

import de.maxhenkel.configbuilder.entry.*;
import de.maxhenkel.peek.Peek;
import de.maxhenkel.peek.config.PeekConfig;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ClothConfigIntegration {
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder
                .create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("cloth_config.peek.settings"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory peeking = builder.getOrCreateCategory(Component.translatable("cloth_config.peek.category.tooltips"));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_empty_containers"),
                Component.translatable("cloth_config.peek.show_empty_containers.description"),
                Peek.CONFIG.showEmptyContainers
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_shulker_boxes"),
                Component.translatable("cloth_config.peek.peek_shulker_boxes.description"),
                Peek.CONFIG.peekShulkerBoxes
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_exploration_maps"),
                Component.translatable("cloth_config.peek.peek_exploration_maps.description"),
                Peek.CONFIG.peekExplorationMaps
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_compasses"),
                Component.translatable("cloth_config.peek.peek_compasses.description"),
                Peek.CONFIG.peekCompasses
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_recovery_compasses"),
                Component.translatable("cloth_config.peek.peek_recovery_compasses.description"),
                Peek.CONFIG.peekRecoveryCompasses
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_suspicious_stews"),
                Component.translatable("cloth_config.peek.peek_suspicious_stews.description"),
                Peek.CONFIG.peekSuspiciousStews
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_chests"),
                Component.translatable("cloth_config.peek.peek_chests.description"),
                Peek.CONFIG.peekChests
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_barrels"),
                Component.translatable("cloth_config.peek.peek_barrels.description"),
                Peek.CONFIG.peekBarrels
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_dispensers"),
                Component.translatable("cloth_config.peek.peek_dispensers.description"),
                Peek.CONFIG.peekDispensers
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_hoppers"),
                Component.translatable("cloth_config.peek.peek_hoppers.description"),
                Peek.CONFIG.peekHoppers
        ));

        peeking.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.peek_ender_chests"),
                Component.translatable("cloth_config.peek.peek_ender_chests.description"),
                Peek.CONFIG.peekEnderChests
        ));

        ConfigCategory shulkerBoxHints = builder.getOrCreateCategory(Component.translatable("cloth_config.peek.category.shulker_box_hints"));

        shulkerBoxHints.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_shulker_box_item_hint"),
                Component.translatable("cloth_config.peek.show_shulker_box_item_hint.description"),
                Peek.CONFIG.showShulkerBoxItemHint
        ));

        shulkerBoxHints.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_shulker_box_block_hint"),
                Component.translatable("cloth_config.peek.show_shulker_box_block_hint.description"),
                Peek.CONFIG.showShulkerBoxBlockHint
        ));

        shulkerBoxHints.addEntry(entryBuilder
                .startEnumSelector(Component.translatable("cloth_config.peek.shulker_box_item_display_type"), PeekConfig.ShulkerItemDisplayType.class, Peek.CONFIG.shulkerBoxItemDisplayType.get())
                .setTooltip(Component.translatable("cloth_config.peek.shulker_box_item_display_type.description"))
                .setDefaultValue(() -> Peek.CONFIG.shulkerBoxItemDisplayType.getDefault())
                .setSaveConsumer(d -> Peek.CONFIG.shulkerBoxItemDisplayType.set(d).save())
                .setEnumNameProvider(v -> ((PeekConfig.ShulkerItemDisplayType) v).getName())
                .setTooltipSupplier(shulkerItemDisplayType -> Optional.of(new Component[]{
                        Component.translatable("cloth_config.peek.shulker_box_item_display_type.description"),
                        Component.translatable("cloth_config.peek.shulker_box_item_display_type.selected", shulkerItemDisplayType.getName().copy().withStyle(ChatFormatting.GREEN), shulkerItemDisplayType.getDescription().copy().withStyle(ChatFormatting.GREEN)),
                }))
                .build());

        shulkerBoxHints.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_shulker_box_labels"),
                Component.translatable("cloth_config.peek.show_shulker_box_labels.description"),
                Peek.CONFIG.showShulkerBoxLabels
        ));

        ConfigCategory decoratedPotHints = builder.getOrCreateCategory(Component.translatable("cloth_config.peek.category.decorated_pot_hints"));

        decoratedPotHints.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.peek.show_decorated_pot_hint"),
                Component.translatable("cloth_config.peek.show_decorated_pot_hint.description"),
                Peek.CONFIG.showDecoratedPotHint
        ));


        AtomicInteger bundleColumns = new AtomicInteger(Peek.CONFIG.bundleColumns.get());
        IntegerListEntry bundleItemAmountEntry = entryBuilder
                .startIntField(Component.translatable("cloth_config.peek.bundle_item_amount"), Peek.CONFIG.bundleItemCount.get())
                .setTooltip(Component.translatable("cloth_config.peek.bundle_item_amount.description"))
                .setMin(Peek.CONFIG.bundleItemCount.getMin())
                .setMax(Peek.CONFIG.bundleItemCount.getMax())
                .setDefaultValue(Peek.CONFIG.bundleItemCount::getDefault)
                .setSaveConsumer(d -> Peek.CONFIG.bundleItemCount.set(d).save())
                .setErrorSupplier(i -> {
                    if (bundleColumns.get() > i) {
                        return Optional.of(Component.translatable("cloth_config.peek.bundle_item_amount.columns_error"));
                    }
                    return Optional.empty();
                })
                .build();

        IntegerListEntry bundleColumnsEntry = entryBuilder
                .startIntField(Component.translatable("cloth_config.peek.bundle_columns"), Peek.CONFIG.bundleColumns.get())
                .setTooltip(Component.translatable("cloth_config.peek.bundle_columns.description"))
                .setMin(Peek.CONFIG.bundleColumns.getMin())
                .setMax(Peek.CONFIG.bundleColumns.getMax())
                .setDefaultValue(Peek.CONFIG.bundleColumns::getDefault)
                .setSaveConsumer(d -> Peek.CONFIG.bundleColumns.set(d).save())
                .setErrorSupplier(i -> {
                    bundleColumns.set(i);
                    return Optional.empty();
                })
                .build();

        if (PeekConfig.shouldModifyBundles()) {
            ConfigCategory bundles = builder.getOrCreateCategory(Component.translatable("cloth_config.peek.category.bundles"));
            bundles.addEntry(bundleItemAmountEntry);
            bundles.addEntry(bundleColumnsEntry);
        }

        return builder.build();
    }

    private static <T> AbstractConfigListEntry<T> fromConfigEntry(ConfigEntryBuilder entryBuilder, Component name, Component description, ConfigEntry<T> entry) {
        if (entry instanceof DoubleConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startDoubleField(name, e.get())
                    .setTooltip(description)
                    .setMin(e.getMin())
                    .setMax(e.getMax())
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof IntegerConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startIntField(name, e.get())
                    .setTooltip(description)
                    .setMin(e.getMin())
                    .setMax(e.getMax())
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof BooleanConfigEntry e) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startBooleanToggle(name, e.get())
                    .setTooltip(description)
                    .setDefaultValue(e::getDefault)
                    .setSaveConsumer(d -> e.set(d).save())
                    .build();
        } else if (entry instanceof StringConfigEntry e) {
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
