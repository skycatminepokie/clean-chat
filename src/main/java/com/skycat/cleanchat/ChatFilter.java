package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

public class ChatFilter {
    private boolean enabled;
    @Getter @Setter
    private ChatFilterSetting[] settings;

    /**
     * Create a ChatFilter. It's probably only necessary to create one.
     * @param enabled If the filter should be enabled
     */
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

    public String[] getSettingNames() {
        String[] shortNames = new String[settings.length];
        for (int i = 0; i < shortNames.length; i++) {
            shortNames[i] = settings[i].getName();
        }
        return shortNames;
    }

    /**
     * Deletes a setting from the list of settings
     * @param setting The setting to delete
     * @return True if the setting was removed, otherwise returns false.
     */
    /*public boolean deleteSetting(ChatFilterSetting setting) {

    }*/

}