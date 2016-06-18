package soganiabhijeet.com.picscramble.model;

import soganiabhijeet.com.picscramble.model.Media;

/**
 * Created by abhijeetsogani on 6/17/16.
 */
public class Items {

    private String tags;

    private String author;

    private String title;

    private String description;

    private String date_taken;

    private String link;

    private String author_id;

    private String published;

    public Media media;

    private boolean isPositionIdentified;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public boolean getIsPositionIdentified() {
        return isPositionIdentified;
    }

    public void setIsPositionIdentified(boolean positionIdentified) {
        this.isPositionIdentified = positionIdentified;
    }




}
