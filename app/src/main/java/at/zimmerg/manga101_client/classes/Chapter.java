package at.zimmerg.manga101_client.classes;

import java.util.List;

public class Chapter {

    private int id;
    private String title;
    private int chapterNumber;
    private List<Page> pages;
    private int mangaId;
    private int nextChapterId;
    private int previousChapterId;

    public Chapter(int id) {
        this.id = id;
    }

    public Chapter(int id, String title, int chapterNumber, List<Page> pages, int mangaId, int nextChapterId, int previousChapterId) {
        this.id = id;
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.pages = pages;
        this.mangaId = mangaId;
        this.nextChapterId = nextChapterId;
        this.previousChapterId = previousChapterId;
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

    public int getNextChapterId() {
        return nextChapterId;
    }

    public void setNextChapterId(int nextChapterId) {
        this.nextChapterId = nextChapterId;
    }

    public int getPreviousChapterId() {
        return previousChapterId;
    }

    public void setPreviousChapterId(int previousChapterId) {
        this.previousChapterId = previousChapterId;
    }
}
