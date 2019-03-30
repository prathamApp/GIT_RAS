package com.pratham.readandspeak;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pratham.readandspeak.custom.shared_preferences.FastSave;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.domain.Modal_Log;
import com.pratham.readandspeak.utilities.BaseActivity;

import net.alhazmy13.catcho.library.Catcho;
import net.alhazmy13.catcho.library.error.CatchoError;

public class CatchoTransparentActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CatchoError error = (CatchoError) getIntent().getSerializableExtra(Catcho.ERROR);
        Modal_Log log = new Modal_Log();
        log.setCurrentDateTime(RAS_Utility.GetCurrentDateTime());
        log.setErrorType("ERROR");
        log.setExceptionMessage(error.toString());
        log.setExceptionStackTrace(error.getError());
        log.setMethodName("NO_METHOD");
        log.setGroupId(FastSave.getInstance().getString("", "no_group"));
        log.setDeviceId("");
        appDatabase.getLogsDao().insertLog(log);
        new BackupDatabase().backup(this);
        finishAffinity();
    }
}
