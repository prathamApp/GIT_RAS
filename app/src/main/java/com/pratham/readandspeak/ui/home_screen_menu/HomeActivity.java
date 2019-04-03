package com.pratham.readandspeak.ui.home_screen_menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.async.API_Content;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableNew;
import com.pratham.readandspeak.interfaces.API_Content_Result;
import com.pratham.readandspeak.utilities.BaseActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements API_Content_Result {

    private Toolbar toolbar;
    ArrayList<String> nodeIds;
    // ArrayList<SectionDataModel> allSampleData;
    public List<ContentTableNew> apiContentParentList, apiContentChildList, contentParentList;
    public List<ContentTable> downloadedContentParentList, childDwContentList;
    Gson gson;
    String responseParent = "";
    API_Content api_content;
    public static int parentPosition = 0;
    RecyclerViewDataAdapter adapterParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nodeIds = new ArrayList<>();
        gson = new Gson();
        nodeIds.add("1299");
        api_content = new API_Content(this, HomeActivity.this);

        downloadedContentParentList = new ArrayList<>();
        contentParentList = new ArrayList<>();

        //allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Talkbot");
        }

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapterParent = new RecyclerViewDataAdapter(this, contentParentList);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapterParent);

        getCOSData();
    }

    public void getCOSData() {

        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    downloadedContentParentList = appDatabase.getContentTableDao().getContentData("" + nodeIds.get(nodeIds.size() - 1));
                    BackupDatabase.backup(HomeActivity.this);
                    contentParentList.clear();
                    try {
                        for (int j = 0; j < downloadedContentParentList.size(); j++) {
                            List<ContentTableNew> tempList;
                            ContentTableNew contentTable = new ContentTableNew();
                            tempList = new ArrayList<>();

                            childDwContentList = appDatabase.getContentTableDao().getContentData("" + downloadedContentParentList.get(j).getNodeId());

                            contentTable.setNodeId("" + downloadedContentParentList.get(j).getNodeId());
                            contentTable.setNodeType("" + downloadedContentParentList.get(j).getNodeType());
                            contentTable.setNodeTitle("" + downloadedContentParentList.get(j).getNodeTitle());
                            contentTable.setNodeKeywords("" + downloadedContentParentList.get(j).getNodeKeywords());
                            contentTable.setNodeAge("" + downloadedContentParentList.get(j).getNodeAge());
                            contentTable.setNodeDesc("" + downloadedContentParentList.get(j).getNodeDesc());
                            contentTable.setNodeServerImage("" + downloadedContentParentList.get(j).getNodeServerImage());
                            contentTable.setNodeImage("" + downloadedContentParentList.get(j).getNodeImage());
                            contentTable.setResourceId("" + downloadedContentParentList.get(j).getResourceId());
                            contentTable.setResourceType("" + downloadedContentParentList.get(j).getNodeType());
                            contentTable.setResourcePath("" + downloadedContentParentList.get(j).getResourcePath());
                            contentTable.setParentId("" + downloadedContentParentList.get(j).getParentId());
                            contentTable.setLevel("" + downloadedContentParentList.get(j).getLevel());
                            contentTable.setContentType(downloadedContentParentList.get(j).getContentType());
                            contentTable.setIsDownloaded("true");
                            contentTable.setOnSDCard(true);

                            for (int i = 0; i < childDwContentList.size(); i++) {
                                ContentTableNew contentChild = new ContentTableNew();
                                contentChild.setNodeId("" + childDwContentList.get(i).getNodeId());
                                contentChild.setNodeType("" + childDwContentList.get(i).getNodeType());
                                contentChild.setNodeTitle("" + childDwContentList.get(i).getNodeTitle());
                                contentChild.setNodeKeywords("" + childDwContentList.get(i).getNodeKeywords());
                                contentChild.setNodeAge("" + childDwContentList.get(i).getNodeAge());
                                contentChild.setNodeDesc("" + childDwContentList.get(i).getNodeDesc());
                                contentChild.setNodeServerImage("" + childDwContentList.get(i).getNodeServerImage());
                                contentChild.setNodeImage("" + childDwContentList.get(i).getNodeImage());
                                contentChild.setResourceId("" + childDwContentList.get(i).getResourceId());
                                contentChild.setResourceType("" + childDwContentList.get(i).getNodeType());
                                contentChild.setResourcePath("" + childDwContentList.get(i).getResourcePath());
                                contentChild.setParentId("" + childDwContentList.get(i).getParentId());
                                contentChild.setLevel("" + childDwContentList.get(i).getLevel());
                                contentChild.setContentType(childDwContentList.get(i).getContentType());
                                contentChild.setIsDownloaded("true");
                                contentChild.setOnSDCard(false);
                                contentChild.setNodelist(null);
                                tempList.add(contentChild);
                            }
                            contentTable.setNodelist(tempList);
                            contentParentList.add(contentTable);
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
                adapterParent.notifyDataSetChanged();
            }
        }.execute();


//        api_content.getAPIContent(requestCode, RAS_Constants.INTERNET_DOWNLOAD_API, nodeIds.get(nodeIds.size() - 1));
//        adapterParent.notifyDataSetChanged();
    }

    public void getParentsChildData() {

    }

    @Override
    public void receivedContent(String header, String response) {

        /*if (header.equalsIgnoreCase(RAS_Constants.API_PARENT)) {

            new AsyncTask<Object, Void, Object>() {

                @Override
                protected Object doInBackground(Object[] objects) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0).toString());
                        Type listType = new TypeToken<ArrayList<ContentTable>>() {
                        }.getType();
                        List<ContentTable> serverContentList = gson.fromJson(response, listType);

                        for (int i = 0; i < serverContentList.size(); i++) {

                            ContentTable contentTableTemp;
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
                            apidownloadedContentParentList.add(contentTableTemp);
*//*                        String childResponse = getAPIContentResult(RAS_Constants.INTERNET_DOWNLOAD_API, serverContentList.get(i).getNodeId());

                        JSONArray jsonArrayChild = new JSONArray(childResponse);
                        JSONObject jsonObjectChild = new JSONObject(jsonArrayChild.getJSONObject(0).toString());
                        Type listTypeChild = new TypeToken<ArrayList<ContentTable>>() {
                        }.getType();
                        List<ContentTable> serverContentChildList = gson.fromJson(childResponse, listTypeChild);

                        ArrayList<SingleItemModel> singleItem = new ArrayList<>();
                        for (int j = 0; j <serverContentChildList.size() ; j++) {
                            ContentTable contentTableChildTemp = new ContentTable();
                            contentTableChildTemp = new ContentTable();
                            contentTableChildTemp.setNodeId("" + serverContentList.get(i).getNodeId());
                            contentTableChildTemp.setNodeType("" + serverContentList.get(i).getNodeType());
                            contentTableChildTemp.setNodeTitle("" + serverContentList.get(i).getNodeTitle());
                            contentTableChildTemp.setNodeKeywords("" + serverContentList.get(i).getNodeKeywords());
                            contentTableChildTemp.setNodeAge("" + serverContentList.get(i).getNodeAge());
                            contentTableChildTemp.setNodeDesc("" + serverContentList.get(i).getNodeDesc());
                            contentTableChildTemp.setNodeServerImage("" + serverContentList.get(i).getNodeServerImage());
                            contentTableChildTemp.setNodeImage("" + serverContentList.get(i).getNodeImage());
                            contentTableChildTemp.setResourceId("" + serverContentList.get(i).getResourceId());
                            contentTableChildTemp.setResourceType("" + serverContentList.get(i).getResourceType());
                            contentTableChildTemp.setResourcePath("" + serverContentList.get(i).getResourcePath());
                            contentTableChildTemp.setParentId("" + serverContentList.get(i).getParentId());
                            contentTableChildTemp.setLevel("" + serverContentList.get(i).getLevel());
                            contentTableChildTemp.setContentType("" + serverContentList.get(i).getContentType());
                            contentTableChildTemp.setIsDownloaded("false");
                            contentTableChildTemp.setOnSDCard(false);
                            apiContentChildList.add(contentTableChildTemp);
                        }*//*

//                    singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
*//*                dm.setAllItemsInSection(singleItem);

                allSampleData.add(dm);*//*
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    childMainList = new ArrayList<>();
                    getChildData(parentPosition);
                }
            }.execute();
        } else if (header.equalsIgnoreCase(RAS_Constants.API_CHILD)) {

            new AsyncTask<Object, Void, Object>() {

                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        apiContentChildList.clear();

*//*                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0).toString());*//*
                        Type listType = new TypeToken<ArrayList<ContentTable>>() {
                        }.getType();
                        List<ContentTable> serverContentList = gson.fromJson(response, listType);

                        for (int i = 0; i < serverContentList.size(); i++) {

                            ContentTable contentTableTemp = new ContentTable();
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
                            apiContentChildList.add(contentTableTemp);
                        }
                        childMainList.add(apiContentChildList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
//                    adapterParent.notifyItemChanged(parentPosition, apidownloadedContentParentList.get(parentPosition));
                    adapterParent.notifyDataSetChanged();
                    parentPosition++;
                    getChildData(parentPosition);
                }
            }.execute();
        }*/
    }

//    private void getChildData(int pos) {
//        parentPosition = pos;
//        if(parentPosition<apidownloadedContentParentList.size()){
//            api_content.getAPIContent(RAS_Constants.API_CHILD, RAS_Constants.INTERNET_DOWNLOAD_API, ""+apidownloadedContentParentList.get(pos).getNodeId());
//        }
//    }

    @Override
    public void receivedError(String header) {
        Toast.makeText(this, "Please Check Internet", Toast.LENGTH_SHORT).show();
    }
}
