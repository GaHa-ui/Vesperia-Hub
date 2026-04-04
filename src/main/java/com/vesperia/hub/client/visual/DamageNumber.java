package com.vesperia.hub.client.visual;

import net.minecraft.util.math.Vec3d;

public class DamageNumber {
    private final Vec3d position;
    private final String text;
    private final int color;
    private final boolean isCritical;
    private final boolean isHeal;
    private final int maxLifetime;
    private int currentLifetime;
    private final float scale;
    private Vec3d velocity;

    public DamageNumber(Vec3d position, String text, int color, boolean isCritical, boolean isHeal, int lifetime, float scale) {
        this.position = position;
        this.text = text;
        this.color = color;
        this.isCritical = isCritical;
        this.isHeal = isHeal;
        this.maxLifetime = lifetime;
        this.currentLifetime = lifetime;
        this.scale = scale;
        this.velocity = new Vec3d(0, 0.05, 0);
    }

    public void update() {
        position.add(velocity);
        velocity = new Vec3d(velocity.x * 0.98, velocity.y, velocity.z * 0.98);
        currentLifetime--;
    }

    public boolean isDead() {
        return currentLifetime <= 0;
    }

    public float getAlpha() {
        return Math.min(1.0f, (float) currentLifetime / 20);
    }

    public Vec3d getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public boolean isHeal() {
        return isHeal;
    }

    public float getScale() {
        return scale * (isCritical ? 1.3f : 1.0f);
    }
}
