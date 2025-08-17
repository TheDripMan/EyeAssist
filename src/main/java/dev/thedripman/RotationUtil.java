package dev.thedripman;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class RotationUtil {

    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isPlaying() {
        return mc.player != null && mc.world != null;
    }

    /**
     * @param range in blocks, defines the range around the player to scan for entities
     * @param entityClass the entity type to look for (Check the Entity class for mobs for example)
     * @return all the entities that are within the given range from the player
     */
    public static <T extends Entity> List<T> getEntitiesAroundPlayer(float range, Class<? extends T> entityClass) {
        Box area = new Box(
                mc.player.getX() - range,
                mc.player.getY() - range,
                mc.player.getZ() - range,
                mc.player.getX() + range,
                mc.player.getY() + range,
                mc.player.getZ() + range
        );

        return mc.world.getEntities(entityClass, area, EntityPredicates.VALID_ENTITY);
    }
    
    /**
     * @param source the source entity
     * @param target the target of the source entity
     */
    public static float[] getYawPitchBetween(Entity source, Entity target) {

        return getYawPitchBetween(
                // source
                source.getX(),
                source.getY() + source.getStandingEyeHeight(),
                source.getZ(),
                // target
                target.getX(),
                target.getY(),
                target.getZ()
        );
    }

    /**
     * @param sourceX x position for source
     * @param sourceY y position for source
     * @param sourceZ z position for source
     * @param targetX x position for target
     * @param targetY y position for target
     * @param targetZ z position for target
     * @return the [yaw, pitch] difference between the source and the target
     */
    public static float[] getYawPitchBetween(
            double sourceX, double sourceY, double sourceZ,
            double targetX, double targetY, double targetZ) {

        double diffX = targetX - sourceX;
        double diffY = targetY - sourceY;
        double diffZ = targetZ - sourceZ;

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = wrapAngleTo180_float((float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F));
        float pitch = wrapAngleTo180_float((float) Math.toDegrees(-Math.atan2(diffY, dist)));

        return new float[] { yaw, pitch };
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static float wrapAngleTo180_float(float value)
    {
        value = value % 360.0F;

        if (value >= 180.0F)
        {
            value -= 360.0F;
        }

        if (value < -180.0F)
        {
            value += 360.0F;
        }

        return value;
    }

    /**
     * @param target the target to aim.
     * @return the [x, y] new positions of the player crosshair
     */
    public static float[] getRotationsNeeded(Entity target) {

        // We calculate the yaw/pitch difference between the target and the player
        float[] yawPitch = getYawPitchBetween(mc.player, target);

        return new float[] { yawPitch[0], yawPitch[1] };
    }

    /**
     * Sets the position of the crosshair
     * @param yaw horizontal pos (degrees)
     * @param pitch vertical pos (degrees)
     */
    public static void setRotations(float yaw, float pitch) {
        mc.player.yaw = yaw;
        mc.player.pitch = pitch;
    }

}