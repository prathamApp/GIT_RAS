package com.pratham.readandspeak.utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.interfaces.Interface_copying;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CopyExistingDb extends AsyncTask<String, String, Boolean> {

    File db_file;
    File folder_file;
    Context context;
    Interface_copying interface_copying;

    public CopyExistingDb(Context context, Interface_copying interface_copying) {
        this.context = context;
        this.interface_copying = interface_copying;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        interface_copying.copyingExisting();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            ArrayList<String> sdPath = FileUtils.getExtSdCardPaths(context);
            if (sdPath.size() > 0) {
                folder_file = new File( RAS_Constants.ext_path);
                if (folder_file.exists()) {
                    db_file = new File(folder_file.getAbsolutePath()+"/"+AppDatabase.DB_NAME);
                    if (db_file.exists()) {
                        SQLiteDatabase db = SQLiteDatabase.openDatabase(db_file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
                        if (db == null) return false;
                        Cursor content_cursor;
                        try {
                            content_cursor = db.rawQuery("SELECT * FROM ContentTable", null);
                            //populate contents
                            List<ContentTable> contents = new ArrayList<>();
                            if (content_cursor.moveToFirst()) {
                                while (!content_cursor.isAfterLast()) {
                                    ContentTable detail = new ContentTable();
                                    detail.setNodeId(content_cursor.getString(content_cursor.getColumnIndex("nodeId")));
                                    detail.setNodeType(content_cursor.getString(content_cursor.getColumnIndex("nodeType")));
                                    detail.setNodeTitle(content_cursor.getString(content_cursor.getColumnIndex("nodeTitle")));
                                    detail.setNodeKeywords(content_cursor.getString(content_cursor.getColumnIndex("nodeKeywords")));
                                    detail.setNodeAge(content_cursor.getString(content_cursor.getColumnIndex("nodeAge")));
                                    detail.setNodeDesc(content_cursor.getString(content_cursor.getColumnIndex("nodeDesc")));
                                    detail.setNodeServerImage(content_cursor.getString(content_cursor.getColumnIndex("nodeServerImage")));
                                    detail.setResourceId(content_cursor.getString(content_cursor.getColumnIndex("resourceId")));
                                    detail.setResourceType(content_cursor.getString(content_cursor.getColumnIndex("resourceType")));
                                    detail.setResourcePath(content_cursor.getString(content_cursor.getColumnIndex("resourcePath")));
                                    detail.setLevel("" + content_cursor.getInt(content_cursor.getColumnIndex("level")));
                                    detail.setContentLanguage(content_cursor.getString(content_cursor.getColumnIndex("contentLanguage")));
                                    detail.setParentId(content_cursor.getString(content_cursor.getColumnIndex("parentId")));
                                    detail.setContentType(content_cursor.getString(content_cursor.getColumnIndex("contentType")));
                                    detail.setIsDownloaded("" + true);
                                    detail.setOnSDCard(true);
                                    contents.add(detail);
                                    content_cursor.moveToNext();
                                }
                            }
                            BaseActivity.appDatabase.getContentTableDao().addContentList(contents);
                            content_cursor.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //populate villages
                        return true;
                    } else
                        return false;
                } else
                    return false;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean copied) {
        super.onPostExecute(copied);
        if (copied)
            interface_copying.successCopyingExisting(folder_file.getAbsolutePath());
        else
            interface_copying.failedCopyingExisting();
    }
}
