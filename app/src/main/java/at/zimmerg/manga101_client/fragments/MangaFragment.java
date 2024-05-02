package at.zimmerg.manga101_client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.zimmerg.manga101_client.databinding.FragmentMangaBinding;
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

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}