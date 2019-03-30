package com.pratham.readandspeak.ui.main_menu;

import com.pratham.readandspeak.domain.ContentTable;

public interface ChooseLevelContract {

    public interface ChooseLevelView{
        void clearContentList();

        void addContentToViewList(ContentTable contentTable);

        void notifyAdapter();

        void setProfileImg(String sImage);
    }

    public interface ChooseLevelPresenter{
        public void startActivity(String activityName);

        void copyListData();

        void getProfileImg();

        void clearNodeIds();

        void endSession();
    }

}
