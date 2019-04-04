package com.pratham.readandspeak.ui.home_screen_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.async.API_Content;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableNew;
import com.pratham.readandspeak.interfaces.API_Content_Result;
import com.pratham.readandspeak.ui.display_content.ContentDisplay;
import com.pratham.readandspeak.utilities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.HomeView, API_Content_Result, ItemClicked {

    HomeContract.HomePresenter presenter;

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new HomePresenter(HomeActivity.this,this);
        nodeIds = new ArrayList<>();
        gson = new Gson();
        api_content = new API_Content(this, HomeActivity.this);

        downloadedContentParentList = new ArrayList<>();
        contentParentList = new ArrayList<>();

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapterParent = new RecyclerViewDataAdapter(this, contentParentList, this);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapterParent);

        presenter.insertNodeId("1299");
        presenter.getDataForList();
    }

    @Override
    public void notifyAdapter(List<ContentTableNew> contentList) {
        contentParentList.addAll(contentList);
        adapterParent.notifyDataSetChanged();
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

/*
    private void getChildData(int pos) {
        parentPosition = pos;
        if(parentPosition<apidownloadedContentParentList.size()){
            api_content.getAPIContent(RAS_Constants.API_CHILD, RAS_Constants.INTERNET_DOWNLOAD_API, ""+apidownloadedContentParentList.get(pos).getNodeId());
        }
    }
*/

    @Override
    public void receivedError(String header) {
        Toast.makeText(this, "Please Check Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContentCardClicked(ContentTableNew singleItem) {
        String nodeId = singleItem.getNodeId();
        Intent intent = new Intent(this, ContentDisplay.class);
        intent.putExtra("nodeId", nodeId);
        startActivity(intent);

    }

}
