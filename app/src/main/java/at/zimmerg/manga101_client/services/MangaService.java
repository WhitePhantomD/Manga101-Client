package at.zimmerg.manga101_client.services;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import at.zimmerg.manga101_client.classes.Chapter;
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
                chapter[0] = new Chapter(chapterId,title,chapterNumber,imagePaths,mangaId);
            } catch (JSONException e) {
                Log.e("Chapter","Parsing error"+ jsonObject);
                _chapter.setValue(STATE_INVALID);
                _chapter.setValue(STATE_INITIAL);
            }
            _chapter.setValue(STATE_SUCCESS);
            _chapter.setValue(STATE_INITIAL);
        },volleyError -> {
            Log.e("Chapter","Error"+volleyError);
            _chapter.setValue(STATE_INVALID);
            _chapter.setValue(STATE_INITIAL);
        });


        requestQueue.add(request);

    }

    private void initQueue(Context context) {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

}
