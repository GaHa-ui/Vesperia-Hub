package com.vesperia.hub.mixin;

import com.vesperia.hub.VesperiaHubClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRenderEnd(float tickDelta, long limitTime, boolean renderBlockOutline, net.minecraft.client.render.RenderTickCounter tickCounter, net.minecraft.client.render.Camera camera, net.minecraft.client.option.GameOptions gameOptions, net.minecraft.client.render.WorldRendererAccessor worldRenderer, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world == null) return;

        VesperiaHubClient mod = VesperiaHubClient.getInstance();
        if (mod == null) return;

        if (mod.getDamageNumberRenderer() != null) {
            mod.getDamageNumberRenderer().render(new net.minecraft.client.util.math.MatrixStack());
        }
    }
}
