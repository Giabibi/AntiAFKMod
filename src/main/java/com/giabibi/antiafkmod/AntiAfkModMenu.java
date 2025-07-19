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
        ConfigCategory movement = builder.getOrCreateCategory(Text.of("Mouvements"));
        ConfigCategory look = builder.getOrCreateCategory(Text.of("Regard"));

        general.addEntry(entry.startBooleanToggle(Text.of("Activer le mod"), AntiAfkModClient.config.enabled)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.enabled = newValue)
                .setTooltip(Text.of("Active ou désactive complètement le mod"))
                .build());

        general.addEntry(entry.startFloatField(Text.of("Intervalle minimum (minutes)"), AntiAfkModClient.config.minIntervalMinutes)
                .setDefaultValue(30.0f)
                .setMin(0.1f)
                .setMax(240.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.minIntervalMinutes = newValue)
                .setTooltip(Text.of("Délai minimum entre deux actions anti-AFK"))
                .build());

        general.addEntry(entry.startFloatField(Text.of("Intervalle maximum (minutes)"), AntiAfkModClient.config.maxIntervalMinutes)
                .setDefaultValue(60.0f)
                .setMin(0.1f)
                .setMax(240.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxIntervalMinutes = newValue)
                .setTooltip(Text.of("Délai maximum entre deux actions anti-AFK"))
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Bouger la tête (look)"), AntiAfkModClient.config.look)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.look = newValue)
                .setTooltip(Text.of("Active les mouvements de tête pour simuler un comportement réel"))
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Bouger le joueur (move)"), AntiAfkModClient.config.move)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.move = newValue)
                .setTooltip(Text.of("Active de petits déplacements du joueur"))
                .build());

        general.addEntry(entry.startBooleanToggle(Text.of("Faire sauter (jump)"), AntiAfkModClient.config.jump)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.jump = newValue)
                .setTooltip(Text.of("Effectue un saut aléatoire de temps en temps"))
                .build());

        look.addEntry(entry.startIntField(Text.of("Nombre d'étapes du mouvement de tête"), AntiAfkModClient.config.headMovementSteps)
                .setDefaultValue(20)
                .setMin(1)
                .setMax(20)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.headMovementSteps = newValue)
                .setTooltip(Text.of("Nombre d'étapes pour un mouvement de tête fluide"))
                .build());

        look.addEntry(entry.startIntField(Text.of("Délai entre les étapes (ms)"), AntiAfkModClient.config.headMovementDelayMs)
                .setDefaultValue(10)
                .setMin(10)
                .setMax(1000)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.headMovementDelayMs = newValue)
                .setTooltip(Text.of("Délai (en millisecondes) entre chaque étape du mouvement de tête"))
                .build());

        look.addEntry(entry.startFloatField(Text.of("Puissance max rotation Yaw"), AntiAfkModClient.config.maxYawStrength)
                .setDefaultValue(90.0f)
                .setMin(0.0f)
                .setMax(180.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxYawStrength = newValue)
                .setTooltip(Text.of("Amplitude horizontale maximale de la rotation de la tête (en degrés)"))
                .build());

        look.addEntry(entry.startFloatField(Text.of("Puissance max rotation Pitch"), AntiAfkModClient.config.maxPitchStrength)
                .setDefaultValue(30.0f)
                .setMin(0.0f)
                .setMax(60.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxPitchStrength = newValue)
                .setTooltip(Text.of("Amplitude verticale maximale de la rotation de la tête (en degrés)"))
                .build());

        look.addEntry(entry.startIntField(Text.of("Delay between head moves (ms)"), AntiAfkModClient.config.headRepeatDelayMs)
                .setDefaultValue(100)
                .setMin(10)
                .setMax(1000)
                .setTooltip(Text.of("Delay between each head rotation when repeating"))
                .setSaveConsumer(newValue -> AntiAfkModClient.config.headRepeatDelayMs = newValue)
                .build());

        look.addEntry(entry.startIntField(Text.of("Min head movements"), AntiAfkModClient.config.minHeadRepeats)
                .setDefaultValue(1)
                .setMin(1)
                .setMax(20)
                .setTooltip(Text.of("Minimum head movement repetitions per anti-AFK event"))
                .setSaveConsumer(newValue -> AntiAfkModClient.config.minHeadRepeats = newValue)
                .build());

        look.addEntry(entry.startIntField(Text.of("Max head movements"), AntiAfkModClient.config.maxHeadRepeats)
                .setDefaultValue(5)
                .setMin(1)
                .setMax(20)
                .setTooltip(Text.of("Maximum head movement repetitions per anti-AFK event"))
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxHeadRepeats = newValue)
                .build());


        movement.addEntry(entry.startFloatField(Text.of("Intensité aléatoire de mouvement"), AntiAfkModClient.config.maxMoveIntensity)
                .setDefaultValue(1.0f)
                .setMin(0.0f)
                .setMax(1.0f)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxMoveIntensity = newValue)
                .setTooltip(Text.of("Facteur de randomisation des déplacements latéraux"))
                .build());

        movement.addEntry(entry.startDoubleField(Text.of("Distance max de mouvement"), AntiAfkModClient.config.maxMoveDistance)
                .setDefaultValue(0.2)
                .setMin(0.01)
                .setMax(2.0)
                .setSaveConsumer(newValue -> AntiAfkModClient.config.maxMoveDistance = newValue)
                .setTooltip(Text.of("Distance maximale de déplacement lors d'une action anti-AFK"))
                .build());

        builder.setSavingRunnable(AntiAfkModClient::saveConfig);

        return builder.build();
    }
}
