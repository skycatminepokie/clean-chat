package com.skycat.cleanchat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatFilterSettingGroup {
    @Getter @Setter private boolean enabled = true;
    @Getter @Setter private ArrayList<ChatFilterSetting> settings;
    @Getter @Setter private String name;

    public void addSetting(ChatFilterSetting setting) {
        settings.add(setting);
    }

    public ChatFilterSettingGroup(boolean enabled,  String name, ArrayList<ChatFilterSetting> settings) {
        this.enabled = enabled;
        this.name = name;
        this.settings = settings;
    }

    public ChatFilterSettingGroup(boolean enabled, String name, ChatFilterSetting... settings) {
        this.enabled = enabled;
        this.name = name;
        this.settings = new ArrayList<ChatFilterSetting>(Arrays.asList(settings));
    }
}
