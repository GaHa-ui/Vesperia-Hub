package com.vesperia.hub.config;

import com.vesperia.hub.VesperiaHubClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class VesperiaConfig {
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("vesperia-hub.json");

    // ============ EFFECTS & ANIMATION ============
    public static boolean HIT_PARTICLES = true;
    public static boolean HIT_COLOR_FLASH = true;
    public static boolean HIT_BUBBLES = true;
    public static boolean SWING_ANIMATION = true;
    public static boolean VIEWMODEL = true;
    public static boolean JUMP_CIRCLE = true;
    public static boolean ITEM_PHYSICS = true;
    public static boolean DYNAMIC_LIGHTS = true;
    public static boolean PROJECTILE_TRAILS = true;
    public static boolean SHIELD_ANIMATION = true;
    public static boolean BOW_ANIMATION = true;
    public static boolean SMOOTH_CAMERA = true;
    public static boolean NO_HURTCAM = true;
    public static boolean TOTEM_ANIMATION = true;

    // ============ HUD ============
    public static boolean TARGET_HUD = true;
    public static boolean DAMAGE_NUMBERS = true;
    public static boolean COMBO_COUNTER = true;
    public static boolean TOTEM_COUNTER = true;
    public static boolean ARMOR_HUD = true;
    public static boolean KEYSTROKES = true;
    public static boolean CPS_COUNTER = true;
    public static boolean FPS_COUNTER = true;
    public static boolean PING_DISPLAY = true;
    public static boolean REACH_DISPLAY = true;
    public static boolean COORDS_DISPLAY = true;
    public static boolean POTION_HUD = true;
    public static boolean DIRECTION_HUD = true;
    public static boolean SCOREBOARD_CUSTOM = true;

    // ============ CROSSHAIR ============
    public static boolean DYNAMIC_CROSSHAIR = true;
    public static boolean CENTERED_CROSSHAIR = true;
    public static boolean CUSTOM_CROSSHAIR = true;
    public static boolean DOT_CROSSHAIR = false;

    // ============ PVP IMPROVEMENTS ============
    public static boolean TRAJECTORY_PREDICTION = true;
    public static boolean ENTITY_HITBOXES = true;
    public static boolean SOUND_INDICATORS = true;
    public static boolean CUSTOM_NAMETAGS = true;
    public static boolean NO_PUMPKIN_BLUR = true;
    public static boolean LOW_FIRE = true;

    // ============ QOL & OPTIMIZATION ============
    public static boolean FULLBRIGHT = true;
    public static boolean ZOOM = true;
    public static boolean DAYTIME_OVERRIDE = false;
    public static int DAYTIME_HOUR = 12;
    public static boolean CUSTOM_FOG = true;
    public static boolean NO_LEAVES_RENDER = false;
    public static boolean ASPECT_RATIO = false;
    public static float ASPECT_RATIO_VALUE = 1.0f;
    public static boolean CLEAR_WEATHER = false;
    public static boolean NO_DEFAULT_PARTICLES = false;
    public static boolean ENHANCED_TOOLTIPS = true;
    public static boolean ITEM_PHYSICS_QOL = true;
    public static boolean DISCORD_RPC = false;

    // ============ BUILDERS/PVP HELPERS ============
    public static boolean BLOCK_IN_VISUALIZER = true;
    public static boolean SCAFFOLD_VISUAL = true;
    public static boolean JUMP_INDICATOR = true;
    public static boolean AUTO_TOTEM_INDICATOR = true;
    public static boolean MLG_INDICATOR = true;
    public static boolean PARKOUR_HELPER = true;

    // ============ STREAM EFFECTS ============
    public static boolean SOUND_VISUALIZER = true;
    public static boolean SCREEN_SHAKE = true;
    public static boolean KILL_EFFECT = true;
    public static boolean CHUNK_BORDERS = false;
    public static boolean SMOOTH_SCROLLING = true;

    // ============ COLORS ============
    public static int HIT_COLOR_NORMAL = 0xFFFFD700;
    public static int HIT_COLOR_CRITICAL = 0xFFFF4444;
    public static int DAMAGE_COLOR_NORMAL = 0xFFFFD700;
    public static int DAMAGE_COLOR_CRITICAL = 0xFFFF4444;
    public static int HEAL_COLOR = 0xFF44FF44;
    public static int CROSSHAIR_COLOR = 0xFFFFFFFF;

    // ============ SIZES & SCALES ============
    public static float HIT_PARTICLE_SCALE = 1.0f;
    public static float DAMAGE_NUMBER_SCALE = 1.0f;
    public static float TARGET_HUD_SCALE = 1.0f;
    public static float CROSSHAIR_SIZE = 1.0f;
    public static float ZOOM_LEVEL = 0.5f;
    public static float VIEWMODEL_SCALE = 1.0f;

    // ============ TIMINGS ============
    public static int HIT_PARTICLE_LIFETIME = 25;
    public static int DAMAGE_NUMBER_LIFETIME = 40;
    public static int JUMP_CIRCLE_DURATION = 20;
    public static int SCREEN_SHAKE_DURATION = 5;

    private static final VesperiaConfig INSTANCE = new VesperiaConfig();

    public static VesperiaConfig getInstance() {
        return INSTANCE;
    }

    public void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split("=");
                if (parts.length != 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                setProperty(key, value);
            }
            VesperiaHubClient.LOGGER.info("Vesperia Hub config loaded");
        } catch (IOException e) {
            VesperiaHubClient.LOGGER.error("Failed to load config", e);
        }
    }

    private void setProperty(String key, String value) {
        try {
            switch (key) {
                // Booleans
                case "hitParticles" -> HIT_PARTICLES = Boolean.parseBoolean(value);
                case "hitColorFlash" -> HIT_COLOR_FLASH = Boolean.parseBoolean(value);
                case "hitBubbles" -> HIT_BUBBLES = Boolean.parseBoolean(value);
                case "swingAnimation" -> SWING_ANIMATION = Boolean.parseBoolean(value);
                case "viewModel" -> VIEWMODEL = Boolean.parseBoolean(value);
                case "jumpCircle" -> JUMP_CIRCLE = Boolean.parseBoolean(value);
                case "itemPhysics" -> ITEM_PHYSICS = Boolean.parseBoolean(value);
                case "dynamicLights" -> DYNAMIC_LIGHTS = Boolean.parseBoolean(value);
                case "projectileTrails" -> PROJECTILE_TRAILS = Boolean.parseBoolean(value);
                case "shieldAnimation" -> SHIELD_ANIMATION = Boolean.parseBoolean(value);
                case "bowAnimation" -> BOW_ANIMATION = Boolean.parseBoolean(value);
                case "smoothCamera" -> SMOOTH_CAMERA = Boolean.parseBoolean(value);
                case "noHurtcam" -> NO_HURTCAM = Boolean.parseBoolean(value);
                case "totemAnimation" -> TOTEM_ANIMATION = Boolean.parseBoolean(value);
                case "targetHud" -> TARGET_HUD = Boolean.parseBoolean(value);
                case "damageNumbers" -> DAMAGE_NUMBERS = Boolean.parseBoolean(value);
                case "comboCounter" -> COMBO_COUNTER = Boolean.parseBoolean(value);
                case "totemCounter" -> TOTEM_COUNTER = Boolean.parseBoolean(value);
                case "armorHud" -> ARMOR_HUD = Boolean.parseBoolean(value);
                case "keystrokes" -> KEYSTROKES = Boolean.parseBoolean(value);
                case "cpsCounter" -> CPS_COUNTER = Boolean.parseBoolean(value);
                case "fpsCounter" -> FPS_COUNTER = Boolean.parseBoolean(value);
                case "pingDisplay" -> PING_DISPLAY = Boolean.parseBoolean(value);
                case "reachDisplay" -> REACH_DISPLAY = Boolean.parseBoolean(value);
                case "coordsDisplay" -> COORDS_DISPLAY = Boolean.parseBoolean(value);
                case "potionHud" -> POTION_HUD = Boolean.parseBoolean(value);
                case "directionHud" -> DIRECTION_HUD = Boolean.parseBoolean(value);
                case "scoreboardCustom" -> SCOREBOARD_CUSTOM = Boolean.parseBoolean(value);
                case "dynamicCrosshair" -> DYNAMIC_CROSSHAIR = Boolean.parseBoolean(value);
                case "centeredCrosshair" -> CENTERED_CROSSHAIR = Boolean.parseBoolean(value);
                case "customCrosshair" -> CUSTOM_CROSSHAIR = Boolean.parseBoolean(value);
                case "dotCrosshair" -> DOT_CROSSHAIR = Boolean.parseBoolean(value);
                case "trajectoryPrediction" -> TRAJECTORY_PREDICTION = Boolean.parseBoolean(value);
                case "entityHitboxes" -> ENTITY_HITBOXES = Boolean.parseBoolean(value);
                case "soundIndicators" -> SOUND_INDICATORS = Boolean.parseBoolean(value);
                case "customNametags" -> CUSTOM_NAMETAGS = Boolean.parseBoolean(value);
                case "noPumpkinBlur" -> NO_PUMPKIN_BLUR = Boolean.parseBoolean(value);
                case "lowFire" -> LOW_FIRE = Boolean.parseBoolean(value);
                case "fullbright" -> FULLBRIGHT = Boolean.parseBoolean(value);
                case "zoom" -> ZOOM = Boolean.parseBoolean(value);
                case "daytimeOverride" -> DAYTIME_OVERRIDE = Boolean.parseBoolean(value);
                case "customFog" -> CUSTOM_FOG = Boolean.parseBoolean(value);
                case "noLeavesRender" -> NO_LEAVES_RENDER = Boolean.parseBoolean(value);
                case "aspectRatio" -> ASPECT_RATIO = Boolean.parseBoolean(value);
                case "clearWeather" -> CLEAR_WEATHER = Boolean.parseBoolean(value);
                case "noDefaultParticles" -> NO_DEFAULT_PARTICLES = Boolean.parseBoolean(value);
                case "enhancedTooltips" -> ENHANCED_TOOLTIPS = Boolean.parseBoolean(value);
                case "itemPhysicsQol" -> ITEM_PHYSICS_QOL = Boolean.parseBoolean(value);
                case "discordRpc" -> DISCORD_RPC = Boolean.parseBoolean(value);
                case "blockInVisualizer" -> BLOCK_IN_VISUALIZER = Boolean.parseBoolean(value);
                case "scaffoldVisual" -> SCAFFOLD_VISUAL = Boolean.parseBoolean(value);
                case "jumpIndicator" -> JUMP_INDICATOR = Boolean.parseBoolean(value);
                case "autoTotemIndicator" -> AUTO_TOTEM_INDICATOR = Boolean.parseBoolean(value);
                case "mlgIndicator" -> MLG_INDICATOR = Boolean.parseBoolean(value);
                case "parkourHelper" -> PARKOUR_HELPER = Boolean.parseBoolean(value);
                case "soundVisualizer" -> SOUND_VISUALIZER = Boolean.parseBoolean(value);
                case "screenShake" -> SCREEN_SHAKE = Boolean.parseBoolean(value);
                case "killEffect" -> KILL_EFFECT = Boolean.parseBoolean(value);
                case "chunkBorders" -> CHUNK_BORDERS = Boolean.parseBoolean(value);
                case "smoothScrolling" -> SMOOTH_SCROLLING = Boolean.parseBoolean(value);

                // Colors
                case "hitColorNormal" -> HIT_COLOR_NORMAL = Integer.parseInt(value);
                case "hitColorCritical" -> HIT_COLOR_CRITICAL = Integer.parseInt(value);
                case "damageColorNormal" -> DAMAGE_COLOR_NORMAL = Integer.parseInt(value);
                case "damageColorCritical" -> DAMAGE_COLOR_CRITICAL = Integer.parseInt(value);
                case "healColor" -> HEAL_COLOR = Integer.parseInt(value);
                case "crosshairColor" -> CROSSHAIR_COLOR = Integer.parseInt(value);

                // Floats
                case "hitParticleScale" -> HIT_PARTICLE_SCALE = Float.parseFloat(value);
                case "damageNumberScale" -> DAMAGE_NUMBER_SCALE = Float.parseFloat(value);
                case "targetHudScale" -> TARGET_HUD_SCALE = Float.parseFloat(value);
                case "crosshairSize" -> CROSSHAIR_SIZE = Float.parseFloat(value);
                case "zoomLevel" -> ZOOM_LEVEL = Float.parseFloat(value);
                case "viewModelScale" -> VIEWMODEL_SCALE = Float.parseFloat(value);
                case "aspectRatioValue" -> ASPECT_RATIO_VALUE = Float.parseFloat(value);

                // Integers
                case "daytimeHour" -> DAYTIME_HOUR = Integer.parseInt(value);
                case "hitParticleLifetime" -> HIT_PARTICLE_LIFETIME = Integer.parseInt(value);
                case "damageNumberLifetime" -> DAMAGE_NUMBER_LIFETIME = Integer.parseInt(value);
                case "jumpCircleDuration" -> JUMP_CIRCLE_DURATION = Integer.parseInt(value);
                case "screenShakeDuration" -> SCREEN_SHAKE_DURATION = Integer.parseInt(value);
            }
        } catch (Exception e) {
            VesperiaHubClient.LOGGER.warn("Unknown config key: " + key);
        }
    }

    public void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Vesperia Hub Configuration\n");
        sb.append("# Generated automatically\n\n");

        sb.append("# EFFECTS & ANIMATION\n");
        sb.append("hitParticles=").append(HIT_PARTICLES).append("\n");
        sb.append("hitColorFlash=").append(HIT_COLOR_FLASH).append("\n");
        sb.append("hitBubbles=").append(HIT_BUBBLES).append("\n");
        sb.append("swingAnimation=").append(SWING_ANIMATION).append("\n");
        sb.append("viewModel=").append(VIEWMODEL).append("\n");
        sb.append("jumpCircle=").append(JUMP_CIRCLE).append("\n");
        sb.append("itemPhysics=").append(ITEM_PHYSICS).append("\n");
        sb.append("dynamicLights=").append(DYNAMIC_LIGHTS).append("\n");
        sb.append("projectileTrails=").append(PROJECTILE_TRAILS).append("\n");
        sb.append("shieldAnimation=").append(SHIELD_ANIMATION).append("\n");
        sb.append("bowAnimation=").append(BOW_ANIMATION).append("\n");
        sb.append("smoothCamera=").append(SMOOTH_CAMERA).append("\n");
        sb.append("noHurtcam=").append(NO_HURTCAM).append("\n");
        sb.append("totemAnimation=").append(TOTEM_ANIMATION).append("\n");

        sb.append("\n# HUD\n");
        sb.append("targetHud=").append(TARGET_HUD).append("\n");
        sb.append("damageNumbers=").append(DAMAGE_NUMBERS).append("\n");
        sb.append("comboCounter=").append(COMBO_COUNTER).append("\n");
        sb.append("totemCounter=").append(TOTEM_COUNTER).append("\n");
        sb.append("armorHud=").append(ARMOR_HUD).append("\n");
        sb.append("keystrokes=").append(KEYSTROKES).append("\n");
        sb.append("cpsCounter=").append(CPS_COUNTER).append("\n");
        sb.append("fpsCounter=").append(FPS_COUNTER).append("\n");
        sb.append("pingDisplay=").append(PING_DISPLAY).append("\n");
        sb.append("reachDisplay=").append(REACH_DISPLAY).append("\n");
        sb.append("coordsDisplay=").append(COORDS_DISPLAY).append("\n");
        sb.append("potionHud=").append(POTION_HUD).append("\n");
        sb.append("directionHud=").append(DIRECTION_HUD).append("\n");
        sb.append("scoreboardCustom=").append(SCOREBOARD_CUSTOM).append("\n");

        sb.append("\n# CROSSHAIR\n");
        sb.append("dynamicCrosshair=").append(DYNAMIC_CROSSHAIR).append("\n");
        sb.append("centeredCrosshair=").append(CENTERED_CROSSHAIR).append("\n");
        sb.append("customCrosshair=").append(CUSTOM_CROSSHAIR).append("\n");
        sb.append("dotCrosshair=").append(DOT_CROSSHAIR).append("\n");
        sb.append("crosshairColor=").append(CROSSHAIR_COLOR).append("\n");
        sb.append("crosshairSize=").append(CROSSHAIR_SIZE).append("\n");

        sb.append("\n# PVP\n");
        sb.append("trajectoryPrediction=").append(TRAJECTORY_PREDICTION).append("\n");
        sb.append("entityHitboxes=").append(ENTITY_HITBOXES).append("\n");
        sb.append("soundIndicators=").append(SOUND_INDICATORS).append("\n");
        sb.append("customNametags=").append(CUSTOM_NAMETAGS).append("\n");
        sb.append("noPumpkinBlur=").append(NO_PUMPKIN_BLUR).append("\n");
        sb.append("lowFire=").append(LOW_FIRE).append("\n");

        sb.append("\n# QOL\n");
        sb.append("fullbright=").append(FULLBRIGHT).append("\n");
        sb.append("zoom=").append(ZOOM).append("\n");
        sb.append("daytimeOverride=").append(DAYTIME_OVERRIDE).append("\n");
        sb.append("daytimeHour=").append(DAYTIME_HOUR).append("\n");
        sb.append("customFog=").append(CUSTOM_FOG).append("\n");
        sb.append("noLeavesRender=").append(NO_LEAVES_RENDER).append("\n");
        sb.append("aspectRatio=").append(ASPECT_RATIO).append("\n");
        sb.append("aspectRatioValue=").append(ASPECT_RATIO_VALUE).append("\n");
        sb.append("clearWeather=").append(CLEAR_WEATHER).append("\n");
        sb.append("noDefaultParticles=").append(NO_DEFAULT_PARTICLES).append("\n");
        sb.append("enhancedTooltips=").append(ENHANCED_TOOLTIPS).append("\n");
        sb.append("itemPhysicsQol=").append(ITEM_PHYSICS_QOL).append("\n");
        sb.append("discordRpc=").append(DISCORD_RPC).append("\n");

        sb.append("\n# BUILDERS\n");
        sb.append("blockInVisualizer=").append(BLOCK_IN_VISUALIZER).append("\n");
        sb.append("scaffoldVisual=").append(SCAFFOLD_VISUAL).append("\n");
        sb.append("jumpIndicator=").append(JUMP_INDICATOR).append("\n");
        sb.append("autoTotemIndicator=").append(AUTO_TOTEM_INDICATOR).append("\n");
        sb.append("mlgIndicator=").append(MLG_INDICATOR).append("\n");
        sb.append("parkourHelper=").append(PARKOUR_HELPER).append("\n");

        sb.append("\n# STREAM\n");
        sb.append("soundVisualizer=").append(SOUND_VISUALIZER).append("\n");
        sb.append("screenShake=").append(SCREEN_SHAKE).append("\n");
        sb.append("killEffect=").append(KILL_EFFECT).append("\n");
        sb.append("chunkBorders=").append(CHUNK_BORDERS).append("\n");
        sb.append("smoothScrolling=").append(SMOOTH_SCROLLING).append("\n");

        sb.append("\n# COLORS\n");
        sb.append("hitColorNormal=").append(HIT_COLOR_NORMAL).append("\n");
        sb.append("hitColorCritical=").append(HIT_COLOR_CRITICAL).append("\n");
        sb.append("damageColorNormal=").append(DAMAGE_COLOR_NORMAL).append("\n");
        sb.append("damageColorCritical=").append(DAMAGE_COLOR_CRITICAL).append("\n");
        sb.append("healColor=").append(HEAL_COLOR).append("\n");

        sb.append("\n# SCALES\n");
        sb.append("hitParticleScale=").append(HIT_PARTICLE_SCALE).append("\n");
        sb.append("damageNumberScale=").append(DAMAGE_NUMBER_SCALE).append("\n");
        sb.append("targetHudScale=").append(TARGET_HUD_SCALE).append("\n");
        sb.append("zoomLevel=").append(ZOOM_LEVEL).append("\n");
        sb.append("viewModelScale=").append(VIEWMODEL_SCALE).append("\n");

        sb.append("\n# TIMINGS\n");
        sb.append("hitParticleLifetime=").append(HIT_PARTICLE_LIFETIME).append("\n");
        sb.append("damageNumberLifetime=").append(DAMAGE_NUMBER_LIFETIME).append("\n");
        sb.append("jumpCircleDuration=").append(JUMP_CIRCLE_DURATION).append("\n");
        sb.append("screenShakeDuration=").append(SCREEN_SHAKE_DURATION).append("\n");

        try {
            Files.writeString(CONFIG_PATH, sb.toString());
            VesperiaHubClient.LOGGER.info("Vesperia Hub config saved");
        } catch (IOException e) {
            VesperiaHubClient.LOGGER.error("Failed to save config", e);
        }
    }
}
