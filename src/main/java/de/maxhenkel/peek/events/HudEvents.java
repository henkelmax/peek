package de.maxhenkel.peek.events;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.peek.Peek;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.HashMap;
import java.util.Map;

public class HudEvents {

    private static final Minecraft MC = Minecraft.getInstance();
    private static final int PADDING = 4;

    public static void onRenderHud(PoseStack poseStack, int screenWidth, int screenHeight) {
        if (!Peek.CONFIG.showHud.get()) {
            return;
        }
        LocalPlayer player = MC.player;
        if (player == null) {
            return;
        }
        if (MC.hitResult instanceof BlockHitResult blockHitResult) {
            renderBlockHud(player, poseStack, screenWidth, screenHeight, blockHitResult);
        } else if (MC.hitResult instanceof EntityHitResult entityHitResult) {
            renderEntityHud(player, poseStack, screenWidth, screenHeight, entityHitResult);
        }
    }

    private static void renderBlockHud(LocalPlayer player, PoseStack poseStack, int screenWidth, int screenHeight, BlockHitResult blockHitResult) {
        Level level = player.getLevel();
        BlockState blockState = level.getBlockState(blockHitResult.getBlockPos());

        if (blockState.isAir()) {
            return;
        }

        Block block = blockState.getBlock();
        ItemStack item = block.getCloneItemStack(level, blockHitResult.getBlockPos(), blockState);

        Component name = item.getHoverName();
        Font font = MC.font;
        int nameWidth = font.width(name);

        int itemSize = 16;
        int hudWidth = nameWidth + PADDING * 3 + itemSize;
        int hudHeight = itemSize + PADDING * 2;
        int hudLeft = screenWidth / 2 - hudWidth / 2;

        Screen.fill(poseStack, hudLeft, 0, hudLeft + hudWidth, hudHeight, Peek.CONFIG.hudBackgroundColorValue);
        MC.getItemRenderer().renderAndDecorateItem(item, hudLeft + PADDING, PADDING);
        font.drawShadow(poseStack, name, hudLeft + itemSize + PADDING * 2, hudHeight / 2 - font.lineHeight / 2, Peek.CONFIG.hudTextColorValue);
    }

    private static void renderEntityHud(LocalPlayer player, PoseStack poseStack, int screenWidth, int screenHeight, EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        Component name = entity.getName();
        Font font = MC.font;
        int nameWidth = font.width(name);

        int maxEntityHeight = 16;
        int maxEntityWidth = 16;

        int hudWidth = nameWidth + PADDING * 3 + maxEntityWidth;
        int hudHeight = maxEntityHeight + PADDING * 2;
        int hudLeft = screenWidth / 2 - hudWidth / 2;

        Screen.fill(poseStack, hudLeft, 0, hudLeft + hudWidth, hudHeight, Peek.CONFIG.hudBackgroundColorValue);
        renderEntity(poseStack, hudLeft + PADDING + maxEntityWidth / 2F, PADDING + maxEntityHeight, maxEntityWidth, maxEntityHeight, getCachedEntity(livingEntity.getType()));
        font.drawShadow(poseStack, name, hudLeft + maxEntityWidth + PADDING * 2, hudHeight / 2 - font.lineHeight / 2, Peek.CONFIG.hudTextColorValue);
    }

    private static void renderEntity(PoseStack poseStack, float x, float y, float maxWidth, float maxHeight, LivingEntity entity) {
        if (entity == null) {
            return;
        }
        float maxXScale = maxWidth / entity.getBbWidth();
        float maxYScale = maxHeight / entity.getBbHeight();

        int entityScale = (int) Math.min(maxXScale, maxYScale);

        float r = (float) (((double) System.currentTimeMillis() / 20D) % 360D);
        //TODO Rotation
        InventoryScreen.renderEntityInInventory((int) x, (int) y, entityScale, 0F, 0F, entity);
    }

    private static final Map<EntityType<?>, LivingEntity> ENTITY_CACHE = new HashMap<>();

    private static LivingEntity getCachedEntity(EntityType<?> entityType) {
        return ENTITY_CACHE.computeIfAbsent(entityType, type -> (LivingEntity) type.create(MC.level));
    }

}
