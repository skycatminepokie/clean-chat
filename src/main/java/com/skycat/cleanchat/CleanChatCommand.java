package com.skycat.cleanchat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.List;

public class CleanChatCommand extends CommandBase {
    private static void displayTextMainMenu() {
        ChatMessageSender.displayTextMainMenu();
    }

    @Override
    public String getCommandName() {
        return "cleanchat";
    }

    @Override //Do I need this override?
    public String getCommandUsage(ICommandSender sender) {
        //TODO: Does this go in lang files?
        return "The cleanchat command does stuff (this is for debug)";
    }

    @Override //Do I need this override?
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            displayTextMainMenu();
        } else {

            String firstArg = args[0].toLowerCase();

            if (firstArg.equalsIgnoreCase("reload")) {
                CleanChat.getChatHandler().loadChatFilter();
                ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Reloaded filter."));
            }
            if (firstArg.startsWith("list") || firstArg.equals("l")) {
                //TODO: Fix the on/off specification not working
                if (args.length >= 1) {
                    ChatMessageSender.listFilterSettings(
                            firstArg.endsWith("on") || firstArg.endsWith("all"),
                            firstArg.endsWith("off") || firstArg.endsWith("all"));
                    if (firstArg.equals("list") || firstArg.equals("l")) {
                        ChatMessageSender.listFilterSettings(true, true);
                    }
                }
            } else {

                if (args[0].equalsIgnoreCase("setting")) {

                    if (args.length == 3) {
                        //Rule is being updated or deleted

                        //Determine if the name is valid
                        boolean isValidName = false;
                        for (String s : CleanChat.getChatHandler().getChatFilter().getSettingNames()) {
                            if (args[1].equalsIgnoreCase(s)) {
                                isValidName = true;
                                break;
                            }
                        }

                        if (isValidName) {
                            ChatFilterSetting setting = null;
                            for (ChatFilterSetting chatFilterSetting : CleanChat.getChatHandler().getChatFilter().getSettings()) {
                                if (chatFilterSetting.getName().equalsIgnoreCase(args[1])) {
                                    setting = chatFilterSetting;
                                    break;
                                }
                            }

                            if (setting != null) {
                                //Turn on the rule
                                if (args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("setOn")) {
                                    setting.setEnabled(true);
                                    ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("Rule updated"));
                                } else {
                                    //Turn off the rule
                                    if (args[2].equalsIgnoreCase("off") || args[2].equalsIgnoreCase("setOff")) {
                                        setting.setEnabled(false);
                                        ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("Rule updated"));
                                    } else {
                                        //Turn the rule on if it is off, and off if it is on.
                                        if (args[2].equalsIgnoreCase("toggle")) {
                                            setting.setEnabled(!setting.isEnabled());
                                            ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("Rule updated"));
                                        } else {
                                            if (args[2].equalsIgnoreCase("delete")) {
                                                System.out.println("Setting exists?: " + CleanChat.getChatHandler().getChatFilter().getSettings().contains(setting));
                                                System.out.println("Setting successfully removed: " + CleanChat.getChatHandler().getChatFilter().getSettings().remove(setting));
                                                ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("Rule removed"));
                                                //DEBUG
                                                System.out.println("Removed " + setting.getName());
                                                for (ChatFilterSetting s: CleanChat.getChatHandler().getChatFilter().getSettings()) {
                                                    System.out.println("Setting: " + s.getName());
                                                }
                                                for (String s: CleanChat.getChatHandler().getChatFilter().getSettingNames()) {
                                                    System.out.println("Setting: " + s);
                                                }
                                            }
                                        }
                                    }
                                }
                                //Could probably be moved elsewhere for slightly better performance.
                                CleanChat.getChatHandler().saveChatFilter();
                            } else {
                                //Should never happen
                                ChatMessageSender.sendMessageToPlayer(new ChatComponentText("That's strange. You entered a valid name, but CleanChat couldn't find the related setting. Feel free to report this to the developer."));
                            }
                        } else {
                            //The user entered a filter name that does not exist, and therefore cannot be deleted or modified
                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("That's not a valid setting name :/"));
                        }
                    }


                    if (args.length >= 4) {

                        //Create a filter
                        if (args[1].equalsIgnoreCase("create")) {
                            ArrayList<ChatFilterSetting> settings = CleanChat.getChatHandler().getChatFilter().getSettings();

                            // /cleanchat setting create settingName <message>
                            String message = "";
                            for (int i = 3; i < args.length; i++) {
                                message = message + args[i] + " ";
                            }

                            message = message.trim();
                            settings.add(new ChatFilterSetting(message, MessageSource.UNKNOWN_ALL, true, args[2] + "(User-added filter)", args[2]));
                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Filter setting added."));
                            CleanChat.getChatHandler().saveChatFilter();
                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Filter settings saved."));
                        }
                    }
                }
            }
        }
    }

    @Override //Do I need this override?
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override //Do I need this override?
    public List<String> addTabCompletionOptions(ICommandSender commandSender, String[] args, BlockPos pos) {
        //Does the "cleanchat" at the beginning of "/cleanchat <args...>" count as an arg here?
        switch (args.length) {
            case 1:
                return getListOfStringsMatchingLastWord(args, "list", "listAll", "listOff", "listOn", "setting", "reload");
            case 2: {
                if (args[0].equalsIgnoreCase("setting")) {
                    String[] oldList = CleanChat.getChatHandler().getChatFilter().getSettingNames();
                    String[] newList = new String[oldList.length + 1];
                    for (int i = 0; i < oldList.length; i++) {
                        newList[i] = oldList[i];
                    }
                    newList[newList.length - 1] = "create";
                    return getListOfStringsMatchingLastWord(args, newList);
                } else {
                    if (args[0].equalsIgnoreCase("list")) {
                        return getListOfStringsMatchingLastWord(args, "all", "on", "off");
                    }
                }
            }
            case 3: {
                if (args[0].equalsIgnoreCase("setting")) {
                    if (args[1].equalsIgnoreCase("create")) {
                        return getListOfStringsMatchingLastWord(args, "<settingName>");
                    }
                    return getListOfStringsMatchingLastWord(args, "on", "off", "toggle", "delete");
                }
            }
            case 4: {
                if (args[0].equalsIgnoreCase("setting")) {
                    return getListOfStringsMatchingLastWord(args, "filteredWords...");
                }
            }
        }
        return null;
    }

}
