package com.pratham.readandspeak.admin_panel.group_selection.fragment_child_attendance;

import android.view.View;

import com.pratham.readandspeak.domain.Student;

public interface ContractChildAttendance {
    interface attendanceView {
        void childItemClicked(Student student, int position);

        void moveToDashboardOnChildClick(Student student, int position, View v);
    }
}
