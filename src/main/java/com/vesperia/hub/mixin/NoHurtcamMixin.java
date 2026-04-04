package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public class NoHurtcamMixin {

    @Inject(method = "getHurtCameraAngle", at = @At("HEAD"), cancellable = true)
    private void onHurtCamera(CallbackInfoReturnable<Float> cir) {
        if (VesperiaConfig.NO_HURTCAM) {
            cir.setReturnValue(0f);
        }
    }
}
