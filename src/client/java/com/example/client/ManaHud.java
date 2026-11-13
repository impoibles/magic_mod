package com.example.client;

import com.example.attachment.ModAttachments;
import com.example.mana.ManaData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class ManaHud {

    public static void register(){
        HudRenderCallback.EVENT.register((guiGraphics,  deltaTrackerDeltaTracker)->{
            LocalPlayer client_player = Minecraft.getInstance().player;
            if (client_player != null) { return;}
            ManaData clientManaData = client_player.getAttached(ModAttachments.MANA);
            if (clientManaData != null) { return;}
            guiGraphics.drawString(Minecraft.getInstance().font,
                    Component.translatable("command.tbmm.mana_count",
                            Math.round(clientManaData.currentMana()),
                            Math.round(clientManaData.maxMana())), 10, 10, 0xFFFFFF );


        });
    }
}
