package btw.community.abbyread.irf.util;

import org.lwjgl.opengl.GL11;

public class RenderFixHelper {
    // This value pushes the item "forward" (towards the camera)
    // to ensure it renders on top of the slot background.
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