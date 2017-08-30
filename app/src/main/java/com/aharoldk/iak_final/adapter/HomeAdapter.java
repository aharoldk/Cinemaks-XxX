package com.aharoldk.iak_final.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.aharoldk.iak_final.R;
import com.aharoldk.iak_final.pojo.Movie.ResultsItem;
import com.aharoldk.iak_final.service.DetailClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    public List<ResultsItem> list;
    private DetailClickListener detailClickListener;

    public HomeAdapter(List<ResultsItem> list) {
        this.list = list;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_home, parent, false);
        return new HomeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {
        holder.bind(list.get(position), list);
        holder.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailClickListener != null) {
                    detailClickListener.onItemDetailClicked(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<ResultsItem> datas) {
        this.list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public void setItemClickListener(DetailClickListener itemClickListener) {
        detailClickListener = itemClickListener;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPoster) ImageView ivPoster;
        @BindView(R.id.progressBar) ProgressBar progressBar;
        @BindView(R.id.llCardView) LinearLayout linearLayout;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void bind(final ResultsItem resultsItem, final List<ResultsItem> list) {
            progressBar.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

            Picasso.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185"+resultsItem.getPosterPath())
                    .resize(185, 278)
                    .centerCrop()
                    .into(ivPoster, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

    }
}
