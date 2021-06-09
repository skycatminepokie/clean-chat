package com.skycat.cleanchat.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiHandler {
    @Getter
    @Setter
    private boolean drawTestGui = false;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (drawTestGui) {
            Minecraft.getMinecraft().displayGuiScreen(new TestScreen());
            drawTestGui = false;
        }
    }
}
