package io.github.epicgo.packetapi.wrapper.impl;

import io.github.epicgo.packetapi.reflection.Reflection;
import io.github.epicgo.packetapi.wrapper.PacketWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
public class PacketWrapperPlayOutAnimation extends PacketWrapper {

    /**
     * Sent whenever an entity should change animation.
     * @param entityId      the entity id to send the animation.
     * @param animation   the animation to send to entity.
     */
    public PacketWrapperPlayOutAnimation(final int entityId, final Animation animation) {
        this(entityId, animation.getId());
    }

    /**
     * Sent whenever an entity should change animation.
     * @param entityId      the entity id to send the animation.
     * @param animationId   the animation id to send to entity.
     */
    public PacketWrapperPlayOutAnimation(final int entityId, final int animationId) {
        super(Reflection.getConstructor(Reflection.getNMSClass("PacketPlayOutAnimation"), entityId, animationId));
    }

    /**
     * Returns {@link Animation} from the specified animationId.
     *
     * @param animationId the animation id.
     * @return An Animation enum object.
     */
    public Animation getAnimationById(final int animationId) {
        return Stream.of(Animation.values()).filter(targetAnimation -> targetAnimation.id == animationId).findFirst().orElse(null);
    }

    /**
     * Animation can be one of the following values.
     */
    @Getter
    @RequiredArgsConstructor
    public static enum Animation {
        /**
         * Makes the entity swing its arm
         * <p/>
         * Only applicable to human entities
         */
        ARM_SWING(0),
        /**
         * Makes the entity action hurt
         * <p/>
         * Only applicable to living entities
         */
        TAKE_DAMAGE(1),
        /**
         * Makes the entity leave the bed
         * <p/>
         * Only applicable to human entities
         */
        LEAVE_BED(2),
        /**
         * Makes the entity eat the item it is holding
         * <p/>
         * Only applicable to human entities
         */
        EAT_FOOD(3),
        /**
         * A critical hit animation
         * <p/>
         * Only applicable to human entities
         */
        CRITICAL_EFFECT(4),
        /**
         * A 'magic' critical hit animation
         * <p/>
         * Only applicable to human entities
         */
        MAGIC_CRITICAL_EFFECT(5);

        private final int id;
    }
}
