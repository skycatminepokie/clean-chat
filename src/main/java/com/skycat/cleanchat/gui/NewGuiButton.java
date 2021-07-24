package com.skycat.cleanchat.gui;

import com.skycat.cleanchat.CleanChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * A button to be used to switch between multiple GUIs (a navigation button).
 */
public class NewGuiButton extends GuiButton {
    private GuiScreen newGui;

    /**
     * Creates a new navigation button
     * @param buttonId The id of the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param widthIn The width of the button
     * @param heightIn The height of the button
     * @param buttonText The text to display in the button
     * @param newGui The gui to change to when the button is clicked
     */
    public NewGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, GuiScreen newGui) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.newGui = newGui;

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        if ((mouseX >= xPosition && mouseX <= xPosition + width) && (mouseY >= yPosition && mouseY <= yPosition + height)) {
            CleanChat.getGuiHandler().setToDraw(newGui);
        }
        super.mouseReleased(mouseX, mouseY);
    }
}
