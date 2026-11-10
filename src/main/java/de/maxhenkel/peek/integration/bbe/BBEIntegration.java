package de.maxhenkel.peek.integration.bbe;

import betterblockentities.data.SupportedBlockEntityTypes;
import betterblockentities.registration.AltRendererRegistration;
import betterblockentities.registration.BBEApiEntryPoint;

public class BBEIntegration implements BBEApiEntryPoint {
    @Override
    public void registerRenderers(AltRendererRegistration context) {
        context.registerRenderer(SupportedBlockEntityTypes.SHULKER_BOX, BBEShulkerBoxRenderer::new);
    }
}
