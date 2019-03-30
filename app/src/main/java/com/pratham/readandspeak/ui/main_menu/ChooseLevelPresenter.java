package com.pratham.readandspeak.ui.main_menu;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.utilities.RAS_Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.pratham.readandspeak.utilities.BaseActivity.appDatabase;

public class ChooseLevelPresenter implements ChooseLevelContract.ChooseLevelPresenter {

    Context context;
    ChooseLevelContract.ChooseLevelView levelView;
    List<ContentTable> contentTableList, downloadedContentTableList;
    ArrayList<String> nodeIds;


    public ChooseLevelPresenter(Context context, ChooseLevelContract.ChooseLevelView levelView) {
        this.context = context;
        this.levelView = levelView;
        nodeIds = new ArrayList<>();
        nodeIds.add("1299");
    }


    @Override
    public void copyListData() {
        new AsyncTask<Object, Void, Object>() {
            String currentSession;

            @Override
            protected Object doInBackground(Object[] objects) {
                try {

                    if(!RAS_Constants.SD_CARD_Content) {

                        AssetManager assetManager = context.getAssets();
                        try {
                            InputStream in = assetManager.open("ras_database");
                            OutputStream out = new FileOutputStream(RASApplication.pradigiPath + "/.LLA/ras_database");
                            byte[] buffer = new byte[1024];
                            int read = in.read(buffer);
                            while (read != -1) {
                                out.write(buffer, 0, read);
                                read = in.read(buffer);
                            }
                            File folder_file, db_file;
                            if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(RAS_Constants.KEY_MENU_COPIED, false)) {
                                try {
                                    if (!RAS_Constants.SMART_PHONE)
                                        folder_file = new File(RAS_Constants.ext_path);
                                    else
                                        folder_file = new File(RASApplication.pradigiPath + "/.LLA/English/");
                                    if (folder_file.exists()) {
                                        Log.d("-CT-", "doInBackground RAS_Constants.ext_path: " + RAS_Constants.ext_path);
                                        db_file = new File(folder_file.getAbsolutePath() + "/" + AppDatabase.DB_NAME);
                                        if (db_file.exists()) {
                                            SQLiteDatabase db = SQLiteDatabase.openDatabase(db_file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
                                            if (db != null) {
                                                Cursor content_cursor;
                                                try {
                                                    content_cursor = db.rawQuery("SELECT * FROM ContentTable", null);
                                                    //populate contents
                                                    List<ContentTable> contents = new ArrayList<>();
                                                    if (content_cursor.moveToFirst()) {
                                                        while (!content_cursor.isAfterLast()) {
                                                            ContentTable detail = new ContentTable();
                                                            detail.setNodeId(content_cursor.getString(content_cursor.getColumnIndex("nodeId")));
                                                            detail.setNodeType(content_cursor.getString(content_cursor.getColumnIndex("nodeType")));
                                                            detail.setNodeTitle(content_cursor.getString(content_cursor.getColumnIndex("nodeTitle")));
                                                            detail.setNodeKeywords(content_cursor.getString(content_cursor.getColumnIndex("nodeKeywords")));
                                                            detail.setNodeAge(content_cursor.getString(content_cursor.getColumnIndex("nodeAge")));
                                                            detail.setNodeDesc(content_cursor.getString(content_cursor.getColumnIndex("nodeDesc")));
                                                            detail.setNodeServerImage(content_cursor.getString(content_cursor.getColumnIndex("nodeServerImage")));
                                                            detail.setNodeImage(content_cursor.getString(content_cursor.getColumnIndex("nodeImage")));
                                                            detail.setResourceId(content_cursor.getString(content_cursor.getColumnIndex("resourceId")));
                                                            detail.setResourceType(content_cursor.getString(content_cursor.getColumnIndex("resourceType")));
                                                            detail.setResourcePath(content_cursor.getString(content_cursor.getColumnIndex("resourcePath")));
                                                            detail.setLevel("" + content_cursor.getInt(content_cursor.getColumnIndex("level")));
                                                            detail.setContentLanguage(content_cursor.getString(content_cursor.getColumnIndex("contentLanguage")));
                                                            detail.setParentId(content_cursor.getString(content_cursor.getColumnIndex("parentId")));
                                                            detail.setContentType(content_cursor.getString(content_cursor.getColumnIndex("contentType")));
                                                            if (!RAS_Constants.SMART_PHONE)
                                                                detail.setOnSDCard(true);
                                                            else
                                                                detail.setOnSDCard(false);
                                                            contents.add(detail);
                                                            content_cursor.moveToNext();
                                                        }
                                                    }
                                                    appDatabase.getContentTableDao().addContentList(contents);
                                                    content_cursor.close();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                getListData();
            }
        }.execute();

    }

    private void getListData() {
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    downloadedContentTableList = appDatabase.getContentTableDao().getContentData(nodeIds.get(nodeIds.size() - 1));
                    BackupDatabase.backup(context);

                    levelView.clearContentList();

                    try {
                        for (int j = 0; j < downloadedContentTableList.size(); j++) {
                            ContentTable contentTable = new ContentTable();
                            contentTable.setNodeId("" + downloadedContentTableList.get(j).getNodeId());
                            contentTable.setNodeType("" + downloadedContentTableList.get(j).getNodeType());
                            contentTable.setNodeTitle("" + downloadedContentTableList.get(j).getNodeTitle());
                            contentTable.setNodeKeywords("" + downloadedContentTableList.get(j).getNodeKeywords());
                            contentTable.setNodeAge("" + downloadedContentTableList.get(j).getNodeAge());
                            contentTable.setNodeDesc("" + downloadedContentTableList.get(j).getNodeDesc());
                            contentTable.setNodeServerImage("" + downloadedContentTableList.get(j).getNodeServerImage());
                            contentTable.setNodeImage("" + downloadedContentTableList.get(j).getNodeImage());
                            contentTable.setResourceId("" + downloadedContentTableList.get(j).getResourceId());
                            contentTable.setResourceType("" + downloadedContentTableList.get(j).getNodeType());
                            contentTable.setResourcePath("" + downloadedContentTableList.get(j).getResourcePath());
                            contentTable.setParentId("" + downloadedContentTableList.get(j).getParentId());
                            contentTable.setLevel("" + downloadedContentTableList.get(j).getLevel());
                            contentTable.setContentType(downloadedContentTableList.get(j).getContentType());
                            contentTable.setIsDownloaded("true");
                            contentTable.setOnSDCard(true);

                            levelView.addContentToViewList(contentTable);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                levelView.notifyAdapter();
            }
        }.execute();
    }

    @Override
    public void getProfileImg() {
        String sImage;
        if(!RAS_Constants.GROUP_LOGIN)
            sImage = AppDatabase.getDatabaseInstance(context).getStudentDao().getStudentAvatar(RAS_Constants.currentStudentID);
        else
           sImage = "group_icon";
        if (sImage != null)
            levelView.setProfileImg(sImage);
    }

    @Override
    public void clearNodeIds() {
        nodeIds.clear();
    }

    @Override
    public void endSession() {
        try {
            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {

                        String curSession = appDatabase.getStatusDao().getValue("CurrentSession");
                        String AppStartDateTime = appDatabase.getStatusDao().getValue("AppStartDateTime");
                        String toDateTemp = appDatabase.getSessionDao().getToDate(curSession);
                        if (toDateTemp.equalsIgnoreCase("na")) {
                            appDatabase.getSessionDao().UpdateToDate(curSession, RASApplication.getCurrentDateTime(false, AppStartDateTime));
                        }
                        BackupDatabase.backup(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(String activityName) {}

}


/*    public void getAPIContent(final String requestType, String url) {
        try {
            AndroidNetworking.get(url + "" + nodeIds.get(nodeIds.size() - 1))
                    .addHeaders("Content-Type", "application/json")
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            recievedContent(requestType, response);
                        }

                        @Override
                        public void onError(ANError anError) {
                            recievedError(requestType);
                            Log.d("Error:", anError.getErrorDetail());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recievedContent(String header, String response) {
        try {
            if (header.equalsIgnoreCase(RAS_Constants.INTERNET_DOWNLOAD)) {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0).toString());
                //               ContentTable contentData = gson.fromJson(response, ContentTable.class);
                Type listType = new TypeToken<ArrayList<ContentTable>>() {
                }.getType();
                List<ContentTable> serverContentList = gson.fromJson(response, listType);
                //JSONArray returnStoryNavigate = jsonObject.getJSONArray("storyList");
                String sName, nId, sId, sThumbnail, sDesc;
                boolean downloadedFlg = false, contentFound = false;

                for (int i = 0; i < serverContentList.size(); i++) {
                    ContentTable contentTableTemp = new ContentTable();
                    contentFound = false;
                    for (int j = 0; j < downloadedContentTableList.size(); j++) {
                        if (serverContentList.get(i).getNodeId().equalsIgnoreCase(downloadedContentTableList.get(j).getNodeId())) {
                            contentFound = true;
                            downloadedFlg = true;
                            break;
                        }
                    }
                    if (!contentFound) {
                        contentTableTemp = new ContentTable();
                        contentTableTemp.setNodeId("" + serverContentList.get(i).getNodeId());
                        contentTableTemp.setNodeType("" + serverContentList.get(i).getNodeType());
                        contentTableTemp.setNodeTitle("" + serverContentList.get(i).getNodeTitle());
                        contentTableTemp.setNodeKeywords("" + serverContentList.get(i).getNodeKeywords());
                        contentTableTemp.setNodeAge("" + serverContentList.get(i).getNodeAge());
                        contentTableTemp.setNodeDesc("" + serverContentList.get(i).getNodeDesc());
                        contentTableTemp.setNodeServerImage("" + serverContentList.get(i).getNodeServerImage());
                        contentTableTemp.setNodeImage("" + serverContentList.get(i).getNodeImage());
                        contentTableTemp.setResourceId("" + serverContentList.get(i).getResourceId());
                        contentTableTemp.setResourceType("" + serverContentList.get(i).getResourceType());
                        contentTableTemp.setResourcePath("" + serverContentList.get(i).getResourcePath());
                        contentTableTemp.setParentId("" + serverContentList.get(i).getParentId());
                        contentTableTemp.setLevel("" + serverContentList.get(i).getLevel());
                        contentTableTemp.setContentType("" + serverContentList.get(i).getContentType());
                        contentTableTemp.setIsDownloaded("false");
                        contentTableTemp.setOnSDCard(false);
                        contentViewList.add(contentTableTemp);
                    }
                }
                Collections.sort(contentViewList);
                Collections.sort(contentViewList, new Comparator<ContentTable>() {
                    @Override
                    public int compare(ContentTable o1, ContentTable o2) {
                        return o1.getNodeId().compareTo(o2.getNodeId());
                    }
                });
                Log.d("sorted", contentViewList.toString());
                levelAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recievedError(String header) {
        levelAdapter.notifyDataSetChanged();
        Log.d("API_Error", "recievedError: "+header);
    }*/
