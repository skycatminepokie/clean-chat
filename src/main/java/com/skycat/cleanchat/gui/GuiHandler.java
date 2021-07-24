package com.skycat.cleanchat.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiHandler {
    @Getter
    @Setter
    private GuiScreen toDraw;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (toDraw != null) {
            Minecraft.getMinecraft().displayGuiScreen(toDraw);
            toDraw = null;
        }
    }
}
