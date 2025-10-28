package btw.community.abbyread.irf.mixin;

import net.minecraft.src.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
/*
    @Shadow
    public abstract void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5);

    *//**
     * Injects before the item is rendered in the GUI.
     * We push the item "forward" slightly on the Z-axis to prevent it from
     * Z-fighting (flickering) with the GUI slot background.
     *//*
    @Inject(
            method = "renderItemIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/TextureManager;Lnet/minecraft/src/ItemStack;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/ItemRenderer;renderItemAndEffectIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/TextureManager;Lnet/minecraft/src/ItemStack;II)V"
            )
    )
    private void beforeRenderInGui(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, CallbackInfo ci) {
        // Use our helper class to apply the offset
        RenderFixHelper.applyGuiZOffset();
    }

    *//**
     * Injects after the item is rendered to restore the Z-level,
     * so it doesn't mess up subsequent rendering.
     *//*
    @Inject(
            method = "renderItemIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/TextureManager;Lnet/minecraft/src/ItemStack;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/ItemRenderer;renderItemAndEffectIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/TextureManager;Lnet/minecraft/src/ItemStack;II)V",
                    shift = At.Shift.AFTER
            )
    )
    private void afterRenderInGui(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, CallbackInfo ci) {
        // Use our helper class to remove the offset
        RenderFixHelper.restoreGuiZOffset();
    }*/
}