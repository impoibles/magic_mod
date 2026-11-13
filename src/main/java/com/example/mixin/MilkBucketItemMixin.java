package com.example.mixin;

import com.example.effects.ModEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.example.utils.MixinUtils.saveManaExhaustionEffect;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    @WrapOperation(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    private boolean keepManaExhaustion(LivingEntity livingEntity, Operation<Boolean> original) {
        return saveManaExhaustionEffect(livingEntity, original);
    }
}
