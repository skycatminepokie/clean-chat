package com.skycat.cleanchat.commands;

import com.skycat.cleanchat.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

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
                //CleanChat.getChatHandler().loadChatFilter();
                CleanChat.getChatHandler().loadChatFilter();
                ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Reloaded filter."));
            }
            if (firstArg.startsWith("list") || firstArg.equals("l")) {
                //TODO: Fix the on/off specification not working
                ChatMessageSender.listFilterSettings(
                        firstArg.endsWith("on") || firstArg.endsWith("all"),
                        firstArg.endsWith("off") || firstArg.endsWith("all"));
                if (firstArg.equals("list") || firstArg.equals("l")) {
                    ChatMessageSender.listFilterSettings(true, true);
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
                            for (ChatFilterSettingGroup chatFilterSettingGroup : CleanChat.getChatHandler().getChatFilter().getSettingGroups()) {
                                for (ChatFilterSetting chatFilterSetting : chatFilterSettingGroup.getSettings()) {
                                    if (chatFilterSetting.getName().equalsIgnoreCase(args[1])) {
                                        setting = chatFilterSetting;
                                        break;
                                    }
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
                                                boolean exists = false;
                                                for (ChatFilterSettingGroup group : CleanChat.getChatHandler().getChatFilter().getSettingGroups()) {
                                                    if (group.getSettings().contains(setting)) {
                                                        exists = true;
                                                        break;
                                                    }
                                                }
                                                System.out.println("Setting exists?: " + exists);
                                                boolean removed = false;
                                                for (ChatFilterSettingGroup group : CleanChat.getChatHandler().getChatFilter().getSettingGroups()) {
                                                    if (group.getSettings().contains(setting)) {
                                                        removed = group.getSettings().remove(setting);
                                                        break; // WARN This statement may cause problems if the same setting is put in multiple groups
                                                    }
                                                }
                                                System.out.println("Setting successfully removed: " + removed);
                                                if (removed) {
                                                    ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("Rule removed"));
                                                } else {
                                                    ChatMessageSender.sendMessageToPlayer(ChatMessageSender.getCleanChatTag().createCopy().appendText("The rule wasn't removed correctly  :/"));
                                                }
                                                //DEBUG
                                                /*System.out.println("Removed " + setting.getName());
                                                for (ChatFilterSetting s: CleanChat.getChatHandler().getChatFilter().getSettings()) {
                                                    System.out.println("Setting: " + s.getName());
                                                }
                                                for (String s: CleanChat.getChatHandler().getChatFilter().getSettingNames()) {
                                                    System.out.println("Setting: " + s);
                                                }
                                                 */
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
                            ArrayList<ChatFilterSetting> settings = new ArrayList<ChatFilterSetting>();
                            for (ChatFilterSettingGroup group : CleanChat.getChatHandler().getChatFilter().getSettingGroups()) {
                                settings.addAll(group.getSettings());
                            }


                            // /cleanchat setting create settingName <message>
                            String message = "";
                            String replacementMessage = "";
                            boolean useReplacement = false;
                            for (int i = 3; i < args.length; i++) {
                                // Detects if a flag was specified
                                if (!args[i].startsWith("--")) {
                                    message = message + args[i] + " ";
                                } else {
                                    if (args[i].equalsIgnoreCase("--replacement")) {
                                        try {
                                            replacementMessage = replacementMessage + args[i + 1] + " ";
                                            useReplacement = true;
                                            i++; // Skip the next argument, it's already been dealt with
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("It looks like you didn't specify replacement text after the --replacement flag. Please try again."));
                                        }
                                    }
                                }
                            }

                            message = message.trim();

                            if (useReplacement) {
                                settings.add(new ChatFilterSetting(message, MessageSource.UNKNOWN_ALL, true, args[2] + " (User-added filter)", args[2], replacementMessage.trim()));
                            } else {
                                settings.add(new ChatFilterSetting(message, MessageSource.UNKNOWN_ALL, true, args[2] + " (User-added filter)", args[2]));
                            }

                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Filter setting added."));
                            CleanChat.getChatHandler().saveChatFilter();
                            ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Filter settings saved."));
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("reset")) {
                        CleanChat.setChatHandler(new ChatHandler());
                        ChatMessageSender.sendMessageToPlayer(new ChatComponentText("Filter settings reset."));
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
        switch (args.length) {
            case 1:
                return getListOfStringsMatchingLastWord(args, "list", "listAll", "listOff", "listOn", "setting", "reload");
            case 2: {
                if (args[0].equalsIgnoreCase("setting")) {
                    String[] oldList = CleanChat.getChatHandler().getChatFilter().getSettingNames();
                    String[] newList = new String[oldList.length + 1];
                    System.arraycopy(oldList, 0, newList, 0, oldList.length);
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
