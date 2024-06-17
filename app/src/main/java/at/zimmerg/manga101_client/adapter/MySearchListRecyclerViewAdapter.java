package at.zimmerg.manga101_client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import at.zimmerg.manga101_client.callbacks.OnItemClickListener;
import at.zimmerg.manga101_client.classes.Manga;
import at.zimmerg.manga101_client.databinding.FragmentMangaCardBinding;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class MySearchListRecyclerViewAdapter extends RecyclerView.Adapter<MySearchListRecyclerViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;
    private List<Manga> mValues;
    private OnItemClickListener listener;

    public MySearchListRecyclerViewAdapter(List<Manga> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMangaCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(holder.mItem.getTitle());
        Picasso.
                get().
                load(holder.mItem.getCoverImage()).
                into(holder.mImageView);
        holder.mGenre.setText(holder.mItem.getGenres().toString());
        holder.mStatus.setText(holder.mItem.getStatus());
        holder.mChapterCount.setText(holder.mItem.getChapterCount() + " Chapters");
        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                int id = holder.mItem.getId();
                listener.onItemClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<Manga> items) {
        mValues = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageView;
        public final TextView mTitle;
        public final TextView mGenre;
        public final TextView mStatus;
        public final TextView mChapterCount;
        public Manga mItem;

        public ViewHolder(FragmentMangaCardBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardCoverIv;
            mTitle = binding.cardTitleTv;
            mGenre = binding.genreTv;
            mStatus = binding.cardStatusTv;
            mChapterCount = binding.cardEditChaptercountTv;
        }
    }
}