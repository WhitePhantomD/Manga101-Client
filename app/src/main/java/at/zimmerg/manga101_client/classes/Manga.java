package at.zimmerg.manga101_client.classes;

import java.util.List;

public class Manga {
    private String title;
    private List<Chapter> chapters;

    public Manga(String title, List<Chapter> chapters) {
        this.title = title;
        this.chapters = chapters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
