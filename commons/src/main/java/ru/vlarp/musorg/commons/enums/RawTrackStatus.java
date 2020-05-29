package ru.vlarp.musorg.commons.enums;

public enum RawTrackStatus {
    NONE(0L),
    NOT_FIND_ARTIST(1L),
    FIND_ARTIST(2L),
    NOT_FIND_TRACK(12L),
    FIND_TRACK(22L);

    RawTrackStatus(long id) {
        this.id = id;
    }

    public final long id;
}
