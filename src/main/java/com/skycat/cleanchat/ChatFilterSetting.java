package com.skycat.cleanchat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.skycat.cleanchat.MessageSource.*;

public class ChatFilterSetting {
    private Pattern regex;
    private boolean enabled;
    private MessageSource messageSource;
    ChatFilterSetting(String message, MessageSource messageSource, boolean enabled) {
        this.regex = generateRegex(message);
        this.messageSource = messageSource;
        this.enabled = enabled;
    }

    private static Pattern generateRegex(String message) {
        //TODO Change to be a Pattern object?
        //TODO Work with capturing and recording username?
        return Pattern.compile(".*?" + message + ".*");
    }

    public boolean isMessageAllowed(String message, MessageSource messageSource) {
        //TODO Return the result of a regex comparison. Maybe return the matcher???
        //To match ranked player messages? (hopefully?): "\\[.*?\\] (?'username'.*?): .*?" + message + ".*"
        if ((messageSource == this.messageSource)||messageSource == UNKNOWN_ALL) {
            return !(regex.matcher(message).matches() && this.enabled);
        }
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
