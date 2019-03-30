package com.pratham.readandspeak.ui.splash_activity;

import com.pratham.readandspeak.database.AppDatabase;

/**
 * Created by Ameya on 23-Nov-17.
 */

public interface SplashContract {

    interface SplashView{

        void startApp();

        void showUpdateDialog();

        void showButton();

        void showProgressDialog();

        void showBottomFragment();

        void dismissProgressDialog();

        void gotoNextActivity();
    }

    interface SplashPresenter {

        void checkVersion();

        void pushData();

        void doInitialEntries(AppDatabase appDatabase);

        void copyZipAndPopulateMenu();

        void versionObtained(String latestVersion);

        void copyDataBase();

        boolean getSdCardPath();

        void populateSDCardMenu();
    }

}
