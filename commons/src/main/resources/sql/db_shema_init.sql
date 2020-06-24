-- we don't know how to generate schema main (class Schema) :(
create table D_ARTISTS
(
    ID   INTEGER not null
        constraint D_ARTISTS_pk
            primary key autoincrement,
    NAME TEXT    not null
);

create unique index D_ARTISTS_ID_uindex
    on D_ARTISTS (ID);

create table K_ARTISTS
(
    ID   INTEGER not null
        constraint K_ARTISTS_pk
            primary key autoincrement,
    NAME TEXT    not null
);

create unique index K_ARTISTS_ID_uindex
    on K_ARTISTS (ID);

create table R_ALBUMS
(
    ID              INTEGER not null
        constraint R_ALBUMS_pk
            primary key autoincrement,
    ARTIST          TEXT,
    TITLE           TEXT,
    CREATION_TIME   INTEGER,
    TRACK_SOURCE_ID INTEGER,
    STATE           INTEGER
);

create unique index R_ALBUMS_ID_uindex
    on R_ALBUMS (ID);

create table R_ALBUMS_STG
(
    ID              INTEGER not null
        constraint R_ALBUMS_STG_pk
            primary key autoincrement,
    ARTIST          TEXT,
    TITLE           TEXT,
    CREATION_TIME   INTEGER,
    TRACK_SOURCE_ID INTEGER
);

create unique index R_ALBUMS_STG_ID_uindex
    on R_ALBUMS_STG (ID);

create table R_ARTISTS
(
    ID              INTEGER not null
        constraint R_ARTISTS_pk
            primary key autoincrement,
    NAME            TEXT,
    CREATION_TIME   INTEGER,
    TRACK_SOURCE_ID INTEGER,
    STATE           INTEGER
);

create unique index R_ARTISTS_ID_uindex
    on R_ARTISTS (ID);

create table R_ARTISTS_STG
(
    ID              INTEGER not null
        constraint R_ARTISTS_STG_pk
            primary key autoincrement,
    NAME            STRING  not null,
    CREATION_TIME   INTEGER,
    TRACK_SOURCE_ID INTEGER
);

create unique index R_ARTISTS_STG_ID_uindex
    on R_ARTISTS_STG (ID);

create table R_TRACKS
(
    ID              INTEGER not null
        constraint R_TRACKS_pk
            primary key autoincrement,
    ARTIST          TEXT,
    TITLE           TEXT,
    TRACK_SOURCE_ID INTEGER default 0 not null,
    CREATION_TIME   INTEGER,
    ALBUM           TEXT,
    STATE           INTEGER
);

create unique index R_TRACKS_ID_uindex
    on R_TRACKS (ID);

create table R_TRACKS_STG
(
    ID              INTEGER not null
        constraint R_TRACKS_STG_pk
            primary key autoincrement,
    ARTIST          TEXT,
    TITLE           TEXT,
    TRACK_SOURCE_ID INTEGER default 0 not null,
    CREATION_TIME   INTEGER,
    ALBUM           TEXT
);

create unique index R_TRACKS_STG_ID_uindex
    on R_TRACKS_STG (ID);

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

