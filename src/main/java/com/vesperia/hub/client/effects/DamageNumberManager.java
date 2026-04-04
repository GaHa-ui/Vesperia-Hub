package com.vesperia.hub.client.effects;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class DamageNumberManager {
    private final List<DamageNumber> numbers = new ArrayList<>();
    private int combo = 0;
    private long lastHitTime = 0;

    public void addDamage(Vec3d pos, float damage, boolean isCritical, boolean isHeal) {
        if (!VesperiaConfig.DAMAGE_NUMBERS) return;

        int color;
        if (isHeal) {
            color = VesperiaConfig.HEAL_COLOR;
        } else if (isCritical) {
            color = VesperiaConfig.DAMAGE_COLOR_CRITICAL;
        } else {
            color = VesperiaConfig.DAMAGE_COLOR_NORMAL;
        }

        String text = isHeal ? "+" + format(damage) : format(damage);
        Vec3d offset = new Vec3d((Math.random() - 0.5) * 0.3, 0, (Math.random() - 0.5) * 0.3);
        numbers.add(new DamageNumber(pos.add(offset), text, color, isCritical, isHeal,
                VesperiaConfig.DAMAGE_NUMBER_LIFETIME, VesperiaConfig.DAMAGE_NUMBER_SCALE));

        long now = System.currentTimeMillis();
        if (now - lastHitTime < 1000) {
            combo++;
        } else {
            combo = 1;
        }
        lastHitTime = now;
    }

    private String format(float n) {
        return n == (int) n ? String.valueOf((int) n) : String.format("%.1f", n);
    }

    public void tick() {
        Iterator<DamageNumber> it = numbers.iterator();
        while (it.hasNext()) {
            DamageNumber dn = it.next();
            dn.update();
            if (dn.isDead()) it.remove();
        }
    }

    public List<DamageNumber> getNumbers() { return numbers; }
    public int getCombo() { return combo; }
}

class DamageNumber {
    private final Vec3d position;
    private Vec3d velocity;
    private final String text;
    private final int color;
    private final boolean isCritical;
    private final boolean isHeal;
    private final int maxLifetime;
    private int currentLifetime;
    private final float scale;

    public DamageNumber(Vec3d pos, String text, int color, boolean crit, boolean heal, int life, float scale) {
        this.position = pos;
        this.text = text;
        this.color = color;
        this.isCritical = crit;
        this.isHeal = heal;
        this.maxLifetime = life;
        this.currentLifetime = life;
        this.scale = scale;
        this.velocity = new Vec3d(0, 0.05, 0);
    }

    public void update() {
        position.add(velocity);
        velocity = new Vec3d(velocity.x * 0.98, velocity.y, velocity.z * 0.98);
        currentLifetime--;
    }

    public boolean isDead() { return currentLifetime <= 0; }
    public float getAlpha() { return Math.min(1.0f, (float) currentLifetime / 20); }
    public Vec3d getPosition() { return position; }
    public String getText() { return text; }
    public int getColor() { return color; }
    public boolean isCritical() { return isCritical; }
    public float getScale() { return scale * (isCritical ? 1.3f : 1.0f); }
}
