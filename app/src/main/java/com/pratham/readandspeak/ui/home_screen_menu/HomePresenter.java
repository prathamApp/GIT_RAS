package com.pratham.readandspeak.ui.home_screen_menu;

import android.content.Context;
import android.os.AsyncTask;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableNew;
import com.pratham.readandspeak.utilities.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.HomePresenter{

    Context mContext;
    HomeContract.HomeView homeView;
    
    public List<ContentTable> dwParentList, childDwContentList;
    public List<ContentTableNew> contentParentList;
    ArrayList<String> nodeIds;

    public HomePresenter(Context mContext, HomeContract.HomeView homeView) {
        this.mContext = mContext;
        this.homeView = homeView;
        nodeIds = new ArrayList<>();
        contentParentList = new ArrayList<>();
    }

    @Override
    public void insertNodeId(String nodeId) {
        nodeIds.add(nodeId);
    }

    @Override
    public void getDataForList() {

        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    dwParentList = BaseActivity.appDatabase.getContentTableDao().getContentData("" + nodeIds.get(nodeIds.size() - 1));
                    contentParentList.clear();
                    try {
                        for (int j = 0; j < dwParentList.size(); j++) {
                            List<ContentTableNew> tempList;
                            ContentTableNew contentTable = new ContentTableNew();
                            tempList = new ArrayList<>();

                            childDwContentList = BaseActivity.appDatabase.getContentTableDao().getContentData("" + dwParentList.get(j).getNodeId());

                            contentTable.setNodeId("" + dwParentList.get(j).getNodeId());
                            contentTable.setNodeType("" + dwParentList.get(j).getNodeType());
                            contentTable.setNodeTitle("" + dwParentList.get(j).getNodeTitle());
                            contentTable.setNodeKeywords("" + dwParentList.get(j).getNodeKeywords());
                            contentTable.setNodeAge("" + dwParentList.get(j).getNodeAge());
                            contentTable.setNodeDesc("" + dwParentList.get(j).getNodeDesc());
                            contentTable.setNodeServerImage("" + dwParentList.get(j).getNodeServerImage());
                            contentTable.setNodeImage("" + dwParentList.get(j).getNodeImage());
                            contentTable.setResourceId("" + dwParentList.get(j).getResourceId());
                            contentTable.setResourceType("" + dwParentList.get(j).getNodeType());
                            contentTable.setResourcePath("" + dwParentList.get(j).getResourcePath());
                            contentTable.setParentId("" + dwParentList.get(j).getParentId());
                            contentTable.setLevel("" + dwParentList.get(j).getLevel());
                            contentTable.setContentType(dwParentList.get(j).getContentType());
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
                homeView.notifyAdapter(contentParentList);
//                adapterParent.notifyDataSetChanged();
            }
        }.execute();

    }
}