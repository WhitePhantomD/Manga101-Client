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

    private int imageNumber;
    private String image;
    private int chapterId;

    public Page(int imageNumber, String image, int chapterId) {
        this.imageNumber = imageNumber;
        this.image = image;
        this.chapterId = chapterId;
    }


    public int getPageNumber() {
        return imageNumber;
    }

    public void setPageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
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
