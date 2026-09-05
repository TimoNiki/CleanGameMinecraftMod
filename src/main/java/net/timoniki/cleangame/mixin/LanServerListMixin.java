package net.timoniki.cleangame.mixin;

import net.minecraft.client.server.LanServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.timoniki.cleangame.CleanGameConfig;

import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(targets = "net.minecraft.client.server.LanServerDetection$LanServerList")
public class LanServerListMixin {

    @Unique
    private static final ConcurrentHashMap<String, AtomicInteger> ipServerCount = new ConcurrentHashMap<>();
    @Unique
    private static final int MAX_SERVERS_PER_IP = 7;

    @Inject(method = "addServer", at = @At("HEAD"), cancellable = true)
    private void onAddServer(String motd, InetAddress address, CallbackInfo ci) {
        if (!CleanGameConfig.isAntiSpamEnabled) {
            return;
        }

        if (address == null) return;

        String ipAddress = address.getHostAddress();
        AtomicInteger count = ipServerCount.computeIfAbsent(ipAddress, k -> new AtomicInteger(0));

        if (count.get() >= MAX_SERVERS_PER_IP) {
            ci.cancel(); 
            return;
        }

        count.incrementAndGet();
    }
}
