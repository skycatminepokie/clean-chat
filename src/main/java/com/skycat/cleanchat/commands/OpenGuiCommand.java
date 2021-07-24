package com.skycat.cleanchat.commands;

import com.skycat.cleanchat.CleanChat;
import com.skycat.cleanchat.gui.ChatFilterSettingsGui;
import com.skycat.cleanchat.gui.TestScreen;
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
                CleanChat.getGuiHandler().setToDraw(new TestScreen());
            } else {
                if (args[0].equalsIgnoreCase("main")) {
                    CleanChat.getGuiHandler().setToDraw(
                            new ChatFilterSettingsGui(
                                    CleanChat.getChatHandler().getChatFilter().getSettingGroups().get(0).getSettings(),
                                    0,
                                    CleanChat.getChatHandler().getChatFilter().getSettingGroups().size() - 1
                            )
                    );
                }
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
