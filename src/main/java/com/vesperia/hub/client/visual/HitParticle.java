package com.vesperia.hub.client.visual;

import net.minecraft.util.math.Vec3d;

public class HitParticle {
    private final Vec3d position;
    private final Vec3d velocity;
    private final int color;
    private final int maxLifetime;
    private int currentLifetime;
    private final float scale;

    public HitParticle(Vec3d position, Vec3d velocity, int color, int lifetime, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.color = color;
        this.maxLifetime = lifetime;
        this.currentLifetime = lifetime;
        this.scale = scale;
    }

    public void update() {
        position.add(velocity);
        velocity.multiply(0.95);
        velocity.y -= 0.02;
        currentLifetime--;
    }

    public boolean isDead() {
        return currentLifetime <= 0;
    }

    public float getAlpha() {
        return (float) currentLifetime / maxLifetime;
    }

    public Vec3d getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public float getScale() {
        return scale;
    }
}
