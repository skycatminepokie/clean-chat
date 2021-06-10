package com.skycat.cleanchat.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class TestScreen extends GuiScreen {
    private OnOffButton myButton = new OnOffButton(1, 100, 100, "button");
    private GuiTextField guiTextField = new GuiTextField(2, fontRendererObj, 200, 75, 200, 50);
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawHorizontalLine(50, 100, 50, 0);
        this.drawCenteredString(fontRendererObj, "HI THERE", 80, 80, 9);
        this.buttonList.add(myButton);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }


}
