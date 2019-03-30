package com.pratham.readandspeak.ui.bottom_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.database.AppDatabase;
import com.pratham.readandspeak.database.BackupDatabase;
import com.pratham.readandspeak.domain.Attendance;
import com.pratham.readandspeak.domain.Session;
import com.pratham.readandspeak.domain.Student;
import com.pratham.readandspeak.interfaces.SplashInterface;
import com.pratham.readandspeak.ui.bottom_fragment.add_student.AddStudentFragment;
//import com.pratham.readandspeak.ui.main_menu.ChooseLevelActivity;
import com.pratham.readandspeak.ui.splash_activity.SplashActivity;
import com.pratham.readandspeak.utilities.RAS_Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pratham.readandspeak.ui.splash_activity.SplashActivity.bgMusic;


public class BottomStudentsFragment extends BottomSheetDialogFragment implements StudentClickListener, SplashInterface {


    @BindView(R.id.students_recyclerView)
    RecyclerView rl_students;
    @BindView(R.id.add_student)
    Button add_student;

    private ArrayList avatars = new ArrayList();
    private List<Student> studentDBList, studentList;
    StudentsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_list_fragment, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        studentList = new ArrayList<>();

        adapter = new StudentsAdapter(getActivity(), this, studentList, avatars);

       /* RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rl_students.setLayoutManager(mLayoutManager);
        rl_students.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(30), true));
        rl_students.setItemAnimator(new DefaultItemAnimator());
        rl_students.setAdapter(adapter);*/

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        rl_students.setLayoutManager(mLayoutManager);
        rl_students.setAdapter(adapter);
        showStudents();
    }

    private void setStudentsToRecycler() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        SplashActivity.fragmentBottomPauseFlg = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            getActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showStudents() {
        try {

            new AsyncTask<Void, Integer, Void>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... voids) {

                    studentList.clear();

                    studentDBList = SplashActivity.appDatabase.getStudentDao().getAllStudents();
                    if (studentDBList != null) {
                        for (int i = 0; i < studentDBList.size(); i++) {
                            Student studentAvatar = new Student();
                            studentAvatar.setStudentID(studentDBList.get(i).getStudentID());
                            studentAvatar.setFullName(studentDBList.get(i).getFullName());
                            studentAvatar.setAvatarName(studentDBList.get(i).getAvatarName());
                            studentList.add(studentAvatar);
                        }
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    setStudentsToRecycler();
                    BackupDatabase.backup(getActivity());
                }

            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.add_student)
    public void addStudent() {
        SplashActivity.fragmentAddStudentOpenFlg = true;
        AddStudentFragment addStudentFragment = AddStudentFragment.newInstance(this);
        addStudentFragment.show(getActivity().getSupportFragmentManager(), AddStudentFragment.class.getSimpleName());

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("BottomSheetCancel", "onCancel: aaaaaaaaaaaaaaaaaaaa");
    }

    @Override
    public void onResume() {
        super.onResume();
        SplashActivity.fragmentBottomPauseFlg = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void messageReceived(String msg) {
        if (msg.equalsIgnoreCase("reload"))
            showStudents();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onStudentClick(final String studentName, final String studentId) {

        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String currentSession = "" + UUID.randomUUID().toString();
                    RAS_Constants.currentSession = currentSession;
                    AppDatabase.getDatabaseInstance(getActivity()).getStatusDao().updateValue("CurrentSession", "" + currentSession);

                    Attendance attendance = new Attendance();
                    attendance.setSessionID(currentSession);
                    attendance.setStudentID(studentId);
                    attendance.setDate(RASApplication.getCurrentDateTime());
                    attendance.setGroupID("PS");
                    attendance.setSentFlag(0);

                    RAS_Constants.currentStudentID = studentId;
                    RAS_Constants.currentStudentName = studentName;
                    AppDatabase.getDatabaseInstance(getActivity()).getAttendanceDao().insert(attendance);
                    RAS_Constants.currentSession = currentSession;

                    Session startSesion = new Session();
                    startSesion.setSessionID("" + currentSession);
                    startSesion.setFromDate("" + RASApplication.getCurrentDateTime());
                    startSesion.setToDate("NA");
                    startSesion.setSentFlag(0);
                    AppDatabase.getDatabaseInstance(getActivity()).getSessionDao().insert(startSesion);

                    try {
                        if (bgMusic != null && bgMusic.isPlaying()) {
                            bgMusic.stop();
                            bgMusic.setLooping(false);
                            bgMusic.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
//                startActivity(new Intent(getActivity(), ChooseLevelActivity.class));
                getActivity().finish();
            }
        }.execute();
    }

    @Override
    public void onChildAdded() {
        showStudents();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + avatar) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + avatar) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


}
