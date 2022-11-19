package io.github.epicgo.packetapi.event;

import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class PacketEvent {

    private final Player player;
    private final PacketWrapper packetWrapper;

    private boolean cancelled;
}
