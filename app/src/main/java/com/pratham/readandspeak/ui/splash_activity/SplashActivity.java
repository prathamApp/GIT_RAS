package com.pratham.readandspeak.ui.splash_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pratham.readandspeak.R;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.interfaces.Interface_copying;
import com.pratham.readandspeak.interfaces.PermissionResult;
import com.pratham.readandspeak.ui.bottom_fragment.BottomStudentsFragment;
import com.pratham.readandspeak.ui.bottom_fragment.add_student.MenuActivity;
import com.pratham.readandspeak.utilities.PermissionUtils;
import com.pratham.readandspeak.utilities.RAS_Constants;
import com.pratham.readandspeak.utilities.SplashSupportActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pratham.readandspeak.RASApplication.sharedPreferences;

public class SplashActivity extends SplashSupportActivity implements SplashContract.SplashView, PermissionResult, Interface_copying {

    @BindView(R.id.btn_start)
    Button btn_start_game;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.iv_logo_pradigi)
    ImageView iv_logo_pradigi;
    @BindView(R.id.temppp)
    RelativeLayout temppp;
    static String fpath, appname;
    public static MediaPlayer bgMusic;
    public static AppDatabase appDatabase;
    public ProgressDialog progressDialog;

    static Context context;
    Dialog dialog;
    SplashContract.SplashPresenter splashPresenter;
    public static boolean firstPause = true, fragmentBottomOpenFlg = false, fragmentBottomPauseFlg = false, fragmentAddStudentPauseFlg = false, fragmentAddStudentOpenFlg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog = new ProgressDialog(this);
        fpath = "";
        appname = "";
        splashPresenter = new SplashPresenter(this, this);
        context = SplashActivity.this;
        btn_start_game.setVisibility(View.GONE);
        iv_logo_pradigi.setVisibility(View.GONE);
        //       bgMusic = MediaPlayer.create(this, R.raw.bg_sound);
        //       bgMusic.setLooping(true);
        initiateApp();
    }

    public void initiateApp() {
        //bgMusic.start();
        ImageViewAnimatedChange(this, iv_logo);
    }

    public void ImageViewAnimatedChange(final Context c, final ImageView iv_logo) {
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.zoom_in_new);
        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageViewAnimatedChangeSecond(c, iv_logo);
            }
        });
        iv_logo.setAnimation(anim_in);
    }

    public void ImageViewAnimatedChangeSecond(final Context c, final ImageView iv_logo) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out_new);
        iv_logo.setAnimation(anim_out);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pradigiAnimation(c, iv_logo_pradigi);
            }
        });
    }

    public void pradigiAnimation(Context c, final ImageView iv_logo_pradigi) {
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.item_animation_from_bottom);
        iv_logo_pradigi.setVisibility(View.VISIBLE);

        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String[] permissionArray = new String[]{PermissionUtils.Manifest_CAMERA,
                        PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,
                        PermissionUtils.Manifest_RECORD_AUDIO,
                        PermissionUtils.Manifest_ACCESS_COARSE_LOCATION,
                        PermissionUtils.Manifest_ACCESS_FINE_LOCATION
                };

                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                    if (!isPermissionsGranted(SplashActivity.this, permissionArray)) {
                        askCompactPermissions(permissionArray, SplashActivity.this);
                    } else {
                        splashPresenter.checkVersion();
                    }
                } else {
                    splashPresenter.checkVersion();
                }
            }
        });
        iv_logo_pradigi.setAnimation(anim_in);
    }

    @Override
    public void showButton() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RAS_Constants.SD_CARD_Content = splashPresenter.getSdCardPath();
                if (!sharedPreferences.getBoolean(RAS_Constants.SD_CARD_Content_STR, false)) {
                    if (!RAS_Constants.SD_CARD_Content)
                        splashPresenter.copyZipAndPopulateMenu();
                    else
                        splashPresenter.populateSDCardMenu();
                } else
                    gotoNextActivity();
            }
        }, 2000);
    }

    @Override
    public void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Upgrade to a better version !");
        builder.setCancelable(false);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Click button action
                dialog.dismiss();
                if (RAS_Utility.isDataConnectionAvailable(SplashActivity.this)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pratham.readandspeak")));
                    finish();
                } else {
                    RAS_Utility.showAlertDialogue(SplashActivity.this, "No internet connection! Try updating later.");
                    startApp();
                }
            }
        });
        builder.show();
    }

    @Override
    public void startApp() {
        createDataBase();
    }

    /*public void getSdCardPath() {
        CharSequence c = "";
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());

        try {
            c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
            appname = c.toString();
            Log.w("LABEL", c.toString());
        } catch (Exception e) {//Name Not FOund Exception
        }
        if (appname.equals("City of Stories")) {
            ArrayList<String> base_path = SDCardUtil.getExtSdCardPaths(this);
            if (base_path.size() > 0) {
                String path = base_path.get(0).replace("[", "");
                path = path.replace("]", "");
                fpath = path;
            } else
                fpath = Environment.getExternalStorageDirectory().getAbsolutePath();
            fpath = fpath + "/.RAS/English/";
            File file = new File(fpath);
            RAS_Constants.ext_path = fpath;
            Log.d("getSD", "getSdCardPath: " + RAS_Constants.ext_path);
            if (file.exists())
                updateSdCardPath(fpath);
            else {
                File direct = new File(Environment.getExternalStorageDirectory().toString() + ".RAS");
                if (!direct.exists()) direct.mkdir();
                direct = new File(Environment.getExternalStorageDirectory().toString() + ".RAS/English");
                if (!direct.exists()) direct.mkdir();
                file = new File(Environment.getExternalStorageDirectory().toString() + ".RAS/English/");
                if (file.exists())
                    updateSdCardPath("" + Environment.getExternalStorageDirectory().toString() + "/.RAS/English/");
            }
        }
    }

    public void updateSdCardPath(final String path) {
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    RAS_Constants.ext_path = path;
                    Log.d("$path", "\n\n\n\n\n\n\n\n\n\nPATH: " + RAS_Constants.ext_path + "\n\n\n\n\n\n\\n\n\n\n");
                    appDatabase.getStatusDao().updateValue("SdCardPath", "" + path);
                    BackupDatabase.backup(SplashActivity.this);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }.execute();
    }
*/
    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading... Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if ((fragmentBottomOpenFlg && fragmentBottomPauseFlg) ||
                (fragmentBottomOpenFlg && fragmentAddStudentOpenFlg && fragmentBottomPauseFlg && fragmentAddStudentPauseFlg)) {
            try {
              /*  if (bgMusic != null && bgMusic.isPlaying()) {
                    bgMusic.setLooping(false);
                    bgMusic.stop();
                    bgMusic.release();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (firstPause) {
            try {
               /* if (bgMusic != null && bgMusic.isPlaying()) {
                    bgMusic.setLooping(false);
                    bgMusic.stop();
                    bgMusic.release();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //showExitDialog();
        super.onBackPressed();
    }

  /*  public void showExitDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView title = dialog.findViewById(R.id.dia_title);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);

        title.setText("Do you want to exit?");
        restart_btn.setText("Yes");
        exit_btn.setText("No");
        dialog.show();

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RAS_Constants.SMART_PHONE)
                    showBottomFragment();
                dialog.dismiss();
            }
        });

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                dialog.dismiss();
            }
        });
    }*/

    @Override
    public void permissionGranted() {
        Log.d("Splash", "permissionGranted: HAHAHAHAHAHA");
        splashPresenter.checkVersion();
    }

    @Override
    public void permissionDenied() {
    }

    @Override
    public void permissionForeverDenied() {
    }

    public void createDataBase() {
        try {
            boolean dbExist = checkDataBase();
            if (!dbExist) {
                try {
                    appDatabase = Room.databaseBuilder(this,
                            AppDatabase.class, AppDatabase.DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            })
                            .build();
                    if (new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ras_database").exists()) {
                        try {
                            splashPresenter.copyDataBase();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showButton();
                        //populateMenu();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                appDatabase = Room.databaseBuilder(this,
                        AppDatabase.class, AppDatabase.DB_NAME)
                        .allowMainThreadQueries()
                        .build();
                showButton();
//                getSdCardPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabasePath(AppDatabase.DB_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @Override
    public void gotoNextActivity() {
/*        if (RAS_Constants.SMART_PHONE && !RAS_Constants.SD_CARD_Content) {
        }*/
        if (RAS_Constants.SMART_PHONE && !RAS_Constants.SD_CARD_Content) {
            splashPresenter.pushData();
            RAS_Constants.ext_path = RASApplication.pradigiPath + "/.RAS/English/";
            dismissProgressDialog();
            showBottomFragment();
        } else {
            dismissProgressDialog();
            startActivity(new Intent(context, MenuActivity.class));
        }
    }

    @Override
    public void showBottomFragment() {
        fragmentBottomOpenFlg = true;
        firstPause = false;
        BottomStudentsFragment bottomStudentsFragment = new BottomStudentsFragment();
        bottomStudentsFragment.show(getSupportFragmentManager(), BottomStudentsFragment.class.getSimpleName());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent objEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyUp(keyCode, objEvent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
           /* if (!bgMusic.isPlaying()) {
                bgMusic = MediaPlayer.create(this, R.raw.bg_sound);
                bgMusic.setLooping(true);
                bgMusic.start();
            }*/
        } catch (Exception e) {
        }
        try {
            /*if (bgMusic.equals(null)) {
                bgMusic = MediaPlayer.create(this, R.raw.bg_sound);
                bgMusic.setLooping(true);
                bgMusic.start();
            }*/
        } catch (Exception e) {
        }
        EventBus.getDefault().post("reload");
    }


    @Override
    public void copyingExisting() {
    }

    @Override
    public void successCopyingExisting(String path) {
    }

    @Override
    public void failedCopyingExisting() {
    }


}