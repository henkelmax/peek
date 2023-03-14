package de.maxhenkel.peek.tooltips;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ClientContainerTooltip implements ClientTooltipComponent {

    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private static final int MARGIN_Y = 4;
    private static final int BORDER_WIDTH = 1;
    private static final int TEXTURE_SIZE = 128;
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 18;
    private final int gridWidth;
    private final int gridHeight;
    private final NonNullList<ItemStack> items;

    public ClientContainerTooltip(ContainerTooltip tooltip) {
        this.gridWidth = tooltip.getWidth();
        this.gridHeight = tooltip.getHeight();
        this.items = tooltip.getItems();
    }

    @Override
    public int getHeight() {
        return gridHeight * SLOT_SIZE_Y + 2 * BORDER_WIDTH + MARGIN_Y;
    }

    @Override
    public int getWidth(Font font) {
        return gridWidth * SLOT_SIZE_X + 2;
    }

    @Override
    public void renderImage(Font font, int tooltipX, int tooltipY, PoseStack poseStack, ItemRenderer itemRenderer) {
        int layer = 400;

        int slotId = 0;
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                int posX = tooltipX + x * SLOT_SIZE_X + BORDER_WIDTH;
                int posY = tooltipY + y * SLOT_SIZE_Y + BORDER_WIDTH;
                renderSlot(posX, posY, slotId++, font, poseStack, itemRenderer, layer);
            }
        }
        drawBorder(tooltipX, tooltipY, poseStack, layer);
    }

    private void renderSlot(int posX, int posY, int slotId, Font font, PoseStack poseStack, ItemRenderer itemRenderer, int layer) {
        ItemStack itemStack = ItemStack.EMPTY;
        if (slotId < items.size()) {
            itemStack = items.get(slotId);
        }

        blit(poseStack, posX, posY, layer, Texture.SLOT);
        itemRenderer.renderAndDecorateItem(poseStack, itemStack, posX + BORDER_WIDTH, posY + BORDER_WIDTH, slotId);
        itemRenderer.renderGuiItemDecorations(poseStack, font, itemStack, posX + BORDER_WIDTH, posY + BORDER_WIDTH);
    }

    private void drawBorder(int x, int y, PoseStack poseStack, int layer) {
        blit(poseStack, x, y, layer, Texture.BORDER_CORNER);
        blit(poseStack, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y, layer, Texture.BORDER_CORNER);

        for (int i = 0; i < gridWidth; i++) {
            blit(poseStack, x + BORDER_WIDTH + i * SLOT_SIZE_X, y, layer, Texture.BORDER_HORIZONTAL);
            blit(poseStack, x + BORDER_WIDTH + i * SLOT_SIZE_X, y + gridHeight * SLOT_SIZE_Y + 1, layer, Texture.BORDER_HORIZONTAL);
        }

        for (int i = 0; i < gridHeight; i++) {
            blit(poseStack, x, y + i * SLOT_SIZE_Y + BORDER_WIDTH, layer, Texture.BORDER_VERTICAL);
            blit(poseStack, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y + i * SLOT_SIZE_Y + BORDER_WIDTH, layer, Texture.BORDER_VERTICAL);
        }

        blit(poseStack, x, y + gridHeight * SLOT_SIZE_Y + 1, layer, Texture.BORDER_CORNER);
        blit(poseStack, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y + gridHeight * SLOT_SIZE_Y + 1, layer, Texture.BORDER_CORNER);
    }

    private void blit(PoseStack poseStack, int i, int j, int k, Texture texture) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(poseStack, i, j, k, (float) texture.x, (float) texture.y, texture.w, texture.h, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    enum Texture {
        SLOT(0, 0, 18, 18),
        BORDER_VERTICAL(0, 18, 1, 18),
        BORDER_HORIZONTAL(0, 20, 18, 1),
        BORDER_CORNER(0, 20, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        Texture(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
}
