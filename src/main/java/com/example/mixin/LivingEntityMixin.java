package com.example.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.example.utils.MixinUtils.saveManaExhaustionEffect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "checkTotemDeathProtection", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    private boolean keepManaExhaustionOnTotem(LivingEntity instance, Operation<Boolean> original){
        return saveManaExhaustionEffect(instance, original);
    }
}
