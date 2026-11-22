package com.example.antieffects;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class AntiEffectsClient implements ClientModInitializer {
    public static boolean noFogAndEffects = false;
    public static boolean forceGlowing = false;

    private static KeyMapping toggleEffectsKey;
    private static KeyMapping toggleGlowKey;

    @Override
    public void onInitializeClient() {
        // Клавиша K (InputConstants вместо GLFW в 26.3)
        toggleEffectsKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.antieffects.toggle_effects",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_K,
                KeyMapping.Category.MISC
        ));

        // Клавиша N
        toggleGlowKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.antieffects.toggle_glow",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_N,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleEffectsKey.consumeClick()) {
                noFogAndEffects = !noFogAndEffects;
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("§eАнти-туман и эффекты: " + (noFogAndEffects ? "§aВКЛ" : "§cВЫКЛ")),
                            true
                    );
                }
            }

            while (toggleGlowKey.consumeClick()) {
                forceGlowing = !forceGlowing;
                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("§eПодсветка сущностей: " + (forceGlowing ? "§aВКЛ" : "§cВЫКЛ")),
                            true
                    );
                }
            }
        });
    }
}

