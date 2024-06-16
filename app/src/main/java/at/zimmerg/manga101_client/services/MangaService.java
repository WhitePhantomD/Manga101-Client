package at.zimmerg.manga101_client.services;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import at.zimmerg.manga101_client.classes.Chapter;
import at.zimmerg.manga101_client.classes.Manga;
import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

public class MangaService {

    private final String SERVER_IP = MainViewModel.SERVER_IP;

    private static MangaService instance;

    private MangaService() {

    }

    public static MangaService getInstance() {
        if (instance == null) {
            instance = new MangaService();
        }
        return instance;
    }

    private RequestQueue requestQueue;

    public static int STATE_INITIAL = 0;
    public static int STATE_INVALID = -1;
    public static int STATE_SUCCESS = 1;

    private final MutableLiveData<Integer> _chapter = new MutableLiveData<>(STATE_INITIAL);
    public final LiveData<Integer> chapterState = _chapter;
    public Chapter[] chapter = new Chapter[1];

    public void getChapterById(int id, Context context) {

        initQueue(context);
        String url = SERVER_IP+"/manga/chapter/"+id;

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject -> {
            try {
                Log.d("Chapter",jsonObject.toString());
                int chapterId = jsonObject.getInt("id");
                String title = jsonObject.getString("title");
                int chapterNumber = jsonObject.getInt("chapterNumber");
                JSONArray images = jsonObject.getJSONArray("images");
                ArrayList<Page> imagePaths = new ArrayList<>();
                for (int i = 0; i < images.length(); i++) {
                    imagePaths.add(new Page(images.getJSONObject(i).getInt("imageNumber"),SERVER_IP+"/"+images.getJSONObject(i).getString("image"),images.getJSONObject(i).getInt("chapterId")));
                }
                int mangaId = jsonObject.getInt("mangaId");
                int nextChapterId = jsonObject.getInt("nextChapterId");
                int previousChapterId = jsonObject.getInt("previousChapterId");
                chapter[0] = new Chapter(chapterId,title,chapterNumber,imagePaths,mangaId,nextChapterId,previousChapterId);
            } catch (JSONException e) {
                Log.e("Chapter","Parsing error"+ jsonObject);
                _chapter.setValue(STATE_INVALID);
                _chapter.setValue(STATE_INITIAL);
            }
            Log.d("Chapter","State Success");
            _chapter.setValue(STATE_SUCCESS);
            _chapter.setValue(STATE_INITIAL);
        },volleyError -> {
            Log.e("Chapter","Error"+volleyError);
            _chapter.setValue(STATE_INVALID);
            _chapter.setValue(STATE_INITIAL);
        });


        requestQueue.add(request);

    }
    private final MutableLiveData<Integer> _mangaChapterList = new MutableLiveData<>(STATE_INITIAL);
    public final LiveData<Integer> mangaChapterListState = _mangaChapterList;
    public Manga[] mangaChapterList = new Manga[1];

    public void getMangaChapterListById(int id, Context context) {
        initQueue(context);
        String url = SERVER_IP+"/manga/chapter/list/"+id;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Type mangaType = new TypeToken<Manga>(){}.getType();
                        Manga mangaObj = gson.fromJson(response, mangaType);
                        mangaChapterList[0] = mangaObj;
                        _mangaChapterList.setValue(STATE_SUCCESS);
                        _mangaChapterList.setValue(STATE_INITIAL);
                    } catch (JsonSyntaxException e) {
                        Log.e("Chapter","Parsing error", e);
                        _mangaChapterList.setValue(STATE_INVALID);
                        _mangaChapterList.setValue(STATE_INITIAL);
                    }
                },
                error -> {
                    Log.e("Chapter","Error", error);
                    _mangaChapterList.setValue(STATE_INVALID);
                    _mangaChapterList.setValue(STATE_INITIAL);
                }
        );

        requestQueue.add(request);
    }

//    public void getMangaChapterListById(int id, Context context) {
//
//        initQueue(context);
//        String url = SERVER_IP+"/manga/chapter/list/"+id;
//
//        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject -> {
//            try {
//                Log.d("Chapter",jsonObject.toString());
//                int mangaId = jsonObject.getInt("mangaId");
//                String mangaName = jsonObject.getString("mangaName");
//                JSONArray chapters = jsonObject.getJSONArray("chapters");
//                ArrayList<Chapter> chapterArrayList = new ArrayList<>();
//                for (int i = 0; i < chapters.length(); i++) {
//                    Chapter newChapter = new Chapter(chapters.getJSONObject(i).getInt("id"));
//                    newChapter.setTitle(chapters.getJSONObject(i).getString("title"));
//                    newChapter.setChapterNumber(chapters.getJSONObject(i).getInt("chapterNumber"));
//                    chapterArrayList.add(newChapter);
//                }
//                mangaChapterList[0] = new Manga(mangaId,mangaName,chapterArrayList);
//            } catch (JSONException e) {
//                Log.e("Chapter","Parsing error"+ jsonObject);
//                _mangaChapterList.setValue(STATE_INVALID);
//                _mangaChapterList.setValue(STATE_INITIAL);
//            }
//            _mangaChapterList.setValue(STATE_SUCCESS);
//            _mangaChapterList.setValue(STATE_INITIAL);
//        },volleyError -> {
//            Log.e("Chapter","Error"+volleyError);
//            _mangaChapterList.setValue(STATE_INVALID);
//            _mangaChapterList.setValue(STATE_INITIAL);
//        });
//
//
//        requestQueue.add(request);
//
//    }

    private void initQueue(Context context) {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

}
