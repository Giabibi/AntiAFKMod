package com.giabibi.antiafkmod;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class AntiAfkModMenu {
    public static Screen createScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Configuration Anti-AFK"));

        ConfigEntryBuilder entry = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.of("Général"));

        general.addEntry(entry.startBooleanToggle(Text.of("Activer le mod"), AntiAfkModClient.config.enabled)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.enabled = newValue)
                .build());

        general.addEntry(entry.startIntField(Text.of("Nombre d'étapes du mouvement de tête"), AntiAfkModClient.config.headMovementSteps)
                .setDefaultValue(5)
                .setMin(1)
                .setMax(20)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.headMovementSteps = newValue)
                .build());

        general.addEntry(entry.startIntField(Text.of("Délai entre les étapes (ms)"), AntiAfkModClient.config.headMovementDelayMs)
                .setDefaultValue(150)
                .setMin(10)
                .setMax(1000)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.headMovementDelayMs = newValue)
                .build());

        general.addEntry(entry.startFloatField(Text.of("Intervalle minimum (minutes)"), AntiAfkModClient.config.minIntervalMinutes)
                .setDefaultValue(30.0f)
                .setMin(1.0f)
                .setMax(240.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.minIntervalMinutes = newValue)
                .build());

        general.addEntry(entry.startFloatField(Text.of("Intervalle maximum (minutes)"), AntiAfkModClient.config.maxIntervalMinutes)
                .setDefaultValue(60.0f)
                .setMin(1.0f)
                .setMax(240.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxIntervalMinutes = newValue)
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Bouger la tête (look)"), AntiAfkModClient.config.look)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.look = newValue)
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Bouger le joueur (move)"), AntiAfkModClient.config.move)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.move = newValue)
                .build());

        general.addEntry(entry.startFloatField(Text.of("Puissance max rotation Yaw"), AntiAfkModClient.config.maxYawStrength)
                .setDefaultValue(3.0f)
                .setMin(0.0f)
                .setMax(10.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxYawStrength = newValue)
                .build());

        general.addEntry(entry.startFloatField(Text.of("Puissance max rotation Pitch"), AntiAfkModClient.config.maxPitchStrength)
                .setDefaultValue(2.0f)
                .setMin(0.0f)
                .setMax(10.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxPitchStrength = newValue)
                .build());

        general.addEntry(entry.startFloatField(Text.of("Intensité aléatoire de mouvement"), AntiAfkModClient.config.maxMoveIntensity)
                .setDefaultValue(1.0f)
                .setMin(0.0f)
                .setMax(1.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxMoveIntensity = newValue)
                .build());

        general.addEntry(entry.startDoubleField(Text.of("Distance max de mouvement"), AntiAfkModClient.config.maxMoveDistance)
                .setDefaultValue(0.2)
                .setMin(0.01)
                .setMax(2.0)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxMoveDistance = newValue)
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Faire sauter (jump)"), AntiAfkModClient.config.jump)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.jump = newValue)
                .build());

        builder.setSavingRunnable(AntiAfkModClient::saveConfig);

        return builder.build();
    }
}