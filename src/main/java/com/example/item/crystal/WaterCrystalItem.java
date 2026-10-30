package com.example.item.crystal;

import com.example.item.MagicCrystalItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class WaterCrystalItem extends MagicCrystalItem {
    public WaterCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.DRIPPING_WATER;
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
