package de.maxhenkel.peek.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.maxhenkel.peek.Peek;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.SulfurCubeArchetype;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.SulfurCubeContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

@Mixin(SulfurCubeContent.class)
public class SulfurCubeContentMixin {

    @Shadow
    @Final
    private ItemStackTemplate absorbedBlockItemStack;

    @Unique
    private static final DecimalFormat ATTRIBUTE_DECIMAL_FORMAT = new DecimalFormat("#.##");

    @WrapOperation(method = "addToTooltip", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    public void addToTooltip(Consumer<Component> instance, Object component, Operation<Void> original, @Local(argsOnly = true, name = "context") Item.TooltipContext context, @Local(argsOnly = true, name = "flag") TooltipFlag flag, @Local(name = "currentStack") ItemStack currentStack) {
        if (!Peek.CONFIG.peekSulfurCubeArchetype.get()) {
            original.call(instance, component);
            return;
        }
        HolderLookup.Provider registries = context.registries();
        if (registries == null) {
            original.call(instance, component);
            return;
        }
        HolderLookup.RegistryLookup<SulfurCubeArchetype> archetypeRegistryLookup = registries.lookupOrThrow(Registries.SULFUR_CUBE_ARCHETYPE);
        Holder.Reference<SulfurCubeArchetype> archetype = archetypeRegistryLookup.filterElements(a -> absorbedBlockItemStack.is(a.items())).listElements().findAny().orElse(null);

        Component archetypeComponent;
        if (archetype == null) {
            archetypeComponent = Component.translatable("tooltip.peek.sulfur_cube_archetype.none");
        } else {
            Identifier identifier = archetype.key().identifier();
            String translationKey = "tooltip.peek.sulfur_cube_archetype.%s.%s".formatted(identifier.getNamespace(), identifier.getPath());
            if (Language.getInstance().has(translationKey)) {
                archetypeComponent = Component.translatable(translationKey);
            } else {
                archetypeComponent = Component.literal(identifier.toString()).withStyle(ChatFormatting.DARK_GRAY);
            }
        }
        instance.accept(
                Component.translatable("entity.minecraft.sulfur_cube.content", currentStack.getHoverName())
                        .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC)
                        .append(" ")
                        .append(Component.translatable("tooltip.peek.sulfur_cube_archetype", archetypeComponent))
        );

        if (archetype != null && flag.isAdvanced()) {
            SulfurCubeArchetype a = archetype.value();
            instance.accept(Component.empty());
            if (a.buoyant()) {
                instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.buoyant").withStyle(ChatFormatting.GRAY));
            }
            a.contactDamage().ifPresent(contactDamage -> {
                float min = contactDamage.amount().min();
                float max = contactDamage.amount().max();
                if (min != max) {
                    instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.contact_damage_range", ATTRIBUTE_DECIMAL_FORMAT.format(min), ATTRIBUTE_DECIMAL_FORMAT.format(max)).withStyle(ChatFormatting.GRAY));
                } else {
                    instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.contact_damage", ATTRIBUTE_DECIMAL_FORMAT.format(min)).withStyle(ChatFormatting.GRAY));
                }
            });
            a.explosion().ifPresent(explosion -> {
                if (explosion.causesFire()) {
                    instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.explosion_fire", explosion.power(), explosion.fuse()).withStyle(ChatFormatting.GRAY));
                } else {
                    instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.explosion", explosion.power(), explosion.fuse()).withStyle(ChatFormatting.GRAY));
                }
            });

            SulfurCubeArchetype.KnockbackModifiers km = a.knockbackModifiers();
            instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.knockback_modifiers", ATTRIBUTE_DECIMAL_FORMAT.format(km.horizontalPower()), ATTRIBUTE_DECIMAL_FORMAT.format(km.verticalPower())).withStyle(ChatFormatting.GRAY));
            instance.accept(Component.empty());

            List<SulfurCubeArchetype.AttributeEntry> attributeEntries = a.attributeModifiers();
            if (!attributeEntries.isEmpty()) {
                instance.accept(Component.translatable("tooltip.peek.sulfur_cube_archetype_advanced.attributes").append(":").withStyle(ChatFormatting.GRAY));
            }
            attributeEntries.forEach(attribute -> {
                instance.accept(
                        Component.literal("  ")
                                .append(attribute.attribute().getRegisteredName())
                                .append(": ")
                                .append(ATTRIBUTE_DECIMAL_FORMAT.format(attribute.modifier().amount()))
                                .withStyle(ChatFormatting.GRAY)
                );
            });

            if (!attributeEntries.isEmpty()) {
                instance.accept(Component.empty());
            }
        }
    }

}
