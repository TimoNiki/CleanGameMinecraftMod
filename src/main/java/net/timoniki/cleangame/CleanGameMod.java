package net.timoniki.cleangame;

import net.fabricmc.api.ClientModInitializer;

public class CleanGameMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CleanGameConfig.load();
        System.out.println("[CleanGame] Защита от спама успешно запущена!");
    }
}
