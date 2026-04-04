package com.vesperia.hub.client.effects;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class ViewModelManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private float itemOffsetX = 0;
    private float itemOffsetY = 0;
    private float itemOffsetZ = 0;
    private float swingProgress = 0;
    private boolean isSwinging = false;

    public void onSwing() {
        if (!VesperiaConfig.SWING_ANIMATION) return;
        isSwinging = true;
        swingProgress = 0;
    }

    public void tick() {
        if (isSwinging && swingProgress < 1.0f) {
            swingProgress += 0.15f;
            if (swingProgress >= 1.0f) {
                swingProgress = 0;
                isSwinging = false;
            }
        }
    }

    public float getItemOffsetX() {
        return VesperiaConfig.VIEWMODEL ? itemOffsetX * VesperiaConfig.VIEWMODEL_SCALE : 0;
    }

    public float getItemOffsetY() {
        return VesperiaConfig.VIEWMODEL ? itemOffsetY * VesperiaConfig.VIEWMODEL_SCALE : 0;
    }

    public float getItemOffsetZ() {
        return VesperiaConfig.VIEWMODEL ? itemOffsetZ * VesperiaConfig.VIEWMODEL_SCALE : 0;
    }

    public float getSwingProgress() {
        return VesperiaConfig.SWING_ANIMATION ? swingProgress : 0;
    }

    public void setOffset(float x, float y, float z) {
        this.itemOffsetX = x;
        this.itemOffsetY = y;
        this.itemOffsetZ = z;
    }
}
