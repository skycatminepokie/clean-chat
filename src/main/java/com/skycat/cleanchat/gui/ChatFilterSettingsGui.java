package com.skycat.cleanchat.gui;

import com.skycat.cleanchat.*;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ChatFilterSettingsGui extends GuiScreen {
    private ArrayList<ChatFilterSetting> settings;

    ChatFilterSettingsGui(ArrayList<ChatFilterSetting> settings) {
        this.settings = settings;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        for (int i = 0; i < settings.size(); i++) {
            ChatFilterSetting setting = settings.get(i);
            buttonList.add(new OnOffButton(i, 50, 50 + 20 * i, 80, 20, setting.getName()));
        }
        super.initGui();
    }
}
