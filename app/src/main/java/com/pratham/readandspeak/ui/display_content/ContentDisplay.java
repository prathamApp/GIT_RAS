package com.pratham.readandspeak.ui.display_content;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.custom.progress_layout.ProgressLayout;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.modalclasses.EventMessage;
import com.pratham.readandspeak.modalclasses.Modal_FileDownloading;
import com.pratham.readandspeak.utilities.BaseActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentDisplay extends BaseActivity implements ContentContract.ContentView, ContentClicked {

    ContentContract.ContentPresenter presenter;

    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    int tempDownloadPos;
    String downloadNodeId, resName, resServerImageName;
    List<ContentTable> ContentTableList;
    public Dialog downloadDialog;
    ProgressLayout progressLayout;
    TextView dialog_file_name;
    ImageView iv_file_trans;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String nodeId;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_display);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        presenter = new ContentPresenter(ContentDisplay.this, this);
        ContentTableList = new ArrayList<>();
        nodeId = getIntent().getStringExtra("nodeId");
        recyclerView = (RecyclerView) findViewById(R.id.content_recycler_view);
        contentAdapter = new ContentAdapter(this, ContentTableList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contentAdapter);
        setTitle();
    }

    private void setTitle() {
        presenter.addNodeIdToList(nodeId);
        presenter.getListData();
    }

    @Override
    public void clearContentList() {
        ContentTableList.clear();
    }

    @Override
    public void addContentToViewList(List<ContentTable> contentTable) {
        ContentTableList.addAll(contentTable);
    }

    @Override
    public void notifyAdapter() {
        Collections.sort(ContentTableList, new Comparator<ContentTable>() {
            @Override
            public int compare(ContentTable o1, ContentTable o2) {
                return o1.getNodeId().compareTo(o2.getNodeId());
            }
        });
        contentAdapter.notifyDataSetChanged();
    }

    
    @Override
    public void showNoDataDownloadedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_title = dialog.findViewById(R.id.dia_title);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);
        dialog.show();
        tv_title.setText("Connect To Internet");
        exit_btn.setText("BYE");
        restart_btn.setVisibility(View.GONE);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onContentClicked(int position, String nId) {
        ButtonClickSound.start();
        ContentTableList.clear();
        presenter.addNodeIdToList(nId);
        presenter.getListData();
    }

   /* @Override
    public void onStoryOpenClicked(int position, String nodeId) {
        ButtonClickSound.start();
        if (ContentTableList.get(position).getNodeType().equalsIgnoreCase("Resource") && contentType.equalsIgnoreCase("Stories") || contentType.equalsIgnoreCase("Rhymes")) {
            Intent mainNew = new Intent(ContentDisplay.this, ReadingScreenActivity.class);
            mainNew.putExtra("storyId", ContentTableList.get(position).getResourceId());
            mainNew.putExtra("StudentID", RAS_Constants.currentStudentID);
            mainNew.putExtra("storyPath", ContentTableList.get(position).getResourcePath());
            mainNew.putExtra("storyTitle", ContentTableList.get(position).getNodeTitle());
            mainNew.putExtra("readType", readType);
            mainNew.putExtra("contentType", contentType);
            presenter.starContentEntry(ContentTableList.get(position).getResourceId(), contentType + " start");
            startActivity(mainNew);
        } else if (ContentTableList.get(position).getNodeType().equalsIgnoreCase("Resource") && contentType.equalsIgnoreCase("conversations")) {
            Intent mainNew = new Intent(ContentDisplay.this, conversationActivity.class);
            mainNew.putExtra("storyId", ContentTableList.get(position).getResourceId());
            mainNew.putExtra("StudentID", RAS_Constants.currentStudentID);
            mainNew.putExtra("contentName", ContentTableList.get(position).getNodeTitle());
            mainNew.putExtra("contentPath", ContentTableList.get(position).getResourcePath());
            mainNew.putExtra("contentType", contentType);
            mainNew.putExtra("convoMode", convoMode);
            startActivity(mainNew);
            presenter.starContentEntry(ContentTableList.get(position).getResourceId(), contentType + " start");
        }
    }*/

/*    @Override
    public void onStoryDownloadClicked(int position, String nodeId) {
        ButtonClickSound.start();
        downloadNodeId = nodeId;
        resName = ContentTableList.get(position).getNodeTitle();
        resServerImageName = ContentTableList.get(position).getNodeServerImage();
        tempDownloadPos = position;
        if (RAS_Utility.isDataConnectionAvailable(ContentDisplay.this))
            presenter.downloadResource(downloadNodeId);
        else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }*/

/*    @Override
    public void onContentDeleteClicked(int position, ContentTable contentList) {

    }*/

    @Override
    public void onContentDeleteClicked(int position, ContentTable contentList) {
        ButtonClickSound.start();
        Log.d("Delete_Clicked", "onClick: G_Activity");
        showDeleteDialog(position, contentList);
    }

    private void showDeleteDialog(final int deletePos, final ContentTable contentTableItem) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        TextView tv_title = dialog.findViewById(R.id.dia_title);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);
        dialog.show();
        tv_title.setText("Delete\n"+contentTableItem.getNodeTitle());
        exit_btn.setText("NO");
        restart_btn.setText("YES");

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteContent(deletePos, contentTableItem);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void notifyAdapterItem(int deletePos) {
        ContentTableList.get(deletePos).setIsDownloaded("false");
        contentAdapter.notifyItemChanged(deletePos, ContentTableList.get(deletePos));
    }

    @Override
    public void onBackPressed() {
        if (presenter.removeLastNodeId()) {
            ContentTableList.clear();
            presenter.getListData();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    private void resourceDownloadDialog(Modal_FileDownloading modal_fileDownloading) {
/*        downloadDialog = new Dialog(this);
        downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        downloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        downloadDialog.setContentView(R.layout.dialog_file_downloading);
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();
        progressLayout = downloadDialog.findViewById(R.id.dialog_progressLayout);
        dialog_file_name = downloadDialog.findViewById(R.id.dialog_file_name);
        iv_file_trans = downloadDialog.findViewById(R.id.iv_file_trans);
        Glide.with(this).load(resServerImageName).into(iv_file_trans);
        dialog_file_name.setText("" + resName);
        progressLayout.setCurProgress(modal_fileDownloading.getProgress());*/
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceived(EventMessage message) {
        if (message != null) {
            if (message.getMessage().equalsIgnoreCase(RAS_Constants.FILE_DOWNLOAD_STARTED)) {
                resourceDownloadDialog(message.getModal_fileDownloading());
            } else if (message.getMessage().equalsIgnoreCase(RAS_Constants.FILE_DOWNLOAD_UPDATE)) {
                if (progressLayout != null)
                    progressLayout.setCurProgress(message.getModal_fileDownloading().getProgress());
            } else if (message.getMessage().equalsIgnoreCase(RAS_Constants.FILE_DOWNLOAD_ERROR)) {
                downloadDialog.dismiss();
                showDownloadErrorDialog();
            } else if (message.getMessage().equalsIgnoreCase(RAS_Constants.FILE_DOWNLOAD_COMPLETE)) {
                if (downloadDialog != null) {
                    downloadDialog.dismiss();
                }
                    resName = "";
                    ContentTableList.get(tempDownloadPos).setIsDownloaded("true");
                    contentAdapter.notifyItemChanged(tempDownloadPos, ContentTableList.get(tempDownloadPos));
            }

        }
    }

    private void showDownloadErrorDialog() {
        final Dialog errorDialog = new Dialog(this);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.setContentView(R.layout.dialog_file_error_downloading);
        errorDialog.setCanceledOnTouchOutside(false);
        errorDialog.show();
        Button ok_btn = errorDialog.findViewById(R.id.dialog_error_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialog.dismiss();
            }
        });
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