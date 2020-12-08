package com.skycat.cleanchat;

public enum MessageSource {
    PLAYER,
    SERVER,
    UNKNOWN_ALL, //For testing, will filter with both server and player messages
    UNKNOWN_NONE //For testing, will filter neither server nor player messages
}
