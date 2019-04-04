package com.pratham.readandspeak.ui.display_content;
import com.pratham.readandspeak.domain.ContentTable;


public interface ContentClicked {

    void onContentClicked(int position, String nId);

    void onContentDeleteClicked(int position, ContentTable contentList);

}
