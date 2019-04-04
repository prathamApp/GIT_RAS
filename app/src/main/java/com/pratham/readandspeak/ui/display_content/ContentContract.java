package com.pratham.readandspeak.ui.display_content;
import com.pratham.readandspeak.domain.ContentTable;
import java.util.List;

/**
 * Created by Ameya on 23-Nov-17.
 */

public interface ContentContract {

    interface ContentView{
        void clearContentList();
        void addContentToViewList(List<ContentTable> contentTable);
        void notifyAdapter();

        void showNoDataDownloadedDialog();

        void notifyAdapterItem(int deletePos);
    }

    interface ContentPresenter {
        void getListData();

        void downloadResource(String downloadNodeId);

        void addNodeIdToList(String nodeId);

        boolean removeLastNodeId();

        void starContentEntry(String nodeId, String s);

        void deleteContent(int position, ContentTable contentList);
    }

}
