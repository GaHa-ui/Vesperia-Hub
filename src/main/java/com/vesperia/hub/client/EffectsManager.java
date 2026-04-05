package com.vesperia.hub.client;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class EffectsManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final List<DamageNumberEntry> damageNumbers = new ArrayList<>();
    private final List<HitParticleEntry> hitParticles = new ArrayList<>();
    private final List<JumpCircleEntry> jumpCircles = new ArrayList<>();
    private float screenShake = 0;
    private int combo = 0;
    private long lastHitTime = 0;

    public void onHit(Vec3d pos, float damage, boolean critical) {
        if (!VesperiaConfig.HIT_EFFECTS) return;
        
        damageNumbers.add(new DamageNumberEntry(pos, damage, critical, 60));
        
        for (int i = 0; i < 8; i++) {
            double spread = 0.3;
            Vec3d vel = new Vec3d(
                (Math.random() - 0.5) * spread,
                Math.random() * 0.4,
                (Math.random() - 0.5) * spread
            );
            hitParticles.add(new HitParticleEntry(
                pos,
                vel,
                critical ? 0xFFFF4444 : 0xFFFFD700,
                25
            ));
        }
        
        if (VesperiaConfig.SCREEN_SHAKE) {
            screenShake = 5.0f;
        }
        
        combo++;
        lastHitTime = System.currentTimeMillis();
    }

    public void onJump(Vec3d pos) {
        if (!VesperiaConfig.JUMP_CIRCLE) return;
        jumpCircles.add(new JumpCircleEntry(pos, 20));
    }

    public void tick() {
        long now = System.currentTimeMillis();
        if (now - lastHitTime > 1500) combo = 0;
        
        if (screenShake > 0) screenShake *= 0.9f;
        
        Iterator<DamageNumberEntry> dn = damageNumbers.iterator();
        while (dn.hasNext()) {
            DamageNumberEntry e = dn.next();
            e.pos = e.pos.add(0, 0.05, 0);
            e.life--;
            if (e.life <= 0) dn.remove();
        }
        
        Iterator<HitParticleEntry> hp = hitParticles.iterator();
        while (hp.hasNext()) {
            HitParticleEntry e = hp.next();
            e.pos = e.pos.add(e.vel);
            e.vel = e.vel.multiply(0.95);
            e.vel = new Vec3d(e.vel.x, e.vel.y - 0.02, e.vel.z);
            e.life--;
            if (e.life <= 0) hp.remove();
        }
        
        Iterator<JumpCircleEntry> jc = jumpCircles.iterator();
        while (jc.hasNext()) {
            JumpCircleEntry e = jc.next();
            e.life--;
            if (e.life <= 0) jc.remove();
        }
    }

    public List<DamageNumberEntry> getDamageNumbers() { return damageNumbers; }
    public List<HitParticleEntry> getHitParticles() { return hitParticles; }
    public List<JumpCircleEntry> getJumpCircles() { return jumpCircles; }
    public float getScreenShake() { return screenShake; }
    public int getCombo() { return combo; }

    public static class DamageNumberEntry {
        public Vec3d pos;
        public float damage;
        public boolean critical;
        public int life;
        
        public DamageNumberEntry(Vec3d pos, float damage, boolean critical, int life) {
            this.pos = pos;
            this.damage = damage;
            this.critical = critical;
            this.life = life;
        }
    }

    public static class HitParticleEntry {
        public Vec3d pos;
        public Vec3d vel;
        public int color;
        public int life;
        
        public HitParticleEntry(Vec3d pos, Vec3d vel, int color, int life) {
            this.pos = pos;
            this.vel = vel;
            this.color = color;
            this.life = life;
        }
    }

    public static class JumpCircleEntry {
        public Vec3d pos;
        public int life;
        public int maxLife;
        
        public JumpCircleEntry(Vec3d pos, int life) {
            this.pos = pos;
            this.maxLife = life;
            this.life = life;
        }
    }
}
