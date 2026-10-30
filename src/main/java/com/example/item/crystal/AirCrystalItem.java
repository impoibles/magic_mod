package com.example.item.crystal;

import com.example.item.MagicCrystalItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirCrystalItem extends MagicCrystalItem {
    public AirCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.CLOUD;
    }

    @Override
    protected double getRotationSpeed() {
        return 0.1D;
    }

    @Override
    protected boolean isPointMode() {
        return false;
    }


}
