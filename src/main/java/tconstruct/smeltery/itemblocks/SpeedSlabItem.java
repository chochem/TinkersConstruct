package tconstruct.smeltery.itemblocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class SpeedSlabItem extends MultiItemBlock {

    public static final String[] blockTypes = { "brownstone.rough", "brownstone.rough.road", "brownstone.smooth",
            "brownstone.smooth.brick", "brownstone.smooth.road", "brownstone.smooth.fancy",
            "brownstone.smooth.chiseled" };

    public SpeedSlabItem(Block b) {
        super(b, "block", "slab", blockTypes);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        int metadata = stack.getItemDamage() % 8;
        if (metadata == 1 || metadata == 4) list.add(StatCollector.translateToLocal("speedblock1.tooltip"));
        else list.add(StatCollector.translateToLocal("speedblock2.tooltip"));
    }
}
