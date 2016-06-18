package soganiabhijeet.com.picscramble.model;

import soganiabhijeet.com.picscramble.model.Items;

/**
 * Created by abhijeetsogani on 6/17/16.
 */
public class FlickrModel {

    private String title;
    private String link;
    private String description;
    private String modified;
    private String generator;
    private Items[] items;

    @Override
    public String toString() {
        return "FlickrModel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", modified='" + modified + '\'' +
                ", generator='" + generator + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public Items[] getItems() {
        return items;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }
}
