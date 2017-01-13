package Objects;

/**
 * Created by rsteller on 1/11/2017.
 */

public class FeedItem {
    String title;
    String link;
    String description;
    String pubDate;
    String audioUrl;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    String length;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
//        String find = "Episode Description";
//        String first = description.substring(description.indexOf(find)+find.length(), find.length());
        //String shortDescription = description.split(test);

        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate.substring(0,17);
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
