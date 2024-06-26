package at.zimmerg.manga101_client.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import at.zimmerg.manga101_client.callbacks.OnItemClickListener;
import at.zimmerg.manga101_client.classes.Chapter;
import at.zimmerg.manga101_client.databinding.FragmentMangaChapterListItemBinding;
import at.zimmerg.manga101_client.services.MangaService;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;


public class MyChapterListRecyclerViewAdapter extends RecyclerView.Adapter<MyChapterListRecyclerViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;
    private List<Chapter> mValues;
    private OnItemClickListener listener;

    public MyChapterListRecyclerViewAdapter(List<Chapter> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMangaChapterListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mChapterTitle.setText(holder.mItem.getTitle());
        holder.mChapterNumber.setText(String.valueOf(holder.mItem.getChapterNumber()));
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

    public void updateList(List<Chapter> items) {
        mValues = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mChapterTitle;
        public final TextView mChapterNumber;
        public Chapter mItem;

        public ViewHolder(FragmentMangaChapterListItemBinding binding) {
            super(binding.getRoot());
            mChapterTitle = binding.chapterTitle;
            mChapterNumber = binding.chapterNumber;
        }
    }
}