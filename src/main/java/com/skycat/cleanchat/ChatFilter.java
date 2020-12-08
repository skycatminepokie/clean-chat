package com.skycat.cleanchat;

public class ChatFilter {
    private boolean enabled;
    private ChatFilterSetting[] settings;

    ChatFilter(boolean enabled) {
        this.enabled = enabled;
    }

    ChatFilter(boolean enabled, ChatFilterSetting[] settings) {
        this.enabled = enabled;
        this.settings = settings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMessageAllowed(String message, MessageSource messageSource) {
        for(ChatFilterSetting setting: settings) {
            if (!setting.isMessageAllowed(message, messageSource)) {
                return false;
            }
        }
        return true;
    }
}