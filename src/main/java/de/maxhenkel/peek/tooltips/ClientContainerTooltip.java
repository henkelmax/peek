package de.maxhenkel.peek.tooltips;

import de.maxhenkel.peek.Peek;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ClientContainerTooltip implements ClientTooltipComponent {

    public static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(Peek.MODID, "textures/gui/container/slot.png");
    private static final int MARGIN_Y = 4;
    private static final int BORDER_WIDTH = 1;
    private static final int TEXTURE_SIZE = 32;
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
    public int getHeight(Font font) {
        return gridHeight * SLOT_SIZE_Y + 2 * BORDER_WIDTH + MARGIN_Y;
    }

    @Override
    public int getWidth(Font font) {
        return gridWidth * SLOT_SIZE_X + 2;
    }

    @Override
    public void renderImage(Font font, int tooltipX, int tooltipY, int i1, int i2, GuiGraphics guiGraphics) {
        int slotId = 0;
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                int posX = tooltipX + x * SLOT_SIZE_X + BORDER_WIDTH;
                int posY = tooltipY + y * SLOT_SIZE_Y + BORDER_WIDTH;
                renderSlot(posX, posY, slotId++, font, guiGraphics);
            }
        }
        drawBorder(tooltipX, tooltipY, guiGraphics);
    }

    private void renderSlot(int posX, int posY, int slotId, Font font, GuiGraphics guiGraphics) {
        ItemStack itemStack = ItemStack.EMPTY;
        if (slotId < items.size()) {
            itemStack = items.get(slotId);
        }

        blit(guiGraphics, posX, posY, Texture.SLOT);
        guiGraphics.renderItem(itemStack, posX + BORDER_WIDTH, posY + BORDER_WIDTH, slotId);
        guiGraphics.renderItemDecorations(font, itemStack, posX + BORDER_WIDTH, posY + BORDER_WIDTH);
    }

    private void drawBorder(int x, int y, GuiGraphics guiGraphics) {
        blit(guiGraphics, x, y, Texture.BORDER_CORNER);
        blit(guiGraphics, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y, Texture.BORDER_CORNER);

        for (int i = 0; i < gridWidth; i++) {
            blit(guiGraphics, x + BORDER_WIDTH + i * SLOT_SIZE_X, y, Texture.BORDER_HORIZONTAL);
            blit(guiGraphics, x + BORDER_WIDTH + i * SLOT_SIZE_X, y + gridHeight * SLOT_SIZE_Y + 1, Texture.BORDER_HORIZONTAL);
        }

        for (int i = 0; i < gridHeight; i++) {
            blit(guiGraphics, x, y + i * SLOT_SIZE_Y + BORDER_WIDTH, Texture.BORDER_VERTICAL);
            blit(guiGraphics, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y + i * SLOT_SIZE_Y + BORDER_WIDTH, Texture.BORDER_VERTICAL);
        }

        blit(guiGraphics, x, y + gridHeight * SLOT_SIZE_Y + 1, Texture.BORDER_CORNER);
        blit(guiGraphics, x + gridWidth * SLOT_SIZE_X + BORDER_WIDTH, y + gridHeight * SLOT_SIZE_Y + 1, Texture.BORDER_CORNER);
    }

    private void blit(GuiGraphics guiGraphics, int i, int j, Texture texture) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE_LOCATION, i, j, (float) texture.x, (float) texture.y, texture.w, texture.h, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    enum Texture {
        SLOT(0, 0, SLOT_SIZE_X, SLOT_SIZE_Y),
        BORDER_HORIZONTAL(0, SLOT_SIZE_Y, SLOT_SIZE_X, BORDER_WIDTH),
        BORDER_VERTICAL(SLOT_SIZE_X, 0, BORDER_WIDTH, SLOT_SIZE_Y),
        BORDER_CORNER(SLOT_SIZE_X, SLOT_SIZE_Y, BORDER_WIDTH, BORDER_WIDTH);

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
