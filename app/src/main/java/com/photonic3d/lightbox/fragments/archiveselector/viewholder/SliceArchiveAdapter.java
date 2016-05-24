package com.photonic3d.lightbox.fragments.archiveselector.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photonic3d.lightbox.R;

import java.util.List;

public class SliceArchiveAdapter extends RecyclerView.Adapter<SliceArchiveAdapter.MyViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(SliceArchive item);
    }

    private List<SliceArchive> sliceArchiveList;
    private OnItemClickListener listener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }

        public void bind(final SliceArchive item, final OnItemClickListener listener) {

            title.setText(item.getFile().getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public SliceArchiveAdapter(List<SliceArchive> sliceArchiveList, OnItemClickListener listener) {
        this.sliceArchiveList = sliceArchiveList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_slice_archive, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SliceArchive sliceArchive = sliceArchiveList.get(position);
        holder.bind(sliceArchive, listener);
    }

    @Override
    public int getItemCount() {
        return sliceArchiveList.size();
    }
}