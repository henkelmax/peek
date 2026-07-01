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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.SulfurCubeArchetype;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.SulfurCubeContent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SulfurCubeContent.class)
public class SulfurCubeContentMixin {

    @Shadow
    @Final
    private ItemStackTemplate absorbedBlockItemStack;

    @WrapOperation(method = "addToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"))
    public MutableComponent wrapOperation(String key, Object[] args, Operation<MutableComponent> original, @Local(argsOnly = true, name = "context") Item.TooltipContext context) {
        if (!Peek.CONFIG.peekSulfurCubeArchetype.get()) {
            return original.call(key, args);
        }
        HolderLookup.Provider registries = context.registries();
        if (registries == null) {
            return original.call(key, args);
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
        return original.call(key, args).append(" ").append(Component.translatable("tooltip.peek.sulfur_cube_archetype", archetypeComponent));
    }

}
