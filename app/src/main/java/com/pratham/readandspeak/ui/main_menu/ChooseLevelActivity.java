package com.pratham.readandspeak.ui.main_menu;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.async.ZipDownloader;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableOuter;
import com.pratham.readandspeak.interfaces.API_Content_Result;
import com.pratham.readandspeak.nestedRecycler.HomeActivity;
import com.pratham.readandspeak.ui.bottom_fragment.add_student.MenuActivity;
/*import com.pratham.readandspeak.ui.games.gamesDisplay.GamesDisplay;
import com.pratham.readandspeak.ui.profile.ProfileActivity;
import com.pratham.readandspeak.ui.reading.ChooseReadingActivity;*/
import com.pratham.readandspeak.ui.splash_activity.SplashActivity;
//import com.pratham.readandspeak.ui.test.assessment_type.TestTypeActivity;
import com.pratham.readandspeak.utilities.BaseActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseLevelActivity extends BaseActivity implements ChooseLevelContract.ChooseLevelView, LevelClicked {

    ChooseLevelContract.ChooseLevelPresenter presenter;

    @BindView(R.id.level_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_Profile)
    RelativeLayout rl_Profile;
    @BindView(R.id.btn_Profile)
    ImageButton btn_Profile;
    LevelAdapter levelAdapter;
    private List<ContentTableOuter> contentViewList;
    Gson gson;
    API_Content_Result APIContentResult;
    ZipDownloader zipDownloader;
    List<ContentTable> ContentTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        ButterKnife.bind(this);
        presenter = new ChooseLevelPresenter(ChooseLevelActivity.this, this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gson = new Gson();
        contentViewList = new ArrayList<>();
        presenter.getProfileImg();
        presenter.copyListData();
        levelAdapter = new LevelAdapter(this, contentViewList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(levelAdapter);
        levelAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearContentList() {
        contentViewList.clear();
    }

    @Override
    public void addContentToViewList(ContentTableOuter contentTable) {

        contentViewList.add(contentTable);

        Collections.sort(contentViewList, new Comparator<ContentTableOuter>() {
            @Override
            public int compare(ContentTableOuter o1, ContentTableOuter o2) {
                return o1.getNodeId().compareTo(o2.getNodeId());
            }
        });
        Log.d("sorted", contentViewList.toString());
    }/*
 @Override
    public void addContentToInnerViewList(ContentTable contentTable) {

        contentViewList.add(contentTable);

        Collections.sort(contentViewList, new Comparator<ContentTable>() {
            @Override
            public int compare(ContentTable o1, ContentTable o2) {
                return o1.getNodeId().compareTo(o2.getNodeId());
            }
        });
        Log.d("sorted", contentViewList.toString());
    }*/

    @Override
    public void notifyAdapter() {
        levelAdapter.notifyDataSetChanged();
        /*if (COS_Utility.isDataConnectionAvailable(ChooseLevelActivity.this))
                    getAPIContent(RAS_Constants.INTERNET_DOWNLOAD, RAS_Constants.INTERNET_DOWNLOAD_API);
                else {
                    levelAdapter.notifyDataSetChanged();
         }*/
    }

    @Override
    public void setProfileImg(String sImage) {
        if(sImage.equalsIgnoreCase("group_icon"))
            btn_Profile.setImageResource(R.drawable.ic_group_black_24dp);
        else
            Glide.with(this).load(RASApplication.pradigiPath + "/.RAS/English/LLA_Thumbs/" + sImage).into(btn_Profile);
    }

    @OnClick({R.id.btn_Profile, R.id.rl_Profile})
    public void gotoProfileActivity() {
        ButtonClickSound.start();
  //      startActivity(new Intent(this, ProfileActivity.class));
    }

/*    private void exitDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCanceledOnTouchOutside(false);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);
        dialog.show();

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.endSession();
                presenter.clearNodeIds();
                finishAffinity();
                dialog.dismiss();
            }
        });
        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.endSession();
                if (RAS_Constants.SMART_PHONE) {
                    startActivity(new Intent(ChooseLevelActivity.this, SplashActivity.class));
                } else {
                    startActivity(new Intent(ChooseLevelActivity.this, MenuActivity.class));
                }
                presenter.clearNodeIds();
                finish();
                dialog.dismiss();
            }
        });
    }*/

    @Override
    public void onLevelClicked(int position, String nodeId, String contentName, String contentType) {
        ButtonClickSound.start();
        if (nodeId.equalsIgnoreCase("1300")) {
//            showRcycler(nodeId);



            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("nodeId", nodeId);
            startActivity(intent);
        } else if (nodeId.equalsIgnoreCase("1301")) {
           /* Intent intent = new Intent(this, GamesDisplay.class);
            intent.putExtra("nodeId", nodeId);
            startActivity(intent);*/
        } else if (nodeId.equalsIgnoreCase("1302")) {
           /* Intent intent = new Intent(this, TestTypeActivity.class);
            intent.putExtra("nodeId", nodeId);
            startActivity(intent);*/
        }
    }


    /*@Override
    public List<ContentTable> setRecyclerView(String nodeId) {
        return presenter.getListData(nodeId);

    }*/

    /*private void showRcycler(String nodeId) {
        zipDownloader = new ZipDownloader(this);
        *//*readingAdapter = new ReadingAdapter(this, ContentTableList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(readingAdapter);*//*
        presenter.getListData(nodeId);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  exitDialog();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + avatar) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + avatar) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}