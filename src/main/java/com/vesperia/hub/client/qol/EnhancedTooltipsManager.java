package com.vesperia.hub.client.qol;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.item.ItemStack;

public class EnhancedTooltipsManager {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public String getEnhancedTooltip(ItemStack stack) {
        if (!VesperiaConfig.ENHANCED_TOOLTIPS) return "";

        StringBuilder tooltip = new StringBuilder();
        
        if (stack.isDamageable()) {
            int durability = stack.getMaxDamage() - stack.getDamage();
            int maxDurability = stack.getMaxDamage();
            tooltip.append("\nПрочность: ").append(durability).append("/").append(maxDurability);
            tooltip.append(" (").append((int)((durability * 100.0) / maxDurability)).append("%)");
        }

        if (stack.hasEnchantments()) {
            tooltip.append("\nЗачарования:");
            stack.getEnchantments().forEachRemaining(enchantment -> {
                tooltip.append("\n  - ").append(enchantment.getTranslationKey());
            });
        }

        return tooltip.toString();
    }
}
