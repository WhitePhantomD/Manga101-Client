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

    private int chapterId;
    private int pageNumber;
    private String image;

    public Page(int pageNumber, String image, int chapterId) {
        this.pageNumber = pageNumber;
        this.image = image;
        this.chapterId = chapterId;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
