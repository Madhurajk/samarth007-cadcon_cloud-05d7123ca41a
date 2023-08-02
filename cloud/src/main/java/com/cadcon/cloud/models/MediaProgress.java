package com.cadcon.cloud.models;

public class MediaProgress {
    private long videoDuration;
    private long watchedDuration;

    public MediaProgress(long videoDuration, long watchedDuration) {
        this.videoDuration = videoDuration;
        this.watchedDuration = watchedDuration;
    }

    public MediaProgress() {
    }

    public long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public long getWatchedDuration() {
        return watchedDuration;
    }

    public void setWatchedDuration(long watchedDuration) {
        this.watchedDuration = watchedDuration;
    }
}
