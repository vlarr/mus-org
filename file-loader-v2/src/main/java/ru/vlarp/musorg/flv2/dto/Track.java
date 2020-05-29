package ru.vlarp.musorg.flv2.dto;

public class Track {
    private String artist;
    private String title;
    private Long trackSource;
    private Long createTime;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTrackSource() {
        return trackSource;
    }

    public void setTrackSource(Long trackSource) {
        this.trackSource = trackSource;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Track() {
    }

    public Track(String artist, String title, Long trackSource, Long createTime) {
        this.artist = artist;
        this.title = title;
        this.trackSource = trackSource;
        this.createTime = createTime;
    }
}
