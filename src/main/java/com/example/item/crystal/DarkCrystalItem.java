package com.example.item.crystal;

import com.example.item.MagicCrystalItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class DarkCrystalItem extends MagicCrystalItem {
    public DarkCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.SOUL;
    }

    @Override
    protected double getRotationSpeed() {
        return 0.1D;
    }

    @Override
    protected boolean isPointMode() {
        return true;
    }


}
