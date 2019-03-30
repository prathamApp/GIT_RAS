package com.pratham.readandspeak.ui.bottom_fragment.add_student;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.pratham.readandspeak.R;
import com.pratham.readandspeak.admin_panel.AdminControlsActivity;
import com.pratham.readandspeak.admin_panel.group_selection.SelectGroupActivity;
import com.pratham.readandspeak.ui.qr_scan.QRScanActivity;
import com.pratham.readandspeak.utilities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @BindView(R.id.btn_qr)
    ImageButton btn_qr;
    @BindView(R.id.btn_grp)
    ImageButton btn_grp;
    @BindView(R.id.btn_admin)
    ImageButton btn_admin;
    @BindView(R.id.rl_admin)
    RelativeLayout rl_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @OnClick(R.id.btn_qr)
    public void gotoQRActivity() {
        ButtonClickSound.start();
        startActivity(new Intent(this, QRScanActivity.class));

    }

    @OnClick(R.id.btn_grp)
    public void gotoGroupLogin() {
        ButtonClickSound.start();
        startActivity(new Intent(this, SelectGroupActivity.class));

    }

    @OnClick({R.id.btn_admin, R.id.rl_admin})
    public void goto_btn_admin() {
        ButtonClickSound.start();
        startActivity(new Intent(this, AdminControlsActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        showExitDialog();
    }

   /* private void showExitDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCanceledOnTouchOutside(false);
        Button exit_btn = dialog.findViewById(R.id.dia_btn_exit);
        Button restart_btn = dialog.findViewById(R.id.dia_btn_restart);
        dialog.show();
        restart_btn.setVisibility(View.GONE);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
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
    }
*/
}