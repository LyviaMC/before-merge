package com.lyviamc.lyvia_creeper_demo.mixin.entity;

import com.lyviamc.lyvia_creeper_demo.HostilityAccessor;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements HostilityAccessor {
    @Unique
    private static boolean isHostilityAgainstCreeper = true;
    @Override
    public void lyviaCreeperDemo$setIsHostilityAgainstCreeper(boolean is_hostility){
        isHostilityAgainstCreeper = is_hostility;
    }
    @Override
    public boolean lyviaCreeperDemo$getIsHostilityAgainstCreeper() {
        return isHostilityAgainstCreeper;
    }
}
