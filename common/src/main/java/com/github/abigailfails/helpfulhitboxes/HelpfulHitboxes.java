package com.github.abigailfails.helpfulhitboxes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

public class HelpfulHitboxes {
    public static final String MOD_ID = "helpfulhitboxes";
    public static final Logger LOGGER = LogManager.getLogger();
    public static CompatibleBlockList COMPATIBLE_BLOCKS;

    public record CompatibleBlockList(HashSet<String> ungroupedBlocks, HashSet<HashSet<String>> blockGroups) {
        public boolean isCompatible(String targetedBlockID, String heldBlockID) {
            boolean useFullShape = false;
            if (targetedBlockID.equals(heldBlockID) && HelpfulHitboxes.COMPATIBLE_BLOCKS.ungroupedBlocks().contains(targetedBlockID))
                useFullShape = true;
            else if (HelpfulHitboxes.COMPATIBLE_BLOCKS.blockGroups().stream().anyMatch(g -> g.contains(targetedBlockID) && g.contains(heldBlockID)))
                useFullShape = true;
            return useFullShape;
        }

        public void updateTags() {
            ModConfig.updateTagsInBlockList(this.ungroupedBlocks());
            this.blockGroups().forEach(ModConfig::updateTagsInBlockList);
        }
    }

    public static void init() {
    }
}
