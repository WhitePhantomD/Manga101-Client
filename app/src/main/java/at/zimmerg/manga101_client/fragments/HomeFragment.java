package at.zimmerg.manga101_client.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import at.zimmerg.manga101_client.adapter.MySearchListRecyclerViewAdapter;
import at.zimmerg.manga101_client.databinding.FragmentHomeBinding;
import at.zimmerg.manga101_client.databinding.FragmentHomePlaceholderBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MainViewModel mainViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getAllManga(requireContext());
        mainViewModel.mangaServiseAllMangaState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentAllManga(mainViewModel.serviceMangaAll);
            }
        });

        Context context = v.getContext();

        RecyclerView recyclerView = binding.homeMangaRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setNestedScrollingEnabled(false);

        MySearchListRecyclerViewAdapter adapter = new MySearchListRecyclerViewAdapter(mainViewModel._allManga.getValue());
        adapter.setOnItemClickListener(id -> {
            mainViewModel.getMangaById(id, requireContext());
            mainViewModel.mangaServiceMangaState.observe(getViewLifecycleOwner(), state -> {
                if (state == MangaService.STATE_SUCCESS) {
                    mainViewModel.setCurrentManga(mainViewModel.serviceManga[0]);
                    mainViewModel.setToManga();
                }
            });
        });
        recyclerView.setAdapter(adapter);

        adapter.setMainViewModel(mainViewModel);

        binding.imageView2.setOnClickListener(view -> {
            mainViewModel.getAllManga(requireContext());
            mainViewModel.mangaServiseAllMangaState.observe(getViewLifecycleOwner(), state -> {
                if (state == MangaService.STATE_SUCCESS) {
                    mainViewModel.setCurrentAllManga(mainViewModel.serviceMangaAll);
                }
            });
        });

        mainViewModel._allManga.observe(getViewLifecycleOwner(), manga -> {
            adapter.updateList(manga);
            adapter.notifyDataSetChanged();
        });

        binding.toolbar2.setTitle("Manga101");

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}