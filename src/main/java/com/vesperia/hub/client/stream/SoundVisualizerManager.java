package com.vesperia.hub.client.stream;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.Random;

public class SoundVisualizerManager {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final float[] bars = new float[16];
    private final Random random = new Random();
    private int lastSoundTime = 0;

    public void onSound() {
        if (!VesperiaConfig.SOUND_VISUALIZER) return;
        lastSoundTime = 20;
        for (int i = 0; i < bars.length; i++) {
            bars[i] = 0.3f + random.nextFloat() * 0.7f;
        }
    }

    public void tick() {
        if (lastSoundTime > 0) lastSoundTime--;
        
        for (int i = 0; i < bars.length; i++) {
            if (lastSoundTime > 0) {
                bars[i] = Math.max(0.1f, bars[i] - 0.05f);
            } else {
                bars[i] = Math.max(0, bars[i] - 0.1f);
            }
        }
    }

    public void render(DrawContext context) {
        if (!VesperiaConfig.SOUND_VISUALIZER) return;

        int sw = client.getWindow().getScaledWidth();
        int sh = client.getWindow().getScaledHeight();
        int barWidth = 8;
        int spacing = 2;
        int startX = sw / 2 - (bars.length * (barWidth + spacing)) / 2;
        int baseY = sh - 50;

        for (int i = 0; i < bars.length; i++) {
            int height = (int)(bars[i] * 30);
            int color = getColorForBar(bars[i]);
            context.fill(startX + i * (barWidth + spacing), baseY - height, 
                        startX + i * (barWidth + spacing) + barWidth, baseY, color);
        }
    }

    private int getColorForBar(float value) {
        if (value > 0.7f) return 0xFFFF5555;
        if (value > 0.4f) return 0xFFFFFF55;
        return 0xFF55FF55;
    }
}
