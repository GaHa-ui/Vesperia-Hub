package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void onGetFov(CallbackInfoReturnable<Float> cir) {
        VesperiaHubClient mod = VesperiaHubClient.getInstance();
        if (mod != null && mod.getQOLManager() != null && mod.getQOLManager().isZooming()) {
            float base = cir.getReturnValue();
            cir.setReturnValue(base * VesperiaConfig.ZOOM_LEVEL);
        }
    }
}
