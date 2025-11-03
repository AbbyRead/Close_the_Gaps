package btw.community.abbyread.ctg.mixin;

import btw.item.items.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolItem.class)
public class ToolItemMixin {

    @Inject(method = "getVisualRollOffsetAsBlock", at = @At("RETURN"), cancellable = true, remap = false)
    private void nudgePositionToFixZFighting(CallbackInfoReturnable<Float> cir) {
        float value = cir.getReturnValue();
        cir.setReturnValue(value + 0.5f);
    }

}
