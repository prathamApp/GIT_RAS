package com.pratham.readandspeak.ui.home_screen_menu;


import com.pratham.readandspeak.domain.ContentTableNew;

import java.util.List;

public interface HomeContract {

    interface HomeView{

        void notifyAdapter(List<ContentTableNew> contentParentList);
    }

    interface HomePresenter {

        void getDataForList();

        void insertNodeId(String nodeId);
    }

}
