package com.example.mixin;


import com.example.attribute.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(at = @At("RETURN"), method="createAttributes")
    private static void addManaAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir){
        cir.getReturnValue()
                .add(ModAttributes.MAX_MANA_ATTRIBUTE)
                .add(ModAttributes.MANA_REGEN_SPEED_ATTRIBUTE)
                .add(ModAttributes.COST_MULTIPLIER_ATTRIBUTE);
    }

}
