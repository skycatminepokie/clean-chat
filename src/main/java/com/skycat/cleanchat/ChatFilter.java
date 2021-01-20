package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ChatFilter {
    private boolean enabled;

    @Getter @Setter
    private ArrayList<ChatFilterSetting> settings;

    /**
     * Create a ChatFilter. It's probably only necessary to create one.
     * @param enabled If the filter should be enabled
     */
    ChatFilter(boolean enabled) {
        this.enabled = enabled;
    }

    ChatFilter(boolean enabled, ArrayList<ChatFilterSetting> settings) {
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
        String[] shortNames = new String[settings.size()];
        for (int i = 0; i < shortNames.length; i++) {
            shortNames[i] = settings.get(i).getName();
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

    /**
     * Make sure that all nullable variables are initialized
     * @return True if none are null, false if one or more is null
     */
    public boolean isFullyCreated() {
        return (settings != null);
    }

    public String modifyChatMessage(String msg) {
        for (ChatFilterSetting s: settings) {
            if (s.getReplacement() != null) {
                msg = msg.replace(s.getMessage(), s.getReplacement());
            }
        }
        return msg;
    }
}