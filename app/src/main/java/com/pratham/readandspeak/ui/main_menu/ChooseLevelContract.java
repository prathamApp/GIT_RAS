package com.pratham.readandspeak.ui.main_menu;

import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.domain.ContentTableOuter;

import java.util.List;

public interface ChooseLevelContract {

    public interface ChooseLevelView {
        void clearContentList();

        void addContentToViewList(ContentTableOuter contentTable);
//        void addContentToInnerViewList(ContentTable contentTable);

        void notifyAdapter();


        void setProfileImg(String sImage);
    }

    public interface ChooseLevelPresenter {
        public void startActivity(String activityName);

        void copyListData();

        void getProfileImg();

        void clearNodeIds();

        void endSession();

//        void getListData(String nodeId);

    }

}
