package dev.thedripman.mixin;

import dev.thedripman.EyeAssist;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
	@ModifyVariable(
			method = "updateMouse()V",
			at = @At("STORE"),
			ordinal = 2
	)
	private double modifySens(double original) {
		return EyeAssist.assistKey.isPressed() ? original * 0.001 : original;
	}
}