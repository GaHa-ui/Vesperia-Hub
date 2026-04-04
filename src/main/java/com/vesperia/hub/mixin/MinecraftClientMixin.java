package com.vesperia.hub.mixin;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "doesEntityHasReducedDebugInfo", at = @At("RETURN"), cancellable = true)
    private void onGetReducedDebugInfo(CallbackInfoReturnable<Boolean> cir) {
        if (VesperiaConfig.CUSTOM_NAMETAGS) {
            cir.setReturnValue(false);
        }
    }
}
