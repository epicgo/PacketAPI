package io.github.epicgo.packetapi.util;

import io.github.epicgo.packetapi.reflection.Reflection;
import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerUtil {

    /**
     * Returns the channel of the player to be injected.
     *
     * @param player the player to get channel.
     * @return the player channel.
     */
    public Channel getChannel(final Player player) {
        final Object craftPlayerHandle = Reflection.getMethod(player, "getHandle");
        final Object playerConnection = Reflection.getField(craftPlayerHandle, "playerConnection");
        final Object networkManager = Reflection.getField(playerConnection, "networkManager");

        return (Channel) Reflection.getField(networkManager, "channel");
    }

    /**
     * Sent the object packet to network player connection.
     *
     * @param player the player to sent packet.
     * @param packet the packet to send.
     */
    public void sendPacket(final Player player, final Object packet) {
        final Object craftPlayerHandle = Reflection.getMethod(player, "getHandle");
        final Object playerConnection = Reflection.getField(craftPlayerHandle, "playerConnection");

        Reflection.getMethod(playerConnection, "sendPacket", packet);
    }
}
