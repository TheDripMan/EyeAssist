package dev.thedripman;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class EyeAssist implements ClientModInitializer {
	public static final String MOD_ID = "eyeassist";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final KeyBinding assistKey = new KeyBinding("eyeassist.key.bind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "eyeassist.key.category");

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(assistKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (RotationUtil.isPlaying() && assistKey.isPressed()) {
				List<EyeOfEnderEntity> entityList = RotationUtil.getEntitiesAroundPlayer(50F, EyeOfEnderEntity.class);
				if (!entityList.isEmpty()) {
					float[] yp = RotationUtil.getRotationsNeeded(entityList.get(0));
					RotationUtil.setRotations(yp[1], yp[0]);
				}
			}
		});
	}
}