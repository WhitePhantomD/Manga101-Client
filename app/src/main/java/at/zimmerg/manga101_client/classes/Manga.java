package at.zimmerg.manga101_client.classes;

import java.util.List;

public class Manga {

    private int id;
    private String title;
    private List<Chapter> chapters;

    public Manga(int id) {
        this.id = id;
    }

    public Manga(int id,String title, List<Chapter> chapters) {
        this.id = id;
        this.title = title;
        this.chapters = chapters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
