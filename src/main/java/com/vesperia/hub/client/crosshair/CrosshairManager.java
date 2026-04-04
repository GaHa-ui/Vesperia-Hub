package com.vesperia.hub.client.crosshair;

import com.vesperia.hub.config.VesperiaConfig;
import com.vesperia.hub.VesperiaHubClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;

public class CrosshairManager {
    private boolean isTargetingEntity = false;

    public void update() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            isTargetingEntity = true;
        } else {
            isTargetingEntity = false;
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.CUSTOM_CROSSHAIR) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        int cx = sw / 2;
        int cy = sh / 2;

        int color = VesperiaConfig.CROSSHAIR_COLOR;
        if (VesperiaConfig.DYNAMIC_CROSSHAIR && isTargetingEntity) {
            color = 0xFFFF5555;
        }

        float size = VesperiaConfig.CROSSHAIR_SIZE;
        int thick = VesperiaConfig.DOT_CROSSHAIR ? (int)(3 * size) : (int)(1 * size);
        int len = (int)(8 * size);

        if (VesperiaConfig.DOT_CROSSHAIR) {
            context.fill(cx - thick/2, cy - thick/2, cx + thick/2, cy + thick/2, color);
        } else {
            context.fill(cx - len, cy - thick/2, cx + len, cy + thick/2, color);
            context.fill(cx - thick/2, cy - len, cx + thick/2, cy + len, color);
        }
    }

    public boolean isTargetingEntity() { return isTargetingEntity; }
}
