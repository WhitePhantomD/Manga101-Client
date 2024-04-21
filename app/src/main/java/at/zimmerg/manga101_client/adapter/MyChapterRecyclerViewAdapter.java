package at.zimmerg.manga101_client.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import at.zimmerg.manga101_client.classes.Page;
import at.zimmerg.manga101_client.databinding.FragmentChapterImageBinding;
import at.zimmerg.manga101_client.viewmodel.MainViewModel;

import java.util.List;


public class MyChapterRecyclerViewAdapter extends RecyclerView.Adapter<MyChapterRecyclerViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;
    private List<Page> mValues;

    public MyChapterRecyclerViewAdapter(List<Page> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentChapterImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
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

        public Page mItem;
        public final ImageView mIdView;


        public ViewHolder(FragmentChapterImageBinding binding) {
            super(binding.getRoot());
            mIdView = binding.imageView;

        }

    }
}