package at.zimmerg.manga101_client.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
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
import com.squareup.picasso.Target;

import java.util.List;

import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.databinding.FragmentChapterImageBinding;
import at.zimmerg.manga101_client.databinding.FragmentChapterNavigationBinding;
import at.zimmerg.manga101_client.services.MangaService;
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
        if (position == 0 || position == mValues.size() +1) {
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
            navigationViewHolder.mButtonManga.setOnClickListener(v ->{
                mainViewModel.getMangaById(mainViewModel.getCurrentChapter().getMangaId(),holder.itemView.getContext());
                mainViewModel.setCurrentManga(mainViewModel.serviceManga[0]);
                mainViewModel.setToManga();
            });
            navigationViewHolder.mButtonNext.setOnClickListener(v ->{
                mainViewModel.getChapterById(mainViewModel.getCurrentChapter().getNextChapterId(), holder.itemView.getContext());
            });
            navigationViewHolder.mButtonPrevious.setOnClickListener(v ->{
                mainViewModel.getChapterById(mainViewModel.getCurrentChapter().getPreviousChapterId(), holder.itemView.getContext());
            });
            navigationViewHolder.mTextView.setOnClickListener(v -> {
                mainViewModel.getMangaChapterListById(mainViewModel.getCurrentChapter().getMangaId(), holder.itemView.getContext());
            });
            if (position == 0) {
                navigationViewHolder.mButtonManga.setVisibility(View.GONE);
                navigationViewHolder.mTextView.setVisibility(View.VISIBLE);
                navigationViewHolder.mTextView.setClickable(true);
                navigationViewHolder.mTextView.setText("Chapter: " + mainViewModel.getCurrentChapter().getChapterNumber());
                if (mainViewModel.getCurrentChapter().getNextChapterId() == -1){
                    navigationViewHolder.mButtonNext.setVisibility(View.GONE);
                } else {
                    navigationViewHolder.mButtonNext.setVisibility(View.VISIBLE);
                }

                if (mainViewModel.getCurrentChapter().getPreviousChapterId() == -1){
                    navigationViewHolder.mButtonPrevious.setVisibility(View.GONE);
                } else {
                    navigationViewHolder.mButtonPrevious.setVisibility(View.VISIBLE);
                }

            } else {
                navigationViewHolder.mTextView.setVisibility(View.GONE);
                navigationViewHolder.mButtonManga.setVisibility(View.VISIBLE);
                if (mainViewModel.getCurrentChapter().getNextChapterId() == -1){
                    navigationViewHolder.mButtonNext.setVisibility(View.GONE);
                } else {
                    navigationViewHolder.mButtonNext.setVisibility(View.VISIBLE);
                }

                if (mainViewModel.getCurrentChapter().getPreviousChapterId() == -1){
                    navigationViewHolder.mButtonPrevious.setVisibility(View.GONE);
                } else {
                    navigationViewHolder.mButtonPrevious.setVisibility(View.VISIBLE);
                }
            }
        } else if(getItemViewType(position) == TYPE_PAGE){
            PageViewHolder pageViewHolder = (PageViewHolder) holder;
            if (position <= mValues.size()){
                try{
                pageViewHolder.mItem = mValues.get(position - 1);
                } catch (Exception e){
                    Log.e("Chapter", "Error: " + e.getMessage());
                }
                Log.d("Chapter", "Image: " + pageViewHolder.mItem.getImage());

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        int imageHeight = bitmap.getHeight();
                        int imageWidth = bitmap.getWidth();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        ((Activity) pageViewHolder.mImageView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int displayWidth = displayMetrics.widthPixels;
                        int displayHeight = imageHeight * displayWidth / imageWidth;

                        Log.d("Chapter", "ImageParams: H-" + imageHeight + " W-" + imageWidth);

                        ViewGroup.LayoutParams params = pageViewHolder.mImageView.getLayoutParams();
                        params.height = displayHeight;
                        params.width = displayWidth;

                        pageViewHolder.mImageView.setLayoutParams(params);

                        pageViewHolder.mImageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Log.d("Picasso", "Failed to load image");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // You can also show a placeholder while the image is loading
                    }
                };

                Picasso.get().cancelRequest(pageViewHolder.mImageView);

                Picasso.
                        get().
                        load(pageViewHolder.mItem.getImage()).
                        into(target);

                pageViewHolder.mImageView.setOnClickListener(v -> {
                    Picasso.
                            get().
                            load(pageViewHolder.mItem.getImage()).
                            into(target);
                });

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
        public final Button mButtonPrevious;
        public final Button mButtonNext;

        public NavigationViewHolder(FragmentChapterNavigationBinding binding) {
            super(binding.getRoot());
            mTextView = binding.chapterTextviewInfo;
            mButtonManga = binding.chapterButtonManga;
            mButtonPrevious = binding.chapterButtonPrevious;
            mButtonNext = binding.chapterButtonNext;

        }

    }
}