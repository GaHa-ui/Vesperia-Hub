package com.vesperia.hub.client.gui;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil.KeyInput;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class VesperiaSettingsScreen extends Screen {
    private final Screen parent;
    private int page = 0;
    private static final int PAGES = 3;

    public VesperiaSettingsScreen(Screen parent) {
        super(Text.literal("Vesperia Hub Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        rebuild();
    }

    private void rebuild() {
        clearAndInit();
        int centerX = this.width / 2;
        int startY = 30;

        String[] titles = {"HUD", "Effects", "Crosshair"};
        this.drawCenteredText(context, Text.literal(titles[page]), centerX, 10, 0xFFFFFF);

        int y = startY;
        int col1X = centerX - 150;
        int col2X = centerX + 20;

        switch (page) {
            case 0: // HUD
                addToggle(col1X, y += 25, "FPS Counter", VesperiaConfig.FPS_COUNTER, v -> VesperiaConfig.FPS_COUNTER = v);
                addToggle(col1X, y += 25, "Ping Display", VesperiaConfig.PING_DISPLAY, v -> VesperiaConfig.PING_DISPLAY = v);
                addToggle(col1X, y += 25, "Coordinates", VesperiaConfig.COORDS_DISPLAY, v -> VesperiaConfig.COORDS_DISPLAY = v);
                addToggle(col1X, y += 25, "Direction (N/E/S/W)", VesperiaConfig.DIRECTION_HUD, v -> VesperiaConfig.DIRECTION_HUD = v);
                addToggle(col1X, y += 25, "Potion Effects", VesperiaConfig.POTION_HUD, v -> VesperiaConfig.POTION_HUD = v);
                addToggle(col1X, y += 25, "Armor Display", VesperiaConfig.ARMOR_HUD, v -> VesperiaConfig.ARMOR_HUD = v);

                y = startY;
                addToggle(col2X, y += 25, "WASD Keystrokes", VesperiaConfig.KEYSTROKES, v -> VesperiaConfig.KEYSTROKES = v);
                addToggle(col2X, y += 25, "CPS Counter", VesperiaConfig.CPS_COUNTER, v -> VesperiaConfig.CPS_COUNTER = v);
                addToggle(col2X, y += 25, "Totem Counter", VesperiaConfig.TOTEM_COUNTER, v -> VesperiaConfig.TOTEM_COUNTER = v);
                addToggle(col2X, y += 25, "Target HUD", VesperiaConfig.TARGET_HUD, v -> VesperiaConfig.TARGET_HUD = v);
                addToggle(col2X, y += 25, "Combo Counter", VesperiaConfig.COMBO_COUNTER, v -> VesperiaConfig.COMBO_COUNTER = v);
                addToggle(col2X, y += 25, "Zoom (Z key)", VesperiaConfig.ZOOM, v -> VesperiaConfig.ZOOM = v);
                break;

            case 1: // Effects
                addToggle(col1X, y += 25, "Hit Effects", VesperiaConfig.HIT_EFFECTS, v -> VesperiaConfig.HIT_EFFECTS = v);
                addToggle(col1X, y += 25, "Damage Numbers", VesperiaConfig.DAMAGE_NUMBERS, v -> VesperiaConfig.DAMAGE_NUMBERS = v);
                addToggle(col1X, y += 25, "Hit Particles", VesperiaConfig.HIT_PARTICLES, v -> VesperiaConfig.HIT_PARTICLES = v);
                addToggle(col1X, y += 25, "Screen Shake", VesperiaConfig.SCREEN_SHAKE, v -> VesperiaConfig.SCREEN_SHAKE = v);
                addToggle(col1X, y += 25, "Jump Circle", VesperiaConfig.JUMP_CIRCLE, v -> VesperiaConfig.JUMP_CIRCLE = v);
                addToggle(col1X, y += 25, "Fullbright", VesperiaConfig.FULLBRIGHT, v -> VesperiaConfig.FULLBRIGHT = v);

                y = startY;
                addToggle(col2X, y += 25, "Trajectory Predict", VesperiaConfig.TRAJECTORY_PREDICTION, v -> VesperiaConfig.TRAJECTORY_PREDICTION = v);
                addToggle(col2X, y += 25, "CPS Counter", VesperiaConfig.CPS_COUNTER, v -> VesperiaConfig.CPS_COUNTER = v);
                break;

            case 2: // Crosshair
                addToggle(col1X, y += 25, "Custom Crosshair", VesperiaConfig.CUSTOM_CROSSHAIR, v -> VesperiaConfig.CUSTOM_CROSSHAIR = v);
                addToggle(col1X, y += 25, "Dynamic Color", VesperiaConfig.DYNAMIC_CROSSHAIR, v -> VesperiaConfig.DYNAMIC_CROSSHAIR = v);
                addToggle(col1X, y += 25, "Dot Crosshair", VesperiaConfig.DOT_CROSSHAIR, v -> VesperiaConfig.DOT_CROSSHAIR = v);
                addToggle(col1X, y += 25, "Zoom (Z)", VesperiaConfig.ZOOM, v -> VesperiaConfig.ZOOM = v);
                break;
        }

        // Navigation buttons
        int navY = this.height - 30;
        
        addDrawableChild(ButtonWidget.builder(Text.literal("◀"), btn -> {
            page = (page - 1 + PAGES) % PAGES;
            rebuild();
        }).dimensions(centerX - 100, navY, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("▶"), btn -> {
            page = (page + 1) % PAGES;
            rebuild();
        }).dimensions(centerX + 70, navY, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Save & Exit"), btn -> {
            VesperiaConfig.save();
            this.client.setScreen(parent);
        }).dimensions(centerX - 40, navY, 80, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), btn -> {
            this.client.setScreen(parent);
        }).dimensions(centerX - 40, navY + 25, 80, 20).build());
    }

    private void addToggle(int x, int y, String text, boolean value, java.util.function.Consumer<Boolean> setter) {
        addDrawableChild(ButtonWidget.builder(Text.literal(value ? "§a● " + text : "§c○ " + text), btn -> {
            setter.accept(!value);
            rebuild();
        }).dimensions(x, y, 130, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.keyCode() == 256) {
            VesperiaConfig.save();
            this.client.setScreen(parent);
            return true;
        }
        return super.keyPressed(input);
    }
}
