package me.voidxwalker.options.extra;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.*;
import org.lwjgl.glfw.GLFW;

public class ExtraOptions implements SpeedrunConfig, ClientModInitializer {
    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public static float distortionEffectScale = 1;

    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public static float fovEffectScale = 1;

    public static boolean controlBowFov = false;

    public static boolean controlSubmergedFov = false;

    public static boolean narratorHotkey = true;

    @Config.Ignored
    public static final KeyBinding narratorKeybinding = new KeyBinding("extra-options.narratorKey", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "extra-options.category");;

    @SuppressWarnings("unused")
    private Text getPercentText(float value) {
        return value == 0 ? ScreenTexts.OFF : new LiteralText((int) (value * 100) + "%");
    }

    @Override
    public String modID() {
        return "extra-options";
    }

    @Override
    public boolean shouldParseStaticFields() {
        return true;
    }

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(narratorKeybinding);
    }
}