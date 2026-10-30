package com.example.item;

import com.example.ExampleMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Общий класс для всех магических кристаллов.
 * Каждый конкретный кристалл (огонь, вода и т.д.) наследуется от него.
 */
public abstract class MagicCrystalItem extends Item {

    private static final double CIRCLE_ANIMATION_RADIUS = 0.2D;
    private static final double PARTICLE_CREATE_CHANCE = 0.1D;

    public MagicCrystalItem(Properties properties) {
        super(properties);
    }

    protected abstract ParticleOptions getParticleType();
    protected abstract double getRotationSpeed();
    protected abstract boolean isPointMode();

    /**
     * Ключ описания в lang-файле.
     * По умолчанию это ключ предмета + ".tooltip", например "item.modid.fire_crystal.tooltip".
     * Наследник может переопределить метод, если нужен другой ключ.
     */
    protected String getTooltipKey() {
        return getDescriptionId() + ".tooltip";
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getTooltipKey()).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide() && isSelected) {
            crystalAnimation(level, entity, getParticleType(), getRotationSpeed(), isPointMode());
        }
    }

    private void crystalAnimation(Level level, Entity entity, ParticleOptions particleType, double rotationSpeed, boolean pointMode) {
        if(pointMode) {
            if (level.random.nextFloat() > PARTICLE_CREATE_CHANCE) return;
        }
        long time = level.getGameTime();
        double angle = time * rotationSpeed;
        double x = CIRCLE_ANIMATION_RADIUS * cos(angle);
        double z = CIRCLE_ANIMATION_RADIUS * sin(angle);

        Vec3 offset = new Vec3(-0.95 * (entity.getBbWidth() / 2) + x, entity.getBbHeight() / 2, 0.9D + z);
        offset = offset.yRot(-entity.getYRot() * Mth.DEG_TO_RAD);
        level.addParticle(particleType, entity.getX() + offset.x, entity.getY() + offset.y, entity.getZ() + offset.z, 0.0D, 0.0D, 0.0D);
        //ExampleMod.LOGGER.info("Particles");

    }

}

