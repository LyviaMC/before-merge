package com.lyviamc.lyvia_creeper_demo.mixin.entity;

import com.lyviamc.lyvia_creeper_demo.AreaManagerWithCreeperActivityMonitoring;
import com.lyviamc.lyvia_creeper_demo.HostilityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public class CreeperMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(CreeperMixin.class);
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void setTarget(LivingEntity target, CallbackInfo ci) {
        CreeperEntity creeper = (CreeperEntity)(Object) this;
        LivingEntity oldTarget = target;
        if (target instanceof ServerPlayerEntity) {
            if (!((HostilityAccessor) target)
                    .lyviaCreeperDemo$getIsHostilityAgainstCreeper()) {
                ci.cancel();
                creeper.setTarget(null);
            }
            else {
                if(oldTarget != creeper.getTarget()) {
                    StringBuilder str = new StringBuilder("The Player ");
                    LOGGER.info(str.append(target.getName()).append(" is under attack at ").append(target.getPos()).toString());
                }
            }
        }
    }

    @Inject(method = "explode", at = @At("HEAD"))
    public void explode(CallbackInfo ci) {
        CreeperEntity creeper = (CreeperEntity)(Object) this;
        LivingEntity target = creeper.getTarget();
        Vec3d explodePose = creeper.getPos();
        StringBuilder str1 = new StringBuilder("!!! Detected explosion at: ");
        assert target != null;
        LOGGER.info(str1.append(explodePose).append(" caused by player ").append(target.getName()).toString());

        if (AreaManagerWithCreeperActivityMonitoring.in3dRange(explodePose)) {
            StringBuilder str2 = new StringBuilder("!!!!!!!!!! CRITICAL explosion at: ");
            LOGGER.warn(str2.append(explodePose).append(" caused by player ").append(target.getName()).toString());
        }
        if (target instanceof ServerPlayerEntity) {
            StringBuilder str3 = new StringBuilder("!!! The explosion at ");
            LOGGER.warn(str3.append(explodePose).append(" was caused by player ").append(target.getName()).toString());
        }
    }
    @Inject(method = "interactMob", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;ignite()V"))
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        CreeperEntity creeper = (CreeperEntity)(Object) this;
        Vec3d explodePose = creeper.getPos();
        StringBuilder str1 = new StringBuilder("??? PLAYER IGNITED CREEPER at: ");
        assert player != null;
        LOGGER.info(str1.append(explodePose).append(" by player ").append(player.getName()).toString());
        if (AreaManagerWithCreeperActivityMonitoring.in3dRange(explodePose)) {
            StringBuilder str2 = new StringBuilder("????????? CRITICAL explosion at: ");
            assert player != null;
            LOGGER.info(str2.append(explodePose).append(" by player ").append(player.getName()).toString());
        }
    }

}
