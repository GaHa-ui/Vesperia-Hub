package com.vesperia.hub.client.visual;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.client.config.ModConfig;
import com.vesperia.hub.client.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DamageNumberRenderer {
    private final List<DamageNumber> damageNumbers = new ArrayList<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void addDamageNumber(Vec3d position, float damage, boolean isCritical, boolean isHeal) {
        if (!ModConfig.ENABLE_DAMAGE_NUMBERS) return;

        int color;
        if (isHeal) {
            color = ModConfig.HEAL_COLOR;
        } else if (isCritical) {
            color = ModConfig.CRITICAL_DAMAGE_COLOR;
        } else {
            color = ModConfig.NORMAL_DAMAGE_COLOR;
        }

        String text = isHeal ? "+" + formatNumber(damage) : formatNumber(damage);
        int lifetime = ModConfig.DAMAGE_NUMBER_LIFETIME;
        float scale = ModConfig.DAMAGE_NUMBER_SCALE;

        Vec3d offset = new Vec3d(
                (Math.random() - 0.5) * 0.3,
                0,
                (Math.random() - 0.5) * 0.3
        );

        damageNumbers.add(new DamageNumber(position.add(offset), text, color, isCritical, isHeal, lifetime, scale));
    }

    private String formatNumber(float number) {
        if (number == (int) number) {
            return String.valueOf((int) number);
        }
        return String.format("%.1f", number);
    }

    public void tick() {
        Iterator<DamageNumber> iterator = damageNumbers.iterator();
        while (iterator.hasNext()) {
            DamageNumber dmgNum = iterator.next();
            dmgNum.update();
            if (dmgNum.isDead()) {
                iterator.remove();
            }
        }
    }

    public void render(MatrixStack matrixStack) {
        if (damageNumbers.isEmpty()) return;

        Camera camera = client.gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();

        VertexConsumerProvider.Immediate immediate = client.getBufferProviders().getBufferProviders();
        TextRenderer textRenderer = client.textRenderer;

        for (DamageNumber dmgNum : damageNumbers) {
            Vec3d pos = dmgNum.getPosition().subtract(cameraPos);

            matrixStack.push();
            matrixStack.translate(pos.x, pos.y, pos.z);
            matrixStack.multiply(camera.getRotation());
            matrixStack.scale(-dmgNum.getScale(), -dmgNum.getScale(), dmgNum.getScale());

            float alpha = dmgNum.getAlpha();
            int color = dmgNum.getColor();
            int a = (int) (alpha * 255);

            matrixStack.translate(-textRenderer.getWidth(dmgNum.getText()) / 2.0, 0, 0);
            textRenderer.draw(matrixStack, dmgNum.getText(),
                    0, 0, (color & 0x00FFFFFF) | (a << 24), false,
                    matrixStack.peek().getPositionMatrix(), immediate, TextRenderer.TextLayerType.NORMAL,
                    0, 0xF000F0);

            matrixStack.pop();
        }

        immediate.draw();
    }

    public List<DamageNumber> getDamageNumbers() {
        return damageNumbers;
    }
}
