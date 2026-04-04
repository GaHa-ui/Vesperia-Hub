package com.vesperia.hub.client.effects;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EffectRenderer {
    private final List<JumpCircle> jumpCircles = new ArrayList<>();
    private final List<TrailPoint> trailPoints = new ArrayList<>();
    private float screenShakeX = 0, screenShakeY = 0;
    private int screenShakeTicks = 0;
    private final List<KillEffect> killEffects = new ArrayList<>();

    public void spawnJumpCircle(Vec3d pos) {
        if (!VesperiaConfig.JUMP_CIRCLE) return;
        jumpCircles.add(new JumpCircle(pos, VesperiaConfig.JUMP_CIRCLE_DURATION));
    }

    public void addTrailPoint(Vec3d pos, int color) {
        if (!VesperiaConfig.PROJECTILE_TRAILS) return;
        trailPoints.add(new TrailPoint(pos, color, 30));
    }

    public void triggerScreenShake() {
        if (!VesperiaConfig.SCREEN_SHAKE) return;
        screenShakeX = (float)(Math.random() - 0.5) * 10;
        screenShakeY = (float)(Math.random() - 0.5) * 10;
        screenShakeTicks = VesperiaConfig.SCREEN_SHAKE_DURATION;
    }

    public void tickScreenShake() {
        if (screenShakeTicks > 0) {
            screenShakeTicks--;
            screenShakeX *= 0.8f;
            screenShakeY *= 0.8f;
        }
    }

    public void triggerKillEffect() {
        if (!VesperiaConfig.KILL_EFFECT) return;
        killEffects.add(new KillEffect(60));
    }

    public void tickKillEffects() {
        Iterator<KillEffect> it = killEffects.iterator();
        while (it.hasNext()) {
            KillEffect e = it.next();
            e.tick();
            if (e.isDead()) it.remove();
        }
    }

    public void tick() {
        Iterator<JumpCircle> jc = jumpCircles.iterator();
        while (jc.hasNext()) {
            JumpCircle j = jc.next();
            j.tick();
            if (j.isDead()) jc.remove();
        }

        Iterator<TrailPoint> tp = trailPoints.iterator();
        while (tp.hasNext()) {
            TrailPoint t = tp.next();
            t.tick();
            if (t.isDead()) tp.remove();
        }
    }

    public float getScreenShakeX() { return screenShakeX; }
    public float getScreenShakeY() { return screenShakeY; }
    public List<JumpCircle> getJumpCircles() { return jumpCircles; }
    public List<TrailPoint> getTrailPoints() { return trailPoints; }
}

class JumpCircle {
    private final Vec3d position;
    private int lifetime;
    private final int maxLifetime;

    public JumpCircle(Vec3d pos, int life) {
        this.position = pos;
        this.maxLifetime = life;
        this.lifetime = life;
    }

    public void tick() { lifetime--; }
    public boolean isDead() { return lifetime <= 0; }
    public Vec3d getPosition() { return position; }
    public float getProgress() { return 1.0f - (float)lifetime / maxLifetime; }
    public float getAlpha() { return (float)lifetime / maxLifetime; }
}

class TrailPoint {
    private final Vec3d position;
    private final int color;
    private int lifetime;
    private final int maxLifetime;

    public TrailPoint(Vec3d pos, int color, int life) {
        this.position = pos;
        this.color = color;
        this.maxLifetime = life;
        this.lifetime = life;
    }

    public void tick() { lifetime--; }
    public boolean isDead() { return lifetime <= 0; }
    public Vec3d getPosition() { return position; }
    public int getColor() { return color; }
    public float getAlpha() { return (float)lifetime / maxLifetime; }
}

class KillEffect {
    private int lifetime;
    private final int maxLifetime;

    public KillEffect(int life) {
        this.maxLifetime = life;
        this.lifetime = life;
    }

    public void tick() { lifetime--; }
    public boolean isDead() { return lifetime <= 0; }
    public float getAlpha() { return (float)lifetime / maxLifetime; }
}
