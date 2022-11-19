package io.github.epicgo.packetapi;

import io.github.epicgo.packetapi.channel.PacketChannelDuplexHandler;
import io.github.epicgo.packetapi.event.PacketEventHandler;
import io.github.epicgo.packetapi.event.PacketEventListener;
import io.github.epicgo.packetapi.util.PlayerUtil;
import io.netty.channel.Channel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PacketAPI implements Listener {

    private final Map<PacketEventListener, List<Method>> packetListenerMap = new HashMap<>();
    private final String packetHandlerName;

    public PacketAPI(final JavaPlugin plugin, final String packetHandlerName) {
        this.packetHandlerName = packetHandlerName;

        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(this, plugin); // Self player injection
    }

    public void addPacketListener(PacketEventListener... packetEventListeners) {
        for (final PacketEventListener packetEventListener : packetEventListeners) {
            final List<Method> methods = new ArrayList<>();
            for (final Method method : packetEventListener.getClass().getDeclaredMethods()) {
                final PacketEventHandler packetEventHandler = method.getAnnotation(PacketEventHandler.class);
                if (packetEventHandler == null) continue;

                methods.add(method);
            }

            packetListenerMap.put(packetEventListener, methods);
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        Channel channel = PlayerUtil.getChannel(player);
        final PacketChannelDuplexHandler duplexHandler = new PacketChannelDuplexHandler(this, player);
        channel.pipeline().addBefore("packet_handler", this.packetHandlerName, duplexHandler);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Channel channel = PlayerUtil.getChannel(player);
        if (channel == null) return;

        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(this.packetHandlerName);
            return null;
        });
    }
}
