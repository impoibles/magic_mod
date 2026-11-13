package com.example.utils;

import com.example.effects.ModEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MixinUtils {
     public static boolean saveManaExhaustionEffect(LivingEntity livingEntity, Operation<Boolean> original){
         MobEffectInstance manaExhaustionEffect = livingEntity.getEffect(ModEffects.MANA_EXHAUSTION);
         // 1. что-то сделать ДО
         boolean result = original.call(livingEntity);   // 2. настоящий вызов
         // 3. что-то сделать ПОСЛЕ
         if (manaExhaustionEffect != null) {
             livingEntity.addEffect(new  MobEffectInstance(manaExhaustionEffect));
         }
         //return result;
         return result;
     }
}
