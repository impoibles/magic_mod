package com.example.attachment;

import com.example.ExampleMod;
import com.example.mana.ManaData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import java.util.concurrent.ThreadLocalRandom;

public class ModAttachments {
    public static AttachmentType<ManaData> MANA;

    public static void register() {
         MANA = AttachmentRegistry.create(ExampleMod.id("mana"),
                builder -> builder
                        .persistent(ManaData.CODEC)
                        .copyOnDeath()
                .initializer(ModAttachments::initializeMana));
    }

    private static ManaData initializeMana() {
        double randomStartMana = ThreadLocalRandom.current().nextInt(1, 101);
        return new ManaData(randomStartMana, randomStartMana, 0, 0);
    }
}
