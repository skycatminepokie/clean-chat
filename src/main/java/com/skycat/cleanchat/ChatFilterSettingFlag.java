package com.skycat.cleanchat;

public enum ChatFilterSettingFlag {
    CASE_SENSITIVE,
    CASE_INSENSITIVE, //Assumed
    WHOLE_WORD_ONLY,
    WHOLE_MESSAGE_ONLY_EXCLUDING_PLAYER_NAME,
    WHOLE_MESSAGE_ONLY,
    REPLACEMENT,
    WHITELIST,
    REMOVE_FORMATTING,
    TEMPORARY, // (Don't save for future launches)
    BYPASS_WHITELIST,
    REGEX,
    MATCH_COLOR,
    ONLY_ON_HYPIXEL, // Not exclusive, just notes that other, non-specified places should be ignored
    ONLY_ON_SKYBLOCK, // Not exclusive, just notes that other, non-specified places should be ignored
    STRICT, // Assumed
    SMART,
    ONLY_AFTER_COLON // Tries to match players only
}
