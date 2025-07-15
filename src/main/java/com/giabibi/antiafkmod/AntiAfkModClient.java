package com.giabibi.antiafkmod;

import java.util.Timer;
import java.util.TimerTask;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class AntiAfkModClient implements ClientModInitializer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    // Key enable/disable
    private static KeyBinding toggleAfkKey;
    private static boolean antiAfkEnabled = false;

    // Intervalle
    private static Timer afkTimer;

    // Config
    public static AntiAfkConfig config = new AntiAfkConfig();

    public static void saveConfig() {
        AntiAfkConfig.save(config);
        restartAfkTimer();
    }

    public static void restartAfkTimer() {
        if (afkTimer != null) {
            afkTimer.cancel();
        }
        startAfkTimer();
    }

    private static void startAfkTimer() {
        if (!antiAfkEnabled) return;

        afkTimer = new Timer();
        afkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player == null || client.world == null || !antiAfkEnabled) return;

                if (config.look) {
                    float currentYaw = client.player.getYaw();
                    float currentPitch = client.player.getPitch();
                    float deltaYaw = (float)(Math.random() * 2.0 - 1.0);
                    float deltaPitch = (float)(Math.random() * 1.0 - 0.5);
                    client.player.setYaw(currentYaw + deltaYaw);
                    client.player.setPitch(currentPitch + deltaPitch);
                }

                if (config.move) {
                    double dx = (Math.random() - 0.5) * config.maxMoveDistance;
                    double dz = (Math.random() - 0.5) * config.maxMoveDistance;
                    double newX = client.player.getX() + dx;
                    double newZ = client.player.getZ() + dz;
                    double y = client.player.getY();
                    client.player.updatePosition(newX, y, newZ);
                }

                if (config.jump) {
                    client.player.jump();
                }

                System.out.println("[AntiAFK] Action anti-AFK exécutée");
            }
        }, 0, config.intervalMinutes * 60L * 1000L);
    }

    @Override
    public void onInitializeClient() {
        config = AntiAfkConfig.load();

        antiAfkEnabled = config.enabled;
        startAfkTimer();
        InputUtil.Key key = InputUtil.fromTranslationKey(config.keyToggleAfk);
        toggleAfkKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.antiafkmod.toggle",
            key.getCode(),
            "key.categories.antiafkmod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleAfkKey.wasPressed()) {
                antiAfkEnabled = !antiAfkEnabled;
                client.player.sendMessage(
                    net.minecraft.text.Text.of("[AntiAFK] Mode " + (antiAfkEnabled ? "activé" : "désactivé")),
                    false
                );
                restartAfkTimer();
            }
        });

        System.out.println("[AntiAfkMod] Mod client initialisé avec raccourci K");
    }
}
