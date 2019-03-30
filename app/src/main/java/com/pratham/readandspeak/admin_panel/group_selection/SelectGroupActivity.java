package com.pratham.readandspeak.admin_panel.group_selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.pratham.readandspeak.RAS_Utility;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.admin_panel.group_selection.fragment_select_group.FragmentSelectGroup;
import com.pratham.readandspeak.custom.SelectAgeGroupDialog;
import com.pratham.readandspeak.ui.bottom_fragment.add_student.MenuActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

public class SelectGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final SelectAgeGroupDialog selectAgeGroupDialog = new SelectAgeGroupDialog(this);
        selectAgeGroupDialog.iv_age_3_to_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAgeGroupDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putBoolean(RAS_Constants.GROUP_AGE_BELOW_7, true);
                RAS_Utility.showFragment(SelectGroupActivity.this, new FragmentSelectGroup(), R.id.frame_group,
                        bundle, FragmentSelectGroup.class.getSimpleName());
            }
        });
        selectAgeGroupDialog.iv_age_8_to_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAgeGroupDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putBoolean(RAS_Constants.GROUP_AGE_ABOVE_7, true);
                RAS_Utility.showFragment(SelectGroupActivity.this, new FragmentSelectGroup(), R.id.frame_group,
                        bundle, FragmentSelectGroup.class.getSimpleName());
            }
        });

        selectAgeGroupDialog.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAgeGroupDialog.dismiss();
                finish();
                startActivity(new Intent(SelectGroupActivity.this, MenuActivity.class));

            }
        });
        selectAgeGroupDialog.setCancelable(false);
        selectAgeGroupDialog.show();
    }
    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

}
