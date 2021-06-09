package com.skycat.cleanchat.gui;

import com.skycat.cleanchat.CleanChat;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class OpenGuiCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "displayGui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "displayGui";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        CleanChat.getGuiHandler().setDrawTestGui(!CleanChat.getGuiHandler().isDrawTestGui());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
