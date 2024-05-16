package at.zimmerg.manga101_client.adapter;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import at.zimmerg.manga101_client.R;
import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.databinding.FragmentChapterImageBinding;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

import java.util.List;


public class MyChapterTestRecyclerViewAdapter extends RecyclerView.Adapter<MyChapterTestRecyclerViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;
    private List<Page> mValues;

    public MyChapterTestRecyclerViewAdapter(List<Page> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentChapterImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Log.d("Chapter", "ImageTest: " + holder.mItem.getImage());

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int imageHeight = bitmap.getHeight();
                int imageWidth = bitmap.getWidth();

                ViewGroup.LayoutParams params = holder.mImageView.getLayoutParams();
                params.height = imageHeight;
                params.width = imageWidth;
                
                holder.mImageView.setLayoutParams(params);

                holder.mImageView.setImageBitmap(bitmap);
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

        Picasso.get().cancelRequest(holder.mImageView);

        Picasso.
                get().
                load(holder.mItem.getImage()).
                into(target);

        holder.mImageView.setOnClickListener(v -> {
            Picasso.
                    get().
                    load(holder.mItem.getImage()).
                    into(target);
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void updateList(List<Page> items) {
        mValues = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public Page mItem;

        public ViewHolder(FragmentChapterImageBinding binding) {
            super(binding.getRoot());
            mImageView = binding.imageView;
        }
    }
}
