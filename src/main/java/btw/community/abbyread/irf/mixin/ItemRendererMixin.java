package btw.community.abbyread.irf.mixin;

import btw.community.abbyread.irf.util.RenderFixHelper;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes model gaps in item rendering by adjusting UV coordinates and quad geometry.
 *
 * Based on the modern "Model Gap FIX" mod approach:
 * 1. Removes texture atlas shrinking that causes UV padding
 * 2. Slightly expands quads to cover gaps
 * 3. Adjusts vertex positions to center quads within texture bounds
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    /**
     * Injects into renderItemIn2D to replace the entire rendering logic
     * with our gap-fixed version.
     */
    @Inject(
            method = "renderItemIn2D",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void fixItemModelGaps(Tessellator par0Tessellator, float par1, float par2,
                                         float par3, float par4, int par5, int par6,
                                         float par7, CallbackInfo ci) {
        // Apply UV adjustment to counteract atlas shrinking
        // The texture coordinates are padded inward by a small amount to prevent bleeding
        // We need to expand them outward slightly

        float uvExpansion = RenderFixHelper.UV_EXPANSION; // Typically 0.001-0.002

        float adjPar1 = par1 - uvExpansion;  // Expand min U outward
        float adjPar3 = par3 + uvExpansion;  // Expand max U outward
        float adjPar2 = par2 - uvExpansion;  // Expand min V outward
        float adjPar4 = par4 + uvExpansion;  // Expand max V outward

        // Apply quad geometry adjustment
        float quadExpansion = RenderFixHelper.QUAD_EXPANSION;      // Typically 0.002-0.008
        float quadIndent = RenderFixHelper.QUAD_INDENT;            // Typically 0.001-0.007

        // Render front face with adjusted UVs and expanded geometry
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, 1.0f);
        par0Tessellator.addVertexWithUV(-quadExpansion, -quadIndent, 0.0, adjPar1, adjPar4);
        par0Tessellator.addVertexWithUV(1.0 + quadExpansion, -quadIndent, 0.0, adjPar3, adjPar4);
        par0Tessellator.addVertexWithUV(1.0 + quadExpansion, 1.0 + quadIndent, 0.0, adjPar3, adjPar2);
        par0Tessellator.addVertexWithUV(-quadExpansion, 1.0 + quadIndent, 0.0, adjPar1, adjPar2);
        par0Tessellator.draw();

        // Render back face with adjusted UVs and expanded geometry
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, -1.0f);
        par0Tessellator.addVertexWithUV(-quadExpansion, 1.0 + quadIndent, 0.0f - par7, adjPar1, adjPar2);
        par0Tessellator.addVertexWithUV(1.0 + quadExpansion, 1.0 + quadIndent, 0.0f - par7, adjPar3, adjPar2);
        par0Tessellator.addVertexWithUV(1.0 + quadExpansion, -quadIndent, 0.0f - par7, adjPar3, adjPar4);
        par0Tessellator.addVertexWithUV(-quadExpansion, -quadIndent, 0.0f - par7, adjPar1, adjPar4);
        par0Tessellator.draw();

        // Side faces remain largely the same but with adjusted expansion
        float var8 = 0.5f * (par1 - par3) / (float)par5;
        float var9 = 0.5f * (par4 - par2) / (float)par6;

        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < par5; ++var10) {
            float var11 = (float)var10 / (float)par5;
            float var12 = par1 + (par3 - par1) * var11 - var8;
            par0Tessellator.addVertexWithUV(var11 - quadExpansion, -quadIndent, 0.0f - par7, var12, adjPar4);
            par0Tessellator.addVertexWithUV(var11 - quadExpansion, -quadIndent, 0.0, var12, adjPar4);
            par0Tessellator.addVertexWithUV(var11 - quadExpansion, 1.0 + quadIndent, 0.0, var12, adjPar2);
            par0Tessellator.addVertexWithUV(var11 - quadExpansion, 1.0 + quadIndent, 0.0f - par7, var12, adjPar2);
        }
        par0Tessellator.draw();

        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < par5; ++var10) {
            float var11 = (float)var10 / (float)par5;
            float var12 = par1 + (par3 - par1) * var11 - var8;
            float var13 = var11 + 1.0f / (float)par5;
            par0Tessellator.addVertexWithUV(var13 + quadExpansion, 1.0 + quadIndent, 0.0f - par7, var12, adjPar2);
            par0Tessellator.addVertexWithUV(var13 + quadExpansion, 1.0 + quadIndent, 0.0, var12, adjPar2);
            par0Tessellator.addVertexWithUV(var13 + quadExpansion, -quadIndent, 0.0, var12, adjPar4);
            par0Tessellator.addVertexWithUV(var13 + quadExpansion, -quadIndent, 0.0f - par7, var12, adjPar4);
        }
        par0Tessellator.draw();

        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int var10 = 0; var10 < par6; ++var10) {
            float var11 = (float)var10 / (float)par6;
            float var12 = par4 + (par2 - par4) * var11 - var9;
            float var13 = var11 + 1.0f / (float)par6;
            par0Tessellator.addVertexWithUV(-quadExpansion, var13 + quadIndent, 0.0, adjPar1, var12);
            par0Tessellator.addVertexWithUV(1.0 + quadExpansion, var13 + quadIndent, 0.0, adjPar3, var12);
            par0Tessellator.addVertexWithUV(1.0 + quadExpansion, var13 + quadIndent, 0.0f - par7, adjPar3, var12);
            par0Tessellator.addVertexWithUV(-quadExpansion, var13 + quadIndent, 0.0f - par7, adjPar1, var12);
        }
        par0Tessellator.draw();

        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int var10 = 0; var10 < par6; ++var10) {
            float var11 = (float)var10 / (float)par6;
            float var12 = par4 + (par2 - par4) * var11 - var9;
            par0Tessellator.addVertexWithUV(1.0 + quadExpansion, var11 - quadIndent, 0.0, adjPar3, var12);
            par0Tessellator.addVertexWithUV(-quadExpansion, var11 - quadIndent, 0.0, adjPar1, var12);
            par0Tessellator.addVertexWithUV(-quadExpansion, var11 - quadIndent, 0.0f - par7, adjPar1, var12);
            par0Tessellator.addVertexWithUV(1.0 + quadExpansion, var11 - quadIndent, 0.0f - par7, adjPar3, var12);
        }
        par0Tessellator.draw();

        // Cancel the original method execution
        ci.cancel();
    }
}
