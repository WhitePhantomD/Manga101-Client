package at.zimmerg.manga101_client.classes;

import java.util.List;

public class Chapter {

    private int id;
    private String title;
    private int chapterNumber;
    private List<Page> pages;
    private int mangaId;

    public Chapter(int id,String title, int chapterNumber, List<Page> images,int mangaId) {
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.pages = images;
        this.id = id;
        this.mangaId = mangaId;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPages() {
        return pages;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }
}
