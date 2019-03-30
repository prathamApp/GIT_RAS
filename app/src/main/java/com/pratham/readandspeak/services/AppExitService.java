package com.pratham.readandspeak.services;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.ui.splash_activity.SplashActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;
import com.pratham.readandspeak.database.AppDatabase;

public class AppExitService extends Service {

    private AppDatabase appDatabase;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        try {

            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {

                        appDatabase = Room.databaseBuilder(AppExitService.this,
                                AppDatabase.class, AppDatabase.DB_NAME)
                                .build();

                        String toDateTemp = appDatabase.getSessionDao().getToDate(RAS_Constants.currentSession);

                        if (toDateTemp.equalsIgnoreCase("na")) {
                            appDatabase.getSessionDao().UpdateToDate(RAS_Constants.currentSession, RASApplication.getCurrentDateTime());
                        }
                        if(RAS_Constants.assessmentFlag) {
                            String toDateAssessment = appDatabase.getSessionDao().getToDate(RAS_Constants.assessmentSession);
                            if (toDateAssessment.equalsIgnoreCase("na")) {
                                appDatabase.getSessionDao().UpdateToDate(RAS_Constants.assessmentSession, RASApplication.getCurrentDateTime());
                            }
                        }


                        BackupDatabase.backup(AppExitService.this);
                        stopService(new Intent(AppExitService.this, SplashActivity.class));
                        stopSelf();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();

            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}