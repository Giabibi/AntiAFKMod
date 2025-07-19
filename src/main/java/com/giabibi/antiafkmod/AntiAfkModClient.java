package com.giabibi.antiafkmod;

import java.util.Timer;
import java.util.TimerTask;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Formatting;

public class AntiAfkModClient implements ClientModInitializer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    // Key enable/disable
    private static KeyBinding toggleAfkKey;
    private static boolean antiAfkEnabled = false;

    // Show HUD
    private static KeyBinding toggleHudKey;
    private static boolean showHud = true;
    private static long nextAfkTime = 0;

    // Intervalle
    private static Timer afkTimer;

    // Config
    public static AntiAfkConfig config = new AntiAfkConfig();

    private static void moveHeadHumanLike(float deltaYaw, float deltaPitch) {
        int steps = config.headMovementSteps;
            long delayBetweenSteps = config.headMovementDelayMs;

        Timer animationTimer = new Timer();
        for (int i = 1; i <= steps; i++) {
            animationTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (client.player == null) return;
                    float yawIncrement = deltaYaw / steps;
                    float pitchIncrement = deltaPitch / steps;
                    client.player.setYaw(client.player.getYaw() + yawIncrement);
                    client.player.setPitch(client.player.getPitch() + pitchIncrement);
                }
            }, i * delayBetweenSteps);
        }
    }

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

        float min = Math.min(config.minIntervalMinutes, config.maxIntervalMinutes);
        float max = Math.max(config.minIntervalMinutes, config.maxIntervalMinutes);
        float interval = min + (float) Math.random() * (max - min);

        long delay = (long)(interval * 60 * 1000);
        nextAfkTime = System.currentTimeMillis() + delay;

        afkTimer = new Timer();
        afkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (client.player == null || client.world == null || !antiAfkEnabled) return;

                if (config.look) {
                    int repeats = config.minHeadRepeats + (int)(Math.random() * (config.maxHeadRepeats - config.minHeadRepeats + 1));
                    for (int i = 0; i < repeats; i++) {
                        float yawStrength = (float)(Math.random() * config.maxYawStrength);
                        float pitchStrength = (float)(Math.random() * config.maxPitchStrength);
                        float deltaYaw = (float)((Math.random() * 2.0 - 1.0) * yawStrength);
                        float deltaPitch = (float)((Math.random() * 2.0 - 1.0) * pitchStrength);
                        moveHeadHumanLike(deltaYaw, deltaPitch);
                        try {
                            Thread.sleep(config.headRepeatDelayMs);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (config.move) {
                    double intensity = Math.random() * config.maxMoveIntensity;
                    double moveDistance = intensity * config.maxMoveDistance;
                    double angle = Math.random() * 2 * Math.PI;
                    double dx = Math.cos(angle) * moveDistance;
                    double dz = Math.sin(angle) * moveDistance;
                    double newX = client.player.getX() + dx;
                    double newZ = client.player.getZ() + dz;
                    double y = client.player.getY();
                    client.player.updatePosition(newX, y, newZ);
                }

                if (config.jump) {
                    client.player.jump();
                }

                System.out.println("[AntiAFK] Action anti-AFK exécutée");

                startAfkTimer();
            }
        }, delay);
    }

    @Override
    public void onInitializeClient() {
        config = AntiAfkConfig.load();

        antiAfkEnabled = config.enabled;
        showHud = config.showHudTimer;
        startAfkTimer();
        InputUtil.Key afkKey = InputUtil.fromTranslationKey(config.keyToggleAfk);
        toggleAfkKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.antiafkmod.toggle",
            afkKey.getCode(),
            "key.categories.antiafkmod"
        ));
        InputUtil.Key hudKey = InputUtil.fromTranslationKey(config.keyToggleHud);
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.antiafkmod.togglehud",
            hudKey.getCode(),
            "key.categories.antiafkmod"
        ));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleAfkKey.wasPressed()) {
                antiAfkEnabled = !antiAfkEnabled;
                client.player.sendMessage(
                        net.minecraft.text.Text.literal("[AntiAFK] Mode " + (antiAfkEnabled ? "activé" : "désactivé"))
                                .formatted(antiAfkEnabled ? Formatting.GREEN : Formatting.RED),
                        false);
                restartAfkTimer();
            }
            while (toggleHudKey.wasPressed()) {
                showHud = !showHud;
                client.player.sendMessage(
                    net.minecraft.text.Text.literal("[AntiAFK] HUD " + (showHud ? "enabled" : "disabled"))
                            .formatted(Formatting.AQUA),
                    false
                );
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (!antiAfkEnabled || !showHud) return;

            long now = System.currentTimeMillis();
            long remaining = nextAfkTime - now;

            if (remaining > 0) {
                int seconds = (int) (remaining / 1000) % 60;
                int minutes = (int) (remaining / (60 * 1000));

                String text = String.format("Prochain AFK: %02d:%02d", minutes, seconds);
                int x = config.hudX;
                int y = config.hudY;

                drawContext.drawText(client.textRenderer, text, x, y, 0xFFFFFF, true);

                int textWidth = client.textRenderer.getWidth(text);
                int padding = 4;
                int bgX1 = x - padding;
                int bgY1 = y - padding;
                int bgX2 = x + textWidth + padding;
                int bgY2 = y + 10 + padding;

                drawContext.fill(bgX1, bgY1, bgX2, bgY2, 0x90000000);
                drawContext.drawText(client.textRenderer, text, x, y, 0xFFFFFF, true);
            }
        });


        System.out.println("[AntiAfkMod] Mod client initialisé");
    }
}
