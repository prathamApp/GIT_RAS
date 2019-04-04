package com.pratham.readandspeak.ui.display_content;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.utilities.RAS_Constants;

import java.util.List;


public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private Context mContext;
    private int lastPos = -1;
    private List<ContentTable> contentViewList;
    ContentClicked contentClicked;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout content_card_view;
        public ImageButton ib_action_btn;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.content_title);
            thumbnail = (ImageView) view.findViewById(R.id.content_thumbnail);
            content_card_view = (RelativeLayout) view.findViewById(R.id.content_card_view);
            ib_action_btn = (ImageButton) view.findViewById(R.id.ib_action_btn);
        }
    }

    public ContentAdapter(Context mContext, List<ContentTable> contentViewList, ContentClicked contentClicked) {
        this.mContext = mContext;
        this.contentViewList = contentViewList;
        this.contentClicked = contentClicked;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ContentTable contentList = contentViewList.get(position);
        holder.title.setText(contentList.getNodeTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.download_icon);
        requestOptions.error(R.drawable.ic_warning);

        if ((contentList.getIsDownloaded().equalsIgnoreCase("true") || contentList.getIsDownloaded().equalsIgnoreCase("1")) && !RAS_Constants.SD_CARD_Content)
            Glide.with(mContext).setDefaultRequestOptions(requestOptions)
                    .load(RASApplication.pradigiPath + "/.RAS/English/RAS_Thumbs/" + contentList.getNodeImage())
                    .into(holder.thumbnail);
        else if ((contentList.getIsDownloaded().equalsIgnoreCase("true") || contentList.getIsDownloaded().equalsIgnoreCase("1")) && RAS_Constants.SD_CARD_Content)
            Glide.with(mContext).setDefaultRequestOptions(requestOptions)
                    .load(RAS_Constants.ext_path + "/.RAS/English/RAS_Thumbs/" + contentList.getNodeImage())
                    .into(holder.thumbnail);
        else
            Glide.with(mContext).setDefaultRequestOptions(requestOptions)
                    .load(contentList.getNodeServerImage())
                    .into(holder.thumbnail);

/*        if (contentList.getNodeType().equalsIgnoreCase("Resource")) {
            holder.iv_download_icon.setVisibility(View.VISIBLE);
            if (contentList.getIsDownloaded().equalsIgnoreCase("true")) {
                holder.iv_download_icon.setVisibility(View.GONE);
            }
        }*/

        holder.ib_action_btn.setVisibility(View.GONE);

        if (!RAS_Constants.SD_CARD_Content) {
            if (contentList.getNodeType().equalsIgnoreCase("Resource") && contentList.getIsDownloaded().equalsIgnoreCase("false")) {
                holder.ib_action_btn.setVisibility(View.VISIBLE);
                holder.ib_action_btn.setImageResource(R.drawable.download_icon);//setVisibility(View.VISIBLE);
                holder.ib_action_btn.setClickable(false);
            } else if (contentList.getIsDownloaded().equalsIgnoreCase("true") && contentList.getNodeType().equalsIgnoreCase("Resource")) {
                holder.ib_action_btn.setVisibility(View.VISIBLE);
                holder.ib_action_btn.setImageResource(R.drawable.iv_delete_icon);
                holder.ib_action_btn.setClickable(true);
                holder.ib_action_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Delete_Clicked", "onClick: G_Adapter");
                        contentClicked.onContentDeleteClicked(position, contentList);
                    }
                });
            }
        }

        holder.content_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentList.getNodeType() != null) {
/*                    if (contentList.getIsDownloaded().equalsIgnoreCase("true") && contentList.getNodeType().equalsIgnoreCase("Resource")) {
                        contentClicked.onStoryOpenClicked(position, contentList.getNodeId());
                    } else if (contentList.getIsDownloaded().equalsIgnoreCase("false") && contentList.getNodeType().equalsIgnoreCase("Resource"))
                        contentClicked.onStoryDownloadClicked(position, contentList.nodeId);
                    else*/
                        contentClicked.onContentClicked(position, contentList.nodeId);
                }
            }
        });
        holder.content_card_view.setVisibility(View.GONE);
        setAnimations(holder.content_card_view, position);
    }

    private void setAnimations(final View content_card_view, final int position) {
        final Animation animation;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.item_fall_down);
        animation.setDuration(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*                if (position > lastPos) {*/
                content_card_view.setVisibility(View.VISIBLE);
                content_card_view.setAnimation(animation);
                lastPos = position;
//                }
            }
        }, (long) (20));

    }

    @Override
    public int getItemCount() {
        return contentViewList.size();
    }
}