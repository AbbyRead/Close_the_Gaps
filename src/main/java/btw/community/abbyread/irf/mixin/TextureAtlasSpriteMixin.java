package btw.community.abbyread.irf.mixin;

import net.minecraft.src.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes model gaps by removing texture atlas shrinking.
 *
 * The vanilla TextureAtlasSprite applies inward padding to UV coordinates
 * to prevent atlas bleeding (neighboring textures bleeding into this one).
 * However, this padding causes visible gaps in item models, especially 2D items.
 *
 * This mixin removes that padding entirely, which fixes the gaps.
 * Note: If your texture pack has atlas bleeding issues, this may not work for you.
 */
@Mixin(TextureAtlasSprite.class)
public abstract class TextureAtlasSpriteMixin {
    @Shadow
    protected boolean rotated;
    @Shadow
    protected int originX;
    @Shadow
    protected int originY;
    @Shadow
    protected int width;
    @Shadow
    protected int height;
    @Shadow
    private float minU;
    @Shadow
    private float maxU;
    @Shadow
    private float minV;
    @Shadow
    private float maxV;

    /**
     * Replaces initSprite to remove the UV padding that causes gaps.
     *
     * Original code:
     *   float var6 = (float)(0.009999999776482582D / (double)par1);
     *   float var7 = (float)(0.009999999776482582D / (double)par2);
     *   this.minU = (float)par3 / (float)((double)par1) + var6;  // Adds padding
     *   this.maxU = (float)(par3 + this.width) / (float)((double)par1) - var6;  // Subtracts padding
     *
     * Our version removes var6 and var7, using 0.0 instead:
     *   this.minU = (float)par3 / (float)((double)par1);  // No padding
     *   this.maxU = (float)(par3 + this.width) / (float)((double)par1);  // No padding
     * @author AbbyRead
     * @reason good
     */
    @Overwrite
    public void initSprite(int par1, int par2, int par3, int par4, boolean par5)
    {
        this.originX = par3;
        this.originY = par4;
        this.rotated = par5;

        // REMOVED: The padding that causes gaps
        // float var6 = (float)(0.009999999776482582D / (double)par1);
        // float var7 = (float)(0.009999999776482582D / (double)par2);

        // Use exact texture coordinates with NO padding
        this.minU = (float)par3 / (float)((double)par1);
        this.maxU = (float)(par3 + this.width) / (float)((double)par1);
        this.minV = (float)par4 / (float)par2;
        this.maxV = (float)(par4 + this.height) / (float)par2;
    }
}