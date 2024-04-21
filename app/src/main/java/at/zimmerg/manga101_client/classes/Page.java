package at.zimmerg.manga101_client.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.Looper;

import java.nio.ByteBuffer;
import java.nio.charset.spi.CharsetProvider;

public class Page {

    private Chapter chapter;
    private int pageNumber;
    private Image image;

    public Page(Chapter chapter, int pageNumber, Image image) {
        this.chapter = chapter;
        this.pageNumber = pageNumber;
        this.image = image;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
