package me.voidxwalker.options.extra.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mouse.class)
public class GameRendererMixin {
    @ModifyVariable(
            method = "updateMouse()V",
            at = @At("STORE"),
            ordinal = 2
    )
    private double modifySens(double original) {
        return original;
    }
}
