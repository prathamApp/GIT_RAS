package com.pratham.readandspeak.async;

import android.os.AsyncTask;

import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.custom.shared_preferences.FastSave;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.interfaces.DownloadedContents;
import com.pratham.readandspeak.utilities.RAS_Constants;


public class GetDownloadedContent extends AsyncTask {
    String parentId;
    DownloadedContents downloadedContents;

    public GetDownloadedContent(DownloadedContents downloadedContents, String parentId) {
        this.parentId = parentId;
        this.downloadedContents = downloadedContents;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String lang = FastSave.getInstance().getString(RAS_Constants.LANGUAGE, RAS_Constants.HINDI);
        if (parentId != null && !parentId.equalsIgnoreCase("0") && !parentId.isEmpty())
            return AppDatabase.getDatabaseInstance(RASApplication.getInstance()).getContentTableDao().getChildsOfParent(parentId, lang);
        else
            return AppDatabase.getDatabaseInstance(RASApplication.getInstance()).getContentTableDao().getParentsHeaders(lang);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (downloadedContents != null)
            downloadedContents.downloadedContents(o, parentId);
    }
}
