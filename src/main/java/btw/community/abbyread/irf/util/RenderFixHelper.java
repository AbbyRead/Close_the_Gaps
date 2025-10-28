package btw.community.abbyread.irf.util;

import org.lwjgl.opengl.GL11;

/**
 * Helper utility for rendering fixes.
 *
 * Values are tunable via configuration to adapt to different texture resolutions
 * and hardware characteristics (similar to the modern Model Gap FIX mod).
 */
public class RenderFixHelper {

    /**
     * UV expansion to counteract atlas shrinking.
     * Remove the inward padding applied to prevent atlas bleeding.
     * Range: 0.0 - 0.01, typically 0.001-0.002
     */
    public static final float UV_EXPANSION = 0.000F;

    /**
     * Quad expansion increment - enlarges each quad to hide gaps.
     * Range: 0.0 - 0.1, typically 0.002-0.008
     * Note: Higher values may increase quad count slightly on HD texture packs.
     */
    public static final float QUAD_EXPANSION = 0.0F;

    /**
     * Quad x/y offset - moves quads toward the center of the item.
     * Range: 0.0 - 0.1, typically 0.001-0.007
     */
    public static final float QUAD_INDENT = 0.0F;

    /**
     * GUI Z-offset to prevent Z-fighting in inventory.
     */
    public static final float GUI_Z_OFFSET = 50.0F;

    /**
     * Applies a Z-offset to prevent Z-fighting in the GUI.
     * Must be paired with restoreGuiZOffset().
     */
    public static void applyGuiZOffset() {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, GUI_Z_OFFSET);
    }

    /**
     * Restores the GL state after applyGuiZOffset() was called.
     */
    public static void restoreGuiZOffset() {
        GL11.glPopMatrix();
    }
}