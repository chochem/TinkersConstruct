package tconstruct.plugins.waila;

import java.util.List;

import mcp.mobius.waila.api.*;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import tconstruct.smeltery.logic.SmelteryLogic;

public class SmelteryDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof SmelteryLogic && config.getConfig("tcon.smeltery", true)) {
            SmelteryLogic te = (SmelteryLogic) accessor.getTileEntity();
            if (te.validStructure) {
                List<FluidStack> fls = te.moltenMetal;
                if (fls.size() <= 0) {
                    currenttip.add(SpecialChars.ITALIC + StatCollector.translateToLocal("tconstruct.waila.empty"));
                } else {
                    for (FluidStack st : fls) {
                        currenttip.add(WailaRegistrar.fluidNameHelper(st) + " (" + st.amount + "mB)");
                    }
                }
            } else {
                currenttip
                        .add(SpecialChars.ITALIC + StatCollector.translateToLocal("tconstruct.waila.invalidstructure"));
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        return tag;
    }
}
