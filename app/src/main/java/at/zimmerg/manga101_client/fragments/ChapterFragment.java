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

        mainViewModel._pages.observe(getViewLifecycleOwner(), pages -> {
            adapter.updateList(pages);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}