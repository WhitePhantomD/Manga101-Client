package at.zimmerg.manga101_client.viewmodel;

import android.content.Context;
import android.media.Image;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import at.zimmerg.manga101_client.R;
import at.zimmerg.manga101_client.classes.Chapter;
import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.services.MangaService;

public class MainViewModel extends ViewModel {

    public static final int LOGIN = 0;
    public static final int HOME = 1;
    public static final int MANGA = 2;
    public static final int CHAPTER = 3;
    public static final int LIKED_CHAPTER = 4;
    public static final int SETTINGS = 5;
    public static final int CHAPTER_LIST = 6;
    public static final int OFFLINE = 7;
    public static final int BACK = 99;

//    public static final String SERVER_IP = "http://192.168.137.149:8080"; //Home Pc Hotspot
    public static final String SERVER_IP = "http://192.168.229.131:8080"; //Handy Hotspot
//    public static final String SERVER_IP = "http://192.168.229.131:8080"; //Laptop Hotspot

    private MutableLiveData<Integer> _state = new MutableLiveData<>(LOGIN);
    public MutableLiveData<Integer> state = _state;

    public void setToHome() {
        _state.setValue(HOME);
    }

    public void setToManga() {
        _state.setValue(MANGA);
    }

    public void setToChapter() {
        _state.setValue(CHAPTER);
    }

    public void setToLikedChapter() {
        _state.setValue(LIKED_CHAPTER);
    }

    public void setToSettings() {
        _state.setValue(SETTINGS);
    }

    public void setToChapterList() {
        _state.setValue(CHAPTER_LIST);
    }

    public void setToOffline() {
        _state.setValue(OFFLINE);
    }

    public void setToLogin() {
        _state.setValue(LOGIN);
    }

    public void setToBack() {
        _state.setValue(BACK);
    }

    private ArrayList<Chapter> chapters = new ArrayList<>();
    public MutableLiveData<ArrayList<Chapter>> _chapters = new MutableLiveData<>(chapters);

    private Chapter currentChapter;

    public void setCurrentChapter(Chapter chapter) {
        currentChapter = chapter;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    private List<Page> pages = new ArrayList<>();
    public MutableLiveData<List<Page>> _pages = new MutableLiveData<>(pages);

    public void setPages(List<Page> pages) {
        this.pages = pages;
        _pages.setValue(pages);
    }

    private final MangaService mangaService = MangaService.getInstance();

    public LiveData<Integer> mangaServiceChapterState = mangaService.chapterState;

    public Chapter[] serviceChapter = mangaService.chapter;

    public void getChapterById(int id, Context context) {
        mangaService.getChapterById(id, context);
    }



}
