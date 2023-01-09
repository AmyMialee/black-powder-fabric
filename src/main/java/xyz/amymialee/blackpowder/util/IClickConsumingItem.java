package xyz.amymialee.blackpowder.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public interface IClickConsumingItem {
    void blackPowder$doAttack(ServerPlayerEntity player, Vec3d pos, Vec3d rot);
}