package at.zimmerg.manga101_client;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import at.zimmerg.manga101_client.databinding.ActivityMainBinding;
import at.zimmerg.manga101_client.fragments.ChapterFragment;
import at.zimmerg.manga101_client.fragments.HomePlaceholderFragment;
import at.zimmerg.manga101_client.fragments.LoginFragment;
import at.zimmerg.manga101_client.fragments.MangaFragment;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainConstraintlayoutFragmentcontainer, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        mainViewModel.state.observe(this, state -> {
            if (state.equals(MainViewModel.LOGIN)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.mainConstraintlayoutFragmentcontainer.getId(), LoginFragment.newInstance())
                        .commit();
            } else if (state.equals(MainViewModel.HOME)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.mainConstraintlayoutFragmentcontainer.getId(), HomePlaceholderFragment.newInstance())
                        .commit();
            } else if (state.equals(MainViewModel.MANGA)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.mainConstraintlayoutFragmentcontainer.getId(), MangaFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            } else if (state.equals(MainViewModel.CHAPTER)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.mainConstraintlayoutFragmentcontainer.getId(), ChapterFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}