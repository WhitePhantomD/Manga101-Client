package at.zimmerg.manga101_client.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

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




        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}