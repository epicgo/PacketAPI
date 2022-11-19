package io.github.epicgo.packetapi.event.impl;

import io.github.epicgo.packetapi.event.PacketEvent;
import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import org.bukkit.entity.Player;

public class PacketSendEvent extends PacketEvent {

    public PacketSendEvent(final Player player, final PacketWrapper packetWrapper) {
        super(player, packetWrapper);
    }
}
