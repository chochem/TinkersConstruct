package tconstruct.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.crafting.PatternBuilder.MaterialSet;
import tconstruct.library.tools.ArrowMaterial;
import tconstruct.library.tools.BowMaterial;
import tconstruct.library.tools.DynamicToolPart;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.HarvestLevels;
import tconstruct.library.util.IToolPart;
import tconstruct.tools.items.ToolPart;
import tconstruct.util.config.PHConstruct;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;

public class RecipeHandlerToolMaterials extends RecipeHandlerBase {

    public class CachedToolMaterialsRecipe extends CachedBaseRecipe {

        public List<PositionedStack> toolParts;
        public ToolMaterial material;
        public BowMaterial bowMaterial;
        public ArrowMaterial arrowMaterial;

        public CachedToolMaterialsRecipe(List<ItemStack> toolParts, int materialID) {
            this.toolParts = new ArrayList<>();
            for (ItemStack stack : toolParts) {
                this.toolParts.add(new PositionedStack(stack, 10, 10));
            }
            this.material = TConstructRegistry.getMaterial(materialID);
        }

        public CachedToolMaterialsRecipe(List<ItemStack> toolParts, int materialID, boolean arrowBow) {
            this.toolParts = new ArrayList<>();
            for (ItemStack stack : toolParts) {
                this.toolParts.add(new PositionedStack(stack, 10, 10));
            }
            this.arrowMaterial = TConstructRegistry.getArrowMaterial(materialID);
            this.bowMaterial = TConstructRegistry.getBowMaterial(materialID);
        }

        @Override
        public PositionedStack getIngredient() {
            return this.toolParts.get((cycleticks / 20) % this.toolParts.size());
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("tconstruct.nei.toolmaterials");
    }

    @Override
    public String getRecipeID() {
        return "tconstruct.tools.materials";
    }

    @Override
    public String getGuiTexture() {
        return "tinker:textures/gui/nei/toolmaterials.png";
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 130);
    }

    @Override
    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(10, 30, 16, 16), this.getRecipeID()));
    }

    @Override
    public void drawExtras(int recipe) {
        CachedToolMaterialsRecipe crecipe = (CachedToolMaterialsRecipe) this.arecipes.get(recipe);
        if (crecipe.material != null) {
            GuiDraw.drawString(EnumChatFormatting.BOLD + crecipe.material.localizedName(), 35, 10, 0x404040, false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.partcrafter4") + crecipe.material.durability,
                    35,
                    20,
                    0x404040,
                    false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.partcrafter5") + crecipe.material.handleModifier + "x",
                    35,
                    30,
                    0x404040,
                    false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.partcrafter11")
                            + Math.round(crecipe.material.durability * crecipe.material.handleModifier),
                    35,
                    40,
                    0x404040,
                    false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.partcrafter6") + crecipe.material.miningspeed / 100F,
                    35,
                    50,
                    0x404040,
                    false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.partcrafter7")
                            + HarvestLevels.getHarvestLevelName(crecipe.material.harvestLevel),
                    35,
                    60,
                    0x404040,
                    false);
            String heart = crecipe.material.attack == 2 ? StatCollector.translateToLocal("gui.partcrafter8")
                    : StatCollector.translateToLocal("gui.partcrafter9");
            if (crecipe.material.attack() % 2 == 0) {
                GuiDraw.drawString(
                        StatCollector.translateToLocal("gui.partcrafter10") + crecipe.material.attack / 2 + heart,
                        35,
                        70,
                        0x404040,
                        false);
            } else {
                GuiDraw.drawString(
                        StatCollector.translateToLocal("gui.partcrafter10") + crecipe.material.attack / 2F + heart,
                        35,
                        70,
                        0x404040,
                        false);
            }
            int abilityY = 85;
            if (crecipe.material.reinforced > 0) {
                GuiDraw.drawString(getReinforcedString(crecipe.material.reinforced), 35, 85, 0x404040, false);
                abilityY += 10;
            }
            String ability = crecipe.material.ability();
            if (ability != null) {
                if (crecipe.material.stonebound != 0) {
                    GuiDraw.drawString(
                            ability + " (" + Math.abs(crecipe.material.stonebound) + ")",
                            35,
                            abilityY,
                            0x404040,
                            false);
                } else {
                    GuiDraw.drawString(ability, 35, abilityY, 0x404040, false);
                }
            }
        }
        if (crecipe.bowMaterial != null) {
            int y = 20;
            int x = 35;
            GuiDraw.drawString(
                    EnumChatFormatting.BOLD + StatCollector.translateToLocal("tconstruct.nei.projectilematerials"),
                    35,
                    10,
                    0x404040,
                    false);
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.toolstation6") + crecipe.bowMaterial.drawspeed,
                    x,
                    y,
                    0x404040,
                    false);
            y += 10;
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.toolstation7") + crecipe.bowMaterial.flightSpeedMax,
                    x,
                    y,
                    0x404040,
                    false);
        }
        if (crecipe.arrowMaterial != null) {
            int y = 50;
            int x = 35;
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.toolstation8") + crecipe.arrowMaterial.mass,
                    x,
                    y,
                    0x404040,
                    false);
            y += 10;
            GuiDraw.drawString(
                    StatCollector.translateToLocal("gui.toolstation22") + crecipe.arrowMaterial.breakChance,
                    x,
                    y,
                    0x404040,
                    false);
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeID())) {
            ToolMaterial mat;
            for (int matID : TConstructRegistry.toolMaterials.keySet()) {
                List<ItemStack> toolParts = new ArrayList<>();

                mat = TConstructRegistry.toolMaterials.get(matID);
                for (ItemKey key : PatternBuilder.instance.materials) {
                    MaterialSet set = PatternBuilder.instance.materialSets.get(key.key);
                    if (set.materialID == matID) {
                        ItemStack stack = new ItemStack(key.item, 1, key.damage);
                        toolParts.add(stack);
                    }
                }
                for (List list : TConstructRegistry.patternPartMapping.keySet()) {
                    if ((Integer) list.get(2) == matID) {
                        toolParts.add(TConstructRegistry.patternPartMapping.get(list));
                    }
                }
                if (!PHConstruct.craftMetalTools) {
                    for (CastingRecipe recipe : TConstructRegistry.getTableCasting().getCastingRecipes()) {
                        ItemStack castResult = recipe.getResult();
                        if (castResult.getItem() instanceof IToolPart) {
                            if (((IToolPart) castResult.getItem()).getMaterialID(castResult) == matID) {
                                toolParts.add(castResult);
                            }
                        }
                    }
                }

                if (toolParts.size() > 0) {
                    this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, matID));
                    this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, matID, true));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingred) {
        if (ingred.getItem() instanceof ToolPart || ingred.getItem() instanceof DynamicToolPart) {
            int materialID = ((IToolPart) ingred.getItem()).getMaterialID(ingred);
            if (materialID >= 0) {
                List toolParts = getSingleList(ingred);
                if (toolParts.size() > 0) {
                    this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, materialID));
                    this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, materialID, true));
                }
            }
        } else if (PatternBuilder.instance.getPartID(ingred) < Short.MAX_VALUE) {
            int materialID = PatternBuilder.instance.getPartID(ingred);
            List toolParts = getSingleList(ingred);
            if (toolParts.size() > 0) {
                this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, materialID));
                this.arecipes.add(new CachedToolMaterialsRecipe(toolParts, materialID, true));
            }
        } else {
            super.loadUsageRecipes(ingred);
        }
    }

    public static String getReinforcedString(int reinforced) {
        if (reinforced > 9) return "Unbreakable";
        String ret = "Reinforced ";
        switch (reinforced) {
            case 1:
                ret += "I";
                break;
            case 2:
                ret += "II";
                break;
            case 3:
                ret += "III";
                break;
            case 4:
                ret += "IV";
                break;
            case 5:
                ret += "V";
                break;
            case 6:
                ret += "VI";
                break;
            case 7:
                ret += "VII";
                break;
            case 8:
                ret += "VIII";
                break;
            case 9:
                ret += "IX";
                break;
            default:
                ret += "X";
                break;
        }
        return ret;
    }
}
