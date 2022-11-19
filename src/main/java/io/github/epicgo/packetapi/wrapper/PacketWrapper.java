package io.github.epicgo.packetapi.wrapper;

import io.github.epicgo.packetapi.reflection.Reflection;
import io.github.epicgo.packetapi.util.PlayerUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class PacketWrapper {

    private final Object packet;

    /**
     * Sets an object to the specific name field.
     *
     * @param fieldName   the field to set.
     * @param objectValue the object to set.
     */
    public void set(final String fieldName, final Object objectValue) {
        Reflection.setDeclaredField(packet, fieldName, objectValue);
    }

    /**
     * Returns the defined object of the field.
     *
     * @param fieldName the field to get.
     * @return the object.
     */
    public Object get(final String fieldName) {
        return Reflection.getDeclaredField(packet, fieldName);
    }

    /**
     * Returns the name of the packet.
     *
     * @return the packet name.
     */
    public String getName() {
        return this.packet.getClass().getSimpleName();
    }

    /**
     * Send this packet to the player.
     *
     * @param player the player to send.
     */
    public void sendPacket(final Player player) {
        PlayerUtil.sendPacket(player, this.packet);
    }
}
