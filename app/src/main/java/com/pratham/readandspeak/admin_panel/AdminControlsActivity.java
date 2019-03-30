package com.pratham.readandspeak.admin_panel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.admin_panel.fragment_admin_panel.AdminPanelFragment;
import com.pratham.readandspeak.interfaces.DataPushListener;
import com.pratham.readandspeak.ui.bottom_fragment.add_student.MenuActivity;


public class AdminControlsActivity extends AppCompatActivity implements DataPushListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controls);
        RAS_Utility.showFragment(this, new AdminPanelFragment(), R.id.frame_attendance,
                null, AdminPanelFragment.class.getSimpleName());
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }
    }


    @Override
    public void onResponseGet() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        RAS_Utility.showFragment(this, new AdminPanelFragment(), R.id.frame_attendance,
                null, AdminPanelFragment.class.getSimpleName());

    }
}
