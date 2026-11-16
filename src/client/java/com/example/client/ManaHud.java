package com.example.client;

import com.example.attachment.ModAttachments;
import com.example.mana.ManaData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

public class ManaHud {
    private static final int manaHudMarginRight = 20;
    private static final int manaHudMarginBottom = 5;
    private static final double manaHudHeightMultiplier = 0.25;
    private static final double manaHudWidthMultiplier = 0.03;

    public static void register(){
        HudRenderCallback.EVENT.register((guiGraphics,  deltaTrackerDeltaTracker)->{
            LocalPlayer client_player = Minecraft.getInstance().player;
            if (client_player == null) { return;}
            ManaData clientManaData = client_player.getAttached(ModAttachments.MANA);
            if (clientManaData == null) { return;}
            int manaRightBottomAngleX =  guiGraphics.guiWidth() - manaHudMarginRight;
            int manaRightBottomAngleY = guiGraphics.guiHeight() - manaHudMarginBottom;
            int manaLeftUpperAngleX = (int) Math.round(manaRightBottomAngleX - manaHudWidthMultiplier * guiGraphics.guiWidth());
            int manaLeftUpperAngleY = (int) Math.round(manaRightBottomAngleY - manaHudHeightMultiplier * guiGraphics.guiHeight());

            //background
            guiGraphics.fill(manaLeftUpperAngleX, manaLeftUpperAngleY, manaRightBottomAngleX, manaRightBottomAngleY, 0x80000000);

            double percentCurrentMana = clientManaData.currentMana() / clientManaData.maxMana();
            int manaHudLeftUpperAngleY = (int) Math.round(manaRightBottomAngleY - percentCurrentMana * (manaRightBottomAngleY - manaLeftUpperAngleY));
            //manaHud
            guiGraphics.fillGradient(
                    manaLeftUpperAngleX,
                    manaHudLeftUpperAngleY,
                    manaRightBottomAngleX,
                    manaRightBottomAngleY,
                    0xFF00E5FF,
                    0xFF0D47A1

            );

            //outbound
            guiGraphics.renderOutline(
                    manaLeftUpperAngleX,
                    manaLeftUpperAngleY,
                    manaRightBottomAngleX - manaLeftUpperAngleX,
                    manaRightBottomAngleY - manaLeftUpperAngleY,
                    0xFFC0C0C0);


        });
    }

    private static void drawManaHud(GuiGraphics guiGraphics){
        }
}

