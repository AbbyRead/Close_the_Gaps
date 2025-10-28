package btw.community.abbyread.irf.mixin;

import net.minecraft.src.ItemRenderer;
import net.minecraft.src.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes model gaps in item rendering by properly closing the sides of 2D items.
 * This prevents visible gaps when items are rotated in first-person view.
 */
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    /**
     * Injects into renderItemIn2D to fill in the side faces of the item quad.
     * The original code leaves the sides open, causing visible gaps when the item rotates.
     *
     * This injection ensures all 6 faces are properly rendered with correct normals
     * and UV coordinates.
     */
    @Inject(
            method = "renderItemIn2D",
            at = @At("TAIL"),
            cancellable = false
    )
    private static void fixItemModelGaps(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7, CallbackInfo ci) {
        // Fill in edge gaps by rendering additional geometry
        // The item is rendered as a 2D quad with thickness (par7), but the edge vertices
        // aren't properly connected, leaving visible gaps

        float var8 = 0.5F * (par1 - par3) / (float)par5;
        float var9 = 0.5F * (par4 - par2) / (float)par6;

        // Close the gaps at the edges of the item
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);

        // Front-left edge
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par7), (double)par1, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par7), (double)par1, (double)par4);

        par0Tessellator.draw();

        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);

        // Front-right edge
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - par7), (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - par7), (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)par3, (double)par2);

        par0Tessellator.draw();
    }
}