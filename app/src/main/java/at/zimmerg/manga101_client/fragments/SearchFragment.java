package at.zimmerg.manga101_client.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import at.zimmerg.manga101_client.adapter.MyChapterListRecyclerViewAdapter;
import at.zimmerg.manga101_client.adapter.MySearchListRecyclerViewAdapter;
import at.zimmerg.manga101_client.databinding.FragmentMangaChapterListBinding;
import at.zimmerg.manga101_client.databinding.FragmentSearchListBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

public class SearchFragment extends Fragment {

    private MainViewModel mainViewModel;
    private FragmentSearchListBinding binding;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        binding = FragmentSearchListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle args = getArguments();
        String query = null;
        if (args != null) {
            query = args.getString("query");
            searchManga(query);
        }

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Context context = view.getContext();
        RecyclerView recyclerView = binding.mangaSearchRecyclerview;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MySearchListRecyclerViewAdapter adapter = new MySearchListRecyclerViewAdapter(mainViewModel._searchedManga.getValue());
        adapter.setOnItemClickListener(id -> {
            mainViewModel.getMangaById(id, requireContext());
            mainViewModel.mangaServiceChapterState.observe(getViewLifecycleOwner(), state -> {
                if (state == MangaService.STATE_SUCCESS) {
                    mainViewModel.setCurrentChapter(mainViewModel.serviceChapter[0]);
                    mainViewModel.setToChapter();
                }
            });
        });
        recyclerView.setAdapter(adapter);

        adapter.setMainViewModel(mainViewModel);

        mainViewModel._searchedManga.observe(getViewLifecycleOwner(), manga -> {
            adapter.updateList(manga);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    public void searchManga(String query) {
        mainViewModel.getSearchedManga(query, requireContext());
        mainViewModel.mangaServiceSearchState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setSearchedManga(mainViewModel.searchManga);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}