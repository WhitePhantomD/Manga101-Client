package at.zimmerg.manga101_client.classes;

import android.media.Image;

import java.util.List;

public class Chapter {

    private String title;
    private int chapterNumber;
    private List<Page> pages;

    public Chapter(String title, int chapterNumber, List<Page> pages) {
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.pages = pages;
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
