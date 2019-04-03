package com.pratham.readandspeak.ui.main_menu;

import com.pratham.readandspeak.domain.ContentTable;

import java.util.List;

/**
 * Created by Ameya on 23-Nov-17.
 */

public interface LevelClicked {

    public void onLevelClicked(int position, String nodeId, String contentName, String contentType);
//    public List<ContentTable> setRecyclerView(String nodeId);
}
