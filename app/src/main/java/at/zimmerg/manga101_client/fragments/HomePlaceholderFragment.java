package at.zimmerg.manga101_client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import at.zimmerg.manga101_client.databinding.FragmentHomePlaceholderBinding;
import at.zimmerg.manga101_client.services.MangaService;
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
//        mainViewModel.getChapterById(3502, requireContext());
//        mainViewModel.getChapterById(3525, requireContext());
//        mainViewModel.getChapterById(3535, requireContext());
        mainViewModel.getChapterById(3545, requireContext());
//        mainViewModel.getChapterById(3575, requireContext());

        Picasso.
                get().
                load("http://192.168.137.131:8080/images/eleceed_c001_c010~c001~081.jpg").
                fit().
                centerCrop().
                into(binding.imageViewPh);

        mainViewModel.mangaServiceChapterState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentChapter(mainViewModel.serviceChapter[0]);
                binding.textView.setText(mainViewModel.getCurrentChapter().getTitle());
            }
        });

        mainViewModel.getMangaById(2755, requireContext());
        mainViewModel.mangaServiceMangaState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentManga(mainViewModel.serviceManga[0]);
                binding.textView.setText(mainViewModel.getCurrentManga().getTitle());
            }
        });

        mainViewModel.getMangaChapterListById(2755, requireContext());

        mainViewModel.mangaScerviseMangaChapterListState.observe(getViewLifecycleOwner(), state -> {
            if (state == MangaService.STATE_SUCCESS) {
                mainViewModel.setCurrentMangaChapterList(mainViewModel.serviceMangaChapterList[0]);
            }
        });

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