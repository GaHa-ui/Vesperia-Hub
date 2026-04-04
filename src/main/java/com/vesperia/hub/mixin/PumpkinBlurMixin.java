package com.vesperia.hub.mixin;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class PumpkinBlurMixin {

    @Inject(method = "getBasicFog", at = @At("HEAD"), cancellable = true)
    private void onGetBasicFog(CallbackInfoReturnable<Boolean> cir) {
        if (VesperiaConfig.NO_PUMPKIN_BLUR) {
            cir.setReturnValue(false);
        }
    }
}
