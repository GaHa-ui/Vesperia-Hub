package com.vesperia.hub.mixin;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class SmoothCameraMixin {

    @Inject(method = "getCameraPitch", at = @At("RETURN"), cancellable = true)
    private void onGetPitch(CallbackInfoReturnable<Float> cir) {
        if (VesperiaConfig.SMOOTH_CAMERA) {
        }
    }

    @Inject(method = "getCameraYaw", at = @At("RETURN"), cancellable = true)
    private void onGetYaw(CallbackInfoReturnable<Float> cir) {
        if (VesperiaConfig.SMOOTH_CAMERA) {
        }
    }
}
