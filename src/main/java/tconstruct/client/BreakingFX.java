package tconstruct.client;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class BreakingFX extends EntityFX {

    public BreakingFX(World par1World, double par2, double par4, double par6, Item par8Item) {
        this(par1World, par2, par4, par6, par8Item, 0);
    }

    public BreakingFX(World par1World, double par2, double par4, double par6, Item par8Item, int par9) {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.setParticleIcon(par8Item.getIconFromDamage(par9));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Blocks.snow.blockParticleGravity;
        this.particleScale /= 2.0F;
    }

    public BreakingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12,
            Item par14Item, int par15) {
        this(par1World, par2, par4, par6, par14Item, par15);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }

    @Override
    public int getFXLayer() {
        return 2;
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6,
            float par7) {
        float f6 = ((float) this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = ((float) this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null) {
            f6 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
            f7 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
            f8 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
            f9 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
        }

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) par2 - interpPosZ);
        float f14 = 1.0F;
        par1Tessellator.setColorOpaque_F(f14 * this.particleRed, f14 * this.particleGreen, f14 * this.particleBlue);
        par1Tessellator.addVertexWithUV(
                f11 - par3 * f10 - par6 * f10,
                f12 - par4 * f10,
                f13 - par5 * f10 - par7 * f10,
                f6,
                f9);
        par1Tessellator.addVertexWithUV(
                f11 - par3 * f10 + par6 * f10,
                f12 + par4 * f10,
                f13 - par5 * f10 + par7 * f10,
                f6,
                f8);
        par1Tessellator.addVertexWithUV(
                f11 + par3 * f10 + par6 * f10,
                f12 + par4 * f10,
                f13 + par5 * f10 + par7 * f10,
                f7,
                f8);
        par1Tessellator.addVertexWithUV(
                f11 + par3 * f10 - par6 * f10,
                f12 - par4 * f10,
                f13 + par5 * f10 - par7 * f10,
                f7,
                f9);
    }
}
