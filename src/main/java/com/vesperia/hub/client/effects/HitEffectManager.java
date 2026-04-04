package com.vesperia.hub.client.effects;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class HitEffectManager {
    private final List<HitParticle> particles = new ArrayList<>();
    private final List<HitBubble> bubbles = new ArrayList<>();
    private int screenFlashTicks = 0;
    private int screenFlashColor = 0;

    public void onHit(Vec3d pos, boolean isCritical, boolean isBlock) {
        if (VesperiaConfig.HIT_PARTICLES) {
            spawnHitParticles(pos, isCritical, isBlock);
        }
        if (VesperiaConfig.HIT_COLOR_FLASH) {
            triggerScreenFlash(isCritical);
        }
        if (VesperiaConfig.HIT_BUBBLES) {
            spawnHitBubbles(pos);
        }
    }

    private void spawnHitParticles(Vec3d pos, boolean isCritical, boolean isBlock) {
        int color = isCritical ? VesperiaConfig.HIT_COLOR_CRITICAL :
                    isBlock ? 0xFFAAAAAA : VesperiaConfig.HIT_COLOR_NORMAL;
        int count = isBlock ? 5 : 8;

        for (int i = 0; i < count; i++) {
            double spread = 0.3;
            Vec3d velocity = new Vec3d(
                    (Math.random() - 0.5) * spread,
                    Math.random() * 0.4,
                    (Math.random() - 0.5) * spread
            );
            particles.add(new HitParticle(pos, velocity, color,
                    VesperiaConfig.HIT_PARTICLE_LIFETIME, VesperiaConfig.HIT_PARTICLE_SCALE));
        }
    }

    private void triggerScreenFlash(boolean isCritical) {
        screenFlashColor = isCritical ? VesperiaConfig.HIT_COLOR_CRITICAL : VesperiaConfig.HIT_COLOR_NORMAL;
        screenFlashTicks = isCritical ? 8 : 4;
    }

    private void spawnHitBubbles(Vec3d pos) {
        for (int i = 0; i < 5; i++) {
            double spread = 0.2;
            Vec3d velocity = new Vec3d(
                    (Math.random() - 0.5) * spread,
                    Math.random() * 0.2 + 0.1,
                    (Math.random() - 0.5) * spread
            );
            bubbles.add(new HitBubble(pos, velocity, 15));
        }
    }

    public void tick() {
        Iterator<HitParticle> pit = particles.iterator();
        while (pit.hasNext()) {
            HitParticle p = pit.next();
            p.update();
            if (p.isDead()) pit.remove();
        }

        Iterator<HitBubble> bit = bubbles.iterator();
        while (bit.hasNext()) {
            HitBubble b = bit.next();
            b.update();
            if (b.isDead()) bit.remove();
        }

        if (screenFlashTicks > 0) screenFlashTicks--;
    }

    public List<HitParticle> getParticles() { return particles; }
    public List<HitBubble> getBubbles() { return bubbles; }
    public int getScreenFlashTicks() { return screenFlashTicks; }
    public int getScreenFlashColor() { return screenFlashColor; }
}

class HitParticle {
    private final Vec3d position;
    private Vec3d velocity;
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
        velocity = new Vec3d(velocity.x * 0.95, velocity.y - 0.02, velocity.z * 0.95);
        currentLifetime--;
    }

    public boolean isDead() { return currentLifetime <= 0; }
    public float getAlpha() { return (float) currentLifetime / maxLifetime; }
    public Vec3d getPosition() { return position; }
    public int getColor() { return color; }
    public float getScale() { return scale; }
}

class HitBubble {
    private final Vec3d position;
    private Vec3d velocity;
    private int lifetime;

    public HitBubble(Vec3d pos, Vec3d vel, int life) {
        this.position = pos;
        this.velocity = vel;
        this.lifetime = life;
    }

    public void update() {
        position.add(velocity);
        velocity = new Vec3d(velocity.x * 0.98, velocity.y + 0.01, velocity.z * 0.98);
        lifetime--;
    }

    public boolean isDead() { return lifetime <= 0; }
    public Vec3d getPosition() { return position; }
    public int getLifetime() { return lifetime; }
}
