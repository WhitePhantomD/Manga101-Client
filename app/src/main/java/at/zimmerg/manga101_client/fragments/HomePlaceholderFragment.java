package at.zimmerg.manga101_client.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.zimmerg.manga101_client.databinding.FragmentHomePlaceholderBinding;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class HomePlaceholderFragment extends Fragment {

    private FragmentHomePlaceholderBinding binding;
    private MainViewModel mainViewModel;


    public HomePlaceholderFragment() {
        // Required empty public constructor
    }


    public static HomePlaceholderFragment newInstance() {
        HomePlaceholderFragment fragment = new HomePlaceholderFragment();
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
        binding = FragmentHomePlaceholderBinding.inflate(inflater, container, false);
//        binding = FragmentHomePlaceholderBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

//        binding.placeholderhomeClLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));

        binding.placeholderhomeBtnLogin.setOnClickListener(x -> {
            mainViewModel.setToLogin();
        });
        binding.placeholderhomeBtnChapter.setOnClickListener(x -> {
            mainViewModel.setToChapter();
        });
        binding.placeholderhomeBtnManga.setOnClickListener(x -> {
            mainViewModel.setToManga();
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}