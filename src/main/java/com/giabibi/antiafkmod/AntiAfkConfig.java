package com.giabibi.antiafkmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class AntiAfkConfig {
    public boolean enabled = false;
    public float minIntervalMinutes = 30.0f;
    public float maxIntervalMinutes = 60.0f;
    public boolean move = true;
    public boolean look = true;
    public boolean jump = false;
    public double maxMoveDistance = 0.2;
    public String keyToggleAfk = "key.keyboard.k";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/antiafkmod.json");

    public static AntiAfkConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, AntiAfkConfig.class);
            } catch (IOException e) {
                System.err.println("[AntiAFK] Erreur lors de la lecture du fichier de config.");
                e.printStackTrace();
            }
        }

        // Fichier inexistant : créer un fichier par défaut
        AntiAfkConfig config = new AntiAfkConfig();
        save(config);
        return config;
    }

    public static void save(AntiAfkConfig config) {
        try {
            if (!CONFIG_FILE.getParentFile().exists()) {
                CONFIG_FILE.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            System.err.println("[AntiAFK] Erreur lors de la sauvegarde du fichier de config.");
            e.printStackTrace();
        }
    }
}
