package com.skycat.cleanchat;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.*;

import java.util.List;

public class CleanChatCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "cleanchat";
    }

    @Override //Do I need this override?
    public String getCommandUsage(ICommandSender sender) {
        return "The cleanchat command does stuff (this is for debug)";
    }

    @Override //Do I need this override?
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        System.out.println("Command used!");
        if (args.length > 0) {
            if (args[0].equals("printHi")) {
                System.out.println("Hi");
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                        new ChatComponentText("Hello")
                                .setChatStyle(new ChatStyle()
                                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/say hi"))
                                        .setColor(EnumChatFormatting.YELLOW)
                                        .setUnderlined(true)));
            } else if (args[0].equals("printBye")) {
                System.out.println("Bye");
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(ChatMessages.getConfirmReport());
            }
            System.out.println("Argument: " + args[0]);
        }
    }

    @Override //Do I need this override?
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override //Do I need this override?
    public List<String> addTabCompletionOptions(ICommandSender commandSender, String[] args, BlockPos pos){
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "printHi", "printBye");
        }
        return null;
    }
    /*CleanChatCommand() {
        System.out.println("CleanChatCommand registered");
    }*/
}
