package at.zimmerg.manga101_client.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import at.zimmerg.manga101_client.adapter.MyChapterListRecyclerViewAdapter;
import at.zimmerg.manga101_client.classes.Manga;
import at.zimmerg.manga101_client.databinding.FragmentMangaBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class MangaFragment extends Fragment {

    private FragmentMangaBinding binding;
    private MainViewModel mainViewModel;

    public MangaFragment() {
        // Required empty public constructor
    }


    public static MangaFragment newInstance() {
        MangaFragment fragment = new MangaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMangaBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setChapters(mainViewModel.getCurrentMangaChapterList().getChapters());

        Manga manga = mainViewModel.getCurrentManga();
        Context context = v.getContext();

        RecyclerView recyclerView = binding.mangaChapterListRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setNestedScrollingEnabled(false);

        MyChapterListRecyclerViewAdapter adapter = new MyChapterListRecyclerViewAdapter(mainViewModel._chapters.getValue());
        adapter.setOnItemClickListener(id -> {
            mainViewModel.getChapterById(id, requireContext());
            mainViewModel.mangaServiceChapterState.observe(getViewLifecycleOwner(), listState -> {
                if (listState == MangaService.STATE_SUCCESS) {
                    mainViewModel.setCurrentChapter(mainViewModel.serviceChapter[0]);
                    mainViewModel.setToChapter();
                }
            });
        });
        recyclerView.setAdapter(adapter);

        adapter.setMainViewModel(mainViewModel);

        mainViewModel._chapters.observe(getViewLifecycleOwner(), chapters -> {
            adapter.updateList(chapters);
            adapter.notifyDataSetChanged();
        });

        mainViewModel.getMangaChapterListById(manga.getId(), requireContext());

        mainViewModel.mangaScerviseMangaChapterListState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentMangaChapterList(mainViewModel.serviceMangaChapterList[0]);
                mainViewModel.getCurrentManga().setChapterCount(mainViewModel.getCurrentMangaChapterList().getChapters().size());
                binding.chaptercountTv.setText(String.valueOf(mainViewModel.getCurrentManga().getChapterCount()));
            }
        });

        Picasso.
                get().
                load(manga.getCoverImage()).
                into(binding.imageView3);

        binding.mangaTitleTv.setText(manga.getTitle());
        binding.authorTv.setText(manga.getAuthor());
        String genres;
        if(manga.getGenres().size() > 0){
            genres = manga.getGenres().get(0);
            for(int i = 1; i < manga.getGenres().size(); i++){
                genres += ", " + manga.getGenres().get(i);
            }
        } else {
            genres = "No genres";
        }
        binding.genresTv.setText(genres);
        binding.statusTv.setText(manga.getStatus());
        binding.chaptercountTv.setText(String.valueOf(manga.getChapterCount()));
        binding.descriptionTv.setText(manga.getDescription());



        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}