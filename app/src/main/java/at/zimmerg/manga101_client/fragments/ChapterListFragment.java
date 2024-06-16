package at.zimmerg.manga101_client.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.zimmerg.manga101_client.adapter.MyChapterListRecyclerViewAdapter;
import at.zimmerg.manga101_client.databinding.FragmentChapterListBinding;
import at.zimmerg.manga101_client.databinding.FragmentMangaChapterListBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

public class ChapterListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MainViewModel mainViewModel;
    private FragmentMangaChapterListBinding binding;

    public ChapterListFragment() {
    }

    public static ChapterListFragment newInstance(int columnCount) {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMangaChapterListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setChapters(mainViewModel.getCurrentMangaChapterList().getChapters());
        // Set the adapter
//        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = binding.mangaChapterListRecyclerview;
            Toolbar toolbar = binding.toolbar;
            toolbar.setTitle(mainViewModel.getCurrentMangaChapterList().getTitle());
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MyChapterListRecyclerViewAdapter adapter = new MyChapterListRecyclerViewAdapter(mainViewModel._chapters.getValue());
            adapter.setOnItemClickListener(id -> {
                mainViewModel.getChapterById(id, requireContext());
                mainViewModel.mangaServiceChapterState.observe(getViewLifecycleOwner(), state -> {
                    if (state == MangaService.STATE_SUCCESS) {
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


//        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}