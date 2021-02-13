package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.skycat.cleanchat.MessageSource.PLAYER;
import static com.skycat.cleanchat.MessageSource.UNKNOWN_ALL;

//What does static mean in an import?

/**
 * A setting that a {@link ChatFilter} can use to decide whether or not a chat message should be cancelled.
 */
public class ChatFilterSetting {
    private final Pattern regex;
    @Getter private String message;
    @Getter @Setter private boolean enabled; //getter is isEnabled, not getEnabled. Why? IDK, Lombok magic.
    private final MessageSource messageSource;
    @Getter private final String description;
    @Getter private String name;
    @Getter private String replacement = null;
    @Getter private ChatFilterSettingFlag[] flags;
    private String[] flagValues;
    @Getter private ArrayList<ChatFilterSettingFlag> flagList;

    //TODO: Allow multiple strings
    /**
     * Creates a ChatFilterSetting, which is used in a {@link ChatFilter}
     * @param message The message to match (also matches partially)
     * @param messageSource Where the message usually comes from
     * @param enabled Enabled the filter setting
     * @param description A human-readable description of the setting (for example, "Player join message (lobby)")
     * @param name A short, human-readable name used in commands. No spaces allowed.
     */
    ChatFilterSetting(String message, MessageSource messageSource, boolean enabled, String description, String name) {
        this.message = message;
        this.regex = generateRegex();
        this.messageSource = messageSource;
        this.enabled = enabled;
        this.description = description;
        this.name = name;
    }


    /**
     * Creates a ChatFilterSetting that allows for replacing content in the chat
     * rather than removing the chat
     * @param message The message to match (also matches partially)
     * @param messageSource Where the message usually comes from
     * @param enabled Enabled the filter setting
     * @param description A human-readable description of the setting (for example, "Player join message (lobby)")
     * @param name A short, human-readable name used in commands. No spaces allowed.
     * @param replacement The string to use as a replacement for the message
     * @see ChatFilter
     */
    ChatFilterSetting(String message, MessageSource messageSource, boolean enabled, String description, String name, String replacement) {
        this.message = message;
        this.regex = generateRegex();
        this.messageSource = messageSource;
        this.enabled = enabled;
        this.description = description;
        this.name = name;
        this.replacement = replacement;
    }

    /**
     * Creates an advanced ChatFilterSetting, complete with flags
     * @param message The criteria to match
     * @param messageSource The typical source of the message
     * @param enabled If the setting is enabled
     * @param description A short, human-readable description of the setting
     * @param name A short, human-readable, one-word name for use in commands. May not contain "--".
     * @param flagValues The values for flags that need them, in order (for example, a replacement string for the REPLACEMENT value
     * @param flags All flags to trip on the setting
     * @see ChatFilter
     * @see ChatFilterSettingFlag
     */
    ChatFilterSetting(String message, MessageSource messageSource, boolean enabled, String description, String name, String[] flagValues, ChatFilterSettingFlag... flags) {
        this.message = message;
        this.messageSource = messageSource;
        this.enabled = enabled;
        this.description = description;
        this.name = name;
        ArrayList<ChatFilterSettingFlag> flagList = new ArrayList<ChatFilterSettingFlag>();
        flagList.addAll(Arrays.asList(flags));
        this.flagList = flagList;
        this.flags = flags;
        this.flagValues = flagValues;
        this.regex = generateRegex();
    }


    /**
     * Creates a regex to help capture parts of the message
     * @return A {@link Pattern} containing the generated regex
     */
    private Pattern generateRegex() {
        String regexMessage = message;

        if (flagList.contains(ChatFilterSettingFlag.CASE_INSENSITIVE)) {
            regexMessage = regexMessage.toLowerCase();
        }

        if (flagList.contains(ChatFilterSettingFlag.WHOLE_MESSAGE_ONLY)) {
            return Pattern.compile(regexMessage);
        } else {
            if (flagList.contains(ChatFilterSettingFlag.WHOLE_WORD_ONLY)) {
                // TODO: Fix this not working if it's the last word or before punctuation
                regexMessage = " " + regexMessage + " "; // This won't work if it's the the last word :/
                // Actual function is "must be surrounded with spaces" rather than "must be a whole word"
            }
            if (messageSource == PLAYER) {
                // Can be improved. Just looks for a colon before the message
                return Pattern.compile(".*?:.*?" + regexMessage + ".*");
            } else {
                return Pattern.compile(".*?" + regexMessage + ".*");
            }
        }
    }



    public boolean isMessageAllowed(String message, MessageSource messageSource) {
        //To match ranked player messages? (hopefully?): "\\[.*?\\] (?'username'.*?): .*?" + message + ".*"
        //TODO: Work with extra flag values
        if ((messageSource == this.messageSource)||(messageSource == UNKNOWN_ALL)) {
            return !(regex.matcher(message).matches() && this.enabled);
        }
        return true;
    }

    public boolean isMessageAllowed(String msg) {
        /*
            The order in which flags should be evaluated:
            Only on server
            Case sensitivity
            Whole message
            Whole word
            Smart?
            Whitelist
        */
        boolean letMessagePass = true;

        if (flagList.contains(ChatFilterSettingFlag.CASE_INSENSITIVE)) {
            msg = msg.toLowerCase();
        }

        letMessagePass = !(regex.matcher(msg).matches() && this.enabled);

        return letMessagePass;
    }
}