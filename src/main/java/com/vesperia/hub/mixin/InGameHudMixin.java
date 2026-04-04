package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        VesperiaHubClient mod = VesperiaHubClient.getInstance();
        if (mod != null && mod.getTargetHUD() != null) {
            mod.getTargetHUD().render(context);
        }
    }
}
