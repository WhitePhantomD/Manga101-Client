package at.zimmerg.manga101_client.classes;

import java.util.ArrayList;
import java.util.List;

public class Manga {

    private int id;
    private String title;
    private ArrayList<Chapter> chapters;
    private String coverImage;
    private String description;
    private String author;
    private String status;
    private ArrayList<String> genres;
    private int chapterCount;

    public Manga() {
    }

    public Manga(int id) {
        this.id = id;
    }

    public Manga(int id,String title, ArrayList<Chapter> chapters) {
        this.id = id;
        this.title = title;
        this.chapters = chapters;
    }

    public Manga(int id, String title, String coverImage, String description, String author, String status, ArrayList<String> genres, int chapterCount) {
        this.id = id;
        this.title = title;
        this.coverImage = coverImage;
        this.description = description;
        this.author = author;
        this.status = status;
        this.genres = genres;
        this.chapterCount = chapterCount;
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

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

}
