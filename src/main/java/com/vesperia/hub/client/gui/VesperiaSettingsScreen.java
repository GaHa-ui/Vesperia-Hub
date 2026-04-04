package com.vesperia.hub.client.gui;

import com.vesperia.hub.VesperiaHubClient;
import com.vesperia.hub.config.VesperiaConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class VesperiaSettingsScreen extends Screen {
    private final Screen parent;
    private int page = 0;
    private static final int PAGES = 4;

    public VesperiaSettingsScreen(Screen parent) {
        super(Text.literal("Vesperia Hub Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 30;

        addDrawableChild(ButtonWidget.builder(Text.literal("◀"), (btn) -> {
            page = (page - 1 + PAGES) % PAGES;
            rebuildWidgets();
        }).dimensions(centerX - 160, this.height - 30, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("▶"), (btn) -> {
            page = (page + 1) % PAGES;
            rebuildWidgets();
        }).dimensions(centerX + 130, this.height - 30, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("✕ Close"), (btn) -> {
            this.client.setScreen(parent);
        }).dimensions(centerX - 40, this.height - 30, 80, 20).build());

        rebuildWidgets();
    }

    private void rebuildWidgets() {
        clearWidgets();

        int centerX = this.width / 2;
        int startY = 30;

        String[] titles = {"Effects", "HUD", "Crosshair", "QoL"};
        drawCenteredText(textRenderer, DrawContext.class.cast(new Object()), 
                Text.literal("Vesperia Hub - " + titles[page]), centerX, 10, 0xFFFFFF);

        int y = startY;
        int col1X = centerX - 160;
        int col2X = centerX + 20;

        switch (page) {
            case 0: // Effects
                addToggle(col1X, y, "Hit Particles", VesperiaConfig.HIT_PARTICLES, v -> VesperiaConfig.HIT_PARTICLES = v); y += 25;
                addToggle(col1X, y, "Hit Color Flash", VesperiaConfig.HIT_COLOR_FLASH, v -> VesperiaConfig.HIT_COLOR_FLASH = v); y += 25;
                addToggle(col1X, y, "Hit Bubbles", VesperiaConfig.HIT_BUBBLES, v -> VesperiaConfig.HIT_BUBBLES = v); y += 25;
                addToggle(col1X, y, "Swing Animation", VesperiaConfig.SWING_ANIMATION, v -> VesperiaConfig.SWING_ANIMATION = v); y += 25;
                addToggle(col1X, y, "ViewModel", VesperiaConfig.VIEWMODEL, v -> VesperiaConfig.VIEWMODEL = v); y += 25;
                addToggle(col1X, y, "Jump Circle", VesperiaConfig.JUMP_CIRCLE, v -> VesperiaConfig.JUMP_CIRCLE = v); y += 25;

                y = startY;
                addToggle(col2X, y, "Projectile Trails", VesperiaConfig.PROJECTILE_TRAILS, v -> VesperiaConfig.PROJECTILE_TRAILS = v); y += 25;
                addToggle(col2X, y, "Screen Shake", VesperiaConfig.SCREEN_SHAKE, v -> VesperiaConfig.SCREEN_SHAKE = v); y += 25;
                addToggle(col2X, y, "Kill Effect", VesperiaConfig.KILL_EFFECT, v -> VesperiaConfig.KILL_EFFECT = v); y += 25;
                addToggle(col2X, y, "Dynamic Lights", VesperiaConfig.DYNAMIC_LIGHTS, v -> VesperiaConfig.DYNAMIC_LIGHTS = v); y += 25;
                addToggle(col2X, y, "NoHurtcam", VesperiaConfig.NO_HURTCAM, v -> VesperiaConfig.NO_HURTCAM = v); y += 25;
                break;

            case 1: // HUD
                addToggle(col1X, y, "Target HUD", VesperiaConfig.TARGET_HUD, v -> VesperiaConfig.TARGET_HUD = v); y += 25;
                addToggle(col1X, y, "Damage Numbers", VesperiaConfig.DAMAGE_NUMBERS, v -> VesperiaConfig.DAMAGE_NUMBERS = v); y += 25;
                addToggle(col1X, y, "Combo Counter", VesperiaConfig.COMBO_COUNTER, v -> VesperiaConfig.COMBO_COUNTER = v); y += 25;
                addToggle(col1X, y, "Totem Counter", VesperiaConfig.TOTEM_COUNTER, v -> VesperiaConfig.TOTEM_COUNTER = v); y += 25;
                addToggle(col1X, y, "Armor HUD", VesperiaConfig.ARMOR_HUD, v -> VesperiaConfig.ARMOR_HUD = v); y += 25;
                addToggle(col1X, y, "Keystrokes", VesperiaConfig.KEYSTROKES, v -> VesperiaConfig.KEYSTROKES = v); y += 25;

                y = startY;
                addToggle(col2X, y, "FPS Counter", VesperiaConfig.FPS_COUNTER, v -> VesperiaConfig.FPS_COUNTER = v); y += 25;
                addToggle(col2X, y, "Ping Display", VesperiaConfig.PING_DISPLAY, v -> VesperiaConfig.PING_DISPLAY = v); y += 25;
                addToggle(col2X, y, "Coords Display", VesperiaConfig.COORDS_DISPLAY, v -> VesperiaConfig.COORDS_DISPLAY = v); y += 25;
                addToggle(col2X, y, "Direction HUD", VesperiaConfig.DIRECTION_HUD, v -> VesperiaConfig.DIRECTION_HUD = v); y += 25;
                addToggle(col2X, y, "Potion HUD", VesperiaConfig.POTION_HUD, v -> VesperiaConfig.POTION_HUD = v); y += 25;
                addToggle(col2X, y, "CPS Counter", VesperiaConfig.CPS_COUNTER, v -> VesperiaConfig.CPS_COUNTER = v); y += 25;
                break;

            case 2: // Crosshair
                addToggle(col1X, y, "Custom Crosshair", VesperiaConfig.CUSTOM_CROSSHAIR, v -> VesperiaConfig.CUSTOM_CROSSHAIR = v); y += 25;
                addToggle(col1X, y, "Dynamic Crosshair", VesperiaConfig.DYNAMIC_CROSSHAIR, v -> VesperiaConfig.DYNAMIC_CROSSHAIR = v); y += 25;
                addToggle(col1X, y, "Dot Crosshair", VesperiaConfig.DOT_CROSSHAIR, v -> VesperiaConfig.DOT_CROSSHAIR = v); y += 25;
                addToggle(col1X, y, "Centered Crosshair", VesperiaConfig.CENTERED_CROSSHAIR, v -> VesperiaConfig.CENTERED_CROSSHAIR = v); y += 25;

                y += 25;
                addSlider(col1X, y, "Crosshair Size", VesperiaConfig.CROSSHAIR_SIZE, 0.5f, 2.0f, 
                    v -> VesperiaConfig.CROSSHAIR_SIZE = v); y += 25;

                y = startY;
                addToggle(col2X, y, "Trajectory Predict", VesperiaConfig.TRAJECTORY_PREDICTION, v -> VesperiaConfig.TRAJECTORY_PREDICTION = v); y += 25;
                addToggle(col2X, y, "Sound Indicators", VesperiaConfig.SOUND_INDICATORS, v -> VesperiaConfig.SOUND_INDICATORS = v); y += 25;
                addToggle(col2X, y, "Entity Hitboxes", VesperiaConfig.ENTITY_HITBOXES, v -> VesperiaConfig.ENTITY_HITBOXES = v); y += 25;
                addToggle(col2X, y, "Custom Nametags", VesperiaConfig.CUSTOM_NAMETAGS, v -> VesperiaConfig.CUSTOM_NAMETAGS = v); y += 25;
                break;

            case 3: // QoL
                addToggle(col1X, y, "Fullbright", VesperiaConfig.FULLBRIGHT, v -> VesperiaConfig.FULLBRIGHT = v); y += 25;
                addToggle(col1X, y, "Zoom (Z key)", VesperiaConfig.ZOOM, v -> VesperiaConfig.ZOOM = v); y += 25;
                addToggle(col1X, y, "Clear Weather", VesperiaConfig.CLEAR_WEATHER, v -> VesperiaConfig.CLEAR_WEATHER = v); y += 25;
                addToggle(col1X, y, "Daytime Override", VesperiaConfig.DAYTIME_OVERRIDE, v -> VesperiaConfig.DAYTIME_OVERRIDE = v); y += 25;
                addToggle(col1X, y, "Enhanced Tooltips", VesperiaConfig.ENHANCED_TOOLTIPS, v -> VesperiaConfig.ENHANCED_TOOLTIPS = v); y += 25;
                addToggle(col1X, y, "No Pumpkin Blur", VesperiaConfig.NO_PUMPKIN_BLUR, v -> VesperiaConfig.NO_PUMPKIN_BLUR = v); y += 25;

                y = startY;
                addToggle(col2X, y, "Block Highlight", VesperiaConfig.BLOCK_IN_VISUALIZER, v -> VesperiaConfig.BLOCK_IN_VISUALIZER = v); y += 25;
                addToggle(col2X, y, "Scaffold Visual", VesperiaConfig.SCAFFOLD_VISUAL, v -> VesperiaConfig.SCAFFOLD_VISUAL = v); y += 25;
                addToggle(col2X, y, "Auto Totem Icon", VesperiaConfig.AUTO_TOTEM_INDICATOR, v -> VesperiaConfig.AUTO_TOTEM_INDICATOR = v); y += 25;
                addToggle(col2X, y, "MLG Indicator", VesperiaConfig.MLG_INDICATOR, v -> VesperiaConfig.MLG_INDICATOR = v); y += 25;
                addToggle(col2X, y, "Parkour Helper", VesperiaConfig.PARKOUR_HELPER, v -> VesperiaConfig.PARKOUR_HELPER = v); y += 25;
                addToggle(col2X, y, "Sound Visualizer", VesperiaConfig.SOUND_VISUALIZER, v -> VesperiaConfig.SOUND_VISUALIZER = v); y += 25;
                break;
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("◀"), (btn) -> {
            page = (page - 1 + PAGES) % PAGES;
            rebuildWidgets();
        }).dimensions(centerX - 160, this.height - 30, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("▶"), (btn) -> {
            page = (page + 1) % PAGES;
            rebuildWidgets();
        }).dimensions(centerX + 130, this.height - 30, 30, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("✕ Close"), (btn) -> {
            VesperiaConfig.getInstance().save();
            this.client.setScreen(parent);
        }).dimensions(centerX - 40, this.height - 30, 80, 20).build());
    }

    private void addToggle(int x, int y, String text, boolean value, java.util.function.Consumer<Boolean> setter) {
        addDrawableChild(CyclingButtonWidget.onOffBuilder(value).initially(value).build(
                x, y, 140, 20,
                Text.literal(text),
                (btn, val) -> setter.accept(val)
        ));
    }

    private void addSlider(int x, int y, String text, float value, float min, float max, java.util.function.Consumer<Float> setter) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            VesperiaConfig.getInstance().save();
            this.client.setScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
