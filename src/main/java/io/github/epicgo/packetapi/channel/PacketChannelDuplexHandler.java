package io.github.epicgo.packetapi.channel;

import io.github.epicgo.packetapi.event.PacketEventHandler;
import io.github.epicgo.packetapi.event.impl.PacketReceiveEvent;
import io.github.epicgo.packetapi.event.impl.PacketSendEvent;
import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import io.github.epicgo.packetapi.wrapper.PacketWrapperType;
import io.github.epicgo.packetapi.PacketAPI;
import io.github.epicgo.packetapi.event.PacketEventListener;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PacketChannelDuplexHandler extends ChannelDuplexHandler {

    private final PacketAPI packetAPI;
    private final Player player;

    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object packet, final ChannelPromise channelPromise) throws Exception {
        final PacketWrapper packetWrapper = new PacketWrapper(packet);

        for (final Map.Entry<PacketEventListener, List<Method>> listener : packetAPI.getPacketListenerMap().entrySet()) {
            for (final Method method : listener.getValue()) {
                if (!method.getParameterTypes()[0].equals(PacketSendEvent.class)) continue;

                final PacketEventHandler packetEventHandler = method.getAnnotation(PacketEventHandler.class);
                if (packetEventHandler.listenType() == PacketWrapperType.ALL || packetEventHandler.listenType().getName().equals(packetWrapper.getName())) {
                    method.setAccessible(true);

                    final PacketSendEvent event = new PacketSendEvent(player, packetWrapper);
                    method.invoke(listener.getKey(), event);

                    if (event.isCancelled()) return;
                }
            }
        }

        super.write(channelHandlerContext, packet, channelPromise);
    }

    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object packet) throws Exception {
        final PacketWrapper packetWrapper = new PacketWrapper(packet);

        for (final Map.Entry<PacketEventListener, List<Method>> listener : packetAPI.getPacketListenerMap().entrySet()) {
            for (final Method method : listener.getValue()) {
                if (!method.getParameterTypes()[0].equals(PacketReceiveEvent.class)) continue;

                final PacketEventHandler packetEventHandler = method.getAnnotation(PacketEventHandler.class);
                if (packetEventHandler.listenType() == PacketWrapperType.ALL || packetEventHandler.listenType().getName().equals(packetWrapper.getName())) {

                    method.setAccessible(true);

                    final PacketReceiveEvent event = new PacketReceiveEvent(player, packetWrapper);
                    method.invoke(listener.getKey(), event);

                    if (event.isCancelled()) return;
                }
            }
        }

        super.channelRead(channelHandlerContext, packet);
    }
}
