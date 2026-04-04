package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        VesperiaHubClient mod = VesperiaHubClient.getInstance();
        if (mod == null) return;

        if (VesperiaConfig.TARGET_HUD) {
            mod.getTargetHUDManager().render(context);
        }

        if (VesperiaConfig.COMBO_COUNTER) {
            mod.getComboCounterManager().render(context);
        }

        if (VesperiaConfig.CUSTOM_CROSSHAIR) {
            mod.getCrosshairManager().render(context);
        }

        mod.getHUDManager().render(context);

        mod.getAutoTotemIndicator().render(context);
    }
}
