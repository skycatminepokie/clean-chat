package com.skycat.cleanchat.gui;

import com.skycat.cleanchat.CleanChat;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiHandler {
    @Getter
    @Setter
    private boolean drawTestGui = false;
    @Setter @Getter
    private boolean drawMainGui = false;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (drawTestGui) {
            Minecraft.getMinecraft().displayGuiScreen(new TestScreen());
            drawTestGui = false;
        } else {
            if (drawMainGui) {
                Minecraft.getMinecraft().displayGuiScreen(new ChatFilterSettingsGui(CleanChat.getChatHandler().getChatFilter().getSettingGroups().get(0).getSettings()));
                drawMainGui = false;
            }
        }
    }
}
