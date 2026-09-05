package net.timoniki.cleangame;

public class CleanGameConfig {
    public static boolean isAntiSpamEnabled = true;
    public static int maxLanServers = 30;

    public static void setMaxLanServers(int value) {
        maxLanServers = value;
    }

    public static void load() {}
}
