package btw.community.abbyread.irf.util;

import org.lwjgl.opengl.GL11;

/**
 * Helper utility for rendering fixes.
 * Currently used for managing GL state during item rendering.
 */
public class RenderFixHelper {

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