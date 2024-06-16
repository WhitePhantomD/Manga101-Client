package at.zimmerg.manga101_client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.zimmerg.manga101_client.adapter.MyChapterRecyclerViewAdapter;
import at.zimmerg.manga101_client.adapter.MyChapterTestRecyclerViewAdapter;
import at.zimmerg.manga101_client.databinding.FragmentChapterListBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class ChapterFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentChapterListBinding binding;

    public ChapterFragment() {
    }


    public static ChapterFragment newInstance() {
        ChapterFragment fragment = new ChapterFragment();
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
        binding = FragmentChapterListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setPages(mainViewModel.getCurrentChapter().getPages());

        RecyclerView recyclerView = binding.chapterRecyclerviewList;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setInitialPrefetchItemCount(3);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyChapterRecyclerViewAdapter adapter = new MyChapterRecyclerViewAdapter(mainViewModel._pages.getValue());
        recyclerView.setAdapter(adapter);

        adapter.setMainViewModel(mainViewModel);

        mainViewModel.mangaServiceChapterState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentChapter(mainViewModel.serviceChapter[0]);
                mainViewModel.setPages(mainViewModel.getCurrentChapter().getPages());
            }
        });

        mainViewModel._pages.observe(getViewLifecycleOwner(), pages -> {
            adapter.updateList(pages);
            adapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPosition(0);
        });

        mainViewModel.mangaScerviseMangaChapterListState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentMangaChapterList(mainViewModel.serviceMangaChapterList[0]);
                mainViewModel.setToChapterList();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}