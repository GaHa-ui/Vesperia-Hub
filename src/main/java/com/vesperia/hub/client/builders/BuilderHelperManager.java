package com.vesperia.hub.client.builders;

import com.vesperia.hub.config.VesperiaConfig;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class BuilderHelperManager {
    private final Set<BlockPos> highlightedBlocks = new HashSet<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public void update() {
        highlightedBlocks.clear();
        if (client.player == null || client.world == null) return;

        if (VesperiaConfig.BLOCK_IN_VISUALIZER) {
            BlockPos playerPos = client.player.getBlockPos();
            for (int x = -4; x <= 4; x++) {
                for (int y = -1; y <= 2; y++) {
                    for (int z = -4; z <= 4; z++) {
                        BlockPos check = playerPos.add(x, y, z);
                        if (client.world.getBlockState(check).isAir()) {
                            highlightedBlocks.add(check);
                        }
                    }
                }
            }
        }

        if (VesperiaConfig.SCAFFOLD_VISUAL) {
            BlockPos below = client.player.getBlockPos().down();
            highlightedBlocks.add(below);
            for (Direction dir : Direction.values()) {
                if (dir != Direction.DOWN) {
                    highlightedBlocks.add(below.offset(dir));
                }
            }
        }
    }

    public Set<BlockPos> getHighlightedBlocks() { return highlightedBlocks; }
}
