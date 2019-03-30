package com.pratham.readandspeak.async;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.domain.ContentTable;
import com.pratham.readandspeak.interfaces.Interface_copying;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CopyExistingJSONS extends AsyncTask<String, String, Boolean> {

    Context context;
    File filePath;
    Interface_copying interface_copying;

    public CopyExistingJSONS(Context context, File filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            try {
                if (filePath.exists()) {
                    FileInputStream is = new FileInputStream(filePath);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String mResponse = new String(buffer);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ContentTable>>() {
                    }.getType();
                    List<ContentTable> tempContents = gson.fromJson(mResponse, listType);
                    for (ContentTable detail : tempContents) {
                        detail.setIsDownloaded("true");
                        if (RASApplication.contentExistOnSD) detail.setOnSDCard(true);
                        else detail.setOnSDCard(false);
                    }
                    AppDatabase.getDatabaseInstance(RASApplication.getInstance()).getContentTableDao().addContentList(tempContents);
                    filePath.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean copied) {
        super.onPostExecute(copied);
//        if (copied && interface_copying != null)
//            interface_copying.successCopyingExisting(folder_file.getAbsolutePath());
//        else
//            interface_copying.failedCopyingExisting();
    }
}
