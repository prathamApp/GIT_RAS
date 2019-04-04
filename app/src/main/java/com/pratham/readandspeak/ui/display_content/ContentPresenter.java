package com.pratham.readandspeak.ui.display_content;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.async.API_Content;
import com.pratham.readandspeak.async.ZipDownloader;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.Score;
import com.pratham.readandspeak.interfaces.API_Content_Result;
import com.pratham.readandspeak.modalclasses.Modal_DownloadContent;
import com.pratham.readandspeak.utilities.BaseActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

import org.androidannotations.annotations.Background;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ContentPresenter implements ContentContract.ContentPresenter, API_Content_Result {

    Context context;
    ContentContract.ContentView contentView;
    String downloadNodeId, fileName;
    Gson gson;
    ZipDownloader zipDownloader;
    Modal_DownloadContent download_content;
    ArrayList<ContentTable> pos;
    ContentTable contentDetail;
    List<ContentTable> downloadedContentTableList, ListForContentTable1, ListForContentTable2;
    ArrayList<String> nodeIds;
    API_Content api_content;

    public ContentPresenter(Context context, ContentContract.ContentView contentView) {
        this.context = context;
        this.contentView = contentView;
        nodeIds = new ArrayList<>();
        gson = new Gson();
        zipDownloader = new ZipDownloader(context);
        pos = new ArrayList<>();
        zipDownloader = new ZipDownloader(context);
        ListForContentTable1 = new ArrayList<ContentTable>();
        ListForContentTable2 = new ArrayList<ContentTable>();
        api_content = new API_Content(context, ContentPresenter.this);
    }

    @Override
    public void addNodeIdToList(String nodeId) {
        nodeIds.add(nodeId);
    }

    @Override
    public boolean removeLastNodeId() {
        if (nodeIds.size() > 1) {
            nodeIds.remove(nodeIds.size() - 1);
            return true;
        } else
            return false;
    }

    @Override
    public void getListData() {

        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    downloadedContentTableList = BaseActivity.appDatabase.getContentTableDao().getContentData("" + nodeIds.get(nodeIds.size() - 1));
                    BackupDatabase.backup(context);

                    contentView.clearContentList();
                    ListForContentTable1.clear();
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
                            if (RAS_Constants.SD_CARD_Content)
                                contentTable.setOnSDCard(true);
                            else
                                contentTable.setOnSDCard(false);
                            ListForContentTable1.add(contentTable);
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

                if (RAS_Utility.isDataConnectionAvailable(context) && !RAS_Constants.SD_CARD_Content)
                    api_content.getAPIContent(RAS_Constants.INTERNET_DOWNLOAD, RAS_Constants.INTERNET_DOWNLOAD_API, nodeIds.get(nodeIds.size() - 1));
                else {
                    if (downloadedContentTableList.size() == 0 && !RAS_Utility.isDataConnectionAvailable(context)) {
                        contentView.showNoDataDownloadedDialog();
                    } else {
                        contentView.addContentToViewList(ListForContentTable1);
                        contentView.notifyAdapter();
                    }
                }
            }
        }.execute();
    }

    @Override
    public void downloadResource(String downloadId) {
        downloadNodeId = downloadId;
        api_content.getAPIContent(RAS_Constants.INTERNET_DOWNLOAD_RESOURCE, RAS_Constants.INTERNET_DOWNLOAD_RESOURCE_API, downloadNodeId);
//        getAPIContent(RAS_Constants.INTERNET_DOWNLOAD_RESOURCE, RAS_Constants.INTERNET_DOWNLOAD_RESOURCE_API);
    }

    public void receivedContent(String header, final String response) {
        try {
            if (header.equalsIgnoreCase(RAS_Constants.INTERNET_DOWNLOAD)) {
                new AsyncTask<Object, Void, Object>() {

                    @Override
                    protected Object doInBackground(Object[] objects) {

                        boolean contentFound = false;

                        try {
                            ListForContentTable2.clear();

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0).toString());
                            Type listType = new TypeToken<ArrayList<ContentTable>>() {
                            }.getType();
                            List<ContentTable> serverContentList = gson.fromJson(response, listType);

                            for (int i = 0; i < serverContentList.size(); i++) {
                                ContentTable contentTableTemp = new ContentTable();
                                contentFound = false;
                                for (int j = 0; j < downloadedContentTableList.size(); j++) {
                                    if (serverContentList.get(i).getNodeId().equalsIgnoreCase(downloadedContentTableList.get(j).getNodeId())) {
                                        contentFound = true;
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
                                    ListForContentTable2.add(contentTableTemp);
                                }

                            }
                            contentView.addContentToViewList(ListForContentTable1);
                            contentView.addContentToViewList(ListForContentTable2);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        contentView.notifyAdapter();
                    }
                }.execute();
            } else if (header.equalsIgnoreCase(RAS_Constants.INTERNET_DOWNLOAD_RESOURCE)) {
                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            download_content = gson.fromJson(jsonObject.toString(), Modal_DownloadContent.class);
                            contentDetail = download_content.getNodelist().get(download_content.getNodelist().size() - 1);

                            for (int i = 0; i < download_content.getNodelist().size(); i++) {
                                ContentTable contentTableTemp = download_content.getNodelist().get(i);
                                pos.add(contentTableTemp);
                            }

                            fileName = download_content.getDownloadurl()
                                    .substring(download_content.getDownloadurl().lastIndexOf('/') + 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        zipDownloader.initialize(context, download_content.getDownloadurl(),
                                download_content.getFoldername(), fileName, contentDetail, pos);
                    }
                }.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Background
    @Override
    public void deleteContent(int deletePos, ContentTable contentItem) {
        checkAndDeleteParent(contentItem);

        Log.d("Delete_Clicked", "onClick: G_Presenter");

/*        if (contentItem.getResourceType().toLowerCase().equalsIgnoreCase(PD_Constant.GAME)) {
            String foldername = contentItem.getResourcepath().split("/")[0];
            PD_Utility.deleteRecursive(new File(PrathamApplication.pradigiPath + "/PrathamGame/" + foldername));
        } else if (contentItem.getResourcetype().toLowerCase().equalsIgnoreCase(PD_Constant.VIDEO)) {
            PD_Utility.deleteRecursive(new File(PrathamApplication.pradigiPath
                    + "/PrathamVideo/" + contentItem.getResourcepath()));
        } else if (contentItem.getResourcetype().toLowerCase().equalsIgnoreCase(PD_Constant.PDF)) {
            PD_Utility.deleteRecursive(new File(PrathamApplication.pradigiPath
                    + "/PrathamPdf/" + contentItem.getResourcepath()));
        }
        //delete content thumbnail image
        PD_Utility.deleteRecursive(new File(PrathamApplication.pradigiPath
                + "/PrathamImages/" + contentItem.getNodeimage()));*/

        String foldername = contentItem.getResourcePath()/*.split("/")[0]*/;
        RAS_Utility.deleteRecursive(new File(RASApplication.pradigiPath
                + "/.RAS/English/Game/" + foldername));

        RAS_Utility.deleteRecursive(new File(RASApplication.pradigiPath
                + "/.RAS/English/RAS_Thumbs/" + contentItem.getNodeImage()));
        contentView.notifyAdapterItem(deletePos);
    }

    private void checkAndDeleteParent(ContentTable contentItem) {
//        String parentId = contentItem.getParentId();
        BaseActivity.appDatabase.getContentTableDao().deleteContent(contentItem.getNodeId());
/*        if (parentId != null && !parentId.equalsIgnoreCase("0") && !parentId.isEmpty()) {
            int count = appDatabase.getContentTableDao().getChildCountOfParent(parentId*//*,
                    FastSave.getInstance().getString(RAS_Constants.LANGUAGE, "English")*//*);
            if (count == 0)
                checkAndDeleteParent(appDatabase.getContentTableDao().getContent(parentId
                        *//*FastSave.getInstance().getString(PD_Constant.LANGUAGE, PD_Constant.HINDI)*//*));
        }*/
    }

    public void receivedError(String header) {
        contentView.addContentToViewList(ListForContentTable1);
        contentView.notifyAdapter();
    }

    @Override
    public void starContentEntry(final String contentID, final String Label) {
        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {

                    String deviceId = BaseActivity.appDatabase.getStatusDao().getValue("DeviceId");
                    String AppStartDateTime = BaseActivity.appDatabase.getStatusDao().getValue("AppStartDateTime");

                    Score score = new Score();
                    score.setSessionID(RAS_Constants.currentSession);
                    score.setResourceID("" + contentID);
                    score.setQuestionId(0);
                    score.setScoredMarks(0);
                    score.setTotalMarks(0);
                    score.setStudentID(RAS_Constants.currentStudentID);
                    score.setStartDateTime(RASApplication.getCurrentDateTime(false, AppStartDateTime));
                    score.setDeviceID(deviceId.equals(null) ? "0000" : deviceId);
                    score.setEndDateTime(RASApplication.getCurrentDateTime());
                    score.setLevel(0);
                    score.setLabel(Label);
                    score.setSentFlag(0);
                    BaseActivity.appDatabase.getScoreDao().insert(score);
                    BackupDatabase.backup(context);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

}


//    public void getAPIContent(final String requestType, String url) {
//        try {
//            String url_id;
//            url_id = url + "" + nodeIds.get(nodeIds.size() - 1);
//            if (requestType.equalsIgnoreCase(RAS_Constants.INTERNET_DOWNLOAD_RESOURCE))
//                url_id = url + "" + downloadNodeId;
//            AndroidNetworking.get(url_id)
//                    .addHeaders("Content-Type", "application/json")
//                    .build()
//                    .getAsString(new StringRequestListener() {
//                        @Override
//                        public void onResponse(String response) {
//                            recievedContent(requestType, response);
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            recievedError(requestType);
//                            try {
//                                Log.d("Error:", anError.getErrorDetail());
//                                Log.d("Error::", anError.getResponse().toString());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
