package de.maxhenkel.peek.integration.bbe;

import betterblockentities.data.SupportedBlockEntityTypes;
import betterblockentities.registration.AltRendererRegistration;

public class BBEApiEntryPoint implements betterblockentities.registration.BBEApiEntryPoint {
    @Override
    public void registerRenderers(AltRendererRegistration context) {
        context.registerRenderer(SupportedBlockEntityTypes.SHULKER_BOX, BBEShulkerBoxRenderer::new);
        context.registerRenderer(SupportedBlockEntityTypes.DECORATED_POT, BBEDecoratedPotRenderer::new);
    }
}
