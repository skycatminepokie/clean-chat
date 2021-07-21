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
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("test")) {
                CleanChat.getGuiHandler().setDrawTestGui(true);
            } else {
                if (args[0].equalsIgnoreCase("main")) {
                    CleanChat.getGuiHandler().setDrawMainGui(true);
                }
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
