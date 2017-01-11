package com.example.studio111.commentist;

/**
 * Created by rsteller on 1/11/2017.
 */

public class FeedItem {
    String title;
    String link;
    String description;
    String pubDate;
    String audioUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
}
