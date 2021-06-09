package com.skycat.cleanchat;

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
        return;
    }
}
