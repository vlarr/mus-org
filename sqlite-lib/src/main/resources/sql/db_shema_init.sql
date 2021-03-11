create table R_MUS_INFO
(
    ID          INTEGER not null
        constraint R_MUS_INFO_pk
            primary key autoincrement,
    ARTIST      TEXT,
    ALBUM       TEXT,
    TRACK       TEXT,
    SOURCE_ID   INTEGER,
    DATE_CREATE INTEGER,
    TAGS        TEXT,
    STATE       INTEGER
);

create unique index R_MUS_INFO_uindex
    on R_MUS_INFO (ID);

create table R_TRACK_SOURCES
(
    ID                 INTEGER not null
        constraint R_TRACK_SOURCE_pk
            primary key,
    NAME               TEXT,
    DESCRIPTION        TEXT,
    CREATION_TIME      INTEGER,
    LAST_MODIFIED_TIME INTEGER,
    ACTIVE_CHANNEL     INTEGER default 1 not null
);

create unique index R_TRACK_SOURCE_ID_uindex
    on R_TRACK_SOURCES (ID);

