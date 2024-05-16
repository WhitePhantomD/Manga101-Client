package at.zimmerg.manga101_client.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import at.zimmerg.manga101_client.R;
import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.databinding.FragmentChapterImageBinding;
import at.zimmerg.manga101_client.databinding.FragmentChapterNavigationBinding;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class MyChapterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NAVIGATION = 0;
    private static final int TYPE_PAGE = 1;

    private MainViewModel mainViewModel;
    private List<Page> mValues;

    public MyChapterRecyclerViewAdapter(List<Page> items) {
        mValues = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == mValues.size()) {
            return TYPE_NAVIGATION;
        } else {
            return TYPE_PAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NAVIGATION) {
            return new NavigationViewHolder(FragmentChapterNavigationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new PageViewHolder(FragmentChapterImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NAVIGATION) {
            NavigationViewHolder navigationViewHolder = (NavigationViewHolder) holder;
            if (position == 0) {
                navigationViewHolder.mButtonManga.setVisibility(View.GONE);
                navigationViewHolder.mTextView.setVisibility(View.VISIBLE);
                navigationViewHolder.mTextView.setText("Chapter: " + mainViewModel.getCurrentChapter().getChapterNumber());
            } else {
                navigationViewHolder.mTextView.setVisibility(View.GONE);
                navigationViewHolder.mButtonManga.setVisibility(View.VISIBLE);
                navigationViewHolder.mButtonManga.setOnClickListener(v -> mainViewModel.setToManga());
            }
        } else if(getItemViewType(position) == TYPE_PAGE){
            PageViewHolder pageViewHolder = (PageViewHolder) holder;
            if (position -1 < mValues.size()){
                pageViewHolder.mItem = mValues.get(position - 1);

                Log.d("Chapter", "Image: " + pageViewHolder.mItem.getImage());


                Picasso.
                        get().
                        load(pageViewHolder.mItem.getImage()).
                        error(R.drawable.ic_launcher_background).
                        fit().
                        centerCrop().
                        into(pageViewHolder.mImageView);
            } else {
                Log.e("Chapter", "To many images?" + (position - 1));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size()+2;
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void updateList(List<Page> items) {
        mValues = items;
    }

    public class PageViewHolder extends RecyclerView.ViewHolder {

        public Page mItem;
        public final ImageView mImageView;


        public PageViewHolder(FragmentChapterImageBinding binding) {
            super(binding.getRoot());
            mImageView = binding.imageView;
        }
    }

    public class NavigationViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextView;
        public final Button mButtonManga;
        public final Button mButtonBefore;
        public final Button mButtonNext;

        public NavigationViewHolder(FragmentChapterNavigationBinding binding) {
            super(binding.getRoot());
            mTextView = binding.chapterTextviewInfo;
            mButtonManga = binding.chapterButtonManga;
            mButtonBefore = binding.chapterButtonBefore;
            mButtonNext = binding.chapterButtonNext;

        }

    }
}