package com.pratham.readandspeak.ui.home_screen_menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableNew;

import java.util.List;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private List<ContentTableNew> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, List<ContentTableNew> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        ContentTableNew singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getNodeTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.download_icon);
        requestOptions.error(R.drawable.ic_warning);


        if (singleItem.getNodeTitle().equalsIgnoreCase("rhymes")) {
            holder.itemImage.setImageResource(R.drawable.rhymes_icon);
        } else if (singleItem.getNodeTitle().equalsIgnoreCase("stories")) {
            holder.itemImage.setImageResource(R.drawable.story_icon);
        } else if (singleItem.getNodeTitle().equalsIgnoreCase("chit-chat")) {
            holder.itemImage.setImageResource(R.drawable.chat_icon);
        } else if (singleItem.getNodeTitle().equalsIgnoreCase("reading challenge")) {
            holder.itemImage.setImageResource(R.drawable.rc_icon);
        }

        Glide.with(mContext).setDefaultRequestOptions(requestOptions)
                .load(singleItem.getNodeServerImage())
                .into(holder.itemImage);

/*        Glide.with(mContext)
                .load(singleItem.getNodeServerImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.ic_warning)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

}