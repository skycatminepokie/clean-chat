package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ChatFilter {
    private boolean enabled;

    @Getter @Setter
    private ArrayList<ChatFilterSettingGroup> settingGroups;

    /**
     * Create a ChatFilter. It's probably only necessary to create one.
     * @param enabled If the filter should be enabled
     */
    ChatFilter(boolean enabled) {
        this.enabled = enabled;
    }

    ChatFilter(boolean enabled, ArrayList<ChatFilterSettingGroup> settingGroups) {
        this.enabled = enabled;
        this.settingGroups = settingGroups;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMessageAllowed(String message, MessageSource messageSource) {
        for (ChatFilterSettingGroup group: settingGroups) {
            if (group.isEnabled()) {
                for(ChatFilterSetting setting: group.getSettings()) {
                    if (!setting.isMessageAllowed(message, messageSource)) {
                        return false;
                    }
                }
            }

        }

        return true;
    }

    public String[] getSettingNames() {
        int numberOfElements = 0;
        for (ChatFilterSettingGroup group: settingGroups) {
            numberOfElements += group.getSettings().size();
        }
        String[] shortNames = new String[numberOfElements];
        int i = 0;
        for (ChatFilterSettingGroup group: settingGroups) {
            for (ChatFilterSetting setting : group.getSettings()) {
                shortNames[i] = setting.getName();
                i++;
            }
        }

        return shortNames;
    }


    /**
     * Make sure that all nullable variables are initialized
     * @return True if none are null, false if one or more is null
     */
    public boolean isFullyCreated() {
        return (settingGroups != null);
    }

    public String modifyChatMessage(String msg) {
        for (ChatFilterSettingGroup group: settingGroups) {
            for (ChatFilterSetting setting: group.getSettings()) {
                if (setting.getReplacement() != null) {
                    msg = msg.replace(setting.getMessage(), setting.getReplacement());
                }
            }
        }
        return msg;
    }
}