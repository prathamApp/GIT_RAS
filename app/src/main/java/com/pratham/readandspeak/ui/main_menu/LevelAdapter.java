package com.pratham.readandspeak.ui.main_menu;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pratham.readandspeak.R;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableOuter;
import com.pratham.readandspeak.nestedRecycler.SectionListDataAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.MyViewHolder> {

    private Context mContext;
    private int lastPos = -1;
    private List<ContentTableOuter> contentViewList;
    LevelClicked levelClicked;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button item_btn;
        private ImageButton item_ivbtn;
        private TextView item_content;
        private RecyclerView recycler_view_list;


        public MyViewHolder(View view) {
            super(view);
            item_content = view.findViewById(R.id.tv_content_title);
            recycler_view_list = view.findViewById(R.id.recycler_view_list);

         /*   item_btn = (Button) view.findViewById(R.id.item_btn);
            item_ivbtn = (ImageButton) view.findViewById(R.id.item_ivbtn);
       */
        }
    }

    public LevelAdapter(Context mContext, List<ContentTableOuter> contentViewList, LevelClicked levelClicked) {
        this.mContext = mContext;
        this.contentViewList = contentViewList;
        this.levelClicked = levelClicked;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public LevelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_row, parent, false);
        String title = contentViewList.get(viewType).getNodeTitle();
        if (title.equalsIgnoreCase("reading")) {
            //       itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_row, parent, false);
        } else if (title.equalsIgnoreCase("games")) {
            //       itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_row, parent, false);
        } else if (title.equalsIgnoreCase("test")) {
            //       itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_row, parent, false);
        }
        return new LevelAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final LevelAdapter.MyViewHolder holder, final int position) {
        final ContentTableOuter contentList = contentViewList.get(position);
        holder.item_content.setText(contentList.getNodeTitle());
//        holder.item_btn.setText(contentList.getNodeTitle());
        List<ContentTable> innerList = new ArrayList<>();
        innerList.addAll(contentList.getContentTableList());
        sortList(innerList);
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, innerList);

     //   holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemListDataAdapter.notifyDataSetChanged();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelClicked.onLevelClicked(position, contentList.getNodeId(), contentList.getNodeTitle(), contentList.getResourceType());
            }
        });
        holder.itemView.setVisibility(View.GONE);
        setAnimations(holder.itemView, position);
    }

    private void sortList(List<ContentTable> innerList) {
        Collections.sort(innerList, new Comparator<ContentTable>() {
            @Override
            public int compare(ContentTable o1, ContentTable o2) {
                return o1.getNodeId().compareTo(o2.getNodeId());
            }
        });
        Log.d("sorted", contentViewList.toString());
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

/*
public LevelAdapter(AppCompatActivity context, int resource, List<MainLevelModel> objects, LevelClicked levelClicked) {
        super(context, resource, objects);
        this.activity = context;
        this.contentList = objects;
        this.levelClicked = levelClicked;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            if (position == 0) {
                convertView = inflater.inflate(R.layout.reading_row, parent, false);
            } else if (position == 1) {
                convertView = inflater.inflate(R.layout.games_row, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.test_row, parent, false);
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_btn.setText("" + getItem(position).getNodeTitle());

        if (convertView != null)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    levelClicked.onLevelClicked(position, getItem(position).getNodeId(), contentList.contentId, contentList.contentName, contentList.contentType, contentList.contentDownloaded);
                }
            });

        Animation animation = null;
        animation = AnimationUtils.loadAnimation(activity, R.anim.zoom_in);
        animation.setDuration(1000);
        convertView.startAnimation(animation);
        animation = null;

        return convertView;
    }

    private static class ViewHolder {
        private Button item_btn;
        private ImageButton item_ivbtn;

        public ViewHolder(View view) {
            item_btn = (Button) view.findViewById(R.id.item_btn);
            item_ivbtn = (ImageButton) view.findViewById(R.id.item_ivbtn);
        }
    }
}
*/
