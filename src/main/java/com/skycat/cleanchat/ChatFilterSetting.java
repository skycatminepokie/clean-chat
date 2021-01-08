package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

import static com.skycat.cleanchat.MessageSource.UNKNOWN_ALL;

//What does static mean in an import?

/**
 * A setting that a {@link ChatFilter} can use to decide whether or not a chat message should be cancelled.
 */
public class ChatFilterSetting {
    private final Pattern regex;
    @Getter @Setter private boolean enabled; //getter is isEnabled, not getEnabled. Why? IDK, Lombok magic.
    private final MessageSource messageSource;
    @Getter private final String description;
    @Getter private String name;

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
        this.regex = generateRegex(message);
        this.messageSource = messageSource;
        this.enabled = enabled;
        this.description = description;
        this.name = name;
    }

    /**
     * Creates a regex to help capture parts of the message
     * @param message The base message to match (or partially match)
     * @return A {@link Pattern} containing the generated regex
     */
    private static Pattern generateRegex(String message) {
        //TODO Work with capturing and recording username
        return Pattern.compile(".*?" + message + ".*");
    }

    public boolean isMessageAllowed(String message, MessageSource messageSource) {
        //To match ranked player messages? (hopefully?): "\\[.*?\\] (?'username'.*?): .*?" + message + ".*"
        if ((messageSource == this.messageSource)||(messageSource == UNKNOWN_ALL)) {
            return !(regex.matcher(message).matches() && this.enabled);
        }
        return true;
    }
}