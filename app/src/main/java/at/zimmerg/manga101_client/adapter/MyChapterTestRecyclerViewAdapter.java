package at.zimmerg.manga101_client.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

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

        Picasso.
                get().
                load(holder.mItem.getImage()).
                fit().
//                resize(50,50).
                centerCrop().
                into(holder.mImageView);
//        Picasso.get().load(holder.mItem.getImage()).into(holder.mImageView);
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
