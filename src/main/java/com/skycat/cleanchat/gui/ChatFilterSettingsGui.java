package com.skycat.cleanchat.gui;

import com.skycat.cleanchat.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

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
        /*for (int i = 0; i < settings.size(); i++) {
            ChatFilterSetting setting = settings.get(i);
            buttonList.add(new OnOffButton(i, 50, 50 + 20 * i, 80, 20, setting.getName()));
        }*/
        if (true) {//Placeholder
            //Display in a fancy grid
            int buttonWidth = 80;
            int buttonHeight = 20;
            int topMargin = 10;
            int bottomMargin = 10;
            int leftMargin = 10;
            int rightMargin = 10;
            int buttonWidthSeparation = 5;
            int buttonHeightSeparation = 5;
            int settingNumber = 0;

            // CREDIT: https://emxtutorials.wordpress.com/simple-in-game-gui-overlay/
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            //int guiWidth = Minecraft.getMinecraft().displayWidth;
            //int guiHeight = Minecraft.getMinecraft().displayHeight;
            int guiWidth = scaledResolution.getScaledWidth();
            int guiHeight = scaledResolution.getScaledHeight();

            int columns = (guiWidth - leftMargin - rightMargin) / (buttonWidth + buttonWidthSeparation);
            int rows = (guiHeight - topMargin - bottomMargin) / (buttonHeight + buttonHeightSeparation);
            for (int i = 0; i < columns; i++) { // Fit as many columns as we can horizontally
                for (int j = 0; j < rows; j++) { // Fit as many buttons as we can vertically
                    if (settings.size() > settingNumber) {  // This is kind of strange, but it should stop at the end of the list because of the start-at-zero thing
                        ChatFilterSetting setting = settings.get(settingNumber);
                        buttonList.add(new OnOffButton(j, (i * (buttonWidth + buttonWidthSeparation) + leftMargin), ((j * (buttonHeight + buttonHeightSeparation) + topMargin)), buttonWidth, buttonHeight, setting.getName()));
                        settingNumber++;
                    } else {
                        break;
                    }
                }
                if (settings.size() <= settingNumber) {
                    break;
                }
            }


        }
        super.initGui();
    }
}
