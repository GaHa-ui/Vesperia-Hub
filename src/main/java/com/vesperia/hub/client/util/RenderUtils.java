package com.vesperia.hub.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;

public class RenderUtils {

    public static VertexConsumer getEntityVertexConsumer(MinecraftClient client) {
        return client.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getEntityTranslucentCull(TexturedSprite.BACKGROUND_TEXTURE));
    }

    public static void drawBillboardQuad(MatrixStack matrixStack, Matrix4f matrix, VertexConsumer buffer,
                                         float x, float y, float z, float size,
                                         int r, int g, int b, int a) {
        float halfSize = size / 2;

        buffer.vertex(matrix, x - halfSize, y - halfSize, z).color(r, g, b, a);
        buffer.vertex(matrix, x + halfSize, y - halfSize, z).color(r, g, b, a);
        buffer.vertex(matrix, x + halfSize, y + halfSize, z).color(r, g, b, a);
        buffer.vertex(matrix, x - halfSize, y + halfSize, z).color(r, g, b, a);
    }

    public static void drawLine(MatrixStack matrixStack, Matrix4f matrix, VertexConsumer buffer,
                                float x1, float y1, float z1, float x2, float y2, float z2,
                                int r, int g, int b, int a) {
        buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
        buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
    }

    public static int blendColors(int color1, int color2, float ratio) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        int a1 = (color1 >> 24) & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        int a2 = (color2 >> 24) & 0xFF;

        int r = (int) (r1 * ratio + r2 * (1 - ratio));
        int g = (int) (g1 * ratio + g2 * (1 - ratio));
        int b = (int) (b1 * ratio + b2 * (1 - ratio));
        int a = (int) (a1 * ratio + a2 * (1 - ratio));

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
